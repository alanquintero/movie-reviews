/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

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
import static com.alanquintero.mp.util.Consts.*;

/**
 * MovieService.java 
 * Purpose: Services of Movie section.
 */
@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Find one movie
     * 
     * @param Movie_id
     * @return Movie_Object
     */
    public Movie getMovieById(int id) {
        return movieRepository.findOne(id);
    }

    /**
     * Get movie details
     * 
     * @param Movie_id
     * @return Movie_Object
     */
    @Transactional
    public Movie getMovieDetailsById(int id) {
        Movie movie = getMovieById(id);
        List<Review> reviews = reviewRepository.getReviewsByMovie(movie,
                new PageRequest(0, 15, Direction.DESC, PUBLISHED_DATE_FIELD));
        movie.setReviews(reviews);
        return movie;
    }

    /**
     * Find a list of movies that match with text provided
     * 
     * @param Movie_title
     * @return List_Movie
     */
    @Transactional
    public List<Movie> searchMovie(String movie) {
        return movieRepository.findAllMovies(PERCENT + movie + PERCENT);
    }

    /**
     * Find the top of popular movies
     * 
     * @return List_Movie
     */
    @Transactional
    public List<Movie> popularMovies() {
        return movieRepository.findPopularMovies(new PageRequest(0, 10, Direction.DESC, RATING_FIELD));
    }

    /**
     * Find all movies
     * 
     * @return List_Movie
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Delete one movie
     * 
     * @param Movie_id
     */
    @PreAuthorize(HAS_ROLE_ADMIN)
    public void deteleMovie(int id) {
        movieRepository.delete(id);
    }

    /**
     * Find a list of movies that match with text provided to auto complete user
     * input search
     * 
     * @param Movie_title
     * @return List_Movie
     */
    public List<MovieModel> getSearchMovies(String movieName) {
        Pageable topSix = new PageRequest(0, 6);
        List<Movie> movies = movieRepository.getSearchMovies(PERCENT + movieName + PERCENT, topSix);
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
