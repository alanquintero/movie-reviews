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

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.service.MovieService;
import com.alanquintero.mp.service.ReviewService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ReviewService reviewService;
	
	@ModelAttribute("movie")
	public Movie contruct(){
		return new Movie();
	}
	
	@ModelAttribute("review")
	public Review contructReview(){
		return new Review();
	}
	
	@RequestMapping("/movie/{id}")
	public String movieDetail(Model model, @PathVariable int id){
		model.addAttribute("movie", movieService.getMovieDetailsById(id));
		return "movie";
	}
	
	@RequestMapping(value="/result/{movie}")
	public String searchMovie(Model model, @PathVariable String movie){
		model.addAttribute("movie", movieService.searchMovie(movie));
		return "result";
	}
	
	@RequestMapping(value="/result")
	public String searchEmpty(Model model){
		model.addAttribute("movie", movieService.popularMovies());
		return "result";
	}
	
	@RequestMapping(value="/movie/{id}", method=RequestMethod.POST)
	public String doAddReview(@ModelAttribute("review") Review review, Principal principal){
		String name = principal.getName();
		reviewService.saveReview(review, name);
		return "redirect:/movie/{id}.html";
	}
	
	@RequestMapping(value="/result/{movie}",  method=RequestMethod.POST)
	public String doAddReviewResult(@ModelAttribute("review") Review review, Principal principal){
		System.out.println("other here");
		String name = principal.getName();
		reviewService.saveReview(review, name);
		return "redirect:/result/{movie}.html";
	}
	
	@RequestMapping("/movies")
	public String getAllMovies(Model model){
		model.addAttribute("movies",movieService.getAllMovies());
		return "movies";
	}
	
	@RequestMapping("/movies/remove/{id}")
	public String removeMovie(@PathVariable int id){
		movieService.deteleMovie(id);
		return "redirect:/movies.html";
	}
	
}
