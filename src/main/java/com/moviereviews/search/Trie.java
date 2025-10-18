/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Trie (prefix tree) data structure used for storing and searching movie titles efficiently.
 * <p>
 * Each movie title is broken down into individual characters, where each character is represented
 * by a {@link TrieNode}. The Trie supports fast prefix-based lookups, making it ideal for implementing
 * features such as movie title auto-completion or prefix search.
 * </p>
 *
 * <p>
 * This class implements the Singleton pattern to ensure only one instance of the Trie
 * is used throughout the application.
 * </p>
 *
 * <p><strong>Example usage:</strong><br>
 * {@code trie.insert("Inception");}<br>
 * </p>
 *
 * @author Alan Quintero
 */
public class Trie {

    /**
     * Singleton instance of the Trie.
     */
    private static Trie trie = null;
    /**
     * Root node of the Trie, representing the empty prefix.
     */
    private final TrieNode rootNode;

    private Trie() {
        rootNode = new TrieNode();
    }

    public static Trie init() {
        if (trie == null) {
            trie = new Trie();
        }
        return trie;
    }

    /**
     * Inserts a movie title into the Trie.
     *
     * @param word the movie title.
     */
    public void insertWord(final String word, final long movieId) {
        TrieNode currentNode = rootNode;

        int index = 0;
        char[] charArray = word.toCharArray();

        while (index < charArray.length) {
            char c = charArray[index];
            if (currentNode.containsNode(c)) {
                currentNode = currentNode.getNode(c);
                index++;
            } else {
                break;
            }
        }

        while (index < charArray.length) {
            currentNode = currentNode.addNode(charArray[index]);
            index++;
        }
        currentNode.setEndOfWord(true);
        currentNode.setMovieId(movieId);
    }

    /**
     * Returns the list of Movie ids based on the given movie title prefix.
     *
     * @param prefix the movie title prefix.
     * @return list of movie ids.
     */
    public List<Long> search(final String prefix) {
        final List<Long> movieIds = new ArrayList<>();
        if (prefix.length() < 2) {
            return movieIds;
        }
        TrieNode currentNode = rootNode;

        for (final char c : prefix.toCharArray()) {
            if (currentNode.containsNode(c)) {
                currentNode = currentNode.getNode(c);
            } else {
                break;
            }
        }

        getMoviesIdForTrieNode(currentNode, movieIds);
        return movieIds;
    }

    private void getMoviesIdForTrieNode(final TrieNode currentNode, final List<Long> movieIds) {
        if (currentNode.isEndOfWord()) {
            movieIds.add(currentNode.getMovieId());
        }
        final Map<Character, TrieNode> children = currentNode.getChildren();
        for (final Character key : children.keySet()) {
            getMoviesIdForTrieNode(children.get(key), movieIds);
        }
    }

    /**
     * Use it ONLY for testing.
     */
    public void clear() {
        trie = new Trie();
    }


    /**
     * Use it ONLY for testing.
     *
     * @return the root node.
     */
    public TrieNode getTrieNode() {
        return rootNode;
    }

    /**
     * Prints all the movie title stored in the Trie.
     * Use it ONLY for manual testing.
     */
    public void print() {
        System.out.println("********** TRIE **********");
        printHelper(rootNode, "");
        System.out.println("**************************");
    }

    /**
     * Collects the movie titles using recursion.
     * Use it ONLY for manual testing.
     */
    public void printHelper(final TrieNode currentNode, final String currentWord) {
        if (currentNode.isEndOfWord()) {
            System.out.println(currentWord);
            return;
        }
        final Map<Character, TrieNode> children = currentNode.getChildren();
        for (final Character key : children.keySet()) {
            printHelper(children.get(key), (currentWord + key));
        }
    }
}
