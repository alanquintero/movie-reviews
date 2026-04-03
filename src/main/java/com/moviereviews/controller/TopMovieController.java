/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.TopMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies/top")
public class TopMovieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopMovieController.class);

    private final TopMovieService topMovieService;

    public TopMovieController(final TopMovieService topMovieService) {
        this.topMovieService = topMovieService;
    }

    /**
     * Retrieves top-rated movies based on IMDB rating.
     * Endpoint: GET /api/v1/top
     *
     * @return List of Movie objects as JSON.
     */
    @GetMapping
    public ResponseEntity<List<MovieSummaryDto>> getTopRatedMovies(@RequestParam(name = "n") Optional<Integer> n) {
        final List<MovieSummaryDto> movies;
        if(n.isPresent()) {
            movies = topMovieService.getTopNRatedMovies(n.get());
        } else {
            movies = topMovieService.getTopRatedMovies();
        }
        LOGGER.debug("Movies: {}", movies);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

}
