/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.dao;

import com.alanquintero.mp.entity.Role;

/**
 * @class RoleDao.java
 * @purpose Interface of DAO Layer for Role Transactions.
 */
public interface RoleDao {

    /**
     * @param String
     * @return Role
     */
    public Role searchRoleByRoleName(String roleName);

}
