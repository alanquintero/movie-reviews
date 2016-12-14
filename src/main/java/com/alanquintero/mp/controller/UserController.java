/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import static com.alanquintero.mp.util.Consts.*;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.service.ReviewService;
import com.alanquintero.mp.service.UserService;
import com.alanquintero.mp.util.Validation;

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

    private static Logger logger = Logger.getLogger(UserController.class);

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
     * @param Principal
     * @param Model
     * @return String
     */
    @RequestMapping(PROFILE_URL)
    public String profile(Principal principal, Model model) {
        logger.info(LOG_URL_REQUEST + PROFILE_URL);
        String userName = principal.getName();

        model.addAttribute(USER, userService.searchUserWithReviewsByName(userName));

        return PROFILE_PAGE;
    }

    /**
     * Delete one review by review id
     *
     * @param Model
     * @param int
     * @return String
     */
    @RequestMapping(DELETE_PROFILE_URL)
    public String removeReview(Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + DELETE_PROFILE_URL);
        Review review = reviewService.searchReviewById(code);

        model.addAttribute(MESSAGE, reviewService.deteleReview(review));

        return REDIRECT_PROFILE_PAGE;
    }

    /**
     * Add or Update a Review or Quote
     * 
     * @param Principal
     * @param Model
     * @param Review
     * @param User
     * @param BindingResult
     * @return String
     */
    @RequestMapping(value = { MOVIE_URL, PROFILE_URL }, method = RequestMethod.POST)
    public String doAddOrUpdateReviewOrQuote(Principal principal, Model model,
            @Valid @ModelAttribute(REVIEW) Review review, @ModelAttribute(USER) User user, BindingResult result) {
        String resultPage = EMPTY_STRING;
        String userName = principal.getName();

        if (review != null && Validation.isValidString(review.getComment())) {
            if (result.hasErrors()) {
                MovieController movieController = new MovieController();
                return movieController.searchMovieDetails(model, review.getMovie().getCode());
            }
            String inPage = reviewService.saveOrUpdateReview(review, userName);
            if (inPage.equals(IN_PROFILE)) {
                logger.info(LOG_URL_REQUEST + PROFILE_URL);
                resultPage = REDIRECT_PROFILE_PAGE;
            } else {
                logger.info(LOG_URL_REQUEST + MOVIE_URL);
                resultPage = REDIRECT_MOVIE_PAGE;
            }
        } else if (user != null && user.getProfile() != null) {
            if (userService.saveOrUpdateQuote(user, userName)) {
                resultPage = REDIRECT_PROFILE_SUCCESS_PAGE;
            } else {
                resultPage = REDIRECT_PROFILE_FAIL_PAGE;
            }
        } else {
            resultPage = REDIRECT_PROFILE_FAIL_PAGE;
        }

        return resultPage;

    }

    /**
     * Add one new review from result page
     * 
     * @param Principal
     * @param Model
     * @param Review
     * @return String
     */
    @RequestMapping(value = RESULT_MOVIE_URL, method = RequestMethod.POST)
    public String doAddReviewResult(Principal principal, Model model, @ModelAttribute(REVIEW) Review review) {
        logger.info(LOG_URL_REQUEST + RESULT_MOVIE_URL);
        String userName = principal.getName();

        model.addAttribute(MESSAGE, reviewService.saveOrUpdateReview(review, userName));

        return REDIRECT_RESULT_MOVIE_PAGE;
    }

    /**
     * Redirect to user settings
     * 
     * @param Principal
     * @param Model
     * @return String
     */
    @RequestMapping(SETTINGS_URL)
    public String settings(Principal principal, Model model) {
        logger.info(LOG_URL_REQUEST + SETTINGS_URL);
        String userName = principal.getName();

        model.addAttribute(USER, userService.searchUserWithReviewsByName(userName));

        return SETTINGS_PAGE;
    }

    /**
     * Check if Password is correct
     * 
     * @param String
     * @param String
     * @return String
     */
    @RequestMapping(value = CHECK_PWD_URL, method = RequestMethod.POST)
    @ResponseBody
    public String checkUserPassword(@RequestParam String userEmail, @RequestParam String userPassword) {
        logger.info(LOG_URL_REQUEST + CHECK_PWD_URL);
        Boolean correctPwd = userService.checkUserPassword(userEmail, userPassword);

        return correctPwd.toString();
    }

    /**
     * Update User Password
     * 
     * @param Principal
     * @param User
     * @return String
     */
    @RequestMapping(value = SETTINGS_URL, method = RequestMethod.POST)
    public String updatePassword(Principal principal, @ModelAttribute(USER) User user) {
        logger.info(LOG_URL_REQUEST + SETTINGS_URL);
        String resultPage = EMPTY_STRING;
        String userName = principal.getName();

        if (userService.updateUserPassword(userName, user.getNewPassword())) {
            resultPage = REDIRECT_SETTINGS_SUCCESS_PAGE;
        } else {
            resultPage = REDIRECT_SETTINGS_FAIL_PAGE;
        }

        return resultPage;
    }

    /**
     * Find user details by user id
     * 
     * @param Principal
     * @param Model
     * @param String
     * @return String
     */
    @RequestMapping(USERS_URL + USER_URL)
    public String userDetail(Principal principal, Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + USERS_URL + USER_URL);
        String resultPage = EMPTY_STRING;
        String userName = principal.getName();
        User user = userService.searchUserWithReviewsById(code);

        model.addAttribute(USER, user);
        if (user.getName().equals(userName)) {
            resultPage = PROFILE_PAGE;
        } else {
            resultPage = USER_PAGE;
        }

        return resultPage;
    }

}
