/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entity representing a Cast Member.
 *
 * @author Alan Quintero
 */
@Entity
@Table(name = "cast_member")
public class CastMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String castMemberName;

    @ManyToMany(mappedBy = "cast")
    private Set<Movie> movies;

    public CastMember() {
    }

    public CastMember(final String castMemberName) {
        this.castMemberName = castMemberName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCastMemberName() {
        return castMemberName;
    }

    public void setCastMemberName(String castMemberName) {
        this.castMemberName = castMemberName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastMember castMember)) return false;
        return castMemberName != null && castMemberName.equals(castMember.castMemberName);
    }

    @Override
    public int hashCode() {
        return castMemberName != null ? castMemberName.hashCode() : 0;
    }
}
