/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao;

import java.util.List;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;

/**
 * @class MovieDao.java
 * @purpose Interface of DAO Layer for Movie Transactions.
 */
public interface MovieDao {

    /**
     * @param int
     * @return Movie
     */
    public Movie searchMovieById(int id);

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
     * @return boolean
     */
    public boolean deteleMovie(int movieId);

    /**
     * @param String
     * @return List_Movie
     */
    public List<Movie> searchAutocompleteMovies(String movieTitle);

    /**
     * @param Review
     * @return Movie
     */
    public Movie searchMovieByReview(Review review);

    /**
     * @param Movie
     * @return List_Review
     */
    public List<Review> searchReviewsByMovie(Movie movie);

    /**
     * @return List_Movie
     */
    public List<Movie> getMostVotedMovies();

}
