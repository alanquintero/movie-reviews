/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.TopMovieDto;
import com.moviereviews.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<TopMovieDto> getTopRatedMovies() {
        final List<TopMovieDto> movies = movieService.getTopRatedMovies();
        LOGGER.info("Movies: {}", movies);
        return movies;
    }
}
