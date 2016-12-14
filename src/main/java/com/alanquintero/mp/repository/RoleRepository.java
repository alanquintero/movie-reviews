/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alanquintero.mp.entity.Role;

/**
 * @class RoleRepository.java
 * @purpose Get Role information from DB.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find one Role by Role Name
     * 
     * @param String
     * @return Role
     */
    public Role findRoleByName(String roleName);

}
