/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import com.moviereviews.dto.TopMovieDto;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieService(movieRepository);
    }

    @Test
    void getTopRatedMovies() {
        // Create mock movies
        final Movie movie1 = new Movie();
        movie1.setTitle("The Shawshank Redemption");
        movie1.setPosterLink("path/image1.jpg");
        movie1.setReleaseYear(1994);
        movie1.setOverview("Movie 1");
        movie1.setNumberOfVotes(2343110);
        movie1.setMetaScore(80);
        movie1.setImdbRating(9.3);
        movie1.setGenres(Set.of(new Genre("Drama")));

        final Movie movie2 = new Movie();
        movie2.setTitle("The Godfather");
        movie1.setPosterLink("path/image2.jpg");
        movie1.setReleaseYear(1972);
        movie1.setOverview("Movie 2");
        movie1.setNumberOfVotes(1620367);
        movie1.setMetaScore(100);
        movie2.setImdbRating(9.2);
        movie2.setGenres(Set.of(new Genre("Crime"), new Genre("Drama")));

        List<Movie> mockMovies = List.of(movie1, movie2);

        // Mock repository
        when(movieRepository.findTopRatedMovies(PageRequest.of(0, 15))).thenReturn(mockMovies);

        // Call service
        List<TopMovieDto> topMovies = movieService.getTopRatedMovies();

        // Verify repository call
        verify(movieRepository, times(1)).findTopRatedMovies(PageRequest.of(0, 15));

        // Assert mapping
        assertEquals(2, topMovies.size());

        TopMovieDto dto1 = topMovies.get(0);
        assertEquals("The Shawshank Redemption", dto1.getTitle());
        assertEquals(9.3, dto1.getImdbRating());
        assertTrue(dto1.getGenres().contains("Drama"));

        TopMovieDto dto2 = topMovies.get(1);
        assertEquals("The Godfather", dto2.getTitle());
        assertEquals(9.2, dto2.getImdbRating());
        assertTrue(dto2.getGenres().contains("Crime"));
        assertTrue(dto2.getGenres().contains("Drama"));
    }
}
