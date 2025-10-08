/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import static com.moviereviews.util.Consts.*;

import java.util.ArrayList;
import java.util.List;

import com.moviereviews.entity.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.moviereviews.dao.MovieDao;
import com.moviereviews.dao.ProfileDao;
import com.moviereviews.dao.ReviewDao;
import com.moviereviews.dao.RoleDao;
import com.moviereviews.dao.UserDao;
import com.moviereviews.entity.AppUser;
import com.moviereviews.util.Data;
import com.moviereviews.util.Message;
import com.moviereviews.util.Validation;


@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final RoleDao roleDao;

    private final UserDao userDao;

    private final MovieDao movieDao;

    private final ReviewDao reviewDao;

    private final ProfileDao profileDao;

    public UserService(final RoleDao roleDao, final UserDao userDao, final MovieDao movieDao, final ReviewDao reviewDao, final ProfileDao profileDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.movieDao = movieDao;
        this.reviewDao = reviewDao;
        this.profileDao = profileDao;
    }

    public List<AppUser> getAllUsers() {
        List<AppUser> appUsers = userDao.getAllUsers();

        appUsers = Validation.validateUserList(appUsers);
        Data.encodeUserList(appUsers);

        return appUsers;
    }

    public AppUser searchUserById(String userCode) {
        int userId = Data.decode(userCode);
        AppUser appUser = userDao.searchUserById(userId);

        if (appUser == null) {
            appUser = Message.setUserFail();
            logger.info(LOG_INVALID_INPUT);
        }
        appUser.setCode(Data.encode(appUser.getId()));

        return appUser;
    }

    @Transactional
    public AppUser searchUserWithReviewsById(String userCode) {
        AppUser appUser = searchUserById(userCode);
        if (appUser != null) {
            Profile profile = profileDao.searchProfileByUser(appUser);
            if (profile != null) {
                List<Review> reviews = reviewDao.searchReviewsByProfile(profile);
                if (reviews != null) {
                    for (Review review : reviews) {
                        Movie movie = movieDao.searchMovieByReview(review);
                        movie.setCode(Data.encode(movie.getId()));
                        review.setMovie(movie);
                    }
                    Data.encodeReviewList(reviews);
                    profile.setReview(reviews);
                }
                profile.setCode(Data.encode(profile.getId()));
                appUser.setProfile(profile);
            }
        } else {
            appUser = Message.setUserFail();
            logger.info(LOG_INVALID_INPUT);
        }
        appUser.setCode(Data.encode(appUser.getId()));

        return appUser;
    }

    public boolean saveUser(AppUser appUser) {
        boolean success = false;

        if ((appUser != null)
                && ((Validation.isValidString(appUser.getUserName())
                && (Validation.validateWordLen(appUser.getUserName(), USER_LENGTH)))
                && (Validation.isValidString(appUser.getEmail()) && (Validation.validateEmail(appUser.getEmail())))
                && (Validation.isValidString(appUser.getPassword()))
                && (Validation.validateWordLen(appUser.getPassword(), PWD_LENGTH)))
                && ((searchUserByName(appUser.getUserName()) == null) && (searchUserByEmail(appUser.getEmail()) == null))) {
            appUser.setEnabled(true);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            appUser.setPassword(encoder.encode(appUser.getPassword()));
            List<UserRole> userRoles = new ArrayList<UserRole>();
            userRoles.add(roleDao.searchRoleByRoleName(ROLE_USER));
            appUser.setRoles(userRoles);
            Profile profile = new Profile();
            profile.setQuote(EMPTY_STRING);
            if (userDao.saveUser(appUser)) {
                AppUser newAppUser = searchUserWithReviewsByName(appUser.getUserName());
                profile.setUser(newAppUser);
                success = userDao.saveProfile(profile);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }

    @Transactional
    public AppUser searchUserWithReviewsByName(String userName) {
        AppUser appUser = null;
        AppUser appUserResponse = null;

        if (Validation.isValidString(userName)) {
            appUser = userDao.searchUserByName(userName);
            if (appUser != null) {
                appUserResponse = searchUserWithReviewsById(Data.encode(appUser.getId()));
                appUserResponse.setCode(Data.encode(appUser.getId()));
            }
        }
        if (appUserResponse == null) {
            appUserResponse = Message.setUserFail();
            appUserResponse.setCode(EMPTY_STRING);
            logger.info(LOG_INVALID_INPUT);
        }

        return appUserResponse;
    }

    @PreAuthorize(HAS_ROLE_ADMIN)
    public String deleteUser(String userCode) {
        boolean success = false;
        AppUser appUser = searchUserById(userCode);

        if (!appUser.getUserName().equals(MSG_FAIL)) {
            success = userDao.deleteUser(appUser.getId());
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return Message.setSuccessOrFail(success);
    }

    public AppUser searchUserByName(String userName) {
        return userDao.searchUserByName(userName);
    }

    public AppUser searchUserByEmail(String userEmail) {
        return userDao.searchUserByEmail(userEmail);
    }

    public boolean saveOrUpdateQuote(AppUser appUser, String userName) {
        boolean success = false;

        if ((Validation.isValidString(appUser.getProfile().getCode()))
                && (Validation.isValidString(appUser.getProfile().getQuote())) && (Validation.isValidString(userName))) {
            AppUser existentAppUser = userDao.searchUserByName(userName);
            Profile profile = null;
            if (existentAppUser != null) {
                profile = profileDao.searchProfileByUser(existentAppUser);
                if (profile != null) {
                    profile.setQuote(appUser.getProfile().getQuote());
                    userDao.saveOrUpdateQuote(profile);
                    success = true;
                }
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }

    public boolean checkUserPassword(String userEmail, String userPassword) {
        return userDao.checkUserPassword(userEmail, userPassword);
    }

    public boolean updateUserPassword(String userName, String newPassword) {
        boolean success = false;

        if ((Validation.isValidString(userName)) && (Validation.validateWordLen(newPassword, 6))) {
            AppUser appUser = userDao.searchUserByName(userName);
            if (appUser != null) {
                success = userDao.updateUserPassword(appUser, newPassword);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }
}
