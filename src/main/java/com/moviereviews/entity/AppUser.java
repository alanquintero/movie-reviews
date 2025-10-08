/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.entity;

import static com.moviereviews.util.Consts.*;

import java.util.List;

import jakarta.persistence.*;

import com.moviereviews.annotation.UniqueEmail;
import com.moviereviews.annotation.UniqueUsername;
import com.moviereviews.model.Code;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "app_user")
public class AppUser extends Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, message = USER_MIN_ERROR_MESSAGE)
    @Column(name = "user_name", unique = true)
    @UniqueUsername(message = USER_EXISTS_ERROR_MESSAGE)
    private String userName;

    @Size(min = 1, message = EMAIL_INVALID_ERROR_MESSAGE)
    @Email(message = EMAIL_INVALID_ERROR_MESSAGE)
    @UniqueEmail(message = EMAIL_EXISTS_ERROR_MESSAGE)
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = PASSWORD_MIN_ERROR_MESSAGE)
    private String password;

    private boolean enabled;

    @Size(min = 6, message = PASSWORD_MIN_ERROR_MESSAGE)
    private String newPassword;

    @ManyToMany
    @JoinTable(
            name = "app_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> userRoles;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    private Profile profile;

    public AppUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<UserRole> getRoles() {
        return userRoles;
    }

    public void setRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
