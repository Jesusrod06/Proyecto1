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
 *
 * @author jesus rodriguez
 */
public class SearchResult {
     private List<WordPath> foundPaths;
    private Map<String, WordPath> wordToPath;
    private long searchTimeMs;
    private int nodesVisited;
    private String searchAlgorithm;
    private boolean searchCompleted;
    
    public SearchResult() {
        this.foundPaths = new ArrayList<>();
        this.wordToPath = new HashMap<>();
        this.searchTimeMs = 0;
        this.nodesVisited = 0;
        this.searchAlgorithm = "";
        this.searchCompleted = false;
    }
    
    public void addWordPath(WordPath wordPath) {
        foundPaths.add(wordPath);
        wordToPath.put(wordPath.getWord(), wordPath);
    }
    
    public List<WordPath> getFoundPaths() {
        return new ArrayList<>(foundPaths);
    }
    
    public WordPath getPathForWord(String word) {
        return wordToPath.get(word.toUpperCase());
    }
    
    public List<String> getFoundWords() {
        List<String> words = new ArrayList<>();
        for (WordPath path : foundPaths) {
            words.add(path.getWord());
        }
        return words;
    }
    
    public int getFoundWordCount() {
        return foundPaths.size();
    }
    
    public boolean isWordFound(String word) {
        return wordToPath.containsKey(word.toUpperCase());
    }
    
    public long getSearchTimeMs() {
        return searchTimeMs;
    }
    
    public void setSearchTimeMs(long searchTimeMs) {
        this.searchTimeMs = searchTimeMs;
    }
    
    public int getNodesVisited() {
        return nodesVisited;
    }
    
    public void setNodesVisited(int nodesVisited) {
        this.nodesVisited = nodesVisited;
    }
    
    public String getSearchAlgorithm() {
        return searchAlgorithm;
    }
    
    public void setSearchAlgorithm(String searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }
    
    public boolean isSearchCompleted() {
        return searchCompleted;
    }
    
    public void setSearchCompleted(boolean searchCompleted) {
        this.searchCompleted = searchCompleted;
    }
    
    public void clear() {
        foundPaths.clear();
        wordToPath.clear();
        searchTimeMs = 0;
        nodesVisited = 0;
        searchCompleted = false;
    }
    
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
