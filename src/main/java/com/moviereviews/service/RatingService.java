/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import static com.moviereviews.util.Consts.LOG_INVALID_INPUT;

import com.moviereviews.entity.AppUser;
import com.moviereviews.entity.Rating;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moviereviews.dao.MovieDao;
import com.moviereviews.dao.ProfileDao;
import com.moviereviews.dao.UserDao;
import com.moviereviews.dao.RatingDao;
import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Profile;
import com.moviereviews.util.Data;
import com.moviereviews.util.Validation;

@Service
@Transactional
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    private final UserDao userDao;

    private final RatingDao ratingDao;

    private final MovieDao movieDao;

    private final ProfileDao profileDao;

    public RatingService(final UserDao userDao, final RatingDao ratingDao, final MovieDao movieDao, final ProfileDao profileDao) {
        this.userDao = userDao;
        this.ratingDao = ratingDao;
        this.movieDao = movieDao;
        this.profileDao = profileDao;
    }

    public int rateMovie(String userName, String movieEncode, int rating) {
        int newRating = 0;

        if ((Validation.isValidString(userName)) && (Validation.isValidString(movieEncode)) && (rating > 0)) {
            AppUser appUser = userDao.searchUserByName(userName);
            int movieId = Data.decode(movieEncode);
            Movie movie = movieDao.searchMovieById(movieId);
            Profile profile = null;
            if (appUser != null) {
                profile = profileDao.searchProfileByUser(appUser);
            }
            if ((profile != null) && (movie != null)) {
                Rating vote = ratingDao.searchRating(profile, movie);
                int numVotes = ratingDao.searchRatingByMovie(movie);
                int totalRating = 0;
                for (Rating v : movie.getRatings()) {
                    if (vote != null) {
                        if (v.getId() != vote.getId()) {
                            totalRating += v.getRating();
                        }
                    } else {
                        totalRating += v.getRating();
                    }
                }
                if (vote == null) {
                    vote = new Rating();
                    vote.setMovie(movie);
                    vote.setProfile(profile);
                    numVotes++;
                    movie.setTotalRating(numVotes);
                    newRating = (int) (Math.round(((float) (totalRating + rating)) / numVotes));
                } else {
                    newRating = Math.round(((float) (totalRating + rating)) / numVotes);
                }
                vote.setRating(rating);
                movie.setRating(newRating);
                ratingDao.saveOrUpdateRating(vote);
                movieDao.saveOrUpdateMovie(movie);
            }
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return newRating;
    }
}
