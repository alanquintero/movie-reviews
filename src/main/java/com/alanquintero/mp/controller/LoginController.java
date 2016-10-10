/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.alanquintero.mp.util.Consts.*;

/**
 * LoginController.java 
 * Purpose: Controller for Login.
 */
@Controller
public class LoginController {

    /**
     * Redirect to login page
     * 
     * @return String
     */
    @RequestMapping(LOGIN_URL)
    public String login() {
        return LOGIN_PAGE;
    }

}
