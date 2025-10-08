/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dao;

import static com.moviereviews.util.Consts.LOG_ERROR_DB;

import java.util.List;
import java.util.Optional;

import com.moviereviews.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.moviereviews.entity.Profile;
import com.moviereviews.repository.ProfileRepository;
import com.moviereviews.repository.UserRepository;

@Repository
public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    public UserDao(final UserRepository userRepository, final ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AppUser searchUserByName(String userName) {
        AppUser appUser = null;

        try {
            appUser = userRepository.findUserByUserName(userName);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return appUser;
    }

    public List<AppUser> getAllUsers() {
        List<AppUser> appUsers = null;

        try {
            appUsers = userRepository.findAll();
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return appUsers;
    }

    public AppUser searchUserById(int userId) {
        AppUser appUser = null;

        try {
            Optional<AppUser> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                appUser = optionalUser.get();
            }

        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return appUser;
    }

    public boolean saveUser(AppUser appUser) {
        boolean success = false;

        try {
            userRepository.save(appUser);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public boolean deleteUser(int userId) {
        boolean success = false;

        try {
            userRepository.deleteById(userId);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public AppUser searchUserByEmail(String userEmail) {
        AppUser appUser = null;

        try {
            appUser = userRepository.findUserByEmail(userEmail);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return appUser;
    }

    public boolean saveOrUpdateQuote(Profile profile) {
        boolean success = false;

        try {
            profileRepository.save(profile);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public boolean checkUserPassword(String userEmail, String userPassword) {
        boolean success = false;

        try {
            AppUser appUser = userRepository.findUserByEmail(userEmail);
            if ((appUser != null) && (encoder.matches(userPassword, appUser.getPassword()))) {
                success = true;
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public boolean updateUserPassword(AppUser appUser, String newPassword) {
        boolean success = false;

        try {
            appUser.setPassword(encoder.encode(newPassword));
            userRepository.save(appUser);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public boolean saveProfile(Profile profile) {
        boolean success = false;

        try {
            profileRepository.save(profile);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

}
