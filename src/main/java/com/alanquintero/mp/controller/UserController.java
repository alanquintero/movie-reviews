/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

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
import static com.alanquintero.mp.util.Consts.*;

/**
 * UserController.java 
 * Purpose: Controller for User.
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
     * @return Review_Object
     */
    @ModelAttribute(REVIEW)
    public Review contructReview() {
        return new Review();
    }

    /**
     * Redirect to user profile
     * 
     * @param Model_Object
     * @param Principal_Object
     * @return String
     */
    @RequestMapping(PROFILE_URL)
    public String profile(Model model, Principal principal) {
        String name = principal.getName();
        model.addAttribute(USER, userService.getUserWithReviews(name));
        return PROFILE_PAGE;
    }

    /**
     * Delete one review by review id
     * 
     * @param Review_id
     * @return String
     */
    @RequestMapping(DELETE_PROFILE_URL)
    public String removeReview(@PathVariable int id) {
        Review review = reviewService.findOne(id);
        reviewService.deteleReview(review);
        return REDIRECT_PROFILE_PAGE;
    }

    /**
     * Add one new review
     * 
     * @param Model_Object
     * @param Review_Object
     * @param BindingResult_Object
     * @param Principal_Object
     * @return String
     */
    @RequestMapping(value = MOVIE_URL, method = RequestMethod.POST)
    public String doAddReview(Model model, @Valid @ModelAttribute(REVIEW) Review review, BindingResult result,
            Principal principal) {
        if (result.hasErrors()) {
            MovieController movieController = new MovieController();
            return movieController.movieDetail(model, review.getMovie().getId());
        }
        String name = principal.getName();
        reviewService.saveReview(review, name);
        return REDIRECT_MOVIE_PAGE;
    }

    /**
     * Add one new review from result page
     * 
     * @param Review_Object
     * @param Principal_Object
     * @return String
     */
    @RequestMapping(value = RESULT_MOVIE_URL, method = RequestMethod.POST)
    public String doAddReviewResult(@ModelAttribute(REVIEW) Review review, Principal principal) {
        String name = principal.getName();
        reviewService.saveReview(review, name);
        return REDIRECT_RESULT_MOVIE_PAGE;
    }

}
