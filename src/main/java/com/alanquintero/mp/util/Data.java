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

import java.util.List;
import java.util.ListIterator;

import org.springframework.util.Base64Utils;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;

/**
 * @class EncodeData.java
 * @purpose Class used to Encode/Decode URL parameters.
 */
public class Data {

    private static ListIterator<Movie> movieIterator;
    private static ListIterator<User> userIterator;
    private static ListIterator<Review> reviewIterator;

    /**
     * Encode Object
     * 
     * @param Object
     * @return String
     */
    public static String encode(Object obj) {
        String encodeData = EMPTY_STRING;

        if (Validation.isValidCode(obj.toString())) {
            encodeData = Base64Utils.encodeToString((obj.toString()).getBytes());
        }

        return encodeData;
    }

    /**
     * Decode Object
     * 
     * @param Object
     * @return int
     */
    public static int decode(Object obj) {
        int decode = 0;

        if (Validation.isValidString(obj.toString())) {
            String str = new String(Base64Utils.decodeFromString(obj.toString()));
            try {
                decode = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                decode = 0;
            }
        }

        return decode;
    }

    /**
     * Encode a List of Movies
     * 
     * @param List_Movie
     */
    public static void encodeMovieList(List<Movie> movies) {
        movieIterator = movies.listIterator();

        while (movieIterator.hasNext()) {
            Movie movie = movieIterator.next();
            if (movie.getId() != 0) {
                movie.setCode(Data.encode(movie.getId()));
            } else {
                movie.setCode(EMPTY_STRING);
            }
            movieIterator.set(movie);
        }
    }

    /**
     * Encode a List of Users
     * 
     * @param List_User
     */
    public static void encodeUserList(List<User> users) {
        userIterator = users.listIterator();

        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.getId() != 0) {
                user.setCode(Data.encode(user.getId()));
            } else {
                user.setCode(EMPTY_STRING);
            }
            userIterator.set(user);
        }
    }

    /**
     * Encode a List of Reviews
     * 
     * @param List_Review
     */
    public static void encodeReviewList(List<Review> reviews) {
        reviewIterator = reviews.listIterator();

        while (reviewIterator.hasNext()) {
            Review review = reviewIterator.next();
            if (review.getId() != 0) {
                review.setCode(Data.encode(review.getId()));
            } else {
                review.setCode(EMPTY_STRING);
            }
            reviewIterator.set(review);
        }
    }

}
