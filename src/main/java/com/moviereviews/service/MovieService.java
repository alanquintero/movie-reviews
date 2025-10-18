/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSearchResultDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.Movie;
import com.moviereviews.mapper.MovieMapper;
import com.moviereviews.repository.MovieRepository;
import com.moviereviews.search.TrieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Movie entities.
 * Encapsulates repository access and provides methods for retrieving Movie data.
 *
 * @author Alan Quintero
 */
@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private final TrieManager trieManager;

    private final MovieRepository movieRepository;

    public MovieService(final TrieManager trieManager, final MovieRepository movieRepository) {
        this.trieManager = trieManager;
        this.movieRepository = movieRepository;
    }

    /**
     * Return the top-rated 15 movies based on IMDB raring.
     *
     * @return list of top-rated movies
     */
    public List<MovieSummaryDto> getTopRatedMovies() {
        LOGGER.info("getTopRatedMovies");
        final List<Movie> topRatedMovies = movieRepository.findTopRatedMovies(PageRequest.of(0, 15));
        return topRatedMovies.stream()
                .map(MovieMapper::toMovieSummaryDto)
                .toList();
    }

    /**
     * Returns all details of a movie.
     *
     * @param id the movie id
     * @return the movie details if movie found
     */
    public MovieDetailsDto getMovieById(final long id) {
        LOGGER.info("getMovieById");
        final Optional<Movie> movieOpt = movieRepository.findById(id);
        return movieOpt.map(MovieMapper::toMovieDetailsDto).orElse(null);
    }

    /**
     * Returns the top 5 (order by imdb rating) of movies found when searching by movie title prefix.
     *
     * @param titlePrefix the movie title prefix.
     * @return the list of movies found.
     */
    public List<MovieSearchResultDto> searchMovieByTitlePrefix(final String titlePrefix) {
        LOGGER.info("searchMovieByTitlePrefix");
        final List<Long> moviesIds = trieManager.searchMovieByTitlePrefix(titlePrefix);

        if (moviesIds.isEmpty()) {
            return new ArrayList<>();
        }
        final List<Movie> movies = movieRepository.findAllById(moviesIds);
        if (movies.isEmpty()) {
            return new ArrayList<>();
        }
        // Sort desc the movies found by IMDb rating and collects the top 5
        final List<Movie> sortedMovies = movies.stream().sorted(Comparator.comparingDouble(Movie::getImdbRating).reversed()).limit(5).toList();
        return MovieMapper.toMovieSearchResultDto(sortedMovies);
    }
}
