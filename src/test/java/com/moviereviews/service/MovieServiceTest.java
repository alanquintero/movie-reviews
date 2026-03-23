/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSearchResultDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Director;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.MovieRepository;
import com.moviereviews.search.TrieManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private TrieManager trieManager;
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        trieManager = mock(TrieManager.class);
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieService(trieManager, movieRepository);
    }

    @Test
    void getMovieDetails() {
        // Mock data
        final Set<CastMember> cast = new HashSet<>();
        cast.add(new CastMember("Tim Robbins"));
        cast.add(new CastMember("Morgan Freeman"));
        final Set<Genre> genre = new HashSet<>();
        genre.add(new Genre("Drama"));
        final Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Shawshank Redemption");
        movie.setPosterLink("path/image1.jpg");
        movie.setReleaseYear(1994);
        movie.setNumberOfVotes(2343110);
        movie.setImdbRating(9.3);
        movie.setOverview("movie1");
        movie.setCertificate("A");
        movie.setRunTime("142 min");
        movie.setMetaScore(80);
        movie.setGross("28,341,469");
        movie.setDirector(new Director("Frank Darabont"));
        movie.setCast(cast);
        movie.setGenres(genre);

        // Mock repository
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // Call service
        final MovieDetailsDto movieDetailsDto = movieService.getMovieById(1L);

        assertNotNull(movieDetailsDto);
        assertEquals("The Shawshank Redemption", movieDetailsDto.getTitle());
        assertEquals(9.3, movieDetailsDto.getImdbRating());
    }

    @Test
    void getMovieDetails_movieNotFound() {
        // Mock repository
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call service
        final MovieDetailsDto movieDetailsDto = movieService.getMovieById(1L);

        assertNull(movieDetailsDto);
    }

    @Test
    public void searchMovieByTitlePrefix() {
        // Given
        final List<Long> moviesIds = new ArrayList<>();
        moviesIds.add(1L);
        moviesIds.add(2L);
        moviesIds.add(3L);
        when(trieManager.searchMovieByTitlePrefix(anyString())).thenReturn(moviesIds);
        final List<Movie> movieResult = new ArrayList<>();
        final Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("The Shawshank Redemption");
        movie1.setImdbRating(9.3);
        movieResult.add(movie1);
        final Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("The Godfather");
        movie2.setImdbRating(9.0);
        movieResult.add(movie2);
        final Movie movie3 = new Movie();
        movie3.setId(3L);
        movie3.setTitle("The Godfather II");
        movie3.setImdbRating(9.1);
        movieResult.add(movie3);
        when(movieRepository.findAllById(moviesIds)).thenReturn(movieResult);

        // When
        final List<MovieSearchResultDto> movies = movieService.searchMovieByTitlePrefix("the");

        // Then
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals(3, movies.size());
        assertEquals(1L, movies.get(0).getId());
        assertEquals(3L, movies.get(1).getId());
        assertEquals(2L, movies.get(2).getId());
    }

    @Test
    public void searchMovieByTitlePrefix_noMoviesFound() {
        // Given
        when(trieManager.searchMovieByTitlePrefix(anyString())).thenReturn(List.of());

        // When
        final List<MovieSearchResultDto> movies = movieService.searchMovieByTitlePrefix("the");

        // Then
        assertNotNull(movies);
        assertTrue(movies.isEmpty());
    }
}
