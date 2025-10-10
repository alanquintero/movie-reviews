/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dto;

/**
 * DTO class to store the data of the top-rated movies.
 *
 * @author Alan Quintero
 */
public class TopMovieDto {

    private String title;

    private String posterLink;

    private int releaseYear;

    private double imdbRating;

    private int numberOfVotes;

    public TopMovieDto(final String title, final String posterLink, final int releaseYear, final double imdbRating, final int numberOfVotes) {
        this.title = title;
        this.posterLink = posterLink;
        this.releaseYear = releaseYear;
        this.imdbRating = imdbRating;
        this.numberOfVotes = numberOfVotes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(final String posterLink) {
        this.posterLink = posterLink;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(final double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(final int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
