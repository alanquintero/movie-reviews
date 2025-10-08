/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import static com.moviereviews.util.Consts.*;

import java.security.Principal;

import com.moviereviews.entity.AppUser;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviereviews.entity.Review;
import com.moviereviews.service.ReviewService;
import com.moviereviews.service.UserService;
import com.moviereviews.util.Validation;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final ReviewService reviewService;

    private final MovieController movieController;

    public UserController(final UserService userService, final ReviewService reviewService, final MovieController movieController) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.movieController = movieController;
    }

    @ModelAttribute(REVIEW)
    public Review contructReview() {
        return new Review();
    }

    @RequestMapping(PROFILE_URL)
    public String profile(Principal principal, Model model) {
        logger.info(LOG_URL_REQUEST + PROFILE_URL);
        String userName = principal.getName();

        model.addAttribute(USER, userService.searchUserWithReviewsByName(userName));

        return PROFILE_PAGE;
    }

    @RequestMapping(DELETE_PROFILE_URL)
    public String removeReview(Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + DELETE_PROFILE_URL);
        Review review = reviewService.searchReviewById(code);

        model.addAttribute(MESSAGE, reviewService.deteleReview(review));

        return REDIRECT_PROFILE_PAGE;
    }

    @RequestMapping(value = {MOVIE_URL, PROFILE_URL}, method = RequestMethod.POST)
    public String doAddOrUpdateReviewOrQuote(Principal principal, Model model,
                                             @Valid @ModelAttribute(REVIEW) Review review, @ModelAttribute(USER) AppUser appUser, BindingResult result,
                                             RedirectAttributes redirectAttributes) {
        String resultPage = EMPTY_STRING;
        String userName = principal.getName();

        if (review != null && Validation.isValidString(review.getComment())) {
            if (result.hasErrors()) {
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
        } else if (appUser != null && appUser.getProfile() != null) {
            if (userService.saveOrUpdateQuote(appUser, userName)) {
                redirectAttributes.addFlashAttribute(SUCCESS, true);
                resultPage = REDIRECT_PROFILE_PAGE;
            } else {
                redirectAttributes.addFlashAttribute(false);
                resultPage = REDIRECT_PROFILE_PAGE;
            }
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS, false);
            resultPage = REDIRECT_PROFILE_PAGE;
        }

        return resultPage;

    }

    @RequestMapping(value = RESULT_MOVIE_URL, method = RequestMethod.POST)
    public String doAddReviewResult(Principal principal, Model model, @ModelAttribute(REVIEW) Review review) {
        logger.info(LOG_URL_REQUEST + RESULT_MOVIE_URL);
        String userName = principal.getName();

        model.addAttribute(MESSAGE, reviewService.saveOrUpdateReview(review, userName));

        return REDIRECT_RESULT_MOVIE_PAGE;
    }

    @RequestMapping(SETTINGS_URL)
    public String settings(Principal principal, Model model) {
        logger.info(LOG_URL_REQUEST + SETTINGS_URL);
        String userName = principal.getName();

        model.addAttribute(USER, userService.searchUserWithReviewsByName(userName));

        return SETTINGS_PAGE;
    }

    @RequestMapping(value = CHECK_PWD_URL, method = RequestMethod.POST)
    @ResponseBody
    public String checkUserPassword(@RequestParam String userEmail, @RequestParam String userPassword) {
        logger.info(LOG_URL_REQUEST + CHECK_PWD_URL);
        Boolean correctPwd = userService.checkUserPassword(userEmail, userPassword);

        return correctPwd.toString();
    }

    @RequestMapping(value = SETTINGS_URL, method = RequestMethod.POST)
    public String updatePassword(Principal principal, @ModelAttribute(USER) AppUser appUser,
                                 RedirectAttributes redirectAttributes) {
        logger.info(LOG_URL_REQUEST + SETTINGS_URL);
        String resultPage = EMPTY_STRING;
        String userName = principal.getName();

        if (userService.updateUserPassword(userName, appUser.getNewPassword())) {
            redirectAttributes.addFlashAttribute(SUCCESS, true);
            resultPage = REDIRECT_SETTINGS_PAGE;
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS, false);
            resultPage = REDIRECT_SETTINGS_PAGE;
        }

        return resultPage;
    }

    @RequestMapping(USERS_URL + USER_URL)
    public String userDetail(Principal principal, Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + USERS_URL + USER_URL);
        String resultPage = EMPTY_STRING;
        String userName = principal.getName();
        AppUser appUser = userService.searchUserWithReviewsById(code);

        model.addAttribute(USER, appUser);
        if (appUser.getUserName().equals(userName)) {
            resultPage = PROFILE_PAGE;
        } else {
            resultPage = USER_PAGE;
        }

        return resultPage;
    }
}
