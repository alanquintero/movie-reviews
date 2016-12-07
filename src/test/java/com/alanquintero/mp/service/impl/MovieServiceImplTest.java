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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

    private static String MOVIE_CODE = "MQ==";
    private static String MOVIE_TITLE = "Back to the Future II";
    private static String MOVIE_INEXISTENT_TITLE = "Back to the Future III";
    private static int MOVIE_YEAR = 1989;
    private Movie movie;

    @Before
    public void setData() {
        movie = new Movie();
        movie.setId(0);
        movie.setTitle("Back to the Future II");
        movie.setYear(1989);
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/c/c2/Back_to_the_Future_Part_II.jpg");
        movie.setTrailer("https://www.youtube.com/watch?v=MdENmefJRpw");
        movie.setSynopsis(
                "After visiting 2015, Marty McFly must repeat his visit to 1955 to prevent disastrous changes "
                        + "to 1985...without interfering with his first trip.");
    }

    @Test
    public void testSearchMovieById() {
        Assert.assertNotNull(movieService.searchMovieById(MOVIE_CODE));
    }

    @Test
    public void testSearchNonexistentMovieById() {
        Movie movie = movieService.searchMovieById(EMPTY_STRING);
        Assert.assertEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void testSearchMovieDetailsById() {
        Movie movie = movieService.searchMovieDetailsById(MOVIE_CODE);
        Assert.assertNotNull(movie);
        Assert.assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void testSearchNonexistentMovieDetailsById() {
        Movie movie = movieService.searchMovieDetailsById(EMPTY_STRING);
        Assert.assertNotNull(movie);
        Assert.assertEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void testSearchMovieByTitle() {
        List<Movie> movies = movieService.searchMovieByTitle(MOVIE_TITLE);
        Assert.assertNotNull(movies);
        for (Movie movie : movies) {
            Assert.assertNotNull(movie);
            Assert.assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    public void testSearchNullMovieByTitle() {
        String movieTitle = null;
        List<Movie> movies = movieService.searchMovieByTitle(movieTitle);
        Assert.assertNotNull(movies);
        for (Movie m : movies) {
            Assert.assertEquals(m.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    public void testSearchEmptyMovieByTitle() {
        List<Movie> movies = movieService.searchMovieByTitle(EMPTY_STRING);
        Assert.assertNotNull(movies);
        for (Movie m : movies) {
            Assert.assertEquals(m.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    public void testGetPopularMovies() {
        List<Movie> movies = movieService.getPopularMovies();
        Assert.assertNotNull(movies);
    }

    @Test
    public void testGetAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        Assert.assertNotNull(movies);
        for (Movie movie : movies) {
            Assert.assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
        }
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testDeteleExistentMovie() {
        Assert.assertEquals(movieService.deteleMovie(MOVIE_CODE), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNonexistentMovie() {
        Assert.assertEquals(movieService.deteleMovie(EMPTY_STRING), MSG_FAIL);
    }

    @Test
    public void testSearchEmptyAutocompleteMovies() {
        List<MovieModel> movies = movieService.searchAutocompleteMovies(EMPTY_STRING);
        Assert.assertNotNull(movies);
    }

    @Test
    public void testSearchAutocompleteMovies() {
        List<MovieModel> movies = movieService.searchAutocompleteMovies(MOVIE_TITLE.substring(0, 6));
        Assert.assertNotNull(movies);
    }

    @Test
    public void testSearchNullAutocompleteMovies() {
        String movieTitle = null;
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
        Assert.assertNotNull(movies);
    }

    @Test
    public void testGetMostVotedMovies() {
        List<Movie> movies = movieService.getMostVotedMovies();
        Assert.assertNotNull(movies);
    }

    @Test
    public void testSaveMovie() {
        Assert.assertTrue(movieService.saveOrUpdateMovie(movie));
    }

    @Test
    public void testUpdateMovie() {
        Assert.assertTrue(movieService.saveOrUpdateMovie(movie));
    }

    @Test
    public void testCheckIfNonExitentMovieExists() {
        Movie movie = new Movie();
        movie.setCode(EMPTY_STRING);
        movie.setTitle(MOVIE_INEXISTENT_TITLE);
        movie.setYear(MOVIE_YEAR);
        Assert.assertFalse(movieService.checkIfMovieExists(movie));
    }

}
