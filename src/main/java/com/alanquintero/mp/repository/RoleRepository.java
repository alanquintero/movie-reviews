/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alanquintero.mp.entity.Role;

/**
 * RoleRepository.java 
 * Purpose: Get Role information from DB.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find one role by role name
     * 
     * @param Role_name
     * @return Role_Object
     */
    public Role findByName(String name);

}
