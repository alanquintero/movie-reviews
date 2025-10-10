/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import com.moviereviews.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Movie entities.
 *
 * @author Alan Quintero
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("Select m FROM Movie m ORDER BY m.imdbRating DESC")
    List<Movie> findTopRatedMovies(final Pageable pageable);
}
