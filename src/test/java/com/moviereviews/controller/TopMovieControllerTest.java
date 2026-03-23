/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.TopMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TopMovieControllerTest {

    private TopMovieService topMovieService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        topMovieService = mock(TopMovieService.class);
        final TopMovieController topMovieController = new TopMovieController(topMovieService);
        mockMvc = MockMvcBuilders.standaloneSetup(topMovieController).build();
    }

    @Test
    void getTopRatedMovies() throws Exception {
        // Mock data
        final MovieSummaryDto movie1 = new MovieSummaryDto(1L, "The Shawshank Redemption", "path/image1.jpg", 1994, 9.3, 2343110);
        final MovieSummaryDto movie2 = new MovieSummaryDto(2L, "The Godfather", "path/image2.jpg", 1972, 9.0, 1620367);

        final List<MovieSummaryDto> mockMovies = List.of(movie1, movie2);
        when(topMovieService.getTopRatedMovies()).thenReturn(mockMovies);

        // Perform GET request
        mockMvc.perform(get("/api/v1/movies/top-rated")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].imdbRating").value(9.3))
                .andExpect(jsonPath("$[1].title").value("The Godfather"));

        // Verify service call
        verify(topMovieService, times(1)).getTopRatedMovies();
    }
}
