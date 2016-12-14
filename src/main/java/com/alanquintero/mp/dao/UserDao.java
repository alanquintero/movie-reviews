/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao;

import java.util.List;

import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.User;

/**
 * @class UserDao.java
 * @purpose Interface of DAO Layer for User Transactions.
 */
public interface UserDao {

    /**
     * @param String
     * @return User
     */
    public User searchUserByName(String userName);

    /**
     * @return List_User
     */
    public List<User> getAllUsers();

    /**
     * @param int
     * @return User
     */
    public User searchUserById(int userId);

    /**
     * @param User
     * @return boolean
     */
    public boolean saveUser(User user);

    /**
     * @param int
     * @return boolean
     */
    public boolean deleteUser(int userId);

    /**
     * @param String
     * @return User
     */
    public User searchUserByEmail(String userEmail);

    /**
     * @param Profile
     * @return boolean
     */
    public boolean saveOrUpdateQuote(Profile profile);

    /**
     * @param String
     * @param String
     * @return boolean
     */
    public boolean checkUserPassword(String userEmail, String userPassword);

    /**
     * @param User
     * @param String
     * @return boolean
     */
    public boolean updateUserPassword(User user, String newPassword);

    /**
     * @param Profile
     * @return boolean
     */
    public boolean saveProfile(Profile profile);

}
