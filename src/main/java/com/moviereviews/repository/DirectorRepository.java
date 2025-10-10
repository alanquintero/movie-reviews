/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.repository;

import com.moviereviews.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Director entities.
 *
 * @author Alan Quintero
 */
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
