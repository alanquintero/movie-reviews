/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import static com.moviereviews.util.Consts.*;

import com.moviereviews.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findUserByUserName(String userName);

    @Query("SELECT u FROM AppUser u WHERE lower(u.email) LIKE lower(:" + USER_EMAIL_PARAM + ")")
    AppUser findUserByEmail(@Param(USER_EMAIL_PARAM) String userEmail);

}
