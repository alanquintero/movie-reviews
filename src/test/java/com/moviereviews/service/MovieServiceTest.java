/**
 * Copyright 2025 Alan Quintero
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
        movie1.setNumberOfVotes(2343110);
        movie1.setImdbRating(9.3);

        final Movie movie2 = new Movie();
        movie2.setTitle("The Godfather");
        movie1.setPosterLink("path/image2.jpg");
        movie1.setReleaseYear(1972);
        movie1.setNumberOfVotes(1620367);
        movie2.setImdbRating(9.2);

        List<Movie> mockMovies = List.of(movie1, movie2);

        // Mock repository
        when(movieRepository.findTopRatedMovies(PageRequest.of(0, 15))).thenReturn(mockMovies);

        // Call service
        List<MovieSummaryDto> topMovies = movieService.getTopRatedMovies();

        // Verify repository call
        verify(movieRepository, times(1)).findTopRatedMovies(PageRequest.of(0, 15));

        // Assert mapping
        assertEquals(2, topMovies.size());

        MovieSummaryDto dto1 = topMovies.get(0);
        assertEquals("The Shawshank Redemption", dto1.getTitle());
        assertEquals(9.3, dto1.getImdbRating());

        MovieSummaryDto dto2 = topMovies.get(1);
        assertEquals("The Godfather", dto2.getTitle());
        assertEquals(9.2, dto2.getImdbRating());
    }
}
