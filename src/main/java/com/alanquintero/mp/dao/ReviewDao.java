/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao;

import java.util.List;

import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;

/**
 * @class ReviewDao.java
 * @purpose Interface of DAO Layer for Review Transactions.
 */
public interface ReviewDao {

    /**
     * @param Review
     * @return boolean
     */
    public boolean saveOrUpdateReview(Review review);

    /**
     * @param Review
     * @return boolean
     */
    public boolean deteleReview(Review review);

    /**
     * @param int
     * @return Review
     */
    public Review searchReviewById(int reviewId);

    /**
     * @param Profile
     * @return List_Review
     */
    public List<Review> searchReviewsByProfile(Profile profile);

}
