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

public interface MovieRepository extends JpaRepository<Movie, Integer>{

	public Movie getMovieByReviews(Review review);
	
	@Query("SELECT m FROM Movie m "
            + "WHERE lower(m.title) like lower(:movie) ")
	public List<Movie> findAllMovies(@Param("movie") String movie);
	
	@Query("SELECT m FROM Movie m ")
	public List<Movie> findPopularMovies(Pageable pageable);
	
	@Query("SELECT m FROM Movie m WHERE id = :id")
	public Movie getMovieById(@Param("id") int id);

	@Query("SELECT m FROM Movie m "
            + "WHERE lower(m.title) like lower(:movieName) ")
	public List<Movie> getSearchMovies(@Param("movieName")String movieName, Pageable topSix);
	
}
