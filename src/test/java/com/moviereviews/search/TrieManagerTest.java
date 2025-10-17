/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrieManagerTest {

    private TrieManager trieManager;

    @BeforeEach
    void setUp() {
        trieManager = new TrieManager();
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
