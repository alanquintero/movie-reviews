/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alanquintero.mp.entity.Movie;
import com.alanquintero.mp.model.MovieModel;
import com.alanquintero.mp.service.MovieService;

/**
 * @class MovieServiceImplTest.java
 * @purpose Class used to test MovieServiceImpl class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(CONF_CONTEXT)
public class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void searchMovieByIdTest() {
        int movieId = 1;
        assertNotNull(movieService.searchMovieById(movieId));
    }

    @Test
    public void searchNonexistentMovieByIdTest() {
        int movieId = 0;
        Movie movie = movieService.searchMovieById(movieId);
        assertEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void searchMovieDetailsByIdTest() {
        int movieId = 1;
        Movie movie = movieService.searchMovieDetailsById(movieId);
        assertNotNull(movie);
        assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void searchNonexistentMovieDetailsByIdTest() {
        int movieId = 0;
        Movie movie = movieService.searchMovieDetailsById(movieId);
        assertNotNull(movie);
        assertEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void searchMovieByTitleTest() {
        String movieTitle = "Back to the Future";
        List<Movie> movies = movieService.searchMovieByTitle(movieTitle);
        assertNotNull(movies);
        for (Movie movie : movies) {
            assertNotNull(movie);
            assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    public void searchNullMovieByTitleTest() {
        String movieTitle = null;
        List<Movie> movies = movieService.searchMovieByTitle(movieTitle);
        assertNotNull(movies);
        for (Movie m : movies) {
            assertEquals(m.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    public void searchEmptyMovieByTitleTest() {
        String movieTitle = "";
        List<Movie> movies = movieService.searchMovieByTitle(movieTitle);
        assertNotNull(movies);
        for (Movie m : movies) {
            assertEquals(m.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    public void getPopularMoviesTest() {
        List<Movie> movies = movieService.getPopularMovies();
        assertNotNull(movies);
    }

    @Test
    public void getAllMoviesTest() {
        List<Movie> movies = movieService.getAllMovies();
        assertNotNull(movies);
        for (Movie movie : movies) {
            assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void deteleExistentMovieTest() {
        int movieId = 2;
        assertEquals(movieService.deteleMovie(movieId), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void tryToDeteleNonexistentMovieTest() {
        int movieId = 0;
        assertEquals(movieService.deteleMovie(movieId), MSG_FAIL);
    }

    @Test
    public void searchEmptyAutocompleteMoviesTest() {
        String movieTitle = "";
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
        assertNotNull(movies);
    }

    @Test
    public void searchAutocompleteMoviesTest() {
        String movieTitle = "Back to the";
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
        assertNotNull(movies);
    }

    @Test
    public void searchNullAutocompleteMoviesTest() {
        String movieTitle = null;
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
        assertNotNull(movies);
    }
    
    @Test
    public void getMostVotedMoviesTest() {
        List<Movie> movies = movieService.getMostVotedMovies();
        assertNotNull(movies);
    }

}
