/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import static com.moviereviews.util.Consts.*;

import java.util.Date;

import com.moviereviews.model.Code;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "review")
public class Review extends Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, message = TITLE_MIN_ERROR_MESSAGE)
    private String title;

    @Size(min = 1, message = COMMENT_MIN_ERROR_MESSAGE)
    @Column(length = 1000)
    private String comment;

    @Column(name = PUBLISHED_DATE_COLUMN)
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name = PROFILE_ID_COLUMN)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = MOVIE_ID_COLUMN)
    private Movie movie;

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
