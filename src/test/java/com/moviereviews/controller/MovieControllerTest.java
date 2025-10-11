/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MovieControllerTest {

    private MovieService movieService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        movieService = mock(MovieService.class);
        final MovieController movieController = new MovieController(movieService);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void getTopRatedMovies() throws Exception {
        // Mock data
        final MovieSummaryDto movie1 = new MovieSummaryDto("The Shawshank Redemption", "path/image1.jpg", 1994, 9.3, 2343110);
        final MovieSummaryDto movie2 = new MovieSummaryDto("The Godfather", "path/image2.jpg", 1972, 9.0, 1620367);

        final List<MovieSummaryDto> mockMovies = List.of(movie1, movie2);
        when(movieService.getTopRatedMovies()).thenReturn(mockMovies);

        // Perform GET request
        mockMvc.perform(get("/api/v1/movies/top-rated")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].imdbRating").value(9.3))
                .andExpect(jsonPath("$[1].title").value("The Godfather"));

        // Verify service call
        verify(movieService, times(1)).getTopRatedMovies();
    }
}
