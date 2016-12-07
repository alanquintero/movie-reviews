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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alanquintero.mp.service.VoteService;

/**
 * @class VoteServiceImplTest.java
 * @purpose Class used to test VoteServiceImpl class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(CONF_CONTEXT)
public class VoteServiceImplTest {

    @Autowired
    private VoteService voteService;

    private static String USER_NAME = "test";
    private static String MOVIE_CODE = "MQ==";
    private static int RATING = 1;

    @Test
    public void testRateMovie() {
        Assert.assertNotNull(voteService.rateMovie(USER_NAME, MOVIE_CODE, RATING));
    }

    @Test
    public void testRateMovieWithEmptyUser() {
        Assert.assertEquals(voteService.rateMovie(EMPTY_STRING, MOVIE_CODE, RATING), 0);
    }

    @Test
    public void testRateMovieWithInvalidMovie() {
        Assert.assertEquals(voteService.rateMovie(USER_NAME, EMPTY_STRING, RATING), 0);
    }

    @Test
    public void testRateMovieWithInvalidRating() {
        Assert.assertEquals(voteService.rateMovie(USER_NAME, EMPTY_STRING, 0), 0);
    }

}
