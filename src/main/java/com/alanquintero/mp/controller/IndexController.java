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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @class IndexController.java
 * @purpose Controller for Default Pages.
 */
@Controller
public class IndexController {

    /**
     * Redirect to home page
     * 
     * @return String
     */
    @RequestMapping(DEFAULT_URL)
    public String home() {
        return INDEX_PAGE;
    }

    /**
     * Redirect to index page
     * 
     * @return String
     */
    @RequestMapping(INDEX_URL)
    public String index() {
        return INDEX_PAGE;
    }

}
