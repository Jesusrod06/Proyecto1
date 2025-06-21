/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/**
 *Initialize class SearchResult
 * 
 */

public class SearchResult {
    private List<WordPath> foundPaths;
    private Map<String, WordPath> wordToPath;
    private long searchTimeMs;
    private int nodesVisited;
    private String searchAlgorithm;
    private boolean searchCompleted;

    /**
     * Constructs a new SearchResult object with initialized fields.
     */
    public SearchResult() {
        this.foundPaths = new ArrayList<>();
        this.wordToPath = new HashMap<>();
        this.searchTimeMs = 0;
        this.nodesVisited = 0;
        this.searchAlgorithm = "";
        this.searchCompleted = false;
    }

    /**
     * Adds a WordPath to the search result.
     *
     * @param wordPath the WordPath to be added
     */
    public void addWordPath(WordPath wordPath) {
        foundPaths.add(wordPath);
        wordToPath.put(wordPath.getWord(), wordPath);
    }

    /**
     * Returns a list of found WordPaths.
     *
     * @return a list of found WordPaths
     */
    public List<WordPath> getFoundPaths() {
        return new ArrayList<>(foundPaths);
    }

    /**
     * Retrieves the WordPath associated with a given word.
     *
     * @param word the word for which to retrieve the WordPath
     * @return the WordPath associated with the word, or null if not found
     */
    public WordPath getPathForWord(String word) {
        return wordToPath.get(word.toUpperCase());
    }

    /**
     * Returns a list of words that were found during the search.
     *
     * @return a list of found words
     */
    public List<String> getFoundWords() {
        List<String> words = new ArrayList<>();
        for (WordPath path : foundPaths) {
            words.add(path.getWord());
        }
        return words;
    }

    /**
     * Returns the count of found words.
     *
     * @return the number of found words
     */
    public int getFoundWordCount() {
        return foundPaths.size();
    }

    /**
     * Checks if a specific word was found during the search.
     *
     * @param word the word to check
     * @return true if the word was found, false otherwise
     */
    public boolean isWordFound(String word) {
        return wordToPath.containsKey(word.toUpperCase());
    }

    /**
     * Returns the search time in milliseconds.
     *
     * @return the search time in milliseconds
     */
    public long getSearchTimeMs() {
        return searchTimeMs;
    }

    /**
     * Sets the search time in milliseconds.
     *
     * @param searchTimeMs the search time to set
     */
    public void setSearchTimeMs(long searchTimeMs) {
        this.searchTimeMs = searchTimeMs;
    }

    /**
     * Returns the number of nodes visited during the search.
     *
     * @return the number of nodes visited
     */
    public int getNodesVisited() {
        return nodesVisited;
    }

    /**
     * Sets the number of nodes visited during the search.
     *
     * @param nodesVisited the number of nodes visited to set
     */
    public void setNodesVisited(int nodesVisited) {
        this.nodesVisited = nodesVisited;
    }

    /**
     * Returns the name of the search algorithm used.
     *
     * @return the search algorithm name
     */
    public String getSearchAlgorithm() {
        return searchAlgorithm;
    }

    /**
     * Sets the name of the search algorithm used.
     *
     * @param searchAlgorithm the search algorithm name to set
     */
    public void setSearchAlgorithm(String searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    /**
     * Checks if the search has been completed.
     *
     * @return true if the search is completed, false otherwise
     */
    public boolean isSearchCompleted() {
        return searchCompleted;
    }

    /**
     * Sets the completion status of the search.
     *
     * @param searchCompleted the completion status to set
     */
    public void setSearchCompleted(boolean searchCompleted) {
        this.searchCompleted = searchCompleted;
    }

    /**
     * Clears the search results, resetting all fields to their initial state.
     */
    public void clear() {
        foundPaths.clear();
        wordToPath.clear();
        searchTimeMs = 0;
        nodesVisited = 0;
        searchCompleted = false;
    }

    /**
     * Returns a string representation of the search results.
     *
     * @return a string containing the search results
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Search Results:\n");
        sb.append("Algorithm: ").append(searchAlgorithm).append("\n");
        sb.append("Words found: ").append(getFoundWordCount()).append("\n");
        sb.append("Search time: ").append(searchTimeMs).append(" ms\n");
        sb.append("Nodes visited: ").append(nodesVisited).append("\n");
        sb.append("Completed: ").append(searchCompleted).append("\n");

        if (!foundPaths.isEmpty()) {
            sb.append("\nFound words:\n");
            for (WordPath path : foundPaths) {
                sb.append("- ").append(path.toString()).append("\n");
            }
        }

        return sb.toString();
    }
}
