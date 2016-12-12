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

import org.apache.log4j.Logger;
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
import com.alanquintero.mp.util.Data;
import com.alanquintero.mp.util.Format;
import com.alanquintero.mp.util.Validation;

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

    private static final Logger logger = Logger.getLogger(MovieDaoImpl.class);

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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
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
            logger.error(LOG_ERROR_DB, e);
        }

        return reviews;
    }

    /**
     * Search 15 Most Voted Movies by Number of Votes
     * 
     * @return List_Movie
     */
    @Override
    public List<Movie> getMostVotedMovies() {
        List<Movie> movies = null;

        try {
            movies = movieRepository.findMostVotedMovies(new PageRequest(0, 15, Direction.DESC, VOTE_FIELD));
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movies;
    }

    /**
     * Update Movie information
     * 
     * @param Movie
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateMovie(Movie movie) {
        boolean success = false;

        try {
            movieRepository.save(movie);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    /**
     * Check If Movie Exists
     * 
     * @param Movie
     * @return boolean
     */
    @Override
    public boolean checkIfMovieExists(Movie movie) {
        boolean exists = false;

        try {
            if (Validation.isValidString(movie.getCode())) {
                if (movieRepository.findMovieByValues(Data.decode(movie.getCode()),
                        Format.removeBlanks(movie.getTitle()), movie.getYear()) != null) {
                    exists = true;
                }
            } else {
                if (movieRepository.findMovieByTitleAndYear(Format.removeBlanks(movie.getTitle()),
                        movie.getYear()) != null) {
                    exists = true;
                }
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return exists;
    }

}
