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

import org.junit.Before;
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
    private ReviewService reviewService;

    private static String REVIEW_CODE = "MQ==";
    private static String MOVIE_CODE = "MQ==";
    private static String USER_NAME = "test";
    private Review review;

    @Before
    public void setData() {
        review = new Review();
        review.setTitle("Title test");
        review.setComment("Comment test");
        review.setCode(REVIEW_CODE);
        Movie movie = new Movie();
        movie.setCode(MOVIE_CODE);
        review.setMovie(movie);
    }

    @Test
    public void testSaveReview() {
        assertEquals(reviewService.saveOrUpdateReview(review, USER_NAME), IN_PROFILE);
    }

    @Test
    public void testSaveReviewWithEmptyUser() {
        assertEquals(reviewService.saveOrUpdateReview(review, EMPTY_STRING), EMPTY_STRING);
    }

    @Test
    public void testSaveReviewWithNullUser() {
        String userName = null;
        assertEquals(reviewService.saveOrUpdateReview(review, userName), EMPTY_STRING);
    }

    @Test
    public void testSaveReviewWithNullReview() {
        Review review = null;
        assertEquals(reviewService.saveOrUpdateReview(review, USER_NAME), EMPTY_STRING);
    }

    @Test
    public void testSaveReviewWithEmptyReview() {
        Review review = new Review();
        assertEquals(reviewService.saveOrUpdateReview(review, USER_NAME), EMPTY_STRING);
    }

    @Test
    public void testSaveReviewWithEmptyReviewAndUser() {
        Review review = new Review();
        assertEquals(reviewService.saveOrUpdateReview(review, EMPTY_STRING), EMPTY_STRING);
    }

    @Test
    public void testSaveReviewWithNullReviewAndUser() {
        Review review = null;
        String userName = null;
        assertEquals(reviewService.saveOrUpdateReview(review, userName), EMPTY_STRING);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testDeteleExistentReview() {
        Review review = new Review();
        review.setCode(REVIEW_CODE);
        assertEquals(reviewService.deteleReview(review), MSG_SUCCESS);

    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNonexistentReview() {
        Review review = new Review();
        review.setCode(EMPTY_STRING);
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
        Review review = reviewService.searchReviewById(REVIEW_CODE);
        assertNotNull(review);
        assertNotEquals(review.getComment(), MSG_FAIL);
    }

    @Test
    public void testSearchReviewByNonexistentId() {
        Review review = reviewService.searchReviewById(EMPTY_STRING);
        assertNotNull(review);
        assertEquals(review.getComment(), MSG_FAIL);
    }

}
