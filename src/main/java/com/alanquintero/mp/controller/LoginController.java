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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @class LoginController.java
 * @purpose Controller for Login transactions.
 */
@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    /**
     * Redirect to login page
     * 
     * @return String
     */
    @RequestMapping(LOGIN_URL)
    public String login() {
        logger.info(LOG_URL_REQUEST + LOGIN_URL);

        return LOGIN_PAGE;
    }

}
