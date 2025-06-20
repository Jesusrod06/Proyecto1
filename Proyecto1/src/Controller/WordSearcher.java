/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.LogWindow;
import proyecto1.Dictionary;
import proyecto1.Graph;
import proyecto1.SearchResult;

/**
 *
 * @author jesus rodriguez
 */
public class WordSearcher {
     private Graph graph;
    private Dictionary dictionary;
    private DFSSearcher dfsSearcher;
    private BFSSearcher bfsSearcher;
    
    public WordSearcher() {
        this.dictionary = new Dictionary();
        this.dfsSearcher = new DFSSearcher();
        this.bfsSearcher = new BFSSearcher();
    }
    
    public void setGrid(char[][] grid) {
        this.graph = new Graph(grid);
        this.dfsSearcher.setGraph(graph);
        this.bfsSearcher.setGraph(graph);
    }
    
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.dfsSearcher.setDictionary(dictionary);
        this.bfsSearcher.setDictionary(dictionary);
    }
    
    public SearchResult searchWithDFS() {
        if (graph == null || dictionary == null) {
            return new SearchResult();
        }
        return dfsSearcher.search();
    }
    
    public SearchResult searchWithBFS() {
        if (graph == null || dictionary == null) {
            return new SearchResult();
        }
        return bfsSearcher.search();
    }
    
    public SearchResult searchForSpecificWord(String word, String algorithm) {
        if (graph == null) {
            return new SearchResult();
        }
        
        Dictionary singleWordDict = new Dictionary();
        singleWordDict.addWord(word);
        
        if ("DFS".equalsIgnoreCase(algorithm)) {
            dfsSearcher.setDictionary(singleWordDict);
            return dfsSearcher.search();
        } else if ("BFS".equalsIgnoreCase(algorithm)) {
            bfsSearcher.setDictionary(singleWordDict);
            return bfsSearcher.search();
        }
        
        return new SearchResult();
    }
    
    public proyecto1.WordPath findWord(String word, String algorithm) {
        if ("DFS".equalsIgnoreCase(algorithm)) {
            return dfsSearcher.findWord(word);
        } else if ("BFS".equalsIgnoreCase(algorithm)) {
            return bfsSearcher.findWord(word);
        }
        return null;
    }
    
    public Controller.TreeVisualizer.TreeNode findWordAndBuildTree(String word) {
        // Esta funcionalidad es específica de BFS según los requerimientos
        return bfsSearcher.findWordAndBuildTree(word);
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public Dictionary getDictionary() {
        return dictionary;
    }
    
    public boolean hasValidConfiguration() {
        return graph != null && dictionary != null && dictionary.getTotalWordCount() > 0;
    }
    
    public void reset() {
        if (dictionary != null) {
            dictionary.resetFoundWords();
        }
    }
    
    public void setLogWindow(LogWindow logWindow) {
        this.dfsSearcher.setLogWindow(logWindow);
        this.bfsSearcher.setLogWindow(logWindow);
    }
} 

