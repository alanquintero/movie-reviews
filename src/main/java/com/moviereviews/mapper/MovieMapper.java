/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.mapper;

import com.moviereviews.dto.TopMovieDto;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;

import java.util.stream.Collectors;

/**
 * Utility class for converting {@link Movie} entities to various DTOs.
 */
public class MovieMapper {

    public static TopMovieDto toTopMovieDTO(final Movie movie) {
        return new TopMovieDto(
                movie.getTitle(),
                movie.getPosterLink(),
                movie.getReleaseYear(),
                movie.getImdbRating(),
                movie.getNumberOfVotes()
        );
    }
}
