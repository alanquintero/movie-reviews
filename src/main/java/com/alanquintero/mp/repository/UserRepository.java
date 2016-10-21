/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import static com.alanquintero.mp.util.Consts.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alanquintero.mp.entity.User;

/**
 * @class UserRepository.java
 * @purpose Get User information from DB.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find one User by User Name
     * 
     * @param String
     * @return User
     */
    public User findUserByName(String userName);

    /**
     * Find one User by User Email
     * 
     * @param String
     * @return User
     */
    @Query("SELECT u FROM User u " + "WHERE lower(u.email) like lower(:user_email)")
    public User findUserByEmail(@Param(USER_EMAIL_PARAM) String userEmail);

}
