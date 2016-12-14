/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Picked".
 * 
 * "Movie Picked" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao.impl;

import static com.alanquintero.mp.util.Consts.LOG_ERROR_DB;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alanquintero.mp.dao.RoleDao;
import com.alanquintero.mp.entity.Role;
import com.alanquintero.mp.repository.RoleRepository;

/**
 * @class RoleDaoImpl.java
 * @purpose Implementation of RoleDao Interface.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private RoleRepository roleRepository;

    private static Logger logger = Logger.getLogger(RoleDaoImpl.class);

    /**
     * Search Role by Role Name
     * 
     * @param String
     * @return Role
     */
    @Override
    public Role searchRoleByRoleName(String roleName) {
        Role role = null;

        try {
            role = roleRepository.findRoleByName(roleName);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }

        return role;
    }

}
