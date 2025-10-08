/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import static com.moviereviews.util.Consts.*;

import com.moviereviews.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("SELECT v FROM Rating v WHERE v.profile.id = :" + PROFILE_ID_PARAM + " AND v.movie.id = :" + MOVIE_ID_PARAM)
    Rating findRatingByProfileIdAndMovieId(@Param(PROFILE_ID_PARAM) int profileId, @Param(MOVIE_ID_PARAM) int movieId);

    @Query("SELECT m.totalRating FROM Movie m WHERE m.id = :" + MOVIE_ID_PARAM)
    int findRatingByMovieId(@Param(MOVIE_ID_PARAM) int movieId);
}
