/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSearchResultDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        LOGGER.debug("Movie: {}", movie);
        return movie;
    }

    /**
     * Returns paginated and sorted list of all movies.
     * Example: /api/v1/movies/all?page=0&size=50&sort=imdbRating,desc
     *
     * @param pageable The Pageable with the sorting information
     * @return The Pageable with movies.
     */
    @GetMapping("/all")
    public ResponseEntity<Page<MovieSummaryDto>> getAllMovies(final Pageable pageable) {
        Page<MovieSummaryDto> movies = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    /**
     * Retrieves the movie details if movie found.
     * Endpoint: GET /api/v1/movies/search?prefix={titlePrefix}
     *
     * @param titlePrefix the movie title prefix
     * @return the movie search result if movies found
     */
    @GetMapping("/search")
    public List<MovieSearchResultDto> searchMovieByTitlePrefix(@RequestParam final String titlePrefix) {
        LOGGER.info("searchMovieByTitlePrefix: " + titlePrefix);
        final List<MovieSearchResultDto> movies = movieService.searchMovieByTitlePrefix(titlePrefix);
        LOGGER.debug("Movies: {}", movies);
        return movies;
    }
}
