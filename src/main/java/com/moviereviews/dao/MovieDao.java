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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Review;
import com.moviereviews.repository.MovieRepository;
import com.moviereviews.repository.ReviewRepository;
import com.moviereviews.util.Data;
import com.moviereviews.util.Format;
import com.moviereviews.util.Validation;

@Repository
public class MovieDao {

    private static final Logger logger = LoggerFactory.getLogger(MovieDao.class);

    private final MovieRepository movieRepository;

    private final ReviewRepository reviewRepository;

    public MovieDao(final MovieRepository movieRepository, final ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    public Movie searchMovieById(int movieId) {
        Movie movie = null;

        try {
            Optional<Movie> optionalMovie = movieRepository.findById(movieId);
            if (optionalMovie.isPresent()) {
                movie = optionalMovie.get();
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movie;
    }

    public List<Movie> searchMovieByTitle(String movieTitle) {
        List<Movie> movies = null;

        try {
            movies = movieRepository.findAllMovies(movieTitle);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movies;
    }

    public List<Movie> getPopularMovies() {
        List<Movie> movies = null;

        try {
            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Direction.DESC, RATING_FIELD));
            movies = movieRepository.findPopularMovies(pageRequest);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movies;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = null;

        try {
            movies = movieRepository.findAll();
            logger.info("All movies found: {}", movies.size());
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movies;
    }

    public boolean deteleMovie(int movieId) {
        boolean success = false;

        try {
            movieRepository.deleteById(movieId);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public List<Movie> searchAutocompleteMovies(String movieTitle) {
        List<Movie> movies = null;
        Pageable topSix = PageRequest.of(0, 6);

        try {
            movies = movieRepository.getSearchMovies(movieTitle, topSix);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movies;
    }

    public Movie searchMovieByReview(Review review) {
        Movie movie = null;

        try {
            movie = movieRepository.getMovieByReviews(review);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movie;
    }

    public List<Review> searchReviewsByMovie(Movie movie) {
        List<Review> reviews = null;

        try {
            PageRequest pageRequest = PageRequest.of(0, 15, Sort.by(Direction.DESC, PUBLISHED_DATE_FIELD));
            reviews = reviewRepository.findReviewsByMovie(movie, pageRequest);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return reviews;
    }

    public List<Movie> getMostVotedMovies() {
        List<Movie> movies = null;

        try {
            PageRequest pageRequest = PageRequest.of(0, 15, Sort.by(Direction.DESC, VOTE_FIELD));
            Page<Movie> page = movieRepository.findMostVotedMovies(pageRequest);
            movies = page.getContent();
            logger.info("getMostVotedMovies returned {}", movies.size());
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return movies;
    }

    public boolean saveOrUpdateMovie(Movie movie) {
        boolean success = false;

        try {
            movieRepository.save(movie);
            success = true;
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return success;
    }

    public boolean checkIfMovieExists(Movie movie) {
        boolean exists = true;

        try {
            if (Validation.isValidString(movie.getCode())) {
                if (movieRepository.findMovieByValues(Data.decode(movie.getCode()),
                        Format.removeBlanks(movie.getTitle()), movie.getReleaseYear()) == null) {
                    exists = false;
                }
            } else {
                if (movieRepository.findMovieByTitleAndYear(Format.removeBlanks(movie.getTitle()),
                        movie.getReleaseYear()) == null) {
                    exists = false;
                }
            }
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return exists;
    }
}
