/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.UserDao;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.ProfileRepository;
import com.alanquintero.mp.repository.UserRepository;

/**
 * @class UserDaoImpl.java
 * @purpose Implementation of UserDao Interface.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Search User by User Name
     * 
     * @param String
     * @return User
     */
    @Override
    public User searchUserByName(String userName) {
        User user = null;
        try {
            user = userRepository.findUserByName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Obtain all Users existent
     * 
     * @return List_User
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Search User by User Id
     * 
     * @param int
     * @return User
     */
    @Override
    public User searchUserById(int userId) {
        User user = null;
        try {
            user = userRepository.findOne(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Save User
     * 
     * @param User
     * @return boolean
     */
    @Override
    public boolean saveUser(User user) {
        boolean success = false;
        try {
            userRepository.save(user);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Delete a User by User Id
     * 
     * @param int
     * @return boolean
     */
    @Override
    public boolean deleteUser(int userId) {
        boolean success = false;
        try {
            userRepository.delete(userId);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Search User by User Email
     * 
     * @param String
     * @return User
     */
    @Override
    public User searchUserByEmail(String userEmail) {
        User user = null;
        try {
            user = userRepository.findUserByEmail(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Add or Update a Quote
     * 
     * @param Profile
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateQuote(Profile profile) {
        boolean success = false;
        try {
            profileRepository.save(profile);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Check that User Password is correct or not
     * 
     * @param String
     * @param String
     * @return User
     */
    @Override
    public boolean checkUserPassword(String userEmail, String userPassword) {
        boolean success = false;
        try {
            User user = userRepository.findUserByEmail(userEmail);
            if ((user != null) && (encoder.matches(userPassword, user.getPassword()))) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Update User Password
     * 
     * @param User
     * @param String
     * @return boolean
     */
    @Override
    public boolean updateUserPassword(User user, String newPassword) {
        boolean success = false;
        try {
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Save Profile
     * 
     * @param Profile
     * @return boolean
     */
    @Override
    public boolean saveProfile(Profile profile) {
        boolean success = false;
        try {
            profileRepository.save(profile);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

}
