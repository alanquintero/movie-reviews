/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entity representing a Director.
 *
 * @author Alan Quintero
 */
@Entity
@Table(name = "director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String directorName;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Movie> movies;

    public Director() {
    }

    public Director(final String directorName) {
        this.directorName = directorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(final String directorName) {
        this.directorName = directorName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director director)) return false;
        return directorName != null && directorName.equals(director.directorName);
    }

    @Override
    public int hashCode() {
        return directorName != null ? directorName.hashCode() : 0;
    }
}
