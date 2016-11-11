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
import com.alanquintero.mp.entity.Vote;
import com.alanquintero.mp.repository.ProfileRepository;
import com.alanquintero.mp.repository.ReviewRepository;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.RoleRepository;
import com.alanquintero.mp.repository.UserRepository;
import com.alanquintero.mp.repository.VoteRepository;

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
    
    @Autowired
    private VoteRepository voteRepository;

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
        userAdmin.setEmail("admin@admin.com");
        userRepository.save(userAdmin);

        Profile adminProfile = new Profile();
        adminProfile.setQuote("Where we're going, we don't need roads.");
        adminProfile.setUser(userAdmin);
        profileRepository.save(adminProfile);

        Movie movie1 = new Movie();
        movie1.setTitle("Back to the Future");
        movie1.setImage("http://imgc.allpostersimages.com/images/P-473-488-90/15/1555/PN9DD00Z/posters/back-to-the-future.jpg");
        Vote vote1 = new Vote();
        vote1.setId(5);
        vote1.setMovie(movie1);
        vote1.setProfile(adminProfile);
        vote1.setRating(5);
        List<Vote> votes1 = new ArrayList<Vote>();
        votes1.add(vote1);
        movie1.setVotes(votes1);
        movie1.setVote(1);
        movie1.setRating(5);
        movie1.setYear(1985);
        movie1.setSynopsis("Marty McFly, a 17-year-old high school student, is accidentally sent 30 years"
                + " into the past in a time-traveling DeLorean invented by his close friend, "
                + "the maverick scientist Doc Brown.");
        movie1.setTrailer("https://www.youtube.com/embed/qvsgGtivCgs");
        movieRepository.save(movie1);

        Review review1 = new Review();
        review1.setTitle("Great movie!");
        review1.setComment("I love this movie!");
        review1.setPublishedDate(new Date());
        review1.setMovie(movie1);
        review1.setProfile(adminProfile);
        reviewRepository.save(review1);
        voteRepository.save(vote1);

        User userTest = new User();
        userTest.setName("test");
        userTest.setPassword(encoder.encode("test"));
        roles = new ArrayList<Role>();
        roles.add(roleUser);
        userTest.setRoles(roles);
        userTest.setEnabled(true);
        userTest.setEmail("test@test.com");
        userRepository.save(userTest);

        Profile testProfile = new Profile();
        testProfile.setQuote("Be the force be with you.");
        testProfile.setUser(userTest);
        profileRepository.save(testProfile);

        Movie movie2 = new Movie();
        movie2.setTitle("Star Wars III - Revenge of the Sith");
        movie2.setImage("https://hobbydb-production.s3.amazonaws.com/processed_uploads/subject_photo/subject_photo/image/9760/1450470843-3-0920/Star-Wars-Episode-III-Revenge-of-the-Sith-2005.jpg");
        Vote vote2 = new Vote();
        vote2.setId(4);
        vote2.setMovie(movie2);
        vote2.setProfile(testProfile);
        vote2.setRating(4);
        List<Vote> votes2 = new ArrayList<Vote>();
        votes2.add(vote2);
        movie1.setVotes(votes2);
        movie2.setVote(1);
        movie2.setRating(4);
        movie2.setYear(2005);
        movie2.setSynopsis("During the near end of the clone wars, Darth Sidious has revealed himself"
                + " and is ready to execute the last part of his plan to rule the Galaxy. "
                + "Sidious is ready for his new apprentice, Lord Vader, to step into action "
                + "and kill the remaining Jedi. Vader, however, struggles to choose the dark "
                + "side and save his wife or remain loyal to the Jedi order.");
        movie2.setTrailer("https://www.youtube.com/embed/5UnjrG_N8hU");
        movieRepository.save(movie2);
        voteRepository.save(vote2);

        Review review2 = new Review();
        review2.setTitle("I am your father!");
        review2.setComment("My favorite movie.");
        review2.setPublishedDate(new Date());
        review2.setMovie(movie2);
        review2.setProfile(testProfile);
        reviewRepository.save(review2);

    }

}
