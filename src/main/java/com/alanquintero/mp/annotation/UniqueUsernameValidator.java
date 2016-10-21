/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.alanquintero.mp.repository.UserRepository;

/**
 * @class UniqueUsernameValidator.java
 * @purpose Validate that user name does not exists.
 */
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    /**
     * Check if user name exists
     * 
     * @param User_username
     * @param ConstraintValidatorContext_Object
     * @return boolean
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        boolean isValid = false;
        if ((userRepository == null) || (userRepository.findUserByName(username) == null)) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

}
