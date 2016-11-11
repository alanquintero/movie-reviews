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

import com.alanquintero.mp.entity.Vote;

/**
 * @class VoteRepository.java
 * @purpose Get Vote information from DB.
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.profile.id = :profile_id AND v.movie.id = :movie_id")
    public Vote findVoteByProfileAndMovie(@Param(PROFILE_ID_PARAM) int profileId, @Param(MOVIE_ID_PARAM) int movieId);
    
    @Query("SELECT m.vote FROM Movie m WHERE m.id = :movie_id")
    public int findMovieVoteByMovieId(@Param(MOVIE_ID_PARAM) int movieId);

}
