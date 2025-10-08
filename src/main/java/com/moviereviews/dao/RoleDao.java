/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.dao;

import static com.moviereviews.util.Consts.LOG_ERROR_DB;

import com.moviereviews.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moviereviews.repository.RoleRepository;

@Repository
public class RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(RoleDao.class);

    private final RoleRepository roleRepository;

    public RoleDao(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserRole searchRoleByRoleName(String roleName) {
        UserRole userRole = null;
        try {
            userRole = roleRepository.findRoleByRoleName(roleName);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DB, e);
        }
        return userRole;
    }
}
