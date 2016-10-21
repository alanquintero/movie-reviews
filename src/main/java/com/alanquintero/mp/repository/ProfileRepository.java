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

import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.User;

/**
 * @class ProfileRepository.java
 * @purpose Get Profile information from DB.
 */
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    /**
     * Find one Profile by User
     * 
     * @param User_Object
     * @return Profile_Object
     */
    public Profile findProfileByUser(User user);

}
