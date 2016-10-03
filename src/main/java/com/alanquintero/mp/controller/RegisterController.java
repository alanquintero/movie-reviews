/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

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

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public User contruct() {
		return new User();
	}

	@RequestMapping
	public String showRegisterPage() {
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}
		userService.saveUser(user);
		return "redirect:/register.html?success=true";
	}

	@RequestMapping("/checkusername")
	@ResponseBody
	public String checkUsername(@RequestParam String userName) {
		Boolean existentUserName = userService.findUserName(userName) == null;
		return existentUserName.toString();
	}

	@RequestMapping("/checkemail")
	@ResponseBody
	public String checkEmail(@RequestParam String email) {
		Boolean existentEmail = userService.findEmail(email) == null;
		return existentEmail.toString();
	}

}
