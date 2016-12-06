/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service;

/**
 * @class VoteService.java
 * @purpose Interface of Vote Layer for Vote Transactions.
 */
public interface VoteService {

    /**
     * @param String
     * @param String
     * @param int
     * @return int
     */
    public int rateMovie(String userName, String movieEncode, int rating);

}
