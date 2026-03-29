/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.mapper;

import com.moviereviews.dto.MovieDetailsDto;
import com.moviereviews.dto.MovieSearchResultDto;
import com.moviereviews.dto.MovieSummaryDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Director;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    @Test
    void toMovieSummaryDto() {
        // Given
        final Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Shawshank Redemption");
        movie.setPosterLink("path/image1.jpg");
        movie.setReleaseYear(1994);
        movie.setImdbRating(9.3);
        movie.setNumberOfVotes(2343110);

        // When
        final MovieSummaryDto dto = MovieMapper.toMovieSummaryDto(movie);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("The Shawshank Redemption", dto.getTitle());
        assertEquals("path/image1.jpg", dto.getPosterLink());
        assertEquals(1994, dto.getReleaseYear());
        assertEquals(9.3, dto.getImdbRating());
        assertEquals(2343110, dto.getNumberOfVotes());
    }

    @Test
    void toMovieDetailsDto() {
        // Given
        final Set<CastMember> cast = new HashSet<>();
        cast.add(new CastMember("Tim Robbins"));
        cast.add(new CastMember("Morgan Freeman"));
        final Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Drama"));

        final Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Shawshank Redemption");
        movie.setPosterLink("path/image1.jpg");
        movie.setReleaseYear(1994);
        movie.setImdbRating(9.3);
        movie.setNumberOfVotes(2343110);
        movie.setOriginalTitle("The Shawshank Redemption");
        movie.setOverview("A timeless classic about hope and redemption");
        movie.setCertificate("A");
        movie.setRunTime("142 min");
        movie.setMetaScore(80);
        movie.setGross("28,341,469");
        movie.setDirector(new Director("Frank Darabont"));
        movie.setCast(cast);
        movie.setGenres(genres);

        // When
        final MovieDetailsDto dto = MovieMapper.toMovieDetailsDto(movie);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("The Shawshank Redemption", dto.getTitle());
        assertEquals("path/image1.jpg", dto.getPosterLink());
        assertEquals(1994, dto.getReleaseYear());
        assertEquals(9.3, dto.getImdbRating());
        assertEquals(2343110, dto.getNumberOfVotes());
        assertEquals("The Shawshank Redemption", dto.getOriginalTitle());
        assertEquals("A timeless classic about hope and redemption", dto.getOverview());
        assertEquals("A", dto.getCertificate());
        assertEquals("142 min", dto.getRunTime());
        assertEquals(80, dto.getMetaScore());
        assertEquals("28,341,469", dto.getGross());
        assertEquals("Frank Darabont", dto.getDirector());
        assertEquals(2, dto.getCast().size());
        assertTrue(dto.getCast().contains("Tim Robbins"));
        assertTrue(dto.getCast().contains("Morgan Freeman"));
        assertEquals(1, dto.getGenres().size());
        assertTrue(dto.getGenres().contains("Drama"));
    }

    @Test
    void toMovieDetailsDto_emptyCollections() {
        // Given
        final Movie movie = new Movie();
        movie.setId(2L);
        movie.setTitle("Test Movie");
        movie.setPosterLink("path/image2.jpg");
        movie.setReleaseYear(2020);
        movie.setImdbRating(7.5);
        movie.setNumberOfVotes(100000);
        movie.setOriginalTitle("Test Movie");
        movie.setOverview("Test overview");
        movie.setCertificate("PG");
        movie.setRunTime("120 min");
        movie.setMetaScore(70);
        movie.setGross("1,000,000");
        movie.setDirector(new Director("Test Director"));
        movie.setCast(new HashSet<>());
        movie.setGenres(new HashSet<>());

        // When
        final MovieDetailsDto dto = MovieMapper.toMovieDetailsDto(movie);

        // Then
        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertTrue(dto.getCast().isEmpty());
        assertTrue(dto.getGenres().isEmpty());
    }

    @Test
    void toMovieDetailsDto_multipleCastAndGenres() {
        // Given
        final Set<CastMember> cast = new HashSet<>();
        cast.add(new CastMember("Actor 1"));
        cast.add(new CastMember("Actor 2"));
        cast.add(new CastMember("Actor 3"));
        
        final Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Action"));
        genres.add(new Genre("Thriller"));
        genres.add(new Genre("Crime"));

        final Movie movie = new Movie();
        movie.setId(3L);
        movie.setTitle("Multi-Genre Movie");
        movie.setPosterLink("path/image3.jpg");
        movie.setReleaseYear(2021);
        movie.setImdbRating(8.0);
        movie.setNumberOfVotes(500000);
        movie.setOriginalTitle("Multi-Genre Movie");
        movie.setOverview("A movie with multiple genres");
        movie.setCertificate("R");
        movie.setRunTime("150 min");
        movie.setMetaScore(75);
        movie.setGross("50,000,000");
        movie.setDirector(new Director("Director Name"));
        movie.setCast(cast);
        movie.setGenres(genres);

        // When
        final MovieDetailsDto dto = MovieMapper.toMovieDetailsDto(movie);

        // Then
        assertNotNull(dto);
        assertEquals(3, dto.getCast().size());
        assertEquals(3, dto.getGenres().size());
        assertTrue(dto.getCast().contains("Actor 1"));
        assertTrue(dto.getCast().contains("Actor 2"));
        assertTrue(dto.getCast().contains("Actor 3"));
        assertTrue(dto.getGenres().contains("Action"));
        assertTrue(dto.getGenres().contains("Thriller"));
        assertTrue(dto.getGenres().contains("Crime"));
    }

    @Test
    void toMovieSearchResultDto() {
        // Given
        final Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Movie One");

        final Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Movie Two");

        final Movie movie3 = new Movie();
        movie3.setId(3L);
        movie3.setTitle("Movie Three");

        final List<Movie> movies = List.of(movie1, movie2, movie3);

        // When
        final List<MovieSearchResultDto> results = MovieMapper.toMovieSearchResultDto(movies);

        // Then
        assertNotNull(results);
        assertEquals(3, results.size());
        
        assertEquals(1L, results.get(0).getId());
        assertEquals("Movie One", results.get(0).getTitle());
        
        assertEquals(2L, results.get(1).getId());
        assertEquals("Movie Two", results.get(1).getTitle());
        
        assertEquals(3L, results.get(2).getId());
        assertEquals("Movie Three", results.get(2).getTitle());
    }

    @Test
    void toMovieSearchResultDto_emptyList() {
        // Given
        final List<Movie> movies = List.of();

        // When
        final List<MovieSearchResultDto> results = MovieMapper.toMovieSearchResultDto(movies);

        // Then
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void toMovieSearchResultDto_singleMovie() {
        // Given
        final Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Single Movie");

        final List<Movie> movies = List.of(movie);

        // When
        final List<MovieSearchResultDto> results = MovieMapper.toMovieSearchResultDto(movies);

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(1L, results.get(0).getId());
        assertEquals("Single Movie", results.get(0).getTitle());
    }
}
