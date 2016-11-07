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
import com.alanquintero.mp.util.Validation;

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
    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        users = Validation.validateUserList(users);
        return users;
    }

    /**
     * Search a User
     * 
     * @param int
     * @return User
     */
    @Override
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
    @Override
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
    @Override
    public boolean saveUser(User user) {
        boolean success = false;
        if ((user != null)
                && ((Validation.isValidString(user.getName())
                        && (Validation.validateWordLen(user.getName(), USER_LENGTH)))
                        && (Validation.isValidString(user.getEmail()) && (Validation.validateEmail(user.getEmail())))
                        && (Validation.isValidString(user.getPassword()))
                        && (Validation.validateWordLen(user.getPassword(), PWD_LENGTH)))
                && ((searchUserByName(user.getName()) == null) && (searchUserByEmail(user.getEmail()) == null))) {
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
    @Override
    @Transactional
    public User searchUserWithReviewsByName(String userName) {
        User user = null;
        User userResponse = null;
        if (Validation.isValidString(userName)) {
            user = userDao.searchUserByName(userName);
            if (user != null) {
                userResponse = searchUserWithReviewsById(user.getId());
            }
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
    @Override
    @PreAuthorize(HAS_ROLE_ADMIN)
    public String deleteUser(int userId) {
        boolean success = false;
        if (userId > 0) {
            User user = searchUserById(userId);
            if (!user.getName().equals(MSG_FAIL)) {
                success = userDao.deleteUser(userId);
            }
        }
        return Message.setSuccessOrFail(success);
    }

    /**
     * Search User Name to validate if it exists or not
     * 
     * @param String
     * @return User
     */
    @Override
    public User searchUserByName(String userName) {
        return userDao.searchUserByName(userName);
    }

    /**
     * Search User Email to validate if it exists or not
     * 
     * @param String
     * @return User
     */
    @Override
    public User searchUserByEmail(String userEmail) {
        return userDao.searchUserByEmail(userEmail);
    }

}
