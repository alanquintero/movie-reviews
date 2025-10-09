/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import jakarta.persistence.*;

import java.util.List;

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

    private String firstName;

    private String lastName;

    @ManyToMany(mappedBy = "cast")
    private List<Movie> movies;

    public CastMember() {
    }

    public CastMember(final String firstName) {
        this.firstName = firstName;
    }

    public CastMember(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastMember castMember)) return false;
        if (firstName != null && lastName != null) {
            return firstName.equals(castMember.firstName)
                    && lastName.equals(castMember.lastName);
        }
        return firstName != null && firstName.equals(castMember.firstName);
    }

    @Override
    public int hashCode() {
        if (firstName != null && lastName != null) {
            return firstName.hashCode() + lastName.hashCode();
        }
        return firstName != null ? firstName.hashCode() : 0;
    }
}
