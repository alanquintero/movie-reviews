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

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
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
@ContextConfiguration({ CONF_DB, CONF_CONTEXT })
@PrepareForTest(Logger.class)
public class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    private static String REVIEW_CODE = "MQ==";
    private static String MOVIE_CODE = "MQ==";
    private static String USER_NAME = "test";
    private static Logger mockLogger;

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

        mockLogger = Mockito.mock(Logger.class);
        Whitebox.setInternalState(ReviewServiceImpl.class, "logger", mockLogger);
    }

    @Test
    public void testSaveReview() {
        Assert.assertEquals(reviewService.saveOrUpdateReview(review, USER_NAME), IN_PROFILE);
    }

    @Test
    public void testSaveReviewWithEmptyUser() {
        Assert.assertEquals(reviewService.saveOrUpdateReview(review, EMPTY_STRING), EMPTY_STRING);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSaveReviewWithNullUser() {
        String userName = null;

        Assert.assertEquals(reviewService.saveOrUpdateReview(review, userName), EMPTY_STRING);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSaveReviewWithNullReview() {
        Review review = null;

        Assert.assertEquals(reviewService.saveOrUpdateReview(review, USER_NAME), EMPTY_STRING);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSaveReviewWithEmptyReview() {
        Review review = new Review();

        Assert.assertEquals(reviewService.saveOrUpdateReview(review, USER_NAME), EMPTY_STRING);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSaveReviewWithEmptyReviewAndUser() {
        Review review = new Review();

        Assert.assertEquals(reviewService.saveOrUpdateReview(review, EMPTY_STRING), EMPTY_STRING);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSaveReviewWithNullReviewAndUser() {
        Review review = null;
        String userName = null;

        Assert.assertEquals(reviewService.saveOrUpdateReview(review, userName), EMPTY_STRING);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testDeteleExistentReview() {
        Review review = new Review();
        review.setCode(REVIEW_CODE);

        Assert.assertEquals(reviewService.deteleReview(review), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNonexistentReview() {
        Review review = new Review();
        review.setCode(EMPTY_STRING);

        Assert.assertEquals(reviewService.deteleReview(review), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNullReview() {
        Review review = null;

        Assert.assertEquals(reviewService.deteleReview(review), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSearchReviewById() {
        Review review = reviewService.searchReviewById(REVIEW_CODE);

        Assert.assertNotNull(review);
    }

    @Test
    public void testSearchReviewByNonexistentId() {
        Review review = reviewService.searchReviewById(EMPTY_STRING);

        Assert.assertNotNull(review);
        Assert.assertEquals(review.getComment(), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

}
