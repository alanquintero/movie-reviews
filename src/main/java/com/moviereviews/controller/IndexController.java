/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import static com.moviereviews.util.Consts.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviereviews.service.MovieService;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final MovieService movieService;

    public IndexController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping("/")
    public String home(Model model) {
        logger.info("at /");
        model.addAttribute("title", "Home - Movie Reviews");
        model.addAttribute("movie", movieService.getMostVotedMovies());
        return "layout";
    }

    @RequestMapping(INDEX_URL)
    public String index(Model model) {
        logger.info(LOG_URL_REQUEST + INDEX_URL);
        model.addAttribute(MOVIE, movieService.getMostVotedMovies());

        return INDEX_PAGE;
    }
}
