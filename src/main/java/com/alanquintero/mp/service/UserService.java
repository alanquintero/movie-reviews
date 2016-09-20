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
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.ProfileRepository;
import com.alanquintero.mp.repository.ReviewRepository;
import com.alanquintero.mp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User getUser(int id){
		return userRepository.findOne(id);
	}
	
	@Transactional
	public User getUserWithReviews(int id){
		User user = getUser(id);
		Profile profile = profileRepository.getProfileByUser(user);
		
		List<Review> reviews = reviewRepository.getReviewsByProfile(profile);
		for(Review review : reviews){
			Movie movie = movieRepository.getMovieByReviews(review);
			review.setMovie(movie);
		}
		profile.setReview(reviews);
		user.setProfile(profile);
		
		return user;
	}
	
	
}
