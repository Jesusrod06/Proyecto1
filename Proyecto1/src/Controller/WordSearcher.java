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
  * Main controller for coordinating WordSearch operations.
  * Manages the graph, dictionary and search algorithms.
  * 
  * <p>This class serves as the central coordination point between different
  * components of the word search system. It is responsible for:</p>
  * <ul>
  *   <li>Maintaining references to the grid graph and dictionary</li>
  *   <li>Coordinating operations between DFS and BFS algorithms</li>
  *   <li>Providing a unified interface for performing searches</li>
  * </ul>
  * 
  */

public class WordSearcher {
    /** Graph representing the puzzle's letter grid */
    private Graph puzzleGraph;
    
    /** Dictionary containing the words to search for */
    private Dictionary wordDictionary;
    
    /** Depth-first search algorithm instance */
    private DFSSearcher depthFirstSearcher;
    
    /** Breadth-first search algorithm instance */
    private BFSSearcher breadthFirstSearcher;
    
   /**
   * Sets up the controller with empty versions of what it needs.
   * 
   * <p>Automatically makes an empty dictionary and gets DFS and BFS
   * search algorithms ready to go.</p>
   */
    public WordSearcher() {
        this.wordDictionary = new Dictionary();
        this.depthFirstSearcher = new DFSSearcher();
        this.breadthFirstSearcher = new BFSSearcher();
    }
    
   /**
    * Sets up the character grid for search operations.
    * 
    * <p>Takes the letter grid you provide and builds a new graph from it,
    * then sets it up for both search algorithms to use.</p>
    * 
    * @param gridData the 2D array of characters representing the letter grid
    * @throws IllegalArgumentException if gridData is null or has wonky dimensions
    */
    public void setGrid(char[][] gridData) {
        this.puzzleGraph = new Graph(gridData);
        this.depthFirstSearcher.setGraph(puzzleGraph);
        this.breadthFirstSearcher.setGraph(puzzleGraph);
    }
    
    /**
     * Sets up the word dictionary for both search algorithms to use.
     * 
     * @param dictionary your collection of target words we'll be searching for
     */
    public void setDictionary(Dictionary dictionary) {
        this.wordDictionary = dictionary;
        this.depthFirstSearcher.setDictionary(dictionary);
        this.breadthFirstSearcher.setDictionary(dictionary);
    }
    
    /**
     * Does a complete search using DFS (goes deep first).
     *  
     * <p>Checks the whole grid for all dictionary words using this method.
     * Good when you care more about finding deep paths than quick results.</p>
     * 
     * @return What we found (words, time, and some numbers)
     * @see #searchWithBFS() (the other search option)
     */
    public SearchResult searchWithDFS() {
        if (puzzleGraph == null || wordDictionary == null) {
            return new SearchResult();
        }
        return depthFirstSearcher.search();
    }
    
    /**
     * Does a full search using BFS (Breadth-First Search).
     * 
     *  <p>Looks for all the dictionary words in the grid using the "go wide first" 
     * approach. This one checks everything level by level like a careful inspector.</p>
     * 
     * @return SearchResult with whatever we managed to find
     * @see #searchWithDFS() (the deep-dive alternative)
     */
    public SearchResult searchWithBFS() {
        if (puzzleGraph == null || wordDictionary == null) {
            return new SearchResult();
        }
        return breadthFirstSearcher.search();
    }
    
    /**
     * Goes on a word hunt for one special term.
     * 
     * <p>We'll make a mini-dictionary with just your word and send
     * either DFS (deep dive) or BFS (wide look) to find it.</p>
     * 
     * @param targetWord the word you wanna find (case doesn't matter)
     * @param algorithm your search buddy "DFS" or "BFS"
     * @return A neat package with what we discovered
     * @throws IllegalArgumentException if you get creative with algorithm names
     */
    public SearchResult searchForSpecificWord(String targetWord, String algorithm) {
        if (puzzleGraph == null) {
            return new SearchResult();
        }
        
        Dictionary singleWordDictionary = new Dictionary();
        singleWordDictionary.addWord(targetWord);
        
        if ("DFS".equalsIgnoreCase(algorithm)) {
            depthFirstSearcher.setDictionary(singleWordDictionary);
            return depthFirstSearcher.search();
        } else if ("BFS".equalsIgnoreCase(algorithm)) {
            breadthFirstSearcher.setDictionary(singleWordDictionary);
            return breadthFirstSearcher.search();
        }
        
        return new SearchResult();
    }
    
    /**
     * Finds the exact path for a word using your chosen algorithm.
     * 
     * @return WordPath showing the node-by-node journey of the word,
     *         or nothing if we strike out
     */
    public proyecto1.WordPath findWord(String palabra, String algoritmo) {
        if ("DFS".equalsIgnoreCase(algoritmo)) {
            return depthFirstSearcher.findWord(palabra);
        } else if ("BFS".equalsIgnoreCase(algoritmo)) {
            return breadthFirstSearcher.findWord(palabra);
        }
        return null;
    }
    
    /**
     * Finds a word and builds its search tree structure.
     * 
     *  <p><strong>Note:</strong> This feature is specific to the BFS algorithm
     * as per project requirements, since BFS naturally builds more
     * structured exploration trees.</p>
     * 
     * @return TreeNode representing the root of the search tree,
     *         or null if the word isn't found
     * @see TreeVisualizer.TreeNode
     */
    public Controller.TreeVisualizer.TreeNode findWordAndBuildTree(String palabra) {
        // Esta funcionalidad es específica de BFS según los requerimientos del proyecto
        return breadthFirstSearcher.findWordAndBuildTree(palabra);
    }
    
    /**
     * Returns the graph structure of our current grid.
     * 
     * @return the grid's graph, or null if we haven't set one up yet
     */
    public Graph getGraph() {
        return puzzleGraph;
    }
    
    /**
     * Grabs the word list we're currently using.
     * 
     * @return our dictionary if it exists, nothing otherwise
     */
    public Dictionary getDictionary() {
        return wordDictionary;
    }
    
    /**
     * Checks if we're all set to start searching.
     * 
     *  <p>For this to work, we need:</p>
     * <ul>
     *   <li>A grid graph ready to go</li>
     *   <li>A dictionary loaded up</li>
     *   <li>At least one word to actually search for</li>
     * </ul>
     * 
     * @return true if we're good to go, false if not ready yet
     */
    public boolean hasValidConfiguration() {
        return puzzleGraph != null && wordDictionary != null && wordDictionary.getTotalWordCount() > 0;
    }
    
    /**
     * Resets the found-word status in the dictionary.
     * 
     * <p>Handy when you want to run multiple searches with the same 
     * dictionary without carrying over results from previous searches.</p>
     */
    public void reset() {
        if (wordDictionary != null) {
            wordDictionary.resetFoundWords();
        }
    }
    
    /**
 * Sets up the LogWindow for both search algorithms.
 * 
 * <p>Lets the algorithms log their progress and decisions during searches,
 * which makes debugging and analysis way easier.</p>
 * 
 * @param ventanaLog the window where algorithm logs will be displayed
 */
    public void setLogWindow(LogWindow ventanaLog) {
        this.depthFirstSearcher.setLogWindow(ventanaLog);
        this.breadthFirstSearcher.setLogWindow(ventanaLog);
    }
} 

