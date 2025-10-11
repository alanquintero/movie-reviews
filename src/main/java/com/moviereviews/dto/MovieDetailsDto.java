/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dto;

import java.util.Set;

/**
 * DTO class to store all the data of movies.
 *
 * @author Alan Quintero
 */
public class MovieDetailsDto extends MovieSummaryDto {

    private String originalTitle;

    private String overview;

    private String certificate;

    private String runTime;

    private int metaScore;

    private String gross;

    private String director;

    private Set<String> cast;

    private Set<String> genres;

    public MovieDetailsDto() {
    }

    public MovieDetailsDto(final Long id, final String title, final String posterLink, final int releaseYear, final double imdbRating, final int numberOfVotes, final String originalTitle, final String overview, final String certificate, final String runTime, final int metaScore, final String gross, final String director, final Set<String> cast, final Set<String> genres) {
        super(id, title, posterLink, releaseYear, imdbRating, numberOfVotes);
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.certificate = certificate;
        this.runTime = runTime;
        this.metaScore = metaScore;
        this.gross = gross;
        this.director = director;
        this.cast = cast;
        this.genres = genres;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
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

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(final int metaScore) {
        this.metaScore = metaScore;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(final String gross) {
        this.gross = gross;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public Set<String> getCast() {
        return cast;
    }

    public void setCast(final Set<String> cast) {
        this.cast = cast;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(final Set<String> genres) {
        this.genres = genres;
    }
}
