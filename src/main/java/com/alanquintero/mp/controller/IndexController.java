/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import static com.alanquintero.mp.util.Consts.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alanquintero.mp.service.MovieService;

/**
 * @class IndexController.java
 * @purpose Controller for Default Pages.
 */
@Controller
public class IndexController {

    @Autowired
    private MovieService movieService;

    private static final Logger logger = Logger.getLogger(IndexController.class);

    /**
     * Redirect to home page
     *
     * @param Model
     * @return String
     */
    @RequestMapping(DEFAULT_URL)
    public String home(Model model) {
        logger.info(LOG_URL_REQUEST + DEFAULT_URL);
        model.addAttribute(MOVIE, movieService.getMostVotedMovies());

        return INDEX_PAGE;
    }

    /**
     * Redirect to index page
     * 
     * @param Model
     * @return String
     */
    @RequestMapping(INDEX_URL)
    public String index(Model model) {
        logger.info(LOG_URL_REQUEST + INDEX_URL);
        model.addAttribute(MOVIE, movieService.getMostVotedMovies());

        return INDEX_PAGE;
    }

}
