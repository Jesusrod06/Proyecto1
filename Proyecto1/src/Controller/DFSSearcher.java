/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

import java.util.List;
import java.util.ArrayList;



/**
 *
 * @author jesus rodriguez
 */
public class DFSSearcher {
     private Graph graph;
    private Dictionary dictionary;
    private SearchResult result;
    private int nodesVisited;
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    
    public SearchResult search() {
        if (graph == null || dictionary == null) {
            return new SearchResult();
        }
        
        long startTime = System.currentTimeMillis();
        result = new SearchResult();
        result.setSearchAlgorithm("DFS");
        nodesVisited = 0;
        
        // Buscar desde cada celda del tablero
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
        
        // Verificar si la palabra actual coincide con alguna del diccionario
        if (dictionary.containsWord(currentWord) && !result.isWordFound(currentWord)) {
            WordPath wordPath = new WordPath(new ArrayList<>(currentPath));
            result.addWordPath(wordPath);
            dictionary.markWordAsFound(currentWord);
        }
        
        // Continuar búsqueda si no hemos encontrado todas las palabras
        if (result.getFoundWordCount() < dictionary.getTotalWordCount()) {
            for (Node neighbor : currentNode.getNeighbors()) {
                dfsFromNode(neighbor, currentPath, currentWord);
            }
        }
        
        // Backtracking
        currentPath.remove(currentPath.size() - 1);
    }
    
    public WordPath findWord(String word) {
        if (graph == null || word == null || word.length() < 3) {
            return null;
        }
         word = word.toUpperCase();
        nodesVisited = 0;
        
        for (int i = 0; i < graph.getRows(); i++) {
            for (int j = 0; j < graph.getCols(); j++) {
                Node startNode = graph.getNode(i, j);
                
                if (startNode.getCharacter() == word.charAt(0) && !startNode.isUsed()) {
                    List<Node> path = new ArrayList<>();
                    boolean[][] visited = new boolean[graph.getRows()][graph.getCols()];
                    
                    WordPath foundPath = dfsRecursive(startNode, path, "", word, visited);
                    if (foundPath != null) {
                        return foundPath;
                    }
                }
            }
        }
        return null;
    }
     private WordPath dfsRecursive(Node currentNode, List<Node> currentPath,String currentWord, String targetWord, boolean[][] visited) {
        if (currentNode == null || currentNode.isUsed() || 
            visited[currentNode.getRow()][currentNode.getCol()]) {
            return null;
        }

        visited[currentNode.getRow()][currentNode.getCol()] = true;
        currentPath.add(currentNode);
        String newWord = currentWord + currentNode.getCharacter();

        if (newWord.equals(targetWord)) {
            return new WordPath(new ArrayList<>(currentPath));
        }
        
        if (!targetWord.startsWith(newWord)) {
            currentPath.remove(currentPath.size() - 1);
            visited[currentNode.getRow()][currentNode.getCol()] = false;
            return null;
        }

        for (Node neighbor : currentNode.getNeighbors()) {
            if (!neighbor.isUsed() && !visited[neighbor.getRow()][neighbor.getCol()]) {
                WordPath foundPath = dfsRecursive(neighbor, currentPath, newWord, targetWord, visited);
                if (foundPath != null) {
                    return foundPath;
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
         visited[currentNode.getRow()][currentNode.getCol()] = false;
        return null;
    }
}
