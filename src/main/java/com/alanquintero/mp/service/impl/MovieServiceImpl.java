/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alanquintero.mp.dao.MovieDao;
import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.entity.Profile;
import com.alanquintero.mp.entity.Review;
import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;
import com.alanquintero.mp.util.Data;
import com.alanquintero.mp.util.Format;
import com.alanquintero.mp.util.Message;
import com.alanquintero.mp.util.Validation;

/**
 * @class MovieServiceImpl.java
 * @purpose Implementation of MovieService interface.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    private static Logger logger = Logger.getLogger(MovieServiceImpl.class);

    /**
     * Search Movie By Movie Id
     * 
     * @param String
     * @return Movie
     */
    @Override
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

    /**
     * Search Movie details by Movie Id
     * 
     * @param String
     * @return Movie
     */
    @Override
    public Movie searchMovieDetailsById(String movieCode) {
        Movie movie = searchMovieById(movieCode);

        if ((movie != null) && (Validation.isValidString(movie.getCode()))) {
            movie.setId(Data.decode(movie.getCode()));
            List<Review> reviews = movieDao.searchReviewsByMovie(movie);
            List<User> users = new ArrayList<User>();
            ListIterator<Review> iterator = reviews.listIterator();

            while (iterator.hasNext()) {
                Review review = iterator.next();
                Profile profile = review.getProfile();
                User user = profile.getUser();

                profile.setCode(Data.encode(profile.getId()));
                user.setCode(Data.encode(user.getId()));

                profile.setUser(user);
                review.setProfile(profile);
            }
            Data.encodeUserList(users);
            Data.encodeReviewList(reviews);
            movie.setReviews(reviews);
        } else {
            movie = Message.setMovieNotFound();
            logger.info(LOG_INVALID_INPUT);
        }
        movie.setCode(Data.encode(movie.getId()));

        return movie;
    }

    /**
     * Search a list of Movies by Movie Title provided
     * 
     * @param String
     * @return List_Movie
     */
    @Override
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

    /**
     * Search a list of Top popular Movies
     * 
     * @return List_Movie
     */
    @Override
    @Transactional
    public List<Movie> getPopularMovies() {
        List<Movie> movies = movieDao.getPopularMovies();

        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    /**
     * Search all Movies
     * 
     * @return List_Movie
     */
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDao.getAllMovies();

        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    /**
     * Delete a Movie by Movie Id
     * 
     * @param String
     * @return String
     */
    @Override
    @PreAuthorize(HAS_ROLE_ADMIN)
    public String deteleMovie(String movieCode) {
        boolean success = false;
        int movieId = Data.decode(movieCode);

        if (movieDao.searchMovieById(movieId) != null) {
            success = movieDao.deteleMovie(movieId);
        }

        return Message.setSuccessOrFail(success);
    }

    /**
     * Search a list of Movies by Movie Title
     * 
     * @param String
     * @return List_Movie
     */
    @Override
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
                        m.getTitle() + PARENTHESIS_OPEN + m.getYear() + PARENTHESIS_CLOSE));
            }
        }

        return moviesModel;
    }

    /**
     * Search a list of Most Voted Movies
     * 
     * @return List_Movie
     */
    @Override
    @Transactional
    public List<Movie> getMostVotedMovies() {
        List<Movie> movies = movieDao.getMostVotedMovies();

        movies = Validation.validateMovieList(movies);
        Data.encodeMovieList(movies);

        return movies;
    }

    /**
     * Add or Update a Movie
     * 
     * @param Movie
     * @return String
     */
    @Override
    public boolean saveOrUpdateMovie(Movie movie) {
        boolean success = false;

        if (Validation.isValidString(movie.getImage()) && Validation.isValidString(movie.getTrailer())
                && Validation.isValidString(movie.getSynopsis())) {
            Movie movieToSave = new Movie();
            if (!Validation.isValidString(movie.getCode())) {
                movieToSave.setRating(0);
                movieToSave.setVote(0);
                movieToSave.setId(0);
            } else {
                movieToSave = searchMovieDetailsById(movie.getCode());
            }
            movieToSave.setCode(EMPTY_STRING);
            movieToSave.setTitle(Format.removeBlanks(movie.getTitle()));
            movieToSave.setYear(movie.getYear());
            if (Validation.isValidURL(movie.getImage())) {
                movieToSave.setImage(movie.getImage());
            } else {
                movieToSave.setImage(MSG_INVALID_URL);
            }
            movieToSave.setSynopsis(movie.getSynopsis());
            if ((Validation.isValidURL(movie.getTrailer())) && (!movie.getTrailer().contains(FORMAT_YT_EMBED))) {
                movieToSave.setTrailer(Format.getYoutubeUrl(movie.getTrailer()));
            } else {
                movieToSave.setTrailer(MSG_INVALID_URL);
            }
            success = movieDao.saveOrUpdateMovie(movieToSave);
        } else {
            logger.info(LOG_INVALID_INPUT);
        }

        return success;
    }

    /**
     * Check If Movie Exists
     * 
     * @param Movie
     * @return boolean
     */
    @Override
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
