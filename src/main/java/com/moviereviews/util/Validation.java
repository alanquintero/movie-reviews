/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.util;

import static com.moviereviews.util.Consts.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.moviereviews.entity.AppUser;
import com.moviereviews.entity.Movie;

public class Validation {

    public static boolean isValidString(String word) {
        boolean isValid = false;

        if ((word != null) && (!word.equals(EMPTY_STRING))) {
            isValid = true;
        }

        return isValid;
    }

    public static boolean validateWordLen(String word, int len) {
        boolean isValid = false;

        if (word.length() >= len) {
            isValid = true;
        }

        return isValid;
    }

    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static List<Movie> validateMovieList(List<Movie> movies) {
        if ((movies == null) || (movies.isEmpty())) {
            movies = new ArrayList<Movie>();
            Movie movie = Message.setMovieNotFound();
            movies.add(movie);
        }

        return movies;
    }

    public static List<AppUser> validateUserList(List<AppUser> appUsers) {
        if ((appUsers == null) || (appUsers.isEmpty())) {
            appUsers = new ArrayList<AppUser>();
            AppUser appUser = Message.setUserFail();
            appUsers = new ArrayList<AppUser>();
            appUsers.add(appUser);
        }

        return appUsers;
    }

    public static boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static boolean isValidCode(String code) {
        boolean isValid = false;

        if ((!code.equals(EMPTY_STRING)) && (!code.equals(ZERO_STRING))) {
            isValid = true;
        }

        return isValid;
    }
}
