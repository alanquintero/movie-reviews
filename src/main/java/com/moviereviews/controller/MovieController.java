/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing endpoints to access Movie data.
 * Provides JSON responses.
 *
 * @author Alan Quintero
 */
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves top-rated movies based on IMDB rating.
     * Endpoint: GET /api/v1/movies/top-rated
     *
     * @return List of Movie objects as JSON.
     */
    @GetMapping("/top-rated")
    public List<MovieSummaryDto> getTopRatedMovies() {
        final List<MovieSummaryDto> movies = movieService.getTopRatedMovies();
        LOGGER.info("Movies: {}", movies);
        return movies;
    }

    /**
     * Retrieves the movie details if movie found.
     * Endpoint: GET /api/v1/movies/{id}
     *
     * @param id the movie id
     * @return the movie details if movie found
     */
    @GetMapping("/{id}")
    public MovieDetailsDto getMovieDetails(@PathVariable final long id) {
        LOGGER.info("getMovieDetails");
        final MovieDetailsDto movie = movieService.getMovieById(id);
        LOGGER.info("Movie: {}", movie);
        return movie;
    }
}
