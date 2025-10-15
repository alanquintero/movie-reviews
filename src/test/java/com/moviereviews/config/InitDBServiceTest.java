/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.config;

import com.moviereviews.dto.MovieDto;
import com.moviereviews.entity.CastMember;
import com.moviereviews.entity.Director;
import com.moviereviews.entity.Genre;
import com.moviereviews.entity.Movie;
import com.moviereviews.repository.CastMemberRepository;
import com.moviereviews.repository.DirectorRepository;
import com.moviereviews.repository.GenreRepository;
import com.moviereviews.repository.MovieRepository;
import com.moviereviews.search.SearchManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class InitDBServiceTest {

    private GenreRepository genreRepository;
    private MovieRepository movieRepository;
    private DirectorRepository directorRepository;
    private CastMemberRepository castMemberRepository;
    private InitDBService initDBService;

    @BeforeEach
    void setUp() {
        genreRepository = mock(GenreRepository.class);
        movieRepository = mock(MovieRepository.class);
        directorRepository = mock(DirectorRepository.class);
        castMemberRepository = mock(CastMemberRepository.class);
        final SearchManager searchManager = mock(SearchManager.class);

        initDBService = new InitDBService(genreRepository, movieRepository, directorRepository, castMemberRepository, searchManager);
    }

    @Test
    void getListOfMovies_returnsNonEmptyList() {
        // Given & When
        final List<MovieDto> movies = initDBService.getListOfMovies();

        // Then
        assertNotNull(movies, "Movie list should not be null");
        assertFalse(movies.isEmpty(), "Movie list should not be empty");
    }

    @Test
    void init_savesEntities() {
        // Given & When
        // Call init (this will load the JSON and try to save)
        initDBService.init();

        // Then
        // Verify that repositories were called
        verify(directorRepository, atLeastOnce()).saveAll(any(Collection.class));
        verify(castMemberRepository, atLeastOnce()).saveAll(any(Set.class));
        verify(genreRepository, atLeastOnce()).saveAll(any(Set.class));
        verify(movieRepository, atLeastOnce()).saveAll(any(List.class));
    }

    @Test
    void init_constructsEntitiesCorrectly() {
        // Given & When
        initDBService.init();

        // Then
        // Capture saved directors
        ArgumentCaptor<Collection<Director>> directorCaptor = ArgumentCaptor.forClass(Collection.class);
        verify(directorRepository).saveAll(directorCaptor.capture());
        final Collection<Director> savedDirector = directorCaptor.getValue();
        assertFalse(savedDirector.isEmpty(), "Directors should not be empty");

        // Capture saved cast members
        ArgumentCaptor<Set<CastMember>> castCaptor = ArgumentCaptor.forClass(Set.class);
        verify(castMemberRepository).saveAll(castCaptor.capture());
        final Set<CastMember> savedCast = castCaptor.getValue();
        assertFalse(savedCast.isEmpty(), "Cast members should not be empty");

        // Capture saved genres
        ArgumentCaptor<Set<Genre>> genreCaptor = ArgumentCaptor.forClass(Set.class);
        verify(genreRepository).saveAll(genreCaptor.capture());
        final Set<Genre> savedGenres = genreCaptor.getValue();
        assertFalse(savedGenres.isEmpty(), "Genres should not be empty");

        // Capture saved movies
        ArgumentCaptor<List<Movie>> movieCaptor = ArgumentCaptor.forClass(List.class);
        verify(movieRepository).saveAll(movieCaptor.capture());
        final List<Movie> savedMovies = movieCaptor.getValue();
        assertFalse(savedMovies.isEmpty(), "Movies should not be empty");
    }
}
