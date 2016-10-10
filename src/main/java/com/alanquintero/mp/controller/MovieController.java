/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;
import static com.alanquintero.mp.util.Consts.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * MovieController.java 
 * Purpose: Controller for Movie.
 */
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Construct movie object model
     * 
     * @return Movie_Object
     */
    @ModelAttribute(MOVIE)
    public Movie contruct() {
        return new Movie();
    }

    /**
     * Construct review object model
     * 
     * @return Review_Object
     */
    @ModelAttribute(REVIEW)
    public Review contructReview() {
        return new Review();
    }

    /**
     * Find movie details by movie id
     * 
     * @param Model_Object
     * @param Movie_id
     * @return String
     */
    @RequestMapping(MOVIE_URL)
    public String movieDetail(Model model, @PathVariable int id) {
        model.addAttribute(MOVIE, movieService.getMovieDetailsById(id));
        return MOVIE_PAGE;
    }

    /**
     * Find movie by movie title
     * 
     * @param Model_Object
     * @param Movie_title
     * @return String
     */
    @RequestMapping(value = RESULT_MOVIE_URL)
    public String searchMovie(Model model, @PathVariable String movie) {
        model.addAttribute(MOVIE, movieService.searchMovie(movie));
        return RESULT;
    }

    /**
     * Find movie by empty input
     * 
     * @param Model_Object
     * @return String
     */
    @RequestMapping(value = POPULAR_MOVIES_URL)
    public String searchEmpty(Model model) {
        model.addAttribute(MOVIE, movieService.popularMovies());
        return RESULT;
    }

    /**
     * Find all movies
     * 
     * @param Model_Object
     * @return String
     */
    @RequestMapping(MOVIES_URL)
    public String getAllMovies(Model model) {
        model.addAttribute(MOVIES, movieService.getAllMovies());
        return MOVIES_PAGE;
    }

    /**
     * Delete a movie by movie id
     * 
     * @param Movie_id
     * @return String
     */
    @RequestMapping(DELETE_MOVIE_URL)
    public String removeMovie(@PathVariable int id) {
        movieService.deteleMovie(id);
        return REDIRECT_MOVIES_PAGE;
    }

    /**
     * Find movies by movie name to auto complete user input
     * 
     * @param Movie_title
     * @return String
     */
    @RequestMapping(value = AUTOCOMPLETE_MOVIES_URL, method = RequestMethod.GET)
    @ResponseBody
    public String getMovies(@RequestParam String movieName) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<MovieModel> movies = movieService.getSearchMovies(movieName);
        return mapper.writeValueAsString(movies);
    }

}
