/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import static com.moviereviews.util.Consts.*;

import java.util.List;

import jakarta.persistence.*;

import com.moviereviews.model.Code;

@Entity
@Table(name = "movie")
public class Movie extends Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(length = 2000)
    private String synopsis;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "release_year")
    private Integer releaseYear;

    private int rating;

    @Column(name = "total_rating")
    private int totalRating;

    @Column(name = "trailer_url", length = 1000)
    private String trailerUrl;

    @OneToMany(mappedBy = MOVIE_ENTITY, cascade = CascadeType.REMOVE)
    private List<Rating> ratings;

    @OneToMany(mappedBy = MOVIE_ENTITY, cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

}
