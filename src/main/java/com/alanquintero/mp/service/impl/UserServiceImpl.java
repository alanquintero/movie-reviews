/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.dao.ProfileDao;
import com.alanquintero.mp.dao.ReviewDao;
import com.alanquintero.mp.dao.RoleDao;
import com.alanquintero.mp.dao.UserDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.Role;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.service.UserService;
import com.alanquintero.mp.util.Message;

/**
 * @class UserService.java
 * @purpose Services of User section.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * Get all Users
     * 
     * @return List_User
     */
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        if (users == null) {
            User user = Message.setUserFail();
            users = new ArrayList<User>();
            users.add(user);
        }
        return users;
    }

    /**
     * Search a User
     * 
     * @param int
     * @return User
     */
    public User searchUserById(int userId) {
        User user = userDao.searchUserById(userId);
        if (user == null) {
            user = Message.setUserFail();
        }
        return user;
    }

    /**
     * Set User details
     * 
     * @param int
     * @return User
     */
    @Transactional
    public User searchUserWithReviewsById(int userId) {
        User user = null;
        if (userId != 0) {
            user = searchUserById(userId);
            if (user != null) {
                Profile profile = profileDao.searchProfileByUser(user);
                if (profile != null) {
                    List<Review> reviews = reviewDao.searchReviewsByProfile(profile);
                    if (reviews != null) {
                        for (Review review : reviews) {
                            Movie movie = movieDao.searchMovieByReview(review);
                            review.setMovie(movie);
                        }
                        profile.setReview(reviews);
                    }
                    user.setProfile(profile);
                }
            }
        }
        if (user == null) {
            user = Message.setUserFail();
        }

        return user;
    }

    /**
     * Add a User
     * 
     * @param User
     * @return String
     */
    public boolean saveUser(User user) {
        boolean success = false;
        if (user != null) {
            user.setEnabled(true);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            List<Role> roles = new ArrayList<Role>();
            roles.add(roleDao.searchRoleByRoleName(ROLE_USER));
            user.setRoles(roles);
            success = userDao.saveUser(user);
        }
        return success;
    }

    /**
     * Search a User with Reviews by User Name
     * 
     * @param String
     * @return User
     */
    @Transactional
    public User searchUserWithReviewsByName(String userName) {
        User user = userDao.searchUserByName(userName);
        User userResponse = null;
        if (user != null) {
            userResponse = searchUserWithReviewsById(user.getId());
        }
        if (userResponse == null) {
            userResponse = Message.setUserFail();
        }
        return userResponse;
    }

    /**
     * Delete a User
     * 
     * @param int
     * @return String
     */
    @PreAuthorize(HAS_ROLE_ADMIN)
    public String deleteUser(int userId) {
        boolean success = false;
        if (userId != 0) {
            success = userDao.deleteUser(userId);
        }
        return Message.setSuccessOrFail(success);
    }

    /**
     * Search User Name to validate if it exists or not
     * 
     * @param String
     * @return User
     */
    public User searchUserByName(String userName) {
        return userDao.searchUserByName(userName);
    }

    /**
     * Search User Email to validate if it exists or not
     * 
     * @param String
     * @return User
     */
    public User searchUserByEmail(String userEmail) {
        return userDao.searchUserByEmail(userEmail);
    }

}
