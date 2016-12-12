/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.LOG_INVALID_INPUT;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.dao.ProfileDao;
import com.alanquintero.mp.dao.UserDao;
import com.alanquintero.mp.dao.VoteDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.entity.Vote;
import com.alanquintero.mp.service.VoteService;
import com.alanquintero.mp.util.Data;
import com.alanquintero.mp.util.Validation;

/**
 * @class VoteServiceImpl.java
 * @purpose Services of Vote section.
 */
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfileDao profileDao;

    private static final Logger logger = Logger.getLogger(VoteServiceImpl.class);

    /**
     * Check if user has already voted, if not it will create a new one else
     * vote will be updated
     * 
     * @param int
     * @param String
     * @return int
     */
    @Override
    public int rateMovie(String userName, String movieEncode, int rating) {
        int newRating = 0;

        if ((Validation.isValidString(userName)) && (Validation.isValidString(movieEncode)) && (rating > 0)) {
            User user = userDao.searchUserByName(userName);
            int movieId = Data.decode(movieEncode);
            Movie movie = movieDao.searchMovieById(movieId);
            Profile profile = null;
            if (user != null) {
                profile = profileDao.searchProfileByUser(user);
            }
            if ((profile != null) && (movie != null)) {
                Vote vote = voteDao.searchVote(profile, movie);
                int numVotes = voteDao.searchMovieVoteByMovie(movie);
                int totalRating = 0;
                for (Vote v : movie.getVotes()) {
                    if (vote != null) {
                        if (v.getId() != vote.getId()) {
                            totalRating += v.getRating();
                        }
                    } else {
                        totalRating += v.getRating();
                    }
                }
                if (vote == null) {
                    vote = new Vote();
                    vote.setMovie(movie);
                    vote.setProfile(profile);
                    numVotes++;
                    movie.setVote(numVotes);
                    newRating = (int) (Math.round(((float) (totalRating + rating)) / numVotes));
                } else {
                    newRating = Math.round(((float) (totalRating + rating)) / numVotes);
                }
                vote.setRating(rating);
                movie.setRating(newRating);
                voteDao.saveOrUpdateVote(vote);
                movieDao.saveOrUpdateMovie(movie);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return newRating;
    }

}
