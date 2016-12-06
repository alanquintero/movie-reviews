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
    public void testRateMovie() {
        String userName = "test";
        int rating = 1;
        String code = "MQ==";
        int result = voteService.rateMovie(userName, code, rating);
        assertNotEquals(result, 0);
    }

    @Test
    public void testRateMovieWithEmptyUser() {
        String userName = "";
        String code = "MQ==";
        int rating = 1;
        int result = voteService.rateMovie(userName, code, rating);
        assertEquals(result, 0);
    }

    @Test
    public void testRateMovieWithInvalidMovie() {
        String userName = "test";
        String code = "";
        int rating = 1;
        int result = voteService.rateMovie(userName, code, rating);
        assertEquals(result, 0);
    }

    @Test
    public void testRateMovieWithInvalidRating() {
        String userName = "test";
        String code = "";
        int rating = 0;
        int result = voteService.rateMovie(userName, code, rating);
        assertEquals(result, 0);
    }

}
