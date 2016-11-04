/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.service.impl;

import static com.alanquintero.mp.util.Consts.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alanquintero.mp.entity.User;
import com.alanquintero.mp.service.UserService;

/**
 * @class UserServiceImplTest.java
 * @purpose Class used to test UserServiceImpl class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(CONF_CONTEXT)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAllUsersTest() {
        assertNotNull(userService.getAllUsers());
    }

    @Test
    public void searchUserByIdTest() {
        int userId = 1;
        User user = userService.searchUserById(userId);
        assertNotNull(user);
        assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void searchUserByNonexistentIdTest() {
        int userId = 0;
        User user = userService.searchUserById(userId);
        assertNotNull(user);
        assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void searchUserWithReviewsByIdTest() {
        int userId = 1;
        User user = userService.searchUserWithReviewsById(userId);
        assertNotNull(user);
        assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void searchUserWithReviewsByNonexistentIdTest() {
        int userId = 0;
        User user = userService.searchUserWithReviewsById(userId);
        assertNotNull(user);
        assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void saveUserTest() {
        User user = new User();
        user.setName("someone");
        user.setEmail("some@one.com");
        user.setPassword("noone123");
        assertEquals(userService.saveUser(user), true);
    }

    @Test
    public void tryToSaveNullUserTest() {
        User user = null;
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void tryToSaveEmptyUserTest() {
        User user = new User();
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void tryToSaveExistentUserTest() {
        User user = new User();
        user.setName("test");
        user.setEmail("some@one.com");
        user.setPassword("noone123");
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void tryToSaveUserWithExistentEmailTest() {
        User user = new User();
        user.setName("someone");
        user.setEmail("test@test.com");
        user.setPassword("noone123");
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void tryToSaveUserWithInvalidNameTest() {
        User user = new User();
        user.setName("s");
        user.setEmail("some@one.com");
        user.setPassword("noone123");
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void tryToSaveUserWithInvalidEmailTest() {
        User user = new User();
        user.setName("someone");
        user.setEmail("someone.com");
        user.setPassword("noone123");
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void tryToSaveUserWithInvalidPasswordTest() {
        User user = new User();
        user.setName("someone");
        user.setEmail("some@one.com");
        user.setPassword("a123");
        assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void searchUserWithReviewsByNameTest() {
        String userName = "test";
        User user = userService.searchUserWithReviewsByName(userName);
        assertNotNull(user);
        assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void searchUserWithReviewsByNonExistentNameTest() {
        String userName = "test123";
        User user = userService.searchUserWithReviewsByName(userName);
        assertNotNull(user);
        assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void searchUserWithReviewsByInvalidNameTest() {
        String userName = "";
        User user = userService.searchUserWithReviewsByName(userName);
        assertNotNull(user);
        assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void deleteUser() {
        int userId = 1;
        assertEquals(userService.deleteUser(userId), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void tryToDeleteNonexistentUser() {
        int userId = 0;
        assertEquals(userService.deleteUser(userId), MSG_FAIL);
    }

    @Test
    public void searchUserByNameTest() {
        String userName = "test";
        assertNotNull(userService.searchUserByName(userName));
    }

    @Test
    public void searchNonexistentUserByNameTest() {
        String userName = "test123";
        assertNull(userService.searchUserByName(userName));
    }

    @Test
    public void searchUserByEmailTest() {
        String userEmail = "test@test.com";
        assertNotNull(userService.searchUserByEmail(userEmail));
    }

    @Test
    public void searchUserByNonexistentEmailTest() {
        String userEmail = "test123@test.com";
        assertNull(userService.searchUserByEmail(userEmail));
    }

}
