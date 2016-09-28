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
		return movieRepository.findAllMovies( "%" + movie + "%");
	}
	
	@Transactional
	public List<Movie> popularMovies(){
		return movieRepository.findPopularMovies(new PageRequest(0, 10, Direction.DESC, "rating"));
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	/**
	 * @param id
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deteleMovie(int id) {
		movieRepository.delete(id);
	}

	public List<MovieModel> getSearchMovies(String movieName) {
		Pageable topSix = new PageRequest(0, 6);
		List<Movie> movies = movieRepository.getSearchMovies("%" + movieName + "%", topSix);
		List<MovieModel> moviesModel = new ArrayList<MovieModel>();
		if(movies != null){
			for(Movie m: movies){
				moviesModel.add(new MovieModel(m.getId(), m.getTitle()+" ("+m.getYear()+")"));
			}
		}
		return moviesModel;
	}

	
}
