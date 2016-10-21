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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.alanquintero.mp.annotation.UniqueEmail;
import com.alanquintero.mp.annotation.UniqueUsername;

/**
 * @class User.java
 * @purpose Entity class.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 4, message = USER_MIN_ERROR_MESSAGE)
    @Column(unique = true)
    @UniqueUsername(message = USER_EXISTS_ERROR_MESSAGE)
    private String name;

    @Size(min = 1, message = EMAIL_INVALID_ERROR_MESSAGE)
    @Email(message = EMAIL_INVALID_ERROR_MESSAGE)
    @UniqueEmail(message = EMAIL_EXISTS_ERROR_MESSAGE)
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = PASSWORD_MIN_ERROR_MESSAGE)
    private String password;

    @ManyToMany
    @JoinTable
    private List<Role> roles;

    @OneToOne(mappedBy = USER_ENTITY, cascade = CascadeType.REMOVE)
    private Profile profile;

    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
