/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrieManagerTest {

    private TrieManager trieManager;

    @BeforeEach
    void setUp() {
        trieManager = new TrieManager();
    }

    @AfterEach
    void tearDown() {
        trieManager.clear();
    }

    @Test
    void insertWord() {
        // Given
        final String word = "test";

        // When
        trieManager.insertMovieTitle(word, 1L);

        // Then
        TrieNode current = trieManager.getTrieNode();
        assertNotNull(current);
        final Map<Character, TrieNode> children = current.getChildren();
        assertNotNull(children);
        assertFalse(children.isEmpty());
        checkTrieContainsWord(current, word);
    }

    @Test
    void insertWords() {
        // Given
        final String word1 = "test";
        final String word2 = "tester";
        final String word3 = "cat";

        // When
        trieManager.insertMovieTitle(word1, 1L);
        trieManager.insertMovieTitle(word2, 2L);
        trieManager.insertMovieTitle(word3, 3L);

        // Then
        TrieNode current = trieManager.getTrieNode();
        assertNotNull(current);
        final Map<Character, TrieNode> children = current.getChildren();
        assertNotNull(children);
        assertFalse(children.isEmpty());
        checkTrieContainsWord(current, word1);
        checkTrieContainsWord(current, word2);
        checkTrieContainsWord(current, word3);
    }

    @Test
    void searchMovieByTitlePrefix() {
        // Given
        trieManager.insertMovieTitle("test", 1L);
        trieManager.insertMovieTitle("tester", 2L);
        trieManager.insertMovieTitle("cat", 3L);
        trieManager.insertMovieTitle("terminator", 4L);
        trieManager.insertMovieTitle("testing", 5L);

        // When
        final List<Long> movieIds = trieManager.searchMovieByTitlePrefix("te");

        // Then
        assertNotNull(movieIds);
        assertFalse(movieIds.isEmpty());
        assertEquals(4, movieIds.size());
        assertTrue(movieIds.contains(1L));
        assertTrue(movieIds.contains(2L));
        assertTrue(movieIds.contains(4L));
        assertTrue(movieIds.contains(5L));
        assertFalse(movieIds.contains(3L));
    }

    @Test
    void searchMovieByTitlePrefix_noMoviesFound() {
        // Given & When
        final List<Long> movieIds = trieManager.searchMovieByTitlePrefix("te");

        // Then
        assertNotNull(movieIds);
        assertTrue(movieIds.isEmpty());
    }

    private void checkTrieContainsWord(TrieNode current, final String word) {
        // Check that the given word is there
        for (final char c : word.toCharArray()) {
            TrieNode tmp = current.getChildren().get(c);
            assertNotNull(tmp);
            current = tmp;
        }
        // Check that the last node of the word has the EndOfWord flag set to true
        assertTrue(current.isEndOfWord());
    }
}
