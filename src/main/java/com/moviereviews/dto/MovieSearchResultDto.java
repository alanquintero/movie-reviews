/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dto;

/**
 * DTO class to return the found movies when searching movies by title.
 *
 * @author Alan Quintero
 */
public class MovieSearchResultDto {

    private Long id;

    private String title;

    public MovieSearchResultDto(final Long id, final String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
