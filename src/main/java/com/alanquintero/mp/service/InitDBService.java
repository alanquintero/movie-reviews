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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Role;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.ProfileRepository;
import com.alanquintero.mp.repository.ReviewRepository;
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
	private ProfileRepository profileRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;

	
	@PostConstruct
	public void init() {
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		roleRepository.save(roleUser);
		
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);
		
		User userAdmin = new User();
		userAdmin.setName("admin");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userAdmin.setPassword(encoder.encode("admin"));
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleAdmin);
		roles.add(roleUser);
		userAdmin.setRoles(roles);
		userRepository.save(userAdmin);
		
		Profile alanProfile = new Profile();
		alanProfile.setQuote("Where we're going, we don't need roads.");
		alanProfile.setUser(userAdmin);
		profileRepository.save(alanProfile);
		
		Movie movie1 = new Movie();
		movie1.setTitle("Back to the Future");
		movie1.setRating(0.0);
		movie1.setYear(1985);
		movie1.setDescription("Marty McFly, a 17-year-old high school student, is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his close friend, the maverick scientist Doc Brown.");
		movieRepository.save(movie1);
		
		Review review1 = new Review();
		review1.setComment("I love this movie!");
		review1.setPublishedDate(new Date());
		review1.setRating(5);
		review1.setMovie(movie1);
		review1.setProfile(alanProfile);
		reviewRepository.save(review1);
		
	}

}
