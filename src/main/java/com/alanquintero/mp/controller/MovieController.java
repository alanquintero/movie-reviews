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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.service.MovieService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@ModelAttribute("movie")
	public Movie contruct(){
		return new Movie();
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
	
}
