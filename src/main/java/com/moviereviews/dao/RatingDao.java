/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dao;

import static com.moviereviews.util.Consts.LOG_ERROR_DB;

import com.moviereviews.entity.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Profile;
import com.moviereviews.repository.RatingRepository;

@Repository
public class RatingDao {

    private static final Logger logger = LoggerFactory.getLogger(RatingDao.class);

    private final RatingRepository ratingRepository;

    public RatingDao(final RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating searchRating(Profile profile, Movie movie) {
        Rating rating = null;

        try {
            rating = ratingRepository.findRatingByProfileIdAndMovieId(profile.getId(), movie.getId());
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return rating;
    }

    public boolean saveOrUpdateRating(Rating rating) {
        boolean success = false;

        try {
            ratingRepository.save(rating);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public int searchRatingByMovie(Movie movie) {
        int vote = 0;

        try {
            vote = ratingRepository.findRatingByMovieId(movie.getId());
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return vote;
    }
}
