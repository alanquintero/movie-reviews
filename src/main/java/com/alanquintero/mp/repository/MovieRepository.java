/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import static com.alanquintero.mp.util.Consts.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;

/**
 * @class MovieRepository.java
 * @purpose Get Movie information from DB.
 */
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /**
     * Find one Movie by Review
     * 
     * @param Review
     * @return Movie
     */
    public Movie getMovieByReviews(Review review);

    /**
     * Find Movies by Movie Title
     * 
     * @param String
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m WHERE lower(m.title) like lower(:movie_title)")
    public List<Movie> findAllMovies(@Param(MOVIE_TITLE_PARAM) String movieTitle);

    /**
     * Find popular Movies
     * 
     * @param Pageable
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m")
    public List<Movie> findPopularMovies(Pageable pageable);

    /**
     * Find one Movie by Movie Id
     * 
     * @param int
     * @return Movie
     */
    @Query("SELECT m FROM Movie m WHERE id = :movie_id")
    public Movie getMovieById(@Param(MOVIE_ID_PARAM) int movieId);

    /**
     * Find Movies by part of the Movie Title
     * 
     * @param String
     * @param Pageable
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m WHERE lower(m.title) like lower(:movie_title)")
    public List<Movie> getSearchMovies(@Param(MOVIE_TITLE_PARAM) String movieTitle, Pageable topSix);

    /**
     * Find Most Voted Movies
     * 
     * @param Pageable
     * @return List_Movie
     */
    @Query("SELECT m FROM Movie m")
    public List<Movie> findMostVotedMovies(Pageable pageable);

    /**
     * Find Movie by Title and Year
     * 
     * @param String
     * @param int
     * @return Movie
     */
    @Query("SELECT m FROM Movie m WHERE lower(m.title) LIKE lower(:movie_title) AND m.year = :movie_year")
    public Movie findMovieByTitleAndYear(@Param(MOVIE_TITLE_PARAM) String movieTitle,
            @Param(MOVIE_YEAR_PARAM) int movieYear);

    /**
     * Find Movie by Id, Title and Year
     * 
     * @param String
     * @param int
     * @return Movie
     */
    @Query("SELECT m FROM Movie m WHERE id != :movie_id AND lower(m.title) LIKE lower(:movie_title) AND m.year = :movie_year")
    public Movie findMovieByValues(@Param(MOVIE_ID_PARAM) int movieId, @Param(MOVIE_TITLE_PARAM) String movieTitle,
            @Param(MOVIE_YEAR_PARAM) int movieYear);

}
