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

    @Lob
    private String overview;

    @Column(length = 1000)
    private String posterLink;

    private String certificate;

    private String runTime;

    private double imdbRating;

    private int metaScore;

    private int numberOfVotes;

    private String gross;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

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

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(final String posterLink) {
        this.posterLink = posterLink;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(final String certificate) {
        this.certificate = certificate;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(final String runTime) {
        this.runTime = runTime;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(final double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(final int metaScore) {
        this.metaScore = metaScore;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(final int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(final String gross) {
        this.gross = gross;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(final Director director) {
        this.director = director;
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
}
