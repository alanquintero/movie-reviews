package com.moviereviews.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SearchManagerTest {

    private SearchManager searchManager;

    @BeforeEach
    void setUp() {
        searchManager = new SearchManager();
    }

    @Test
    void insertWord() {
        // Given
        final String word = "test";

        // When
        searchManager.insert(word);

        // Then
        TrieNode current = searchManager.getTrieNode();
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
        searchManager.insert(word1);
        searchManager.insert(word2);
        searchManager.insert(word3);

        // Then
        TrieNode current = searchManager.getTrieNode();
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
