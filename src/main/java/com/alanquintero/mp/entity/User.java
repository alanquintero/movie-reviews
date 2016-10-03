/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.entity;

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

@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min = 4, message = "User Name must be at least four characters!")
	@Column(unique = true)
	@UniqueUsername(message = "User Name already exists!")
	private String name;

	@Size(min = 1, message = "Invalid Email Address!")
	@Email(message = "Invalid Email Address!")
	@UniqueEmail(message = "Email is already in use!")
	@Column(unique = true)
	private String email;

	@Size(min = 6, message = "Password must be at least six characters!")
	private String password;

	@ManyToMany
	@JoinTable
	private List<Role> roles;

	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
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
