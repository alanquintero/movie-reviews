/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Profile;
import com.moviereviews.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findReviewsByProfile(Profile profile, Pageable pageable);

    List<Review> findReviewsByMovie(Movie movie, Pageable pageable);
}
