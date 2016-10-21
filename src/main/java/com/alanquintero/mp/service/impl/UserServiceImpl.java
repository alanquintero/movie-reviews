/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

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
import com.alanquintero.mp.service.UserService;

import static com.alanquintero.mp.util.Consts.*;

/**
 * UserService.java Purpose: Services of User section.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

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
     * Get all Users
     * 
     * @return List_User
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Search a User
     * 
     * @param int
     * @return User
     */
    public User searchUserById(int userId) {
        return userRepository.findOne(userId);
    }

    /**
     * Set User details
     * 
     * @param int
     * @return User
     */
    @Transactional
    public User searchUserWithReviewsById(int userId) {
        User user = searchUserById(userId);
        Profile profile = profileRepository.findProfileByUser(user);

        List<Review> reviews = reviewRepository.findReviewsByProfile(profile,
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
     * Add a User
     * 
     * @param User
     */
    public void saveUser(User user) {
        user.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<Role>();
        roles.add(roleRepository.findRoleByName(ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
    }

    /**
     * Search a User with Reviews by User Name
     * 
     * @param String
     * @return User
     */
    @Transactional
    public User searchUserWithReviewsByName(String userName) {
        User user = userRepository.findUserByName(userName);
        return searchUserWithReviewsById(user.getId());
    }

    /**
     * Delete a User
     * 
     * @param int
     */
    @PreAuthorize(HAS_ROLE_ADMIN)
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    /**
     * Search User Name to validate if it exists or not
     * 
     * @param String
     * @return User
     */
    public User searchUserByName(String userName) {
        return userRepository.findUserByName(userName);
    }

    /**
     * Search User Email to validate if it exists or not
     * 
     * @param String
     * @return User
     */
    public User searchUserByEmail(String userEmail) {
        return userRepository.findUserByEmail(userEmail);
    }

}
