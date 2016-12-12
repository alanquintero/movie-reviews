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

import org.apache.log4j.Logger;
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
import com.alanquintero.mp.util.Data;
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

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    /**
     * Get all Users
     * 
     * @return List_User
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();

        users = Validation.validateUserList(users);
        Data.encodeUserList(users);

        return users;
    }

    /**
     * Search a User
     * 
     * @param String
     * @return User
     */
    @Override
    public User searchUserById(String userCode) {
        int userId = Data.decode(userCode);
        User user = userDao.searchUserById(userId);

        if (user == null) {
            user = Message.setUserFail();
            logger.info(LOG_INVALID_INPUT);
        }
        user.setCode(Data.encode(user.getId()));

        return user;
    }

    /**
     * Set User details
     * 
     * @param String
     * @return User
     */
    @Override
    @Transactional
    public User searchUserWithReviewsById(String userCode) {
        User user = null;

        user = searchUserById(userCode);
        if (user != null) {
            Profile profile = profileDao.searchProfileByUser(user);
            if (profile != null) {
                List<Review> reviews = reviewDao.searchReviewsByProfile(profile);
                if (reviews != null) {
                    for (Review review : reviews) {
                        Movie movie = movieDao.searchMovieByReview(review);
                        movie.setCode(Data.encode(movie.getId()));
                        review.setMovie(movie);
                    }
                    Data.encodeReviewList(reviews);
                    profile.setReview(reviews);
                }
                profile.setCode(Data.encode(profile.getId()));
                user.setProfile(profile);
            }
        } else {
            user = Message.setUserFail();
            logger.info(LOG_INVALID_INPUT);
        }
        user.setCode(Data.encode(user.getId()));

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
            Profile profile = new Profile();
            profile.setQuote(EMPTY_STRING);
            if (userDao.saveUser(user)) {
                User newUser = searchUserWithReviewsByName(user.getName());
                profile.setUser(newUser);
                success = userDao.saveProfile(profile);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
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
                userResponse = searchUserWithReviewsById(Data.encode(user.getId()));
                userResponse.setCode(Data.encode(user.getId()));
            }
        }
        if (userResponse == null) {
            userResponse = Message.setUserFail();
            userResponse.setCode(EMPTY_STRING);
            logger.info(LOG_INVALID_INPUT);
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
    public String deleteUser(String userCode) {
        boolean success = false;
        User user = searchUserById(userCode);

        if (!user.getName().equals(MSG_FAIL)) {
            success = userDao.deleteUser(user.getId());
        } else {
            logger.info(LOG_INVALID_INPUT);
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

    /**
     * Add or Update a Quote
     * 
     * @param User
     * @param String
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateQuote(User user, String userName) {
        boolean success = false;

        if ((Validation.isValidString(user.getProfile().getCode()))
                && (Validation.isValidString(user.getProfile().getQuote())) && (Validation.isValidString(userName))) {
            User existentUser = userDao.searchUserByName(userName);
            Profile profile = null;
            if (existentUser != null) {
                profile = profileDao.searchProfileByUser(existentUser);
                if (profile != null) {
                    profile.setQuote(user.getProfile().getQuote());
                    userDao.saveOrUpdateQuote(profile);
                    success = true;
                }
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }

    /**
     * Check that User Password is correct
     * 
     * @param String
     * @param String
     * @return User
     */
    @Override
    public boolean checkUserPassword(String userEmail, String userPassword) {
        return userDao.checkUserPassword(userEmail, userPassword);
    }

    /**
     * Update User Password
     * 
     * @param String
     * @param String
     * @return boolean
     */
    @Override
    public boolean updateUserPassword(String userName, String newPassword) {
        boolean success = false;

        if ((Validation.isValidString(userName)) && (Validation.validateWordLen(newPassword, 6))) {
            User user = userDao.searchUserByName(userName);
            if (user != null) {
                success = userDao.updateUserPassword(user, newPassword);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }

}
