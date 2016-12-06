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
    public void testSearchMovieById() {
        String movieCode = "MQ==";
        Assert.assertNotNull(movieService.searchMovieById(movieCode));
    }

    @Test
    public void testSearchNonexistentMovieById() {
        String movieCode = "";
        Movie movie = movieService.searchMovieById(movieCode);
        Assert.assertEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void testSearchMovieDetailsById() {
        String movieCode = "MQ==";
        Movie movie = movieService.searchMovieDetailsById(movieCode);
        Assert.assertNotNull(movie);
        Assert.assertNotEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void testSearchNonexistentMovieDetailsById() {
        String movieCode = "";
        Movie movie = movieService.searchMovieDetailsById(movieCode);
        Assert.assertNotNull(movie);
        Assert.assertEquals(movie.getTitle(), MSG_MOVIE_NOT_FOUND);
    }

    @Test
    public void testSearchMovieByTitle() {
        String movieTitle = "Back to the Future";
        List<Movie> movies = movieService.searchMovieByTitle(movieTitle);
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
        String movieTitle = "";
        List<Movie> movies = movieService.searchMovieByTitle(movieTitle);
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
        String movieCode = "MQ==";
        Assert.assertEquals(movieService.deteleMovie(movieCode), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeteleNonexistentMovie() {
        String movieCode = "";
        Assert.assertEquals(movieService.deteleMovie(movieCode), MSG_FAIL);
    }

    @Test
    public void testSearchEmptyAutocompleteMovies() {
        String movieTitle = "";
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
        Assert.assertNotNull(movies);
    }

    @Test
    public void testSearchAutocompleteMovies() {
        String movieTitle = "Back to the";
        List<MovieModel> movies = movieService.searchAutocompleteMovies(movieTitle);
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
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle("Back to the Future II");
        movie.setYear(1989);
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/c/c2/Back_to_the_Future_Part_II.jpg");
        movie.setTrailer("https://www.youtube.com/watch?v=MdENmefJRpw");
        movie.setSynopsis(
                "After visiting 2015, Marty McFly must repeat his visit to 1955 to prevent disastrous changes to 1985...without interfering with his first trip.");
        Assert.assertTrue(movieService.saveOrUpdateMovie(movie));
    }

    @Test
    public void testUpdateMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Back to the Future");
        movie.setYear(1985);
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/c/c2/Back_to_the_Future_Part_II.jpg");
        movie.setTrailer("https://www.youtube.com/watch?v=MdENmefJRpw");
        movie.setSynopsis(
                "After visiting 2015, Marty McFly must repeat his visit to 1955 to prevent disastrous changes to 1985...without interfering with his first trip.");
        Assert.assertTrue(movieService.saveOrUpdateMovie(movie));
    }

    @Test
    public void testCheckIfNonExitentMovieExists() {
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle("Back to the Future II");
        movie.setYear(1989);
        Assert.assertFalse(movieService.checkIfMovieExists(movie));
    }

}
