/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviereviews.dto.MovieDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Director;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.CastMemberRepository;
import com.moviereviews.repository.DirectorRepository;
import com.moviereviews.repository.GenreRepository;
import com.moviereviews.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service responsible for initializing the database with movie data from a JSON file.
 * <p>
 * This service is marked as a Spring {@link Service} and {@link Transactional}, and it
 * loads the movie data once the application starts using the {@link PostConstruct} method.
 * <p>
 * It reads the JSON file specified by {@link #JSON_FILE}, parses it into {@link MovieDto} objects,
 * and then constructs entities for {@link Movie}, {@link Genre}, and {@link CastMember}.
 * These entities are saved into the corresponding repositories.
 * Notes:
 * - Cast member names are split into first name and last name if possible.
 * - Genres and cast members are stored as sets to avoid duplicates.
 * - Movie descriptions may be long; ensure the database columns can handle the size.
 *
 * @author Alan Quintero
 */
@Transactional
@Service
public class InitDBService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitDBService.class);

    public static final String JSON_FILE = "top1000imdb.json";

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final CastMemberRepository castMemberRepository;

    public InitDBService(final GenreRepository genreRepository, final MovieRepository movieRepository, final DirectorRepository directorRepository, final CastMemberRepository castMemberRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.castMemberRepository = castMemberRepository;
    }

    @PostConstruct
    public void init() {
        final List<MovieDto> movieDtoList = getListOfMovies();
        if (movieDtoList == null || movieDtoList.isEmpty()) {
            LOGGER.error("There are no movies in the JSON file!");
            return;
        }

        LOGGER.info("Constructing entities...");
        final Set<Genre> genres = new HashSet<>();
        final Map<String, Director> directorMap = new HashMap<>();
        final Set<CastMember> castMembers = new HashSet<>();
        for (final MovieDto movieDto : movieDtoList) {
            // ******* Director *******
            directorMap.putIfAbsent(movieDto.getDirector(), new Director(movieDto.getDirector()));

            // ******* Cast Member *******
            for (final String castMember : movieDto.getCast()) {
                castMembers.add(new CastMember(castMember));
            }

            // ******* Genre *******
            for (final String genre : movieDto.getGenres()) {
                genres.add(new Genre(genre));
            }
        }
        LOGGER.info("Inserting Director, Cast Member, and Genre data into the DB");
        LOGGER.info("Directors: {}", directorMap.size());
        LOGGER.info("Cast members: {}", castMembers.size());
        LOGGER.info("Genres: {}", genres.size());
        directorRepository.saveAll(directorMap.values());
        castMemberRepository.saveAll(castMembers);
        genreRepository.saveAll(genres);

        // Re-fetch persisted entities to ensure we’re using managed instances
        final List<Director> persistedDirectors = directorRepository.findAll();
        final List<CastMember> persistedCast = castMemberRepository.findAll();
        final List<Genre> persistedGenres = genreRepository.findAll();
        // Convert lists to lookup maps for fast access
        final Map<String, Director> persistedDirectorMap = persistedDirectors.stream()
                .collect(Collectors.toMap(Director::getDirectorName, d -> d));
        final Map<String, CastMember> persistedCastMap = persistedCast.stream()
                .collect(Collectors.toMap(CastMember::getCastMemberName, c -> c));
        final Map<String, Genre> persistedGenreMap = persistedGenres.stream()
                .collect(Collectors.toMap(Genre::getGenre, g -> g));

        final List<Movie> movies = new ArrayList<>();

        for (final MovieDto movieDto : movieDtoList) {
            final Movie movie = new Movie();
            movie.setTitle(movieDto.getTitle());
            movie.setReleaseYear(movieDto.getReleaseYear());
            movie.setOverview(movieDto.getOverview());
            movie.setPosterLink(movieDto.getPosterLink());
            movie.setCertificate(movieDto.getCertificate());
            movie.setRunTime(movieDto.getRunTime());
            movie.setImdbRating(movieDto.getImdbRating());
            movie.setMetaScore(movieDto.getMetaScore());
            movie.setNumberOfVotes(movieDto.getNumberOfVotes());
            movie.setGross(movieDto.getGross());

            movie.setDirector(persistedDirectorMap.get(movieDto.getDirector()));

            final Set<CastMember> movieCast = movieDto.getCast().stream()
                    .map(persistedCastMap::get)
                    .collect(Collectors.toSet());
            movie.setCast(movieCast);

            final Set<Genre> movieGenres = movieDto.getGenres().stream()
                    .map(persistedGenreMap::get)
                    .collect(Collectors.toSet());
            movie.setGenres(movieGenres);

            movies.add(movie);
        }

        LOGGER.info("Inserting Movie data into the DB");
        LOGGER.info("Movies: {}", movies.size());
        movieRepository.saveAll(movies);
    }

    List<MovieDto> getListOfMovies() {
        // Load file from resources
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(JSON_FILE);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        if (is == null) {
            LOGGER.error("File not found: {}", JSON_FILE);
            return null;
        }

        // Parse the JSON file
        List<MovieDto> movieDtoList = null;
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            movieDtoList = objectMapper.readValue(is, new TypeReference<>() {
            });
            LOGGER.info("Movies in the JSON file: {}", movieDtoList.size());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return movieDtoList;
    }
}
