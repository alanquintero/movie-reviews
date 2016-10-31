/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao;

import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.User;

/**
 * @class ProfileDao.java
 * @purpose Interface of DAO Layer for Profile Transactions.
 */
public interface ProfileDao {

    /**
     * @param User
     * @return Profile
     */
    public Profile searchProfileByUser(User user);
}
