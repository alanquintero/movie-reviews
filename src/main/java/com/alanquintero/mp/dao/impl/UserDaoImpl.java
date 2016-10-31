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
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.UserDao;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.UserRepository;

/**
 * @class UserDaoImpl.java
 * @purpose Implementation of UserDao Interface.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

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

}
