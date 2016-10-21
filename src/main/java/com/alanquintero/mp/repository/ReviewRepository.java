/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;

/**
 * @class ReviewRepository.java
 * @purpose Get Review information from DB.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * Find Reviews by Profile
     * 
     * @param Profile
     * @param Pageable
     * @return List_Review
     */
    public List<Review> findReviewsByProfile(Profile profile, Pageable pageable);

    /**
     * Find Reviews by Movie
     * 
     * @param Movie
     * @param Pageable
     * @return List_Review
     */
    public List<Review> findReviewsByMovie(Movie movie, Pageable pageable);

}
