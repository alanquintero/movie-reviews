/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.Movie;
import com.moviereviews.mapper.MovieMapper;
import com.moviereviews.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing queries to Movies.
 * Encapsulates repository access and provides methods for retrieving Top Movie data.
 *
 * @author Alan Quintero
 */
@Service
public class TopMovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopMovieService.class);

    private final MovieRepository movieRepository;

    public TopMovieService(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Return the top-rated movies based on IMDB rating.
     * Page size: 15
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
     * Return the top-rated N movies based on IMDB rating.
     * Page size: 15
     *
     * @return list of top-rated movies
     */
    public List<MovieSummaryDto> getTopNRatedMovies(final int n) {
        LOGGER.info("getTopNRatedMovies");
        final List<Movie> topRatedMovies = movieRepository.findTopNRatedMovies(PageRequest.of(0, 15), n);
        return topRatedMovies.stream()
                .map(MovieMapper::toMovieSummaryDto)
                .toList();
    }
}
