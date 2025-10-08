/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import static com.moviereviews.util.Consts.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Movie getMovieByReviews(Review review);

    @Query("SELECT m FROM Movie m WHERE lower(m.title) LIKE lower(:" + MOVIE_TITLE_PARAM + ")")
    List<Movie> findAllMovies(@Param(MOVIE_TITLE_PARAM) String movieTitle);

    @Query("SELECT m FROM Movie m")
    List<Movie> findPopularMovies(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.id = :" + MOVIE_ID_PARAM)
    Movie getMovieById(@Param(MOVIE_ID_PARAM) int movieId);

    @Query("SELECT m FROM Movie m WHERE lower(m.title) LIKE lower(:" + MOVIE_TITLE_PARAM + ")")
    List<Movie> getSearchMovies(@Param(MOVIE_TITLE_PARAM) String movieTitle, Pageable topSix);

    @Query("SELECT m FROM Movie m")
    Page<Movie> findMostVotedMovies(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE lower(m.title) LIKE lower(:" + MOVIE_TITLE_PARAM + ") " + "AND m.releaseYear = :"
            + MOVIE_YEAR_PARAM)
    Movie findMovieByTitleAndYear(@Param(MOVIE_TITLE_PARAM) String movieTitle,
                                  @Param(MOVIE_YEAR_PARAM) int movieYear);

    @Query("SELECT m FROM Movie m WHERE m.id != :" + MOVIE_ID_PARAM + " AND lower(m.title) LIKE lower(:"
            + MOVIE_TITLE_PARAM + ") AND m.releaseYear = :" + MOVIE_YEAR_PARAM)
    Movie findMovieByValues(@Param(MOVIE_ID_PARAM) int movieId, @Param(MOVIE_TITLE_PARAM) String movieTitle,
                            @Param(MOVIE_YEAR_PARAM) int movieYear);
}
