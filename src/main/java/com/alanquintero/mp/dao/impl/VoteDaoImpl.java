/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao.impl;

import static com.alanquintero.mp.util.Consts.LOG_ERROR_DB;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.VoteDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Vote;
import com.alanquintero.mp.repository.VoteRepository;

/**
 * @class VoteDaoImpl.java
 * @purpose Implementation of VoteDao Interface.
 */
@Repository
public class VoteDaoImpl implements VoteDao {

    @Autowired
    private VoteRepository voteRepository;

    private static Logger logger = Logger.getLogger(VoteDaoImpl.class);

    /**
     * Search a specific Vote of Movie by User
     * 
     * @param Profile
     * @param Movie
     * @return Vote
     */
    @Override
    public Vote searchVote(Profile profile, Movie movie) {
        Vote vote = null;

        try {
            vote = voteRepository.findVoteByProfileAndMovie(profile.getId(), movie.getId());
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return vote;
    }

    /**
     * Save or Update Vote of Movie By User
     * 
     * @param Vote
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateVote(Vote vote) {
        boolean success = false;

        try {
            voteRepository.save(vote);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    /**
     * Search number of votes in a Movie
     * 
     * @param Movie
     * @return int
     */
    @Override
    public int searchMovieVoteByMovie(Movie movie) {
        int vote = 0;

        try {
            vote = voteRepository.findMovieVoteByMovieId(movie.getId());
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return vote;
    }

}
