/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.ReviewDao;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.repository.ReviewRepository;

/**
 * @class ReviewDaoImpl.java
 * @purpose Implementation of ReviewDao Interface.
 */
@Repository
public class ReviewDaoImpl implements ReviewDao {

    @Autowired
    private ReviewRepository reviewRepository;

    private static Logger logger = Logger.getLogger(ReviewDaoImpl.class);

    /**
     * Save or Update a Review
     * 
     * @param Review
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateReview(Review review) {
        boolean success = false;

        try {
            reviewRepository.save(review);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    /**
     * Delete a Review
     * 
     * @param Review
     * @return boolean
     */
    @Override
    public boolean deteleReview(Review review) {
        boolean success = false;

        try {
            reviewRepository.delete(review);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    /**
     * Search a Review by Review Id
     * 
     * @param int
     * @return Review
     */
    @Override
    public Review searchReviewById(int reviewId) {
        Review review = null;

        try {
            review = reviewRepository.findOne(reviewId);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return review;
    }

    /**
     * Search all Reviews by User Profile
     * 
     * @param Profile
     * @return List_Review
     */
    @Override
    public List<Review> searchReviewsByProfile(Profile profile) {
        List<Review> reviews = null;

        try {
            reviews = reviewRepository.findReviewsByProfile(profile,
                    new PageRequest(0, 10, Direction.DESC, PUBLISHED_DATE_FIELD));
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return reviews;
    }
}
