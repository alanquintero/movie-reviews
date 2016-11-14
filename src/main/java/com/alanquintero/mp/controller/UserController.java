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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.service.ReviewService;
import com.alanquintero.mp.service.UserService;

/**
 * @class UserController.java
 * @purpose Controller for User.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

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
     * Redirect to user profile
     * 
     * @param Model
     * @param Principal
     * @return String
     */
    @RequestMapping(PROFILE_URL)
    public String profile(Model model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute(USER, userService.searchUserWithReviewsByName(userName));
        return PROFILE_PAGE;
    }

    /**
     * Delete one review by review id
     * 
     * @param int
     * @return String
     */
    @RequestMapping(DELETE_PROFILE_URL)
    public String removeReview(@PathVariable int reviewId, Model model) {
        Review review = reviewService.searchReviewById(reviewId);
        model.addAttribute(MESSAGE, reviewService.deteleReview(review));
        return REDIRECT_PROFILE_PAGE;
    }

    /**
     * Add or Update a Review
     * 
     * @param Model
     * @param Review
     * @param BindingResult
     * @param Principal
     * @return String
     */
    @RequestMapping(value = { MOVIE_URL, PROFILE_URL }, method = RequestMethod.POST)
    public String doAddOrUpdateReview(Model model, @Valid @ModelAttribute(REVIEW) Review review, BindingResult result,
            Principal principal) {
        String resultPage = EMPTY_STRING;
        if (result.hasErrors()) {
            MovieController movieController = new MovieController();
            return movieController.searchMovieDetails(model, review.getMovie().getId());
        }
        String userName = principal.getName();
        reviewService.saveOrUpdateReview(review, userName);
        if (review.getId() > 0) {
            resultPage = REDIRECT_PROFILE_PAGE;
        } else {
            resultPage = REDIRECT_MOVIE_PAGE;
        }
        return resultPage;
    }

    /**
     * Add one new review from result page
     * 
     * @param Review
     * @param Principal
     * @return String
     */
    @RequestMapping(value = RESULT_MOVIE_URL, method = RequestMethod.POST)
    public String doAddReviewResult(@ModelAttribute(REVIEW) Review review, Principal principal, Model model) {
        String userName = principal.getName();
        model.addAttribute(MESSAGE, reviewService.saveOrUpdateReview(review, userName));
        return REDIRECT_RESULT_MOVIE_PAGE;
    }

}
