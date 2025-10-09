/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviereviews.dto.MovieDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.CastMemberRepository;
import com.moviereviews.repository.GenreRepository;
import com.moviereviews.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static final String JSON_FILE = "movies-2020s.json";

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final CastMemberRepository castMemberRepository;

    public InitDBService(final GenreRepository genreRepository, final MovieRepository movieRepository, final CastMemberRepository castMemberRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
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
        final Set<CastMember> castMembers = new HashSet<>();
        final List<Movie> movies = new ArrayList<>();
        for (final MovieDto movieDto : movieDtoList) {
            // ******* Cast Member *******
            for (final String castMember : movieDto.getCast()) {
                // Limit name to two parts, firstname (everything before first space) and lastname the rest of the string (everything after first space)
                final String[] parts = castMember.split(" ", 2); // Split by space, limit to 2 parts
                if (parts.length == 1) {
                    // only has firstName
                    castMembers.add(new CastMember(parts[0]));
                } else if (parts.length > 1) {
                    // has firstName and lastName
                    castMembers.add(new CastMember(parts[0], parts[1]));
                }
            }

            // ******* Genre *******
            for (final String genre : movieDto.getGenres()) {
                genres.add(new Genre(genre));
            }

            // ******* Movie *******
            final Movie movie = new Movie();
            movie.setTitle(movieDto.getTitle());
            movie.setReleaseYear(movieDto.getReleaseYear());
            movie.setHref(movieDto.getHref());
            movie.setExtract(movieDto.getExtract());
            movie.setThumbnail(movieDto.getThumbnail());
            movie.setThumbnailWidth(movieDto.getThumbnailWidth());
            movie.setThumbnailHeight(movieDto.getThumbnailHeight());
            movies.add(movie);
        }

        LOGGER.info("Inserting data into the DB");
        LOGGER.info("Cast members: {}", castMembers.size());
        LOGGER.info("Genres: {}", genres.size());
        LOGGER.info("Movies: {}", movies.size());
        castMemberRepository.saveAll(castMembers);
        genreRepository.saveAll(genres);
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
