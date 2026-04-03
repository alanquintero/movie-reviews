/**
 * Copyright 2026 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.controller;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSearchResultDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void getMovieDetails_returnsMovieDetailsDto() throws Exception {
        final MovieDetailsDto dto = new MovieDetailsDto(
                1L, "The Shawshank Redemption", "path/image1.jpg", 1994, 9.3, 2343110,
                "The Shawshank Redemption", "Two imprisoned men bond.", "A", "142 min",
                80, "28,341,469", "Frank Darabont",
                Set.of("Tim Robbins", "Morgan Freeman"), Set.of("Drama"));

        when(movieService.getMovieById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.imdbRating").value(9.3))
                .andExpect(jsonPath("$.director").value("Frank Darabont"))
                .andExpect(jsonPath("$.releaseYear").value(1994));

        verify(movieService).getMovieById(1L);
    }

    @Test
    void getMovieDetails_movieNotFound_returnsEmptyBody() throws Exception {
        when(movieService.getMovieById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/movies/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(movieService).getMovieById(999L);
    }

    @Test
    void getAllMovies_returnsPaginatedMovies() throws Exception {
        final MovieSummaryDto dto1 = new MovieSummaryDto(1L, "The Shawshank Redemption", "path/1.jpg", 1994, 9.3, 2343110);
        final MovieSummaryDto dto2 = new MovieSummaryDto(2L, "The Godfather", "path/2.jpg", 1972, 9.2, 1620367);
        final Page<MovieSummaryDto> page = new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0, 10), 2);

        when(movieService.getAllMovies(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/movies/all").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.content[1].title").value("The Godfather"))
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(movieService).getAllMovies(any());
    }

    @Test
    void getAllMovies_emptyPage_returnsEmptyContent() throws Exception {
        final Page<MovieSummaryDto> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(movieService.getAllMovies(any())).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/movies/all").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void searchMovieByTitlePrefix_returnsMatchingMovies() throws Exception {
        final List<MovieSearchResultDto> results = List.of(
                new MovieSearchResultDto(1L, "The Shawshank Redemption"),
                new MovieSearchResultDto(2L, "The Godfather"));

        when(movieService.searchMovieByTitlePrefix("the")).thenReturn(results);

        mockMvc.perform(get("/api/v1/movies/search").param("titlePrefix", "the"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[1].title").value("The Godfather"));

        verify(movieService).searchMovieByTitlePrefix("the");
    }

    @Test
    void searchMovieByTitlePrefix_noMatches_returnsEmptyList() throws Exception {
        when(movieService.searchMovieByTitlePrefix("xyz")).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/movies/search").param("titlePrefix", "xyz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(movieService).searchMovieByTitlePrefix("xyz");
    }

    @Test
    void searchMovieByTitlePrefix_missingParam_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/movies/search"))
                .andExpect(status().isBadRequest());
    }
}
