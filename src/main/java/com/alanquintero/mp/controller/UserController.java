/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public User contruct(){
		return new User();
	}
	
	@RequestMapping("/users")
	public String users(Model model){
		model.addAttribute("users",userService.getAllUsers());
		return "users";
	}
	
	@RequestMapping("/users/{id}")
	public String userDetail(Model model, @PathVariable int id){
		model.addAttribute("user", userService.getUserWithReviews(id));
		return "user-detail";
	}
	
	
	@RequestMapping("/register")
	public String showRegisterPage(){
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String doRegister(@ModelAttribute("user") User user){
		userService.saveUser(user);
		return "redirect:/register.html?success=true";
	}
	
	@RequestMapping("/profile")
	public String profile(Model model, Principal principal){
		String name = principal.getName();
		model.addAttribute("user", userService.getUserWithReviews(name));
		return "profile";
	}
	
	@RequestMapping("/users/remove/{id}")
	public String removeUser(@PathVariable int id){
		userService.deleteUser(id);
		return "redirect:/users.html";
	}
	
}
