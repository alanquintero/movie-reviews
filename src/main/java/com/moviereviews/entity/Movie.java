/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Entity representing a Movie.
 *
 * @author Alan Quintero
 */
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int releaseYear;

    @ManyToMany
    @JoinTable(
            name = "movie_castmember", // join table name
            joinColumns = @JoinColumn(name = "movie_id"), // FK to movie
            inverseJoinColumns = @JoinColumn(name = "cast_member_id") // FK to cast_member
    )
    private Set<CastMember> cast;

    @ManyToMany
    @JoinTable(
            name = "movie_genre", // join table name
            joinColumns = @JoinColumn(name = "movie_id"), // FK to movie
            inverseJoinColumns = @JoinColumn(name = "genre_id") // FK to genre
    )

    private Set<Genre> genres;

    private String href;

    @Lob
    private String extract;

    @Column(length = 1000)
    private String thumbnail;

    private int thumbnailWidth;

    private int thumbnailHeight;

    public Movie() {
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<CastMember> getCast() {
        return cast;
    }

    public void setCast(final Set<CastMember> cast) {
        this.cast = cast;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(final Set<Genre> genres) {
        this.genres = genres;
    }

    public String getHref() {
        return href;
    }

    public void setHref(final String href) {
        this.href = href;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(final String extract) {
        this.extract = extract;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(final int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(final int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }
}
