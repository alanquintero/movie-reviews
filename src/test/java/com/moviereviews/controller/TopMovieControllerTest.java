/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.TopMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopMovieController.class)
class TopMovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopMovieService topMovieService;

    @Test
    void getTopRatedMovies_withoutN_returnsDefaultTopMovies() throws Exception {
        final List<MovieSummaryDto> movies = List.of(
                new MovieSummaryDto(1L, "The Shawshank Redemption", "path/1.jpg", 1994, 9.3, 2343110),
                new MovieSummaryDto(2L, "The Godfather", "path/2.jpg", 1972, 9.2, 1620367));

        when(topMovieService.getTopRatedMovies()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies/top"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].imdbRating").value(9.3))
                .andExpect(jsonPath("$[1].title").value("The Godfather"));

        verify(topMovieService).getTopRatedMovies();
    }

    @Test
    void getTopRatedMovies_withN_returnsTopNMovies() throws Exception {
        final List<MovieSummaryDto> movies = List.of(
                new MovieSummaryDto(1L, "The Shawshank Redemption", "path/1.jpg", 1994, 9.3, 2343110),
                new MovieSummaryDto(2L, "The Godfather", "path/2.jpg", 1972, 9.2, 1620367),
                new MovieSummaryDto(3L, "The Dark Knight", "path/3.jpg", 2008, 9.0, 2500000));

        when(topMovieService.getTopNRatedMovies(3)).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies/top").param("n", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[2].title").value("The Dark Knight"));

        verify(topMovieService).getTopNRatedMovies(3);
    }

    @Test
    void getTopRatedMovies_withoutN_emptyList_returnsOk() throws Exception {
        when(topMovieService.getTopRatedMovies()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/movies/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(topMovieService).getTopRatedMovies();
    }

    @Test
    void getTopRatedMovies_withN_emptyList_returnsOk() throws Exception {
        when(topMovieService.getTopNRatedMovies(5)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/movies/top").param("n", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(topMovieService).getTopNRatedMovies(5);
    }

    @Test
    void getTopRatedMovies_withN1_returnsSingleMovie() throws Exception {
        final List<MovieSummaryDto> movies = List.of(
                new MovieSummaryDto(1L, "The Shawshank Redemption", "path/1.jpg", 1994, 9.3, 2343110));

        when(topMovieService.getTopNRatedMovies(1)).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies/top").param("n", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].imdbRating").value(9.3));

        verify(topMovieService).getTopNRatedMovies(1);
    }
}
