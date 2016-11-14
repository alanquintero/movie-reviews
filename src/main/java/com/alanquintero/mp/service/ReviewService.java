/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

import com.alanquintero.mp.entity.Review;

/**
 * @class ReviewService.java
 * @purpose Interface of Service Layer for Review Transactions.
 */
public interface ReviewService {

    /**
     * @param Review
     * @param String
     * @return String
     */
    public String saveOrUpdateReview(Review review, String userName);

    /**
     * @param Review
     * @return String
     */
    public String deteleReview(Review review);

    /**
     * @param int
     * @return Review
     */
    public Review searchReviewById(int reviewId);

}
