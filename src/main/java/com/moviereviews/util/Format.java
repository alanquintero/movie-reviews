/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.util;

import static com.moviereviews.util.Consts.*;

public class Format {

    public static String removeBlanks(String input) {
        return input.replaceAll(FORMAT_BLANK_SPACE, EMPTY_STRING);
    }

    public static String getYoutubeUrl(String url) {
        url = url.substring(0, 24) + FORMAT_YT_EMBED + url.substring(32, url.length());

        return url;
    }
}
