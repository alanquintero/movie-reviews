/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import static com.moviereviews.util.Consts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.moviereviews.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviereviews.dao.MovieDao;
import com.moviereviews.entity.Movie;
import com.moviereviews.entity.Profile;
import com.moviereviews.entity.Review;
import com.moviereviews.model.MovieModel;
import com.moviereviews.util.Data;
import com.moviereviews.util.Format;
import com.moviereviews.util.Message;
import com.moviereviews.util.Validation;

@Service
@Transactional
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieDao movieDao;

    public MovieService(final MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Transactional
    public Movie searchMovieById(String movieCode) {
        Movie movie = movieDao.searchMovieById(Data.decode(movieCode));

        if (movie == null) {
            movie = Message.setMovieNotFound();
            logger.info(LOG_INVALID_INPUT);
        }
        movie.setCode(Data.encode(movie.getId()));

        return movie;
    }

    public Movie searchMovieDetailsById(String movieCode) {
        Movie movie = searchMovieById(movieCode);

        if ((movie != null) && (Validation.isValidString(movie.getCode()))) {
            movie.setId(Data.decode(movie.getCode()));
            List<Review> reviews = movieDao.searchReviewsByMovie(movie);
            List<AppUser> appUsers = new ArrayList<AppUser>();
            ListIterator<Review> iterator = reviews.listIterator();

            while (iterator.hasNext()) {
                Review review = iterator.next();
                Profile profile = review.getProfile();
                AppUser appUser = profile.getUser();

                profile.setCode(Data.encode(profile.getId()));
                appUser.setCode(Data.encode(appUser.getId()));

                profile.setUser(appUser);
                review.setProfile(profile);
            }
            Data.encodeUserList(appUsers);
            Data.encodeReviewList(reviews);
            movie.setReviews(reviews);
        } else {
            movie = Message.setMovieNotFound();
            logger.info(LOG_INVALID_INPUT);
        }
        movie.setCode(Data.encode(movie.getId()));

        return movie;
    }

    @Transactional
    public List<Movie> searchMovieByTitle(String movieTitle) {
        List<Movie> movies = new ArrayList<Movie>();

        if (Validation.isValidString(movieTitle)) {
            movies = movieDao.searchMovieByTitle(PERCENT + movieTitle + PERCENT);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }
        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    @Transactional
    public List<Movie> getPopularMovies() {
        List<Movie> movies = movieDao.getPopularMovies();

        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDao.getAllMovies();

        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    @PreAuthorize(HAS_ROLE_ADMIN)
    public String deteleMovie(String movieCode) {
        boolean success = false;
        int movieId = Data.decode(movieCode);

        if (movieDao.searchMovieById(movieId) != null) {
            success = movieDao.deteleMovie(movieId);
        }

        return Message.setSuccessOrFail(success);
    }

    public List<MovieModel> searchAutocompleteMovies(String movieTitle) {
        List<Movie> movies = new ArrayList<Movie>();
        List<MovieModel> moviesModel = new ArrayList<MovieModel>();

        if (Validation.isValidString(movieTitle)) {
            movies = movieDao.searchAutocompleteMovies(PERCENT + movieTitle + PERCENT);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }
        if ((movies != null) && (!movies.isEmpty())) {
            for (Movie m : movies) {
                moviesModel.add(new MovieModel(Data.encode(m.getId()),
                        m.getTitle() + PARENTHESIS_OPEN + m.getReleaseYear() + PARENTHESIS_CLOSE));
            }
        }

        return moviesModel;
    }

    @Transactional
    public List<Movie> getMostVotedMovies() {
        List<Movie> movies = movieDao.getAllMovies();

        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    public boolean saveOrUpdateMovie(Movie movie) {
        boolean success = false;

        if (Validation.isValidString(movie.getImageUrl()) && Validation.isValidString(movie.getTrailerUrl())
                && Validation.isValidString(movie.getSynopsis())) {
            Movie movieToSave = new Movie();
            if (!Validation.isValidString(movie.getCode())) {
                movieToSave.setRating(0);
                movieToSave.setTotalRating(0);
                movieToSave.setId(0);
            } else {
                movieToSave = searchMovieDetailsById(movie.getCode());
            }
            movieToSave.setCode(EMPTY_STRING);
            movieToSave.setTitle(Format.removeBlanks(movie.getTitle()));
            movieToSave.setReleaseYear(movie.getReleaseYear());
            if (Validation.isValidURL(movie.getImageUrl())) {
                movieToSave.setImageUrl(movie.getImageUrl());
            } else {
                movieToSave.setImageUrl(MSG_INVALID_URL);
            }
            movieToSave.setSynopsis(movie.getSynopsis());
            if ((Validation.isValidURL(movie.getTrailerUrl())) && (!movie.getTrailerUrl().contains(FORMAT_YT_EMBED))) {
                movieToSave.setTrailerUrl(Format.getYoutubeUrl(movie.getTrailerUrl()));
            } else {
                movieToSave.setTrailerUrl(MSG_INVALID_URL);
            }
            success = movieDao.saveOrUpdateMovie(movieToSave);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }

    public boolean checkIfMovieExists(Movie movie) {
        boolean exists = true;

        if (Validation.isValidString(movie.getTitle())) {
            exists = movieDao.checkIfMovieExists(movie);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return exists;
    }

}
