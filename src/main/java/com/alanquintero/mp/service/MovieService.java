/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

import java.util.List;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.model.MovieModel;

/**
 * @class MovieService.java
 * @purpose Interface of Service Layer for Movie Transactions.
 */
public interface MovieService {

    /**
     * @param int
     * @return Movie
     */
    public Movie searchMovieById(int movieId);

    /**
     * @param int
     * @return Movie
     */
    public Movie searchMovieDetailsById(int movieId);

    /**
     * @param String
     * @return List_Movie
     */
    public List<Movie> searchMovieByTitle(String movieTitle);

    /**
     * @return List_Movie
     */
    public List<Movie> getPopularMovies();

    /**
     * @return List_Movie
     */
    public List<Movie> getAllMovies();

    /**
     * @param int
     */
    public void deteleMovie(int movieId);

    /**
     * @param String
     * @return List_Movie
     */
    public List<MovieModel> searchAutocompleteMovies(String movieTitle);

}
