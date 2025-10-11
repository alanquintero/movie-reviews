/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.mapper;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for converting {@link Movie} entities to various DTOs.
 */
public class MovieMapper {

    public static MovieSummaryDto toMovieSummaryDto(final Movie movie) {
        return new MovieSummaryDto(
                movie.getId(),
                movie.getTitle(),
                movie.getPosterLink(),
                movie.getReleaseYear(),
                movie.getImdbRating(),
                movie.getNumberOfVotes()
        );
    }

    public static MovieDetailsDto toMovieDetailsDto(final Movie movie) {
        final Set<String> cast = movie.getCast().stream().map(CastMember::getCastMemberName).collect(Collectors.toSet());
        final Set<String> genre = movie.getGenres().stream().map(Genre::getGenre).collect(Collectors.toSet());

        return new MovieDetailsDto(movie.getId(), movie.getTitle(), movie.getPosterLink(),
                movie.getReleaseYear(), movie.getImdbRating(), movie.getNumberOfVotes(),
                movie.getOriginalTitle(), movie.getOverview(), movie.getCertificate(), movie.getRunTime(),
                movie.getMetaScore(), movie.getGross(), movie.getDirector().getDirectorName(), cast, genre
        );
    }
}
