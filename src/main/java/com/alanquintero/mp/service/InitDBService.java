/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Blog;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Role;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.BlogRepository;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.RoleRepository;
import com.alanquintero.mp.repository.UserRepository;

@Transactional
@Service
public class InitDBService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private BlogRepository blogRepository;

	@PostConstruct
	public void init() {
		Role roleUser = new Role();
		roleUser.setRoleName("ROLE_USER");
		roleRepository.save(roleUser);
		
		Role roleAdmin = new Role();
		roleAdmin.setRoleName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);
		
		User userAdmin = new User();
		userAdmin.setName("admin");
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleAdmin);
		roles.add(roleUser);
		userAdmin.setRoles(roles);
		userRepository.save(userAdmin);
		
		Blog blogAlan = new Blog();
		blogAlan.setName("Alan Quintero");
		blogAlan.setUrl("https://mx.linkedin.com/in/alan-quintero-benítez-a7770a87");
		blogAlan.setUser(userAdmin);
		blogRepository.save(blogAlan);
		
		Movie movie1 = new Movie();
		movie1.setTitle("Back to the Future");
		movie1.setRating(5);
		movie1.setComment("It is my favorite movie");
		movie1.setPublishedDate(new Date());
		movie1.setBlog(blogAlan);
		movieRepository.save(movie1);
		
		Movie movie2 = new Movie();
		movie2.setTitle("Star Wars");
		movie2.setRating(5);
		movie2.setComment("I love this movie");
		movie2.setPublishedDate(new Date());
		movie2.setBlog(blogAlan);
		movieRepository.save(movie2);
		
	}

}
