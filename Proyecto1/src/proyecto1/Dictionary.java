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
 *
 * @author jesus rodriguez
 */
public class Dictionary {
    private  Map<String, Boolean> words; // Word -> isFound
     public Dictionary() {
        this.words = new LinkedHashMap<>();
    }
    
    public Dictionary(List<String> wordList) {
        this.words = new LinkedHashMap<>();
        addWords(wordList);
    }
    
    public void addWord(String word) {
        if (word != null && !word.trim().isEmpty()) {
            words.put(word.toUpperCase().trim(), false);
        }
    }
    
    public void addWords(List<String> wordList) {
        for (String word : wordList) {
            addWord(word);
        }
    }
    
    public boolean containsWord(String word) {
        return words.containsKey(word.toUpperCase());
    }
    
    public void markWordAsFound(String word) {
        String upperWord = word.toUpperCase();
        if (words.containsKey(upperWord)) {
            words.put(upperWord, true);
        }
    }
    
    public boolean isWordFound(String word) {
        Boolean found = words.get(word.toUpperCase());
        return found != null && found;
    }
    
    public List<String> getAllWords() {
        return new ArrayList<>(words.keySet());
    }
    
    public List<String> getFoundWords() {
        List<String> foundWords = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : words.entrySet()) {
            if (entry.getValue()) {
                foundWords.add(entry.getKey());
            }
        }
        return foundWords;
    }
    
    public List<String> getRemainingWords() {
        List<String> remaining = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : words.entrySet()) {
            if (!entry.getValue()) {
                remaining.add(entry.getKey());
            }
        }
        return remaining;
    }
    
    public int getTotalWordCount() {
        return words.size();
    }
    
    public int getFoundWordCount() {
        int count = 0;
        for (Boolean found : words.values()) {
            if (found) {
                count++;
            }
        }
        return count;
    }
    
    public void clear() {
        words.clear();
    }
    
    public void resetFoundWords() {
        for (String word : words.keySet()) {
            words.put(word, false);
        }
    }
}
