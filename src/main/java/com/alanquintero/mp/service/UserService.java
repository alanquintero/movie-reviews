/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.Role;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.MovieRepository;
import com.alanquintero.mp.repository.ProfileRepository;
import com.alanquintero.mp.repository.ReviewRepository;
import com.alanquintero.mp.repository.RoleRepository;
import com.alanquintero.mp.repository.UserRepository;
import static com.alanquintero.mp.util.Consts.*;

/**
 * UserService.java 
 * Purpose: Services of User section.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Find all users
     * 
     * @return List_User
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find one user
     * 
     * @param User_id
     * @return User_Object
     */
    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

    /**
     * Set own details to user
     * 
     * @param User_id
     * @return User_Object
     */
    @Transactional
    public User getUserWithReviews(int id) {
        User user = getUserById(id);
        Profile profile = profileRepository.getProfileByUser(user);

        List<Review> reviews = reviewRepository.getReviewsByProfile(profile,
                new PageRequest(0, 10, Direction.DESC, PUBLISHED_DATE_FIELD));
        for (Review review : reviews) {
            Movie movie = movieRepository.getMovieByReviews(review);
            review.setMovie(movie);
        }
        profile.setReview(reviews);
        user.setProfile(profile);

        return user;
    }

    /**
     * Add one user
     * 
     * @param User_Object
     */
    public void saveUser(User user) {
        user.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<Role>();
        roles.add(roleRepository.findByName(ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
    }

    /**
     * Find one user with review details
     * 
     * @param User_username
     * @return User_Object
     */
    @Transactional
    public User getUserWithReviews(String name) {
        User user = userRepository.getUserByName(name);
        return getUserWithReviews(user.getId());
    }

    /**
     * Delete one user
     * 
     * @param User_id
     */
    @PreAuthorize(HAS_ROLE_ADMIN)
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    /**
     * Find user name to validate if exists or not
     * 
     * @param User_username
     * @return User_Object
     */
    public User findUserName(String userName) {
        return userRepository.getUserByName(userName);
    }

    /**
     * Find email to validate if exists or not
     * 
     * @param User_username
     * @return User_Object
     */
    public User findEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
