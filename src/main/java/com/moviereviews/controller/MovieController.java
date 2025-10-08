/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import static com.moviereviews.util.Consts.*;

import java.security.Principal;
import java.util.List;

import com.moviereviews.entity.Rating;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Review;
import com.moviereviews.model.MovieModel;
import com.moviereviews.service.MovieService;
import com.moviereviews.service.RatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    private final RatingService ratingService;

    public MovieController(final MovieService movieService, final RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @ModelAttribute(MOVIE)
    public Movie constructMovie() {
        return new Movie();
    }

    @ModelAttribute(REVIEW)
    public Review contructReview() {
        return new Review();
    }

    @ModelAttribute(VOTE)
    public Rating constructVote() {
        return new Rating();
    }

    @RequestMapping(MOVIE_URL)
    public String searchMovieDetails(Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + MOVIE_URL);
        model.addAttribute(MOVIE, movieService.searchMovieDetailsById(code));

        return MOVIE_PAGE;
    }

    @RequestMapping(value = RESULT_MOVIE_URL)
    public String searchMovieByTitle(Model model, @PathVariable String movieTitle) {
        logger.info(LOG_URL_REQUEST + RESULT_MOVIE_URL);
        model.addAttribute(MOVIE, movieService.searchMovieByTitle(movieTitle));

        return RESULT;
    }

    @RequestMapping(value = POPULAR_MOVIES_URL)
    public String searchByEmptyTitle(Model model) {
        logger.info(LOG_URL_REQUEST + POPULAR_MOVIES_URL);
        model.addAttribute(MOVIE, movieService.getPopularMovies());

        return RESULT;
    }

    @RequestMapping(MOVIES_URL)
    public String getAllMovies(Model model) {
        logger.info(LOG_URL_REQUEST + MOVIES_URL);
        model.addAttribute(MOVIES, movieService.getAllMovies());

        return MOVIES_PAGE;
    }

    @RequestMapping(DELETE_MOVIE_URL)
    public String removeMovie(Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + DELETE_MOVIE_URL);
        model.addAttribute(MESSAGE, movieService.deteleMovie(code));

        return REDIRECT_MOVIES_PAGE;
    }

    @RequestMapping(value = AUTOCOMPLETE_MOVIES_URL, method = RequestMethod.GET)
    @ResponseBody
    public String searchAutocompleteMovies(@RequestParam String movieTitle) throws JsonProcessingException {
        logger.info(LOG_URL_REQUEST + AUTOCOMPLETE_MOVIES_URL);
        ObjectMapper mapper = new ObjectMapper();
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);

        return mapper.writeValueAsString(movies);
    }

    @RequestMapping(value = RATE_MOVIE, method = RequestMethod.GET)
    @ResponseBody
    public String voteMovie(Principal principal, @RequestParam int rating, @RequestParam String code) {
        logger.info(LOG_URL_REQUEST + RATE_MOVIE);
        String returnPage = EMPTY_STRING;

        if (principal != null) {
            int newRating = ratingService.rateMovie(principal.getName(), code, rating);
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

    @RequestMapping(value = MOVIES_URL, method = RequestMethod.POST)
    public String doAddOrUpdateMovie(Model model, RedirectAttributes redirectAttributes,
                                     @Valid @ModelAttribute(MOVIE) Movie movie) {
        logger.info(LOG_URL_REQUEST + MOVIES_URL);
        String resultPage = EMPTY_STRING;

        if (movie != null) {
            if (movieService.checkIfMovieExists(movie)) {
                redirectAttributes.addFlashAttribute(SUCCESS, false);
                resultPage = REDIRECT_MOVIES_PAGE;
            } else if (movieService.saveOrUpdateMovie(movie)) {
                redirectAttributes.addFlashAttribute(SUCCESS, true);
                resultPage = REDIRECT_MOVIES_PAGE;
            } else {
                redirectAttributes.addFlashAttribute(SUCCESS, false);
                resultPage = REDIRECT_MOVIES_PAGE;
            }
        }

        return resultPage;
    }
}
