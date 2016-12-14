/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.Date;

import org.apache.log4j.Logger;
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
import com.alanquintero.mp.util.Data;
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

    private static Logger logger = Logger.getLogger(ReviewServiceImpl.class);

    /**
     * Add or Update a Review
     * 
     * @param Review
     * @param String
     * @return String
     */
    @Override
    public String saveOrUpdateReview(Review review, String userName) {
        String inPage = EMPTY_STRING;

        if (((review != null) && (Validation.isValidString(review.getTitle())
                && (Validation.isValidString(review.getComment())) && (review.getMovie() != null)))
                && (Validation.isValidString(userName))) {
            User user = userDao.searchUserByName(userName);
            Profile profile = null;
            if (user != null) {
                profile = profileDao.searchProfileByUser(user);
            }
            if (!Validation.isValidString(review.getCode())) {
                review.setId(null);
            } else {
                review.setId(Data.decode(review.getCode()));
                inPage = IN_PROFILE;
            }
            review.setCode(EMPTY_STRING);
            review.setPublishedDate(new Date());
            Movie movie = movieDao.searchMovieById(Data.decode(review.getMovie().getCode()));
            if ((Validation.isValidString(review.getComment())) && (profile != null) && (movie != null)) {
                review.setProfile(profile);
                review.setMovie(movie);
                reviewDao.saveOrUpdateReview(review);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return inPage;
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

        if ((review != null) && (Validation.isValidString(review.getCode()))) {
            review.setId(Data.decode(review.getCode()));
            success = reviewDao.deteleReview(review);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return Message.setSuccessOrFail(success);
    }

    /**
     * Search a Review by Review Id
     * 
     * @param String
     * @return Review
     */
    @Override
    public Review searchReviewById(String reviewCode) {
        Review review = reviewDao.searchReviewById(Data.decode(reviewCode));

        if (review == null) {
            review = Message.setReviewFail();
            logger.info(LOG_INVALID_INPUT);
        }
        review.setCode(Data.encode(review.getId()));

        return review;
    }

}
