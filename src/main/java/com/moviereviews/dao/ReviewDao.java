/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dao;

import static com.moviereviews.util.Consts.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.moviereviews.entity.Profile;
import com.moviereviews.entity.Review;
import com.moviereviews.repository.ReviewRepository;

@Repository
public class ReviewDao {

    private static final Logger logger = LoggerFactory.getLogger(ReviewDao.class);

    private final ReviewRepository reviewRepository;

    public ReviewDao(final ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean saveOrUpdateReview(Review review) {
        boolean success = false;

        try {
            reviewRepository.save(review);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public boolean deteleReview(Review review) {
        boolean success = false;

        try {
            reviewRepository.delete(review);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public Review searchReviewById(int reviewId) {
        Review review = null;

        try {
            Optional<Review> optionalReview = reviewRepository.findById(reviewId);
            if (optionalReview.isPresent()) {
                review = optionalReview.get();
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return review;
    }

    public List<Review> searchReviewsByProfile(Profile profile) {
        List<Review> reviews = null;

        try {
            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Direction.DESC, PUBLISHED_DATE_FIELD));
            reviews = reviewRepository.findReviewsByProfile(profile, pageRequest);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }
        return reviews;
    }
}
