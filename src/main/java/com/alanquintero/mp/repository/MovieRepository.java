/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import static com.alanquintero.mp.util.Consts.*;

/**
 * MovieRepository.java 
 * Purpose: Get Movie information from DB.
 */
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /**
     * Find movie by review
     * 
     * @param Review_Object
     * @return Movie_Object
     */
    public Movie getMovieByReviews(Review review);

    /**
     * Find movies by movie title
     * 
     * @param Movie_title
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m " + "WHERE lower(m.title) like lower(:movie_title) ")
    public List<Movie> findAllMovies(@Param(MOVIE_TITLE_PARAM) String movie);

    /**
     * Find popular movies
     * 
     * @param Pageable_Object
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m ")
    public List<Movie> findPopularMovies(Pageable pageable);

    /**
     * Find one movie by movie id
     * 
     * @param Movie_id
     * @return Movie_Object
     */
    @Query("SELECT m FROM Movie m WHERE id = :movie_id")
    public Movie getMovieById(@Param(MOVIE_ID_PARAM) int id);

    /**
     * Find movies by movie title
     * 
     * @param Movie_title
     * @param Pageable_Object
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m " + "WHERE lower(m.title) like lower(:movie_title) ")
    public List<Movie> getSearchMovies(@Param(MOVIE_TITLE_PARAM) String movieName, Pageable topSix);

}
