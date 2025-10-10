/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * DTO class to represent the data in the JSON file: src/resources/movies-2020s.json
 *
 * @author Alan Quintero
 */
public class MovieDto {

    @JsonProperty("Series_Title")
    private String title;

    @JsonProperty("Poster_Link")
    private String posterLink;

    @JsonProperty("Overview")
    private String overview;

    @JsonProperty("Released_Year")
    private int releaseYear;

    @JsonProperty("Certificate")
    private String certificate;

    @JsonProperty("Runtime")
    private String runTime;

    @JsonProperty("IMDB_Rating")
    private double imdbRating;

    @JsonProperty("Meta_score")
    private int metaScore;

    @JsonProperty("No_of_Votes")
    private int numberOfVotes;

    @JsonProperty("Gross")
    private String gross;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("cast")
    private List<String> cast;

    @JsonProperty("genres")
    private List<String> genres;

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

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(final List<String> cast) {
        this.cast = cast;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(final List<String> genres) {
        this.genres = genres;
    }
}
