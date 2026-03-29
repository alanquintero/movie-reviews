/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TopMoviesServiceTest {

    private MovieRepository movieRepository;
    private TopMovieService topMovieService;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        topMovieService = new TopMovieService(movieRepository);
    }

    @Test
    void getTopRatedMovies() {
        // Create mock movies
        final Movie movie1 = new Movie();
        movie1.setTitle("The Shawshank Redemption");
        movie1.setPosterLink("path/image1.jpg");
        movie1.setReleaseYear(1994);
        movie1.setNumberOfVotes(2343110);
        movie1.setImdbRating(9.3);

        final Movie movie2 = new Movie();
        movie2.setTitle("The Godfather");
        movie1.setPosterLink("path/image2.jpg");
        movie1.setReleaseYear(1972);
        movie1.setNumberOfVotes(1620367);
        movie2.setImdbRating(9.2);

        final List<Movie> mockMovies = List.of(movie1, movie2);

        // Mock repository
        when(movieRepository.findTopRatedMovies(PageRequest.of(0, 15))).thenReturn(mockMovies);

        // Call service
        final List<MovieSummaryDto> topMovies = topMovieService.getTopRatedMovies();

        // Verify repository call
        verify(movieRepository, times(1)).findTopRatedMovies(PageRequest.of(0, 15));

        // Assert mapping
        assertEquals(2, topMovies.size());

        final MovieSummaryDto dto1 = topMovies.get(0);
        assertEquals("The Shawshank Redemption", dto1.getTitle());
        assertEquals(9.3, dto1.getImdbRating());

        final MovieSummaryDto dto2 = topMovies.get(1);
        assertEquals("The Godfather", dto2.getTitle());
        assertEquals(9.2, dto2.getImdbRating());
    }

    @Test
    void getTopRatedMovies_emptyList() {
        // Mock repository to return empty list
        when(movieRepository.findTopRatedMovies(PageRequest.of(0, 15))).thenReturn(List.of());

        // Call service
        final List<MovieSummaryDto> topMovies = topMovieService.getTopRatedMovies();

        // Verify repository call
        verify(movieRepository, times(1)).findTopRatedMovies(PageRequest.of(0, 15));

        // Assert empty result
        assertNotNull(topMovies);
        assertEquals(0, topMovies.size());
    }

    @Test
    void getTopRatedMovies_singleMovie() {
        // Create mock movie
        final Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Single Movie");
        movie.setPosterLink("path/image.jpg");
        movie.setReleaseYear(2023);
        movie.setNumberOfVotes(100000);
        movie.setImdbRating(8.5);

        // Mock repository
        when(movieRepository.findTopRatedMovies(PageRequest.of(0, 15))).thenReturn(List.of(movie));

        // Call service
        final List<MovieSummaryDto> topMovies = topMovieService.getTopRatedMovies();

        // Assert result
        assertEquals(1, topMovies.size());
        assertEquals("Single Movie", topMovies.get(0).getTitle());
        assertEquals(8.5, topMovies.get(0).getImdbRating());
    }

    @Test
    void getTopNRatedMovies() {
        // Create mock movies
        final Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Movie 1");
        movie1.setPosterLink("path/1.jpg");
        movie1.setReleaseYear(2021);
        movie1.setNumberOfVotes(500000);
        movie1.setImdbRating(9.5);

        final Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Movie 2");
        movie2.setPosterLink("path/2.jpg");
        movie2.setReleaseYear(2020);
        movie2.setNumberOfVotes(400000);
        movie2.setImdbRating(9.0);

        final Movie movie3 = new Movie();
        movie3.setId(3L);
        movie3.setTitle("Movie 3");
        movie3.setPosterLink("path/3.jpg");
        movie3.setReleaseYear(2022);
        movie3.setNumberOfVotes(300000);
        movie3.setImdbRating(8.5);

        final List<Movie> mockMovies = List.of(movie1, movie2, movie3);

        // Mock repository
        when(movieRepository.findTopNRatedMovies(PageRequest.of(0, 15), 3)).thenReturn(mockMovies);

        // Call service
        final List<MovieSummaryDto> topMovies = topMovieService.getTopNRatedMovies(3);

        // Verify repository call
        verify(movieRepository, times(1)).findTopNRatedMovies(PageRequest.of(0, 15), 3);

        // Assert mapping
        assertEquals(3, topMovies.size());
        assertEquals("Movie 1", topMovies.get(0).getTitle());
        assertEquals(9.5, topMovies.get(0).getImdbRating());
        assertEquals("Movie 2", topMovies.get(1).getTitle());
        assertEquals("Movie 3", topMovies.get(2).getTitle());
    }

    @Test
    void getTopNRatedMovies_withDifferentN() {
        // Create mock movies
        final Movie movie1 = new Movie();
        movie1.setTitle("Top Movie");
        movie1.setPosterLink("path/top.jpg");
        movie1.setReleaseYear(2023);
        movie1.setNumberOfVotes(1000000);
        movie1.setImdbRating(9.8);

        // Mock repository
        when(movieRepository.findTopNRatedMovies(PageRequest.of(0, 15), 1)).thenReturn(List.of(movie1));

        // Call service
        final List<MovieSummaryDto> topMovies = topMovieService.getTopNRatedMovies(1);

        // Assert result
        assertEquals(1, topMovies.size());
        assertEquals("Top Movie", topMovies.get(0).getTitle());
        assertEquals(9.8, topMovies.get(0).getImdbRating());

        verify(movieRepository, times(1)).findTopNRatedMovies(PageRequest.of(0, 15), 1);
    }

    @Test
    void getTopNRatedMovies_emptyResult() {
        // Mock repository to return empty list
        when(movieRepository.findTopNRatedMovies(PageRequest.of(0, 15), 5)).thenReturn(List.of());

        // Call service
        final List<MovieSummaryDto> topMovies = topMovieService.getTopNRatedMovies(5);

        // Assert empty result
        assertNotNull(topMovies);
        assertEquals(0, topMovies.size());

        verify(movieRepository, times(1)).findTopNRatedMovies(PageRequest.of(0, 15), 5);
    }
}
