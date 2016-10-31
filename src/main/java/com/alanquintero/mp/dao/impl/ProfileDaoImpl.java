/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.ProfileDao;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.repository.ProfileRepository;

/**
 * @class ProfileDaoImpl.java
 * @purpose Implementation of ProfileDao Interface.
 */
@Repository
public class ProfileDaoImpl implements ProfileDao {

    @Autowired
    ProfileRepository profileRepository;

    /**
     * Search a Profile by User
     * 
     * @param User
     * @return Profile
     */
    @Override
    public Profile searchProfileByUser(User user) {
        Profile profile = null;
        try {
            profile = profileRepository.findProfileByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }
}