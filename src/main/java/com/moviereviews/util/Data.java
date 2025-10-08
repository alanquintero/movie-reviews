/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.util;

import static com.moviereviews.util.Consts.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.ListIterator;

import com.moviereviews.entity.AppUser;
import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Review;

public class Data {

    private static ListIterator<Movie> movieIterator;
    private static ListIterator<AppUser> userIterator;
    private static ListIterator<Review> reviewIterator;

    public static String encode(Object obj) {
        String encodeData = EMPTY_STRING;

        if (Validation.isValidCode(obj.toString())) {
            encodeData = Base64.getEncoder().encodeToString(obj.toString().getBytes());
        }

        return encodeData;
    }

    public static int decode(Object obj) {
        int decode = 0;

        if (Validation.isValidString(obj.toString())) {
            String str = new String(Base64.getDecoder().decode(obj.toString()), StandardCharsets.UTF_8);
            try {
                decode = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                decode = 0;
            }
        }

        return decode;
    }

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

    public static void encodeUserList(List<AppUser> appUsers) {
        userIterator = appUsers.listIterator();

        while (userIterator.hasNext()) {
            AppUser appUser = userIterator.next();
            if (appUser.getId() != 0) {
                appUser.setCode(Data.encode(appUser.getId()));
            } else {
                appUser.setCode(EMPTY_STRING);
            }
            userIterator.set(appUser);
        }
    }

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
