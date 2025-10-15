/**
 * Copyright 2025 Alan Quintero
 * Source: https://github.com/alanquintero/movie-reviews
 */
package com.moviereviews.search;

import java.util.*;

/**
 * Represents a single node in a Trie data structure used to store movie titles.
 * <p>
 * Each node corresponds to a character in the title. Nodes are connected through a
 * {@code Map<Character, TrieNode>} where each key represents a character and the value
 * represents the next node in the Trie.
 * </p>
 *
 * <p>
 * When {@code isEndOfWord} is set to {@code true}, it indicates that the path from
 * the root node to this node forms a complete movie title.
 * </p>
 *
 * <p><strong>Example:</strong><br>
 * For the title "TENET", the Trie would consist of nodes linked in sequence for
 * 'T' → 'E' → 'N' → 'E' → 'T', where the final node has {@code isEndOfWord = true}.
 * </p>
 *
 * @author Alan Quintero
 */
public class TrieNode {

    private final Map<Character, TrieNode> children;
    // Used for indicating the end of a string
    private boolean isEndOfWord;

    public TrieNode() {
        this(false);
    }

    public TrieNode(final boolean isEndOfWord) {
        this.isEndOfWord = isEndOfWord;
        this.children = new HashMap<>();
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(final boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public boolean containsNode(final char c) {
        return children.containsKey(c);
    }

    public TrieNode getNode(final char c) {
        return children.get(c);
    }

    public TrieNode addNode(final char c) {
        final TrieNode newNode = new TrieNode();
        children.put(c, newNode);
        return newNode;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }
}
