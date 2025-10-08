/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import static com.moviereviews.util.Consts.*;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.moviereviews.dao.MovieDao;
import com.moviereviews.dao.ProfileDao;
import com.moviereviews.dao.ReviewDao;
import com.moviereviews.dao.UserDao;
import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Profile;
import com.moviereviews.entity.Review;
import com.moviereviews.entity.AppUser;
import com.moviereviews.util.Data;
import com.moviereviews.util.Message;
import com.moviereviews.util.Validation;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    private final UserDao userDao;

    private final MovieDao movieDao;

    private final ReviewDao reviewDao;

    private final ProfileDao profileDao;

    public ReviewService(final UserDao userDao, final MovieDao movieDao, final ReviewDao reviewDao, final ProfileDao profileDao) {
        this.userDao = userDao;
        this.movieDao = movieDao;
        this.reviewDao = reviewDao;
        this.profileDao = profileDao;
    }

    public String saveOrUpdateReview(Review review, String userName) {
        String inPage = EMPTY_STRING;

        if (((review != null) && (Validation.isValidString(review.getTitle())
                && (Validation.isValidString(review.getComment())) && (review.getMovie() != null)))
                && (Validation.isValidString(userName))) {
            AppUser appUser = userDao.searchUserByName(userName);
            Profile profile = null;
            if (appUser != null) {
                profile = profileDao.searchProfileByUser(appUser);
            }
            if (!Validation.isValidString(review.getCode())) {
                review.setId(null);
            } else {
                review.setId(Data.decode(review.getCode()));
                inPage = IN_PROFILE;
            }
            review.setCode(EMPTY_STRING);
            review.setPublishedDate(new Date());
            Movie movie = movieDao.searchMovieById(Data.decode(review.getMovie().getCode()));
            if ((Validation.isValidString(review.getComment())) && (profile != null) && (movie != null)) {
                review.setProfile(profile);
                review.setMovie(movie);
                reviewDao.saveOrUpdateReview(review);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return inPage;
    }

    @PreAuthorize(USERNAME_OR_ADMIN)
    public String deteleReview(@P(REVIEW) Review review) {
        boolean success = false;

        if ((review != null) && (Validation.isValidString(review.getCode()))) {
            review.setId(Data.decode(review.getCode()));
            success = reviewDao.deteleReview(review);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return Message.setSuccessOrFail(success);
    }

    public Review searchReviewById(String reviewCode) {
        Review review = reviewDao.searchReviewById(Data.decode(reviewCode));

        if (review == null) {
            review = Message.setReviewFail();
            logger.info(LOG_INVALID_INPUT);
        }
        review.setCode(Data.encode(review.getId()));

        return review;
    }

}
