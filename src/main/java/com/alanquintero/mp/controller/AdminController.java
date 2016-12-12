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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alanquintero.mp.service.UserService;

/**
 * @class AdminController.java
 * @purpose Controller for User transactions.
 */
@Controller
@RequestMapping(USERS_URL)
public class AdminController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(AdminController.class);

    /**
     * Find all Users
     * 
     * @param Principal
     * @param Model
     * @return String
     */
    @RequestMapping
    public String users(Principal principal, Model model) {
        logger.info(LOG_URL_REQUEST + USERS_URL);
        model.addAttribute(ADMIN, principal.getName());
        model.addAttribute(USERS, userService.getAllUsers());

        return USERS_PAGE;
    }

    /**
     * Delete one User by user id
     * 
     * @param Model
     * @param String
     * @return String
     */
    @RequestMapping(DELETE_USER_URL)
    public String removeUser(Model model, @PathVariable String code) {
        logger.info(LOG_URL_REQUEST + DELETE_USER_URL);
        model.addAttribute(MESSAGE, userService.deleteUser(code));

        return DELETE_USER_PAGE;
    }

}
