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
 * ReviewRepository.java 
 * Purpose: Get Review information from DB.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * Find reviews by profile
     * 
     * @param Profile_Object
     * @param Pageable_Object
     * @return List_Review
     */
    public List<Review> getReviewsByProfile(Profile profile, Pageable pageable);

    /**
     * Find reviews by movie
     * 
     * @param Movie_Object
     * @param Pageable_Object
     * @return List_Review
     */
    public List<Review> getReviewsByMovie(Movie movie, Pageable pageable);

}
