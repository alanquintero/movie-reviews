/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.util;

import static com.alanquintero.mp.util.Consts.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.User;

/**
 * @class Validation.java
 * @purpose Class used to validate inputs.
 */
public class Validation {

    /**
     * Validate that String is not null or empty
     * 
     * @param String
     * @return boolean
     */
    public static boolean isValidString(String word) {
        boolean isValid = false;
        if ((word != null) && (!word.equals(EMPTY_STRING))) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Validate length of String
     * 
     * @param String
     * @param int
     * @return boolean
     */
    public static boolean validateWordLen(String word, int len) {
        boolean isValid = false;
        if (word.length() >= len) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Validate that Email match with a Pattern
     * 
     * @param String
     * @return boolean
     */
    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Validate that Movie List is not null or empty
     * 
     * @param List_Movie
     * @return List_Movie
     */
    public static List<Movie> validateMovieList(List<Movie> movies) {
        if ((movies == null) || (movies.isEmpty())) {
            movies = new ArrayList<Movie>();
            Movie movie = Message.setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Validate that Movie List is not null or empty
     * 
     * @param List_Movie
     * @return List_Movie
     */
    public static List<User> validateUserList(List<User> users) {
        if ((users == null) || (users.isEmpty())) {
            users = new ArrayList<User>();
            User user = Message.setUserFail();
            users = new ArrayList<User>();
            users.add(user);
        }
        return users;
    }

    /**
     * Validate if URL is valid or not
     * 
     * @param String
     * @return boolean
     */
    public static boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Validate if Code is valid or not
     * 
     * @param String
     * @return boolean
     */
    public static boolean isValidCode(String code) {
        boolean isValid = false;
        if ((!code.equals(EMPTY_STRING)) && (!code.equals(ZERO_STRING))) {
            isValid = true;
        }
        return isValid;
    }

}
