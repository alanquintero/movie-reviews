/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.ReviewRepository;

@Service
@Transactional
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	public Movie getMovieById(int id){
		return movieRepository.findOne(id);
	}
	
	@Transactional
	public Movie getMovieDetailsById(int id){
		Movie movie = getMovieById(id);
		
		List<Review> reviews = reviewRepository.getReviewsByMovie(movie, new PageRequest(0, 15, Direction.DESC, "publishedDate"));
		movie.setReviews(reviews);
		
		return movie;
	}
	
	@Transactional
	public List<Movie> searchMovie(String movie){
		List<Movie> movies = movieRepository.findAllMovies( "%" + movie + "%");
		
		return movies;
	}
	
	
	@Transactional
	public List<Movie> popularMovies(){
		List<Movie> movies = movieRepository.findPopularMovies(new PageRequest(0, 10, Direction.DESC, "rating"));
		
		return movies;
	}
	
}
