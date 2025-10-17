/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.search;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for managing Trie operations related to movie titles.
 * <p>
 * This class acts as the main interface for working with the {@link Trie} data structure.
 * It provides methods for initializing the Trie and performing core operations such as
 * inserting, searching, or clearing movie titles.
 * </p>
 *
 * <p>
 * This class will be initialized at application startup to preload movie titles
 * from a data source, enabling fast prefix-based searches throughout the app.
 * </p>
 *
 * @author Alan Quintero
 */
@Service
public class TrieManager {

    /**
     * The main Trie instance used for all movie title operations.
     */
    private final Trie trie;

    public TrieManager() {
        trie = Trie.init();
    }

    /**
     * Inserts the given word into the Trie.
     *
     * @param word the movie title.
     */
    public void insertMovieTitle(final String word, final long movieId) {
        trie.insertWord(word, movieId);
    }

    /**
     * Returns the list of Movie ids based on the given movie title prefix.
     *
     * @param titlePrefix the movie title prefix.
     * @return list of movie ids.
     */
    public List<Long> searchMovieByTitlePrefix(final String titlePrefix) {
        return trie.search(titlePrefix);
    }

    /**
     * Use it ONLY for manual testing.
     */
    public TrieNode getTrieNode() {
        return trie.getTrieNode();
    }

    /**
     * Use it ONLY for manual testing.
     */
    public void print() {
        trie.print();
    }


}
