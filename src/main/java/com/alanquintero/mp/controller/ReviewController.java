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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@ModelAttribute("review")
	public Review contructReview(){
		return new Review();
	}
	
	@RequestMapping("profile/remove/{id}")
	public String removeReview(@PathVariable int id){
		Review review = reviewService.findOne(id);
		reviewService.deteleReview(review);
		return "redirect:/profile.html";
	}
	
	
}
