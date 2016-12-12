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

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
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
@PrepareForTest(Logger.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private static String USER_CODE = "MQ==";
    private static String USER_EMAIL = "test@test.com";
    private static String USER_NAME = "test";
    private static String USER_PASSWORD = "test123";
    private static String USER_NEW_PASSWORD = "test123456";
    private static String INEXISTENT_USER_NAME = "test123";
    private static String NEW_USER_EMAIL = "some@one.com";
    private static String NEW_USER_NAME = "someone";
    private static String NEW_USER_PASSWORD = "noone123";
    private static Logger mockLogger;

    private User user;

    @Before
    public void setData() {
        user = new User();
        user.setName(NEW_USER_NAME);
        user.setEmail(NEW_USER_EMAIL);
        user.setPassword(NEW_USER_PASSWORD);

        mockLogger = Mockito.mock(Logger.class);
        Whitebox.setInternalState(UserServiceImpl.class, "logger", mockLogger);
    }

    @Test
    public void testGetAllUsers() {
        Assert.assertNotNull(userService.getAllUsers());
    }

    @Test
    public void testSearchUserById() {
        User user = userService.searchUserById(USER_CODE);

        Assert.assertNotNull(user);
        Assert.assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserByNonexistentId() {
        User user = userService.searchUserById(EMPTY_STRING);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSearchUserWithReviewsById() {
        User user = userService.searchUserWithReviewsById(USER_CODE);
        Assert.assertNotNull(user);
        Assert.assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserWithReviewsByNonexistentId() {
        User user = userService.searchUserWithReviewsById(EMPTY_STRING);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSaveUser() {
        Assert.assertEquals(userService.saveUser(user), true);
    }

    @Test
    public void testTryToSaveNullUser() {
        User user = null;

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testTryToSaveEmptyUser() {
        User user = new User();

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testTryToSaveExistentUser() {

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testTryToSaveUserWithExistentEmail() {
        user.setEmail(USER_EMAIL);

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testTryToSaveUserWithInvalidName() {
        User user = new User();
        user.setName("s");

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testTryToSaveUserWithInvalidEmail() {
        User user = new User();
        user.setEmail("someone.com");

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testTryToSaveUserWithInvalidPassword() {
        User user = new User();
        user.setPassword("a123");

        Assert.assertEquals(userService.saveUser(user), false);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSearchUserWithReviewsByName() {
        User user = userService.searchUserWithReviewsByName(USER_NAME);

        Assert.assertNotNull(user);
        Assert.assertNotEquals(user.getName(), MSG_FAIL);
    }

    @Test
    public void testSearchUserWithReviewsByNonExistentName() {
        User user = userService.searchUserWithReviewsByName(INEXISTENT_USER_NAME);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testSearchUserWithReviewsByInvalidName() {
        User user = userService.searchUserWithReviewsByName(EMPTY_STRING);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), MSG_FAIL);
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testDeleteUser() {
        Assert.assertEquals(userService.deleteUser(USER_CODE), MSG_SUCCESS);
    }

    @Test
    @WithMockUser(roles = { ROLE_ADMIN })
    public void testTryToDeleteNonexistentUser() {
        Assert.assertEquals(userService.deleteUser(EMPTY_STRING), MSG_FAIL);
    }

    @Test
    public void testSearchUserByName() {
        Assert.assertNotNull(userService.searchUserByName(USER_NAME));
    }

    @Test
    public void testSearchNonexistentUserByName() {
        Assert.assertNull(userService.searchUserByName(INEXISTENT_USER_NAME));
    }

    @Test
    public void testSearchUserByEmail() {
        Assert.assertNotNull(userService.searchUserByEmail(USER_EMAIL));
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
        profile.setCode("MQ==");
        profile.setQuote("New quote");
        user.setProfile(profile);

        Assert.assertTrue(userService.saveOrUpdateQuote(user, USER_NAME));
    }

    @Test
    public void testUpdateQuoteWithEmptyUserName() {
        User user = new User();
        Profile profile = new Profile();
        profile.setId(2);
        profile.setQuote("New quote");
        user.setProfile(profile);

        Assert.assertFalse(userService.saveOrUpdateQuote(user, EMPTY_STRING));
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testUpdateQuoteWithEmptyProfile() {
        User user = new User();
        Profile profile = new Profile();
        profile.setId(0);
        profile.setQuote(EMPTY_STRING);
        user.setProfile(profile);

        Assert.assertFalse(userService.saveOrUpdateQuote(user, USER_NAME));
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

    @Test
    public void testCheckUserPassword() {
        Assert.assertTrue(userService.checkUserPassword(USER_EMAIL, USER_PASSWORD));
    }

    @Test
    public void testCheckUserPasswordWithWrongPwd() {
        Assert.assertFalse(userService.checkUserPassword(USER_EMAIL, USER_NEW_PASSWORD));
    }

    @Test
    public void testUpdateUserPassword() {
        Assert.assertTrue(userService.updateUserPassword(USER_NAME, USER_NEW_PASSWORD));
    }

    @Test
    public void testUpdateUserPasswordWithWrongUser() {
        Assert.assertFalse(userService.updateUserPassword(EMPTY_STRING, USER_NEW_PASSWORD));
        Mockito.verify(mockLogger).info(LOG_INVALID_INPUT);
    }

}
