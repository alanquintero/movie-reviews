/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import static com.moviereviews.util.Consts.*;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviereviews.service.UserService;


@Controller
@RequestMapping(USERS_URL)
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;

    public AdminController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String users(Principal principal, Model model) {
        logger.info(LOG_URL_REQUEST + USERS_URL);
        model.addAttribute(ADMIN, principal.getName());
        model.addAttribute(USERS, userService.getAllUsers());

        return USERS_PAGE;
    }

    @RequestMapping(DELETE_USER_URL)
    public String removeUser(Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + DELETE_USER_URL);
        model.addAttribute(MESSAGE, userService.deleteUser(code));

        return REDIRECT_USERS_PAGE;
    }
}
