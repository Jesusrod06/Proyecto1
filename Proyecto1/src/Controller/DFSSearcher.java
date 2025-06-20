/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interfaces.LogWindow;
import java.util.List;
import java.util.ArrayList;
import proyecto1.Dictionary;
import proyecto1.Graph;
import proyecto1.Node;
import proyecto1.SearchResult;
import proyecto1.WordPath;



/**
 *
 * @author jesus rodriguez
 */
public class DFSSearcher {
 private Graph graph;
    private Dictionary dictionary;
    private SearchResult result;
    private int nodesVisited;
    private LogWindow logWindow;
    
    public DFSSearcher() {
        this.result = new SearchResult();
    }
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    
    public void setLogWindow(LogWindow logWindow) {
        this.logWindow = logWindow;
    }
    
    public SearchResult search() {
        if (graph == null || dictionary == null) {
            return new SearchResult();
        }
        
        long startTime = System.currentTimeMillis();
        result = new SearchResult();
        result.setSearchAlgorithm("DFS");
        nodesVisited = 0;
        
        // Search from each cell in the grid
        for (int i = 0; i < graph.getRows(); i++) {
            for (int j = 0; j < graph.getCols(); j++) {
                Node startNode = graph.getNode(i, j);
                List<Node> currentPath = new ArrayList<>();
                dfsFromNode(startNode, currentPath, "");
            }
        }
        
        long endTime = System.currentTimeMillis();
        result.setSearchTimeMs(endTime - startTime);
        result.setNodesVisited(nodesVisited);
        result.setSearchCompleted(true);
        
        return result;
    }
    
    private void dfsFromNode(Node currentNode, List<Node> currentPath, String currentWord) {
        if (currentNode == null || currentPath.contains(currentNode)) {
            return;
        }
        
        nodesVisited++;
        currentPath.add(currentNode);
        currentWord += currentNode.getCharacter();
        
        if (logWindow != null) {
            logWindow.appendLog("DFS visitando nodo: (" + currentNode.getRow() + ", " + currentNode.getCol() + ") letra: " + currentNode.getCharacter() + " palabra actual: " + currentWord);
        }
        
        // Check if current word matches any dictionary word
        if (dictionary.containsWord(currentWord) && !result.isWordFound(currentWord)) {
            WordPath wordPath = new WordPath(new ArrayList<>(currentPath));
            result.addWordPath(wordPath);
            dictionary.markWordAsFound(currentWord);
        }
        
        // Continue searching if we haven't found all words yet
        if (result.getFoundWordCount() < dictionary.getTotalWordCount()) {
            // Explore neighbors
            for (Node neighbor : currentNode.getNeighbors()) {
                dfsFromNode(neighbor, currentPath, currentWord);
            }
        }
        
        // Backtrack
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Finds a specific word using DFS and returns its path.
     * @param word The word to search for.
     * @return WordPath if found, null otherwise.
     */
    public WordPath findWord(String word) {
        if (graph == null || word == null || word.length() < 3) {
            return null;
        }
        
        word = word.toUpperCase(); // Ensure word is uppercase
        nodesVisited = 0;
        
        // Try starting from each position in the grid
        for (int i = 0; i < graph.getRows(); i++) {
            for (int j = 0; j < graph.getCols(); j++) {
                Node startNode = graph.getNode(i, j);
                
                // Only start if the first character matches
                if (startNode.getCharacter() == word.charAt(0) && !startNode.isUsed()) {
                    List<Node> path = new ArrayList<>();
                    boolean[][] visited = new boolean[graph.getRows()][graph.getCols()];
                    
                    if (logWindow != null) {
                        logWindow.appendLog("Intentando desde posición (" + i + "," + j + ") con letra '" + startNode.getCharacter() + "'");
                    }
                    
                    WordPath foundPath = dfsRecursive(startNode, path, "", word, visited);
                    if (foundPath != null) {
                        if (logWindow != null) {
                            logWindow.appendLog("¡Palabra encontrada! Camino: " + pathToString(foundPath.getPath()));
                        }
                        return foundPath;
                    }
                }
            }
        }
        return null; // Word not found
    }

    private WordPath dfsRecursive(Node currentNode, List<Node> currentPath, String currentWord, String targetWord, boolean[][] visited) {
        // Verificar condiciones base
        if (currentNode == null || currentNode.isUsed() || 
            visited[currentNode.getRow()][currentNode.getCol()]) {
            return null;
        }

        nodesVisited++;
        
        // Marcar como visitado en este camino
        visited[currentNode.getRow()][currentNode.getCol()] = true;
        currentPath.add(currentNode);
        String newWord = currentWord + currentNode.getCharacter();

        if (logWindow != null) {
            logWindow.appendLog("DFS: Visitando (" + currentNode.getRow() + "," + currentNode.getCol() + ") -> " + newWord);
        }

        // ¿Hemos encontrado la palabra completa?
        if (newWord.equals(targetWord)) {
            return new WordPath(new ArrayList<>(currentPath)); // ¡Palabra encontrada!
        }
        
        // Pruning: si newWord no es un prefijo de targetWord, parar
        if (!targetWord.startsWith(newWord)) {
            // Backtrack
            currentPath.remove(currentPath.size() - 1);
            visited[currentNode.getRow()][currentNode.getCol()] = false;
            return null;
        }

        // Explorar vecinos adyacentes
        List<Node> neighbors = getAdjacentNodes(currentNode);
        for (Node neighbor : neighbors) {
            if (!neighbor.isUsed() && !visited[neighbor.getRow()][neighbor.getCol()]) {
                WordPath foundPath = dfsRecursive(neighbor, currentPath, newWord, targetWord, visited);
                if (foundPath != null) {
                    return foundPath; // Propagar el camino encontrado
                }
            }
        }

        // Backtrack
        currentPath.remove(currentPath.size() - 1);
        visited[currentNode.getRow()][currentNode.getCol()] = false;
        return null;
    }
    
    /**
     * Get all adjacent nodes (8 directions) that are valid and within bounds
     */
    private List<Node> getAdjacentNodes(Node node) {
        List<Node> adjacent = new ArrayList<>();
        int row = node.getRow();
        int col = node.getCol();
        
        // 8 direcciones: arriba, abajo, izquierda, derecha, y las 4 diagonales
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];
            
            if (isValidPosition(newRow, newCol)) {
                adjacent.add(graph.getNode(newRow, newCol));
            }
        }
        
        return adjacent;
    }
    
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < graph.getRows() && col >= 0 && col < graph.getCols();
    }
    
    private String pathToString(List<Node> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            Node node = path.get(i);
            sb.append("(").append(node.getRow()).append(",").append(node.getCol()).append(")");
            if (i < path.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }
}
