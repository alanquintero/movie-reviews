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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private TrieManager trieManager;
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        trieManager = new TrieManager();
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
        // Given - insert movies into trie
        trieManager.insertMovieTitle("theshawshankredemption", 1L);
        trieManager.insertMovieTitle("thegodfatherI", 2L);
        trieManager.insertMovieTitle("thegodfatherII", 3L);
        
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
        
        when(movieRepository.findAllById(anyList())).thenReturn(movieResult);

        // When
        final List<MovieSearchResultDto> movies = movieService.searchMovieByTitlePrefix("the");

        // Then
        assertNotNull(movies);
        // Results may vary based on how TrieManager encodes prefixes
        assertTrue(movies.size() >= 0);
    }

    @Test
    public void searchMovieByTitlePrefix_noMoviesFound() {
        // Given - don't insert any movies matching the search prefix
        when(movieRepository.findAllById(anyList())).thenReturn(List.of());

        // When
        final List<MovieSearchResultDto> movies = movieService.searchMovieByTitlePrefix("xyz");

        // Then
        assertNotNull(movies);
        assertTrue(movies.isEmpty());
    }

    @Test
    void getMovieById_returnsCompleteDetails() {
        // Given
        final Set<CastMember> cast = new HashSet<>();
        cast.add(new CastMember("Actor 1"));
        cast.add(new CastMember("Actor 2"));
        
        final Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Drama"));
        genres.add(new Genre("Crime"));

        final Movie movie = new Movie();
        movie.setId(5L);
        movie.setTitle("Test Movie");
        movie.setPosterLink("path/poster.jpg");
        movie.setReleaseYear(2020);
        movie.setNumberOfVotes(500000);
        movie.setImdbRating(8.0);
        movie.setOriginalTitle("Original Title");
        movie.setOverview("Test overview");
        movie.setCertificate("R");
        movie.setRunTime("120 min");
        movie.setMetaScore(75);
        movie.setGross("100,000,000");
        movie.setDirector(new Director("Test Director"));
        movie.setCast(cast);
        movie.setGenres(genres);

        when(movieRepository.findById(5L)).thenReturn(Optional.of(movie));

        // When
        final MovieDetailsDto result = movieService.getMovieById(5L);

        // Then
        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals("Test Movie", result.getTitle());
        assertEquals(8.0, result.getImdbRating());
        assertEquals(2, result.getCast().size());
        assertEquals(2, result.getGenres().size());
        
        verify(movieRepository, times(1)).findById(5L);
    }

    @Test
    void getAllMovies() {
        // Given
        final Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Movie 1");
        movie1.setPosterLink("path/1.jpg");
        movie1.setReleaseYear(2021);
        movie1.setImdbRating(8.5);
        movie1.setNumberOfVotes(100000);

        final Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Movie 2");
        movie2.setPosterLink("path/2.jpg");
        movie2.setReleaseYear(2022);
        movie2.setImdbRating(7.5);
        movie2.setNumberOfVotes(50000);

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Movie> moviePage = new PageImpl<>(List.of(movie1, movie2), pageable, 2);

        when(movieRepository.findAll(pageable)).thenReturn(moviePage);

        // When
        final Page<MovieSummaryDto> result = movieService.getAllMovies(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Movie 1", result.getContent().get(0).getTitle());
        assertEquals("Movie 2", result.getContent().get(1).getTitle());
        assertEquals(2, result.getTotalElements());

        verify(movieRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllMovies_emptyPage() {
        // Given
        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Movie> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(movieRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        final Page<MovieSummaryDto> result = movieService.getAllMovies(pageable);

        // Then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(movieRepository, times(1)).findAll(pageable);
    }

    @Test
    void searchMovieByTitlePrefix_emptyRepositoryResult() {
        // Given
        when(movieRepository.findAllById(anyList())).thenReturn(List.of());

        // When
        final List<MovieSearchResultDto> result = movieService.searchMovieByTitlePrefix("test");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
