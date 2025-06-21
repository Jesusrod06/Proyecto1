/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
/**
 * start class dictionary
 * 
 */

public class Dictionary {
    private Map<String, Boolean> words; // Word -> isFound

    /**
     * Constructs an empty Dictionary.
     */
    public Dictionary() {
        this.words = new LinkedHashMap<>();
    }
    
    /**
     * Constructs a Dictionary with a list of words.
     * @param wordList The list of words to add to the dictionary.
     */
    public Dictionary(List<String> wordList) {
        this.words = new LinkedHashMap<>();
        addWords(wordList);
    }
    
    /**
     * Adds a single word to the dictionary.
     * @param word The word to add.
     */
    public void addWord(String word) {
        if (word != null && !word.trim().isEmpty()) {
            words.put(word.toUpperCase().trim(), false);
        }
    }
    
    /**
     * Adds multiple words to the dictionary from a list.
     * @param wordList The list of words to add.
     */
    public void addWords(List<String> wordList) {
        for (String word : wordList) {
            addWord(word);
        }
    }
    
    /**
     * Checks if the dictionary contains a specific word.
     * @param word The word to check.
     * @return true if the word is in the dictionary, false otherwise.
     */
    public boolean containsWord(String word) {
        return words.containsKey(word.toUpperCase());
    }
    
    /**
     * Marks a word as found in the dictionary.
     * @param word The word to mark as found.
     */
    public void markWordAsFound(String word) {
        String upperWord = word.toUpperCase();
        if (words.containsKey(upperWord)) {
            words.put(upperWord, true);
        }
    }
    
    /**
     * Checks if a specific word has been found.
     * @param word The word to check.
     * @return true if the word has been found, false otherwise.
     */
    public boolean isWordFound(String word) {
        Boolean found = words.get(word.toUpperCase());
        return found != null && found;
    }
    
    /**
     * Retrieves a list of all words in the dictionary.
     * @return A list of all words.
     */
    public List<String> getAllWords() {
        return new ArrayList<>(words.keySet());
    }
    
    /**
     * Retrieves a list of words that have been found.
     * @return A list of found words.
     */
    public List<String> getFoundWords() {
        List<String> foundWords = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : words.entrySet()) {
            if (entry.getValue()) {
                foundWords.add(entry.getKey());
            }
        }
        return foundWords;
    }
    
    /**
     * Retrieves a list of words that have not been found.
     * @return A list of remaining words.
     */
    public List<String> getRemainingWords() {
        List<String> remaining = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : words.entrySet()) {
            if (!entry.getValue()) {
                remaining.add(entry.getKey());
            }
        }
        return remaining;
    }
    
    /**
     * Returns the total number of words in the dictionary.
     * @return The total word count.
     */
    public int getTotalWordCount() {
        return words.size();
    }
    
    /**
     * Returns the count of words that have been found.
     * @return The count of found words.
     */
    public int getFoundWordCount() {
        int count = 0;
        for (Boolean found : words.values()) {
            if (found) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Clears all words from the dictionary.
     */
    public void clear() {
        words.clear();
    }
    
    /**
     * Resets the found status of all words in the dictionary to false.
     */
    public void resetFoundWords() {
        for (String word : words.keySet()) {
            words.put(word, false);
        }
    }
}
