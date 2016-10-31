/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.ReviewRepository;

/**
 * @class MovieDaoImpl.java
 * @purpose Implementation of MovieDao Interface.
 */
@Repository
public class MovieDaoImpl implements MovieDao {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Search a Movie by Movie Id
     * 
     * @param int
     * @return Movie
     */
    @Override
    public Movie searchMovieById(int movieId) {
        Movie movie = null;
        try {
            movie = movieRepository.findOne(movieId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    /**
     * Search a list of Movies that match with Movie Title provided
     * 
     * @param String
     * @return List_Movie
     */
    @Override
    public List<Movie> searchMovieByTitle(String movieTitle) {
        List<Movie> movies = null;
        try {
            movies = movieRepository.findAllMovies(movieTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Search Top 10 Popular Movies by Rating
     * 
     * @return List_Movie
     */
    @Override
    public List<Movie> getPopularMovies() {
        List<Movie> movies = null;
        try {
            movies = movieRepository.findPopularMovies(new PageRequest(0, 10, Direction.DESC, RATING_FIELD));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Search all Movies existent
     * 
     * @return List_Movie
     */
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = null;
        try {
            movies = movieRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Delete a Movie by Movie Id
     * 
     * @param int
     * @return boolean
     */
    @Override
    public boolean deteleMovie(int movieId) {
        boolean success = false;
        try {
            movieRepository.delete(movieId);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Search a list of Movies that match with text provided by auto complete
     * User input search
     * 
     * @param String
     * @return List_Movie
     */
    @Override
    public List<Movie> searchAutocompleteMovies(String movieTitle) {
        List<Movie> movies = null;
        Pageable topSix = new PageRequest(0, 6);
        try {
            movies = movieRepository.getSearchMovies(movieTitle, topSix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Search a Movie by Review
     * 
     * @param Review
     * @return Movie
     */
    @Override
    public Movie searchMovieByReview(Review review) {
        Movie movie = null;
        try {
            movie = movieRepository.getMovieByReviews(review);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    /**
     * Search all Reviews of a Movie
     * 
     * @param Movie
     * @return List_Review
     */
    @Override
    public List<Review> searchReviewsByMovie(Movie movie) {
        List<Review> reviews = null;
        try {
            reviews = reviewRepository.findReviewsByMovie(movie,
                    new PageRequest(0, 15, Direction.DESC, PUBLISHED_DATE_FIELD));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

}
