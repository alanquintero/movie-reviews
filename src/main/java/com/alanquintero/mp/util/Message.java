/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.util;

import static com.alanquintero.mp.util.Consts.*;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;

/**
 * @class Message.java
 * @purpose Class used to set response messages.
 */
public class Message {

    /**
     * Set Movie not Found Message
     * 
     * @return Movie
     */
    public static Movie setMovieNotFound() {
        Movie movie = new Movie();

        movie.setId(0);
        movie.setTitle(MSG_MOVIE_NOT_FOUND);
        movie.setYear(0);

        return movie;
    }

    /**
     * Set Successful or Fail Message
     * 
     * @param boolean
     * @return String
     */
    public static String setSuccessOrFail(boolean success) {
        String response = EMPTY_STRING;

        if (success == true) {
            response = MSG_SUCCESS;
        } else {
            response = MSG_FAIL;
        }

        return response;
    }

    /**
     * Set Message Something went wrong in Review
     * 
     * @return Review
     */
    public static Review setReviewFail() {
        Review review = new Review();

        review.setId(0);
        review.setComment(MSG_FAIL);

        return review;
    }

    /**
     * Set Message Something went wrong in User
     * 
     * @return User
     */
    public static User setUserFail() {
        User user = new User();

        user.setId(0);
        user.setName(MSG_FAIL);

        return user;
    }

}
