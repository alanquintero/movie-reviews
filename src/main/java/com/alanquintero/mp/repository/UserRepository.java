/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alanquintero.mp.entity.User;
import static com.alanquintero.mp.util.Consts.*;

/**
 * UserRepository.java 
 * Purpose: Get User information from DB.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find one user by user name
     * 
     * @param User_username
     * @return User_Object
     */
    public User getUserByName(String name);

    /**
     * Find one user by email
     * 
     * @param User_email
     * @return User_Object
     */
    @Query("SELECT u FROM User u " + "WHERE lower(u.email) like lower(:user_email) ")
    public User getUserByEmail(@Param(USER_EMAIL_PARAM) String email);

}
