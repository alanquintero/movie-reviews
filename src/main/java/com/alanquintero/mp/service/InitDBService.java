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
		userAdmin.setEnabled(true);
		userRepository.save(userAdmin);
		
		Profile adminProfile = new Profile();
		adminProfile.setQuote("Where we're going, we don't need roads.");
		adminProfile.setUser(userAdmin);
		profileRepository.save(adminProfile);
		
		Movie movie1 = new Movie();
		movie1.setTitle("Back to the Future");
		movie1.setRating(10.0);
		movie1.setYear(1985);
		movie1.setStoryline("Marty McFly, a 17-year-old high school student, is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his close friend, the maverick scientist Doc Brown.");
		movieRepository.save(movie1);
		
		Review review1 = new Review();
		review1.setTitle("Great movie!");
		review1.setComment("I love this movie!");
		review1.setPublishedDate(new Date());
		review1.setRating(10);
		review1.setMovie(movie1);
		review1.setProfile(adminProfile);
		reviewRepository.save(review1);
		
		
		User userTest = new User();
		userTest .setName("test");
		userTest .setPassword(encoder.encode("test"));
		roles = new ArrayList<Role>();
		roles.add(roleUser);
		userTest.setRoles(roles);
		userTest.setEnabled(true);
		userRepository.save(userTest);
		
		Profile testProfile = new Profile();
		testProfile.setQuote("Be the force be with you.");
		testProfile.setUser(userTest);
		profileRepository.save(testProfile);
		
		Movie movie2 = new Movie();
		movie2.setTitle("Star Wars III - Revenge of the Sith");
		movie2.setRating(9.0);
		movie2.setYear(2005);
		movie2.setStoryline("During the near end of the clone wars, Darth Sidious has revealed himself and is ready to execute the last part of his plan to rule the Galaxy. Sidious is ready for his new apprentice, Lord Vader, to step into action and kill the remaining Jedi. Vader, however, struggles to choose the dark side and save his wife or remain loyal to the Jedi order.");
		movieRepository.save(movie2);
		
		Review review2 = new Review();
		review2.setTitle("I am your father!");
		review2.setComment("My favorite movie.");
		review2.setPublishedDate(new Date());
		review2.setRating(9);
		review2.setMovie(movie2);
		review2.setProfile(testProfile);
		reviewRepository.save(review2);
		
		
	}

}
