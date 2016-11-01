/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;
import com.alanquintero.mp.util.Message;

/**
 * @class MovieServiceImpl.java
 * @purpose Implementation of MovieService interface.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    /**
     * Search Movie By Movie Id
     * 
     * @param int
     * @return Movie
     */
    @Override
    @Transactional
    public Movie searchMovieById(int movieId) {
        Movie movie = movieDao.searchMovieById(movieId);
        if (movie == null) {
            movie = Message.setMovieNotFound();
        }
        return movie;
    }

    /**
     * Search Movie details by Movie Id
     * 
     * @param int
     * @return Movie
     */
    @Override
    public Movie searchMovieDetailsById(int movieId) {
        Movie movie = searchMovieById(movieId);
        if ((movie != null) && ((movie.getId() != null) && (movie.getId() > 0))) {
            List<Review> reviews = movieDao.searchReviewsByMovie(movie);
            movie.setReviews(reviews);
        } else {
            movie = Message.setMovieNotFound();
        }
        return movie;
    }

    /**
     * Search a list of Movies by Movie Title provided
     * 
     * @param String
     * @return List_Movie
     */
    @Override
    @Transactional
    public List<Movie> searchMovieByTitle(String movieTitle) {
        List<Movie> movies = movieDao.searchMovieByTitle(PERCENT + movieTitle + PERCENT);
        if ((movies == null) || (movies.isEmpty())) {
            Movie movie = Message.setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Search a list of Top popular Movies
     * 
     * @return List_Movie
     */
    @Override
    @Transactional
    public List<Movie> getPopularMovies() {
        List<Movie> movies = movieDao.getPopularMovies();
        if ((movies == null) || (movies.isEmpty())) {
            Movie movie = Message.setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Search all Movies
     * 
     * @return List_Movie
     */
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDao.getAllMovies();
        if ((movies == null) || (movies.isEmpty())) {
            Movie movie = Message.setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Delete a Movie by Movie Id
     * 
     * @param int
     * @return String
     */
    @Override
    @PreAuthorize(HAS_ROLE_ADMIN)
    public String deteleMovie(int movieId) {
        boolean success = false;
        if (movieDao.searchMovieById(movieId) != null) {
            success = movieDao.deteleMovie(movieId);
        }
        return Message.setSuccessOrFail(success);
    }

    /**
     * Search a list of Movies by Movie Title
     * 
     * @param String
     * @return List_Movie
     */
    @Override
    public List<MovieModel> searchAutocompleteMovies(String movieTitle) {
        List<Movie> movies = movieDao.searchAutocompleteMovies(PERCENT + movieTitle + PERCENT);
        List<MovieModel> moviesModel = new ArrayList<MovieModel>();
        if ((movies != null) && (!movies.isEmpty())) {
            for (Movie m : movies) {
                moviesModel.add(
                        new MovieModel(m.getId(), m.getTitle() + PARENTHESIS_OPEN + m.getYear() + PARENTHESIS_CLOSE));
            }
        } 
        return moviesModel;
    }

}
