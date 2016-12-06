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
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.service.ReviewService;

/**
 * @class ReviewServiceImplTest.java
 * @purpose Class used to test ReviewServiceImpl class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(CONF_CONTEXT)
public class ReviewServiceImplTest {

    @Autowired
    ReviewService reviewService;

    @Test
    public void testSaveReview() {
        Review review = new Review();
        review.setTitle("Title test");
        review.setComment("Comment test");
        review.setCode("MQ==");
        Movie movie = new Movie();
        movie.setCode("MQ==");
        review.setMovie(movie);
        String userName = "test";
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_SUCCESS);
    }

    @Test
    public void testSaveReviewWithEmptyUser() {
        Review review = new Review();
        review.setTitle("Title test");
        review.setComment("Comment test");
        Movie movie = new Movie();
        movie.setId(1);
        review.setMovie(movie);
        String userName = "";
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_FAIL);
    }

    @Test
    public void testSaveReviewWithNullUser() {
        Review review = new Review();
        review.setTitle("Title test");
        review.setComment("Comment test");
        Movie movie = new Movie();
        movie.setId(1);
        review.setMovie(movie);
        String userName = null;
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_FAIL);
    }

    @Test
    public void testSaveReviewWithNullReview() {
        Review review = null;
        String userName = "test";
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_FAIL);
    }

    @Test
    public void testSaveReviewWithEmptyReview() {
        Review review = new Review();
        String userName = "test";
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_FAIL);
    }

    @Test
    public void testSaveReviewWithEmptyReviewAndUser() {
        Review review = new Review();
        String userName = "";
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_FAIL);
    }

    @Test
    public void testSaveReviewWithNullReviewAndUser() {
        Review review = null;
        String userName = null;
        assertEquals(reviewService.saveOrUpdateReview(review, userName), MSG_FAIL);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testDeteleExistentReview() {
        Review review = new Review();
        review.setId(1);
        assertEquals(reviewService.deteleReview(review), MSG_SUCCESS);

    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNonexistentReview() {
        Review review = new Review();
        review.setId(0);
        assertEquals(reviewService.deteleReview(review), MSG_FAIL);

    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNullReview() {
        Review review = null;
        assertEquals(reviewService.deteleReview(review), MSG_FAIL);

    }

    @Test
    public void testSearchReviewById() {
        String reviewCode = "MQ==";
        Review review = reviewService.searchReviewById(reviewCode);
        assertNotNull(review);
        assertNotEquals(review.getComment(), MSG_FAIL);
    }

    @Test
    public void testSearchReviewByNonexistentId() {
        String reviewCode = "";
        Review review = reviewService.searchReviewById(reviewCode);
        assertNotNull(review);
        assertEquals(review.getComment(), MSG_FAIL);
    }

}
