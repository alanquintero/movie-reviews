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

    /**
     * Find all users
     * 
     * @param Model
     * @return String
     */
    @RequestMapping
    public String users(Model model, Principal principal) {
        model.addAttribute(USERS, userService.getAllUsers());
        model.addAttribute(ADMIN, principal.getName());
        return USERS_PAGE;
    }

    /**
     * Find user details by user id
     * 
     * @param Model
     * @param int
     * @return String
     */
    @RequestMapping(USER_URL)
    public String userDetail(Model model, @PathVariable int userId) {
        model.addAttribute(USER, userService.searchUserWithReviewsById(userId));
        return USER_PAGE;
    }

    /**
     * Delete one user by user id
     * 
     * @param int
     * @return String
     */
    @RequestMapping(DELETE_USER_URL)
    public String removeUser(@PathVariable int userId, Model model) {
        model.addAttribute(MESSAGE, userService.deleteUser(userId));
        return DELETE_USER_PAGE;
    }

}
