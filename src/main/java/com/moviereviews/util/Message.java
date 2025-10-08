/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.util;

import static com.moviereviews.util.Consts.*;

import com.moviereviews.entity.AppUser;
import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Review;

public class Message {

    public static Movie setMovieNotFound() {
        Movie movie = new Movie();

        movie.setId(0);
        movie.setTitle(MSG_MOVIE_NOT_FOUND);
        movie.setReleaseYear(0);

        return movie;
    }

    public static String setSuccessOrFail(boolean success) {
        String response = EMPTY_STRING;

        if (success == true) {
            response = MSG_SUCCESS;
        } else {
            response = MSG_FAIL;
        }

        return response;
    }

    public static Review setReviewFail() {
        Review review = new Review();

        review.setId(0);
        review.setComment(MSG_FAIL);

        return review;
    }

    public static AppUser setUserFail() {
        AppUser appUser = new AppUser();

        appUser.setId(0);
        appUser.setUserName(MSG_FAIL);

        return appUser;
    }
}
