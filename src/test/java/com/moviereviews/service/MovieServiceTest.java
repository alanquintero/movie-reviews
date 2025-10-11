/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.service;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Director;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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

        final List<Movie> mockMovies = List.of(movie1, movie2);

        // Mock repository
        when(movieRepository.findTopRatedMovies(PageRequest.of(0, 15))).thenReturn(mockMovies);

        // Call service
        final List<MovieSummaryDto> topMovies = movieService.getTopRatedMovies();

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
}
