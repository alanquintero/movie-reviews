/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alanquintero.mp.service.UserService;
import static com.alanquintero.mp.util.Consts.*;

/**
 * AdminController.java 
 * Purpose: Controller for User.
 */
@Controller
@RequestMapping(USERS_URL)
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Find all users
     * 
     * @param Model_Object
     * @return String
     */
    @RequestMapping
    public String users(Model model) {
        model.addAttribute(USERS, userService.getAllUsers());
        return USERS_PAGE;
    }

    /**
     * Find user details by user id
     * 
     * @param Model_Object
     * @param User_id
     * @return String
     */
    @RequestMapping(USER_URL)
    public String userDetail(Model model, @PathVariable int id) {
        model.addAttribute(USER, userService.getUserWithReviews(id));
        return USER_PAGE;
    }

    /**
     * Delete one user by user id
     * 
     * @param User_id
     * @return String
     */
    @RequestMapping(DELETE_USER_URL)
    public String removeUser(@PathVariable int id) {
        userService.deleteUser(id);
        return DELETE_USER_PAGE;
    }

}
