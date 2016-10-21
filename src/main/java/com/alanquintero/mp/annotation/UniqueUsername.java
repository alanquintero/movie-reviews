/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @class UniqueUsername.java
 * @purpose Interface to be used in UniqueUsernameValidator class.
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { UniqueUsernameValidator.class })
public @interface UniqueUsername {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
