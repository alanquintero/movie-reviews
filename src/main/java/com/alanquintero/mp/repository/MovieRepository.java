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
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;

public interface MovieRepository extends JpaRepository<Movie, Integer>{

	public Movie getMovieByReviews(Review review);
	
//	public List<Movie> findAllMovies(String movie, Pageable pageable);
	
}
