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

@Controller
@RequestMapping("/users")
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping
	public String users(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	@RequestMapping("/{id}")
	public String userDetail(Model model, @PathVariable int id) {
		model.addAttribute("user", userService.getUserWithReviews(id));
		return "user-detail";
	}

	@RequestMapping("/remove/{id}")
	public String removeUser(@PathVariable int id) {
		userService.deleteUser(id);
		return "redirect:/users.html";
	}

}
