/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviereviews.entity.Profile;
import com.moviereviews.entity.AppUser;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findProfileByAppUser(final AppUser appUser);
}
