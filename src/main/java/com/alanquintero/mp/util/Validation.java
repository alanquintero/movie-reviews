/*******************************************************
 * Copyright (C) 2016 Alan Quintero <alan_q_b@hotmail.com>
 * 
 * This file is part of My Personal Project: "Movie Pick".
 * 
 * "Movie Pick" can not be copied and/or distributed without the express
 * permission of Alan Quintero.
 *******************************************************/
package com.alanquintero.mp.util;

import static com.alanquintero.mp.util.Consts.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @class Validation.java
 * @purpose: Class used to validate inputs.
 */
public class Validation {

    public static boolean isValidString(String word) {
        boolean isValid = false;
        if ((word != null) && (!word.equals(EMPTY_STRING))) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateWordLen(String word, int len) {
        boolean isValid = false;
        if (word.length() >= len) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
