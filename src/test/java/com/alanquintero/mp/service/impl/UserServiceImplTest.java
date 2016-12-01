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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alanquintero.mp.entity.Profile;
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
    public void testGetAllUsers() {
        Assert.assertNotNull(userService.getAllUsers());
    }

    @Test
    public void testSearchUserById() {
        int userId = 1;
        User user = userService.searchUserById(userId);
        Assert.assertNotNull(user);
        Assert.assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserByNonexistentId() {
        int userId = 0;
        User user = userService.searchUserById(userId);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserWithReviewsById() {
        int userId = 1;
        User user = userService.searchUserWithReviewsById(userId);
        Assert.assertNotNull(user);
        Assert.assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserWithReviewsByNonexistentId() {
        int userId = 0;
        User user = userService.searchUserWithReviewsById(userId);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setName("someone");
        user.setEmail("some@one.com");
        user.setPassword("noone123");
        Assert.assertEquals(userService.saveUser(user), true);
    }

    @Test
    public void testTryToSaveNullUser() {
        User user = null;
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testTryToSaveEmptyUser() {
        User user = new User();
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testTryToSaveExistentUser() {
        User user = new User();
        user.setName("test");
        user.setEmail("some@one.com");
        user.setPassword("noone123");
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testTryToSaveUserWithExistentEmail() {
        User user = new User();
        user.setName("someone");
        user.setEmail("test@test.com");
        user.setPassword("noone123");
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testTryToSaveUserWithInvalidName() {
        User user = new User();
        user.setName("s");
        user.setEmail("some@one.com");
        user.setPassword("noone123");
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testTryToSaveUserWithInvalidEmail() {
        User user = new User();
        user.setName("someone");
        user.setEmail("someone.com");
        user.setPassword("noone123");
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testTryToSaveUserWithInvalidPassword() {
        User user = new User();
        user.setName("someone");
        user.setEmail("some@one.com");
        user.setPassword("a123");
        Assert.assertEquals(userService.saveUser(user), false);
    }

    @Test
    public void testSearchUserWithReviewsByName() {
        String userName = "test";
        User user = userService.searchUserWithReviewsByName(userName);
        Assert.assertNotNull(user);
        Assert.assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserWithReviewsByNonExistentName() {
        String userName = "test123";
        User user = userService.searchUserWithReviewsByName(userName);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserWithReviewsByInvalidName() {
        String userName = "";
        User user = userService.searchUserWithReviewsByName(userName);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testDeleteUser() {
        int userId = 1;
        Assert.assertEquals(userService.deleteUser(userId), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeleteNonexistentUser() {
        int userId = 0;
        Assert.assertEquals(userService.deleteUser(userId), MSG_FAIL);
    }

    @Test
    public void testSearchUserByName() {
        String userName = "test";
        Assert.assertNotNull(userService.searchUserByName(userName));
    }

    @Test
    public void testSearchNonexistentUserByName() {
        String userName = "test123";
        Assert.assertNull(userService.searchUserByName(userName));
    }

    @Test
    public void testSearchUserByEmail() {
        String userEmail = "test@test.com";
        Assert.assertNotNull(userService.searchUserByEmail(userEmail));
    }

    @Test
    public void testSearchUserByNonexistentEmail() {
        String userEmail = "test123@test.com";
        Assert.assertNull(userService.searchUserByEmail(userEmail));
    }

    @Test
    public void testUpdateQuote() {
        User user = new User();
        Profile profile = new Profile();
        profile.setId(2);
        profile.setQuote("New quote");
        user.setProfile(profile);
        String userName = "test";
        Assert.assertTrue(userService.saveOrUpdateQuote(user, userName));
    }

    @Test
    public void testUpdateQuoteWithEmptyUserName() {
        User user = new User();
        Profile profile = new Profile();
        profile.setId(2);
        profile.setQuote("New quote");
        user.setProfile(profile);
        String userName = "";
        Assert.assertFalse(userService.saveOrUpdateQuote(user, userName));
    }

    @Test
    public void testUpdateQuoteWithEmptyProfile() {
        User user = new User();
        Profile profile = new Profile();
        profile.setId(0);
        profile.setQuote("");
        user.setProfile(profile);
        String userName = "test";
        Assert.assertFalse(userService.saveOrUpdateQuote(user, userName));
    }

    @Test
    public void testCheckUserPassword() {
        String userEmail = "test@test.com";
        String userPassword = "test123";
        Assert.assertTrue(userService.checkUserPassword(userEmail, userPassword));
    }

    @Test
    public void testCheckUserPasswordWithWrongPwd() {
        String userEmail = "test@test.com";
        String userPassword = "test123456";
        Assert.assertFalse(userService.checkUserPassword(userEmail, userPassword));
    }

    @Test
    public void testUpdateUserPassword() {
        String userName = "test";
        String newPassword = "test123456";
        Assert.assertTrue(userService.updateUserPassword(userName, newPassword));
    }

    @Test
    public void testUpdateUserPasswordWithWrongUser() {
        String userName = "";
        String newPassword = "test123456";
        Assert.assertFalse(userService.updateUserPassword(userName, newPassword));
    }

}
