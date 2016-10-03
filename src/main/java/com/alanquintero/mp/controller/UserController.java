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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.service.ReviewService;
import com.alanquintero.mp.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@ModelAttribute("review")
	public Review contructReview() {
		return new Review();
	}

	@RequestMapping("/profile")
	public String profile(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.getUserWithReviews(name));
		return "profile";
	}

	@RequestMapping("profile/remove/{id}")
	public String removeReview(@PathVariable int id) {
		Review review = reviewService.findOne(id);
		reviewService.deteleReview(review);
		return "redirect:/profile.html";
	}

	@RequestMapping(value = "/movie/{id}", method = RequestMethod.POST)
	public String doAddReview(Model model, @Valid @ModelAttribute("review") Review review, BindingResult result,
			Principal principal) {
		if (result.hasErrors()) {
			MovieController movieController = new MovieController();
			return movieController.movieDetail(model, review.getMovie().getId());
		}
		String name = principal.getName();
		reviewService.saveReview(review, name);
		return "redirect:/movie/{id}.html";
	}

	@RequestMapping(value = "/result/{movie}", method = RequestMethod.POST)
	public String doAddReviewResult(@ModelAttribute("review") Review review, Principal principal) {
		String name = principal.getName();
		reviewService.saveReview(review, name);
		return "redirect:/result/{movie}.html";
	}

}
