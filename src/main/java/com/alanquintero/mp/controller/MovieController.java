/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;

	@ModelAttribute("movie")
	public Movie contruct() {
		return new Movie();
	}

	@ModelAttribute("review")
	public Review contructReview() {
		return new Review();
	}

	@RequestMapping("/movie/{id}")
	public String movieDetail(Model model, @PathVariable int id) {
		model.addAttribute("movie", movieService.getMovieDetailsById(id));
		return "movie";
	}

	@RequestMapping(value = "/result/{movie}")
	public String searchMovie(Model model, @PathVariable String movie) {
		model.addAttribute("movie", movieService.searchMovie(movie));
		return "result";
	}

	@RequestMapping(value = "/result")
	public String searchEmpty(Model model) {
		model.addAttribute("movie", movieService.popularMovies());
		return "result";
	}

	@RequestMapping("/movies")
	public String getAllMovies(Model model) {
		model.addAttribute("movies", movieService.getAllMovies());
		return "movies";
	}

	@RequestMapping("/movies/remove/{id}")
	public String removeMovie(@PathVariable int id) {
		movieService.deteleMovie(id);
		return "redirect:/movies.html";
	}

	@RequestMapping(value = "/getMovies", method = RequestMethod.GET)
	@ResponseBody
	public String getMovies(@RequestParam String movieName)  throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        List<MovieModel> movies = movieService.getSearchMovies(movieName);
        return mapper.writeValueAsString(movies);
	}

}
