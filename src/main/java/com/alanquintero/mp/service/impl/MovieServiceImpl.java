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
import com.alanquintero.mp.util.Format;
import com.alanquintero.mp.util.Message;
import com.alanquintero.mp.util.Validation;

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
        if ((movie != null) && (movie.getId() > 0)) {
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
        List<Movie> movies = new ArrayList<Movie>();
        if (Validation.isValidString(movieTitle)) {
            movies = movieDao.searchMovieByTitle(PERCENT + movieTitle + PERCENT);
        }
        movies = Validation.validateMovieList(movies);
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
        movies = Validation.validateMovieList(movies);
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
        movies = Validation.validateMovieList(movies);
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
        List<Movie> movies = new ArrayList<Movie>();
        List<MovieModel> moviesModel = new ArrayList<MovieModel>();
        if (Validation.isValidString(movieTitle)) {
            movies = movieDao.searchAutocompleteMovies(PERCENT + movieTitle + PERCENT);
        }
        if ((movies != null) && (!movies.isEmpty())) {
            for (Movie m : movies) {
                moviesModel.add(
                        new MovieModel(m.getId(), m.getTitle() + PARENTHESIS_OPEN + m.getYear() + PARENTHESIS_CLOSE));
            }
        }
        return moviesModel;
    }

    /**
     * Search a list of Most Voted Movies
     * 
     * @return List_Movie
     */
    @Override
    @Transactional
    public List<Movie> getMostVotedMovies() {
        List<Movie> movies = movieDao.getMostVotedMovies();
        movies = Validation.validateMovieList(movies);
        return movies;
    }

    /**
     * Add or Update a Movie
     * 
     * @param Movie
     * @return String
     */
    @Override
    public boolean saveOrUpdateMovie(Movie movie) {
        boolean success = false;
        if (Validation.isValidURL(movie.getImage()) && Validation.isValidURL(movie.getTrailer())
                && Validation.isValidString(movie.getSynopsis())) {
            if (movie.getId() != null && movie.getId() != 0) {
                movie.setRating(0);
                movie.setVote(0);
            }
            movie.setTitle(Format.removeBlanks(movie.getTitle()));
            if (!movie.getTrailer().contains(FORMAT_YT_EMBED)) {
                movie.setTrailer(Format.getYoutubeUrl(movie.getTrailer()));
            }
            success = movieDao.saveOrUpdateMovie(movie);
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
        boolean exists = true;
        if (Validation.isValidString(movie.getTitle())) {
            exists = movieDao.checkIfMovieExists(movie);
        }
        return exists;
    }

}
