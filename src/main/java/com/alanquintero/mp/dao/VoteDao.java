/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Vote;

/**
 * @class VoteDao.java
 * @purpose Interface of DAO Layer for Vote Transactions.
 */
public interface VoteDao {

    /**
     * @param Profile
     * @param Movie
     * @return Vote
     */
    public Vote searchVote(Profile profile, Movie movie);

    /**
     * @param Vote
     * @return boolean
     */
    public boolean saveOrUpdateVote(Vote vote);

    /**
     * @param Movie
     * @return int
     */
    public int searchMovieVoteByMovie(Movie movie);

}
