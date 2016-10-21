/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

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
import com.alanquintero.mp.service.ReviewService;

import static com.alanquintero.mp.util.Consts.*;

/**
 * ReviewService.java Purpose: Services of Review section.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    MovieRepository movieRepository;

    /**
     * Add a Review
     * 
     * @param Review
     * @param String
     */
    public void saveReview(Review review, String userName) {
        User user = userRepository.findUserByName(userName);
        Profile profile = profileRepository.findProfileByUser(user);
        review.setId(null);
        review.setPublishedDate(new Date());
        review.setRating(0);
        review.setProfile(profile);
        Movie movie = movieRepository.getMovieById(review.getMovie().getId());
        review.setMovie(movie);
        reviewRepository.save(review);
    }

    /**
     * Delete a Review
     * 
     * @param Review
     */
    @PreAuthorize(USERNAME_OR_ADMIN)
    public void deteleReview(@P(REVIEW) Review review) {
        reviewRepository.delete(review);
    }

    /**
     * Search a Review by Review Id
     * 
     * @param int
     * @return Review
     */
    public Review searchReviewById(int reviewId) {
        return reviewRepository.findOne(reviewId);
    }

}
