/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dao;

import static com.moviereviews.util.Consts.LOG_ERROR_DB;

import com.moviereviews.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moviereviews.entity.Profile;
import com.moviereviews.repository.ProfileRepository;

@Repository
public class ProfileDao {

    private static final Logger logger = LoggerFactory.getLogger(ProfileDao.class);

    private final ProfileRepository profileRepository;

    public ProfileDao(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile searchProfileByUser(AppUser appUser) {
        Profile profile = null;
        try {
            profile = profileRepository.findProfileByAppUser(appUser);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }
        return profile;
    }
}
