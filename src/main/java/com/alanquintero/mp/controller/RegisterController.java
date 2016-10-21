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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.service.UserService;

/**
 * @class RegisterController.java
 * @purpose Controller for Register.
 */
@Controller
@RequestMapping(REGISTER_URL)
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * Construct user object model
     * 
     * @return User
     */
    @ModelAttribute(USER)
    public User contruct() {
        return new User();
    }

    /**
     * Redirect to register page
     * 
     * @return String
     */
    @RequestMapping
    public String showRegisterPage() {
        return REGISTER_PAGE;
    }

    /**
     * Register one new user
     * 
     * @param User
     * @param BindingResult
     * @return String
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute(USER) User user, BindingResult result) {
        String pageResult = DEFAULT_URL;
        if (result.hasErrors()) {
            pageResult = REGISTER_PAGE;
        } else {
            userService.saveUser(user);
            pageResult = REGISTER_SUCCESS_PAGE;
        }
        return pageResult;
    }

    /**
     * Check if user name exists
     * 
     * @param String
     * @return String
     */
    @RequestMapping(VALIDATE_USERNAME_URL)
    @ResponseBody
    public String checkUsername(@RequestParam String userName) {
        Boolean existentUserName = userService.searchUserByName(userName) == null;
        return existentUserName.toString();
    }

    /**
     * Check if email exists
     * 
     * @param String
     * @return String
     */
    @RequestMapping(VALIDATE_EMAIL_URL)
    @ResponseBody
    public String checkEmail(@RequestParam String userEmail) {
        Boolean existentEmail = userService.searchUserByEmail(userEmail) == null;
        return existentEmail.toString();
    }

}
