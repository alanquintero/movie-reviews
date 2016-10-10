/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.ProfileRepository;
import com.alanquintero.mp.repository.ReviewRepository;
import com.alanquintero.mp.repository.UserRepository;
import static com.alanquintero.mp.util.Consts.*;

/**
 * ReviewService.java 
 * Purpose: Services of Review section.
 */
@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    MovieRepository movieRepository;

    /**
     * Add one review
     * 
     * @param Review_Object
     * @param User_username
     */
    public void saveReview(Review review, String userName) {
        User user = userRepository.getUserByName(userName);
        Profile profile = profileRepository.getProfileByUser(user);
        review.setId(null);
        review.setPublishedDate(new Date());
        review.setRating(0);
        review.setProfile(profile);
        Movie movie = movieRepository.getMovieById(review.getMovie().getId());
        review.setMovie(movie);
        reviewRepository.save(review);
    }

    /**
     * Delete one review
     * 
     * @param Review_Object
     */
    @PreAuthorize(USERNAME_OR_ADMIN)
    public void deteleReview(@P(REVIEW) Review review) {
        reviewRepository.delete(review);
    }

    /**
     * Find one review
     * 
     * @param Review_id
     * @return Review_Object
     */
    public Review findOne(int id) {
        return reviewRepository.findOne(id);
    }

}
