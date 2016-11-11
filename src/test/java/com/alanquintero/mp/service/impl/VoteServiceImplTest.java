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

    @Test
    public void rateMovieTest() {
        String userName = "test";
        int movieId = 1;
        int rating = 1;
        int result = voteService.rateMovie(userName, movieId, rating);
        assertNotEquals(result, 0);
    }

    @Test
    public void rateMovieWithEmptyUserTest() {
        String userName = "";
        int movieId = 1;
        int rating = 1;
        int result = voteService.rateMovie(userName, movieId, rating);
        assertEquals(result, 0);
    }

    @Test
    public void rateMovieWithInvalidMovieTest() {
        String userName = "test";
        int movieId = 0;
        int rating = 1;
        int result = voteService.rateMovie(userName, movieId, rating);
        assertEquals(result, 0);
    }

    @Test
    public void rateMovieWithInvalidRatingTest() {
        String userName = "test";
        int movieId = 1;
        int rating = 0;
        int result = voteService.rateMovie(userName, movieId, rating);
        assertEquals(result, 0);
    }

}
