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

/**
 * @class Format.java
 * @purpose Class used to give format to the inputs.
 */
public class Format {

    /**
     * Remove blank spaces at the front and end of the String
     * 
     * @param String
     * @return String
     */
    public static String removeBlanks(String input) {
        return input.replaceAll(FORMAT_BLANK_SPACE, EMPTY_STRING);
    }

    /**
     * Add format to YouTube URL
     * 
     * @param String
     * @return String
     */
    public static String getYoutubeUrl(String url) {
        url = url.substring(0, 24) + FORMAT_YT_EMBED + url.substring(32, url.length());

        return url;
    }

}
