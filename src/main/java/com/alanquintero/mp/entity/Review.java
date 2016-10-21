/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.entity;

import static com.alanquintero.mp.util.Consts.*;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

/**
 * @class Review.java
 * @purpose Entity class.
 */
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 1, message = TITLE_MIN_ERROR_MESSAGE)
    private String title;

    @Size(min = 1, message = COMMENT_MIN_ERROR_MESSAGE)
    private String comment;

    private int rating;

    @Column(name = PUBLISHED_DATE_COLUMN)
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name = PROFILE_ID_COLUMN)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = MOVIE_ID_COLUMN)
    private Movie movie;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
