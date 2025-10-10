/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dto;

import java.util.List;

/**
 * DTO class to store the data of the top-rated movies.
 *
 * @author Alan Quintero
 */
public class TopMovieDto {

    private String title;

    private String posterLink;

    private String overview;

    private int releaseYear;

    private double imdbRating;

    private int numberOfVotes;

    private int metaScore;

    private List<String> genres;

    public TopMovieDto(final String title, final String posterLink, final String overview, final int releaseYear, final double imdbRating, final int numberOfVotes, final int metaScore, final List<String> genres) {
        this.title = title;
        this.posterLink = posterLink;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.imdbRating = imdbRating;
        this.numberOfVotes = numberOfVotes;
        this.metaScore = metaScore;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(int metaScore) {
        this.metaScore = metaScore;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
