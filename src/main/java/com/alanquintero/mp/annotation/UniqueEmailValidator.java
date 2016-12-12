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
 * @class UniqueEmailValidator.java
 * @purpose Validate that email does not exists.
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    /**
     * Check if email exists
     * 
     * @param User_email
     * @param ConstraintValidatorContext_Object
     * @return boolean
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean isValid = false;

        if ((userRepository == null) || (userRepository.findUserByEmail(email) == null)) {
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

}
