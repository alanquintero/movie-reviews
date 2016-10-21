/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.ReviewRepository;
import com.alanquintero.mp.service.MovieService;

import static com.alanquintero.mp.util.Consts.*;

/**
 * @class MovieServiceImpl.java
 * @purpose Implementation of MovieService interface.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Search Movie By Movie Id
     * 
     * @param int
     * @return Movie
     */
    public Movie searchMovieById(int movieId) {
        return movieRepository.findOne(movieId);
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
        List<Review> reviews = reviewRepository.findReviewsByMovie(movie,
                new PageRequest(0, 15, Direction.DESC, PUBLISHED_DATE_FIELD));
        movie.setReviews(reviews);
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
        return movieRepository.findAllMovies(PERCENT + movieTitle + PERCENT);
    }

    /**
     * Search a list of Top popular Movies
     * 
     * @return List_Movie
     */
    @Transactional
    public List<Movie> getPopularMovies() {
        return movieRepository.findPopularMovies(new PageRequest(0, 10, Direction.DESC, RATING_FIELD));
    }

    /**
     * Search all Movies
     * 
     * @return List_Movie
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Delete a Movie by Movie Id
     * 
     * @param int
     */
    @PreAuthorize(HAS_ROLE_ADMIN)
    public void deteleMovie(int id) {
        movieRepository.delete(id);
    }

    /**
     * Search a list of Movies by Movie Title
     * 
     * @param String
     * @return List_Movie
     */
    public List<MovieModel> searchAutocompleteMovies(String movieTitle) {
        Pageable topSix = new PageRequest(0, 6);
        List<Movie> movies = movieRepository.getSearchMovies(PERCENT + movieTitle + PERCENT, topSix);
        List<MovieModel> moviesModel = new ArrayList<MovieModel>();
        if (movies != null) {
            for (Movie m : movies) {
                moviesModel.add(
                        new MovieModel(m.getId(), m.getTitle() + PARENTHESIS_OPEN + m.getYear() + PARENTHESIS_CLOSE));
            }
        }
        return moviesModel;
    }

}
