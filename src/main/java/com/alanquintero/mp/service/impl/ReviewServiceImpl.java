/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.dao.ProfileDao;
import com.alanquintero.mp.dao.ReviewDao;
import com.alanquintero.mp.dao.UserDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.service.ReviewService;
import com.alanquintero.mp.util.Message;
import com.alanquintero.mp.util.Validation;

/**
 * @class ReviewService.java
 * @purpose Services of Review section.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfileDao profileDao;

    /**
     * Add a Review
     * 
     * @param Review
     * @param String
     * @return String
     */
    @Override
    public String saveReview(Review review, String userName) {
        boolean success = false;
        if (((review != null) && (Validation.isValidString(review.getTitle())
                && (Validation.isValidString(review.getComment())) && (review.getMovie() != null)))
                && (Validation.isValidString(userName))) {
            User user = userDao.searchUserByName(userName);
            Profile profile = null;
            if (user != null) {
                profile = profileDao.searchProfileByUser(user);
            }
            review.setId(null);
            review.setPublishedDate(new Date());
            review.setRating(0);
            Movie movie = movieDao.searchMovieById(review.getMovie().getId());
            if ((Validation.isValidString(review.getComment())) && (profile != null) && (movie != null)) {
                review.setProfile(profile);
                review.setMovie(movie);
                success = reviewDao.saveReview(review);
            }
        }
        return Message.setSuccessOrFail(success);
    }

    /**
     * Delete a Review
     * 
     * @param Review
     * @return String
     */
    @Override
    @PreAuthorize(USERNAME_OR_ADMIN)
    public String deteleReview(@P(REVIEW) Review review) {
        boolean success = false;
        if ((review != null) && (review.getId() > 0)) {
            success = reviewDao.deteleReview(review);
        }
        return Message.setSuccessOrFail(success);
    }

    /**
     * Search a Review by Review Id
     * 
     * @param int
     * @return Review
     */
    @Override
    public Review searchReviewById(int reviewId) {
        Review review = reviewDao.searchReviewById(reviewId);
        if (review == null) {
            review = Message.setReviewFail();
        }
        return review;
    }

}
