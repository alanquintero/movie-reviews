/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.model;

public class MovieModel extends Code {

    private String movieName;

    public MovieModel(String code, String movieName) {
        super();
        super.setCode(code);
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
