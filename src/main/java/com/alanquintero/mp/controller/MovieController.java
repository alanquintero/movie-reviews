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

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

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
import com.alanquintero.mp.entity.Vote;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;
import com.alanquintero.mp.service.VoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @class MovieController.java
 * @purpose Controller for Movie transactions.
 */
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private VoteService voteService;

    /**
     * Construct movie object model
     * 
     * @return Movie
     */
    @ModelAttribute(MOVIE)
    public Movie contruct() {
        return new Movie();
    }

    /**
     * Construct review object model
     * 
     * @return Review
     */
    @ModelAttribute(REVIEW)
    public Review contructReview() {
        return new Review();
    }

    /**
     * Construct vote object model
     * 
     * @return Vote
     */
    @ModelAttribute(VOTE)
    public Vote contructVote() {
        return new Vote();
    }

    /**
     * Find movie details by movie id
     * 
     * @param Model
     * @param String
     * @return String
     */
    @RequestMapping(MOVIE_URL)
    public String searchMovieDetails(Model model, @PathVariable String code) {
        model.addAttribute(MOVIE, movieService.searchMovieDetailsById(code));
        return MOVIE_PAGE;
    }

    /**
     * Find movie by movie title
     * 
     * @param Model
     * @param String
     * @return String
     */
    @RequestMapping(value = RESULT_MOVIE_URL)
    public String searchMovieByTitle(Model model, @PathVariable String movieTitle) {
        model.addAttribute(MOVIE, movieService.searchMovieByTitle(movieTitle));
        return RESULT;
    }

    /**
     * Find movie by empty input
     * 
     * @param Model
     * @return String
     */
    @RequestMapping(value = POPULAR_MOVIES_URL)
    public String searchByEmptyTitle(Model model) {
        model.addAttribute(MOVIE, movieService.getPopularMovies());
        return RESULT;
    }

    /**
     * Find all movies
     * 
     * @param Model
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
     * @param Model
     * @param String
     * @return String
     */
    @RequestMapping(DELETE_MOVIE_URL)
    public String removeMovie(Model model, @PathVariable String code) {
        model.addAttribute(MESSAGE, movieService.deteleMovie(code));
        return REDIRECT_MOVIES_PAGE;
    }

    /**
     * Find movies by movie name to auto complete user input
     * 
     * @param String
     * @return String
     */
    @RequestMapping(value = AUTOCOMPLETE_MOVIES_URL, method = RequestMethod.GET)
    @ResponseBody
    public String searchAutocompleteMovies(@RequestParam String movieTitle) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
        return mapper.writeValueAsString(movies);
    }

    /**
     * Give Vote to a Movie
     * 
     * @param Principal
     * @param int
     * @param String
     * @return String
     */
    @RequestMapping(value = RATE_MOVIE, method = RequestMethod.GET)
    @ResponseBody
    public String voteMovie(Principal principal, @RequestParam int rating, @RequestParam String code) {
        String returnPage = EMPTY_STRING;
        if (principal != null) {
            int newRating = voteService.rateMovie(principal.getName(), code, rating);
            if (newRating != 0) {
                returnPage = EMPTY_STRING + newRating;
            } else {
                returnPage = MSG_FAIL;
            }
        } else {
            returnPage = REDIRECT_LOGIN_PAGE;
        }
        return returnPage;
    }

    /**
     * Add or Update a Movie
     * 
     * @param Model
     * @param Movie
     * @return String
     */
    @RequestMapping(value = { MOVIES_URL }, method = RequestMethod.POST)
    public String doAddOrUpdateReview(Model model, @Valid @ModelAttribute(MOVIE) Movie movie) {
        String resultPage = EMPTY_STRING;
        if (movie != null) {
            if (movieService.checkIfMovieExists(movie)) {
                resultPage = REDIRECT_MOVIES_FAIL_PAGE;
            } else if (movieService.saveOrUpdateMovie(movie)) {
                resultPage = REDIRECT_MOVIES_SUCCESS_PAGE;
            } else {
                resultPage = REDIRECT_MOVIES_FAIL_PAGE;
            }
        }
        return resultPage;
    }

}
