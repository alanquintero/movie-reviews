/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import static com.moviereviews.util.Consts.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviereviews.entity.AppUser;
import com.moviereviews.service.UserService;

@Controller
@RequestMapping(REGISTER_URL)
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final UserService userService;

    public RegisterController(final UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(USER)
    public AppUser contruct() {
        return new AppUser();
    }

    @RequestMapping
    public String showRegisterPage() {
        return REGISTER_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute(USER) AppUser appUser, BindingResult result,
                             RedirectAttributes redirectAttributes) {
        logger.info(LOG_URL_REQUEST + REGISTER_URL);
        String pageResult = DEFAULT_URL;

        if (result.hasErrors()) {
            pageResult = REGISTER_PAGE;
        } else {
            boolean success = userService.saveUser(appUser);
            if (success == true) {
                redirectAttributes.addFlashAttribute(SUCCESS, true);
                pageResult = REDIRECT_REGISTER_PAGE;
            } else {
                pageResult = REGISTER_PAGE;
            }
        }

        return pageResult;
    }

    @RequestMapping(VALIDATE_USERNAME_URL)
    @ResponseBody
    public String checkUsername(@RequestParam String userName) {
        logger.info(LOG_URL_REQUEST + REGISTER_URL + VALIDATE_USERNAME_URL);
        Boolean existentUserName = userService.searchUserByName(userName) == null;

        return existentUserName.toString();
    }

    @RequestMapping(VALIDATE_EMAIL_URL)
    @ResponseBody
    public String checkUserEmail(@RequestParam String userEmail) {
        logger.info(LOG_URL_REQUEST + REGISTER_URL + VALIDATE_EMAIL_URL);
        Boolean existentEmail = userService.searchUserByEmail(userEmail) == null;

        return existentEmail.toString();
    }

}
