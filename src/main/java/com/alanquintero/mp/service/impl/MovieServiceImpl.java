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

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;

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
    public Movie searchMovieById(int movieId) {
        Movie movie = movieDao.searchMovieById(movieId);
        if (movie == null) {
            movie = setMovieNotFound();
        }
        return movie;
    }

    /**
     * Search Movie details by Movie Id
     * 
     * @param int
     * @return Movie
     */
    @Transactional
    public Movie searchMovieDetailsById(int movieId) {
        Movie movie = searchMovieById(movieId);
        if ((movie != null) && ((movie.getId() != null) && (movie.getId() > 0))) {
            List<Review> reviews = movieDao.searchReviewsByMovie(movie);
            movie.setReviews(reviews);
        } else {
            movie = setMovieNotFound();
        }
        return movie;
    }

    /**
     * Search a list of Movies by Movie Title provided
     * 
     * @param String
     * @return List_Movie
     */
    @Transactional
    public List<Movie> searchMovieByTitle(String movieTitle) {
        List<Movie> movies = movieDao.searchMovieByTitle(PERCENT + movieTitle + PERCENT);
        if ((movies == null) || (movies.isEmpty())) {
            Movie movie = setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Search a list of Top popular Movies
     * 
     * @return List_Movie
     */
    @Transactional
    public List<Movie> getPopularMovies() {
        List<Movie> movies = movieDao.getPopularMovies();
        if ((movies == null) || (movies.isEmpty())) {
            Movie movie = setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Search all Movies
     * 
     * @return List_Movie
     */
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDao.getAllMovies();
        if ((movies == null) || (movies.isEmpty())) {
            Movie movie = setMovieNotFound();
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Delete a Movie by Movie Id
     * 
     * @param int
     */
    @PreAuthorize(HAS_ROLE_ADMIN)
    public void deteleMovie(int movieId) {
        if (movieDao.searchMovieById(movieId) != null) {
            movieDao.deteleMovie(movieId);
        }
    }

    /**
     * Search a list of Movies by Movie Title
     * 
     * @param String
     * @return List_Movie
     */
    public List<MovieModel> searchAutocompleteMovies(String movieTitle) {
        List<Movie> movies = movieDao.searchAutocompleteMovies(PERCENT + movieTitle + PERCENT);
        List<MovieModel> moviesModel = new ArrayList<MovieModel>();
        if ((movies != null) && (!movies.isEmpty())) {
            for (Movie m : movies) {
                moviesModel.add(
                        new MovieModel(m.getId(), m.getTitle() + PARENTHESIS_OPEN + m.getYear() + PARENTHESIS_CLOSE));
            }
        } else {
            moviesModel.add(new MovieModel(0, MSG_MOVIE_NOT_FOUND));
        }
        return moviesModel;
    }

    /**
     * Set Movie not Found
     * 
     * @return Movie
     */
    public Movie setMovieNotFound() {
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle(MSG_MOVIE_NOT_FOUND);
        movie.setYear(0);
        return movie;
    }

}
