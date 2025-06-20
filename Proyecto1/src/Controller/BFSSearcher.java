/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Controller.TreeVisualizer.TreeNode;
import Interfaces.LogWindow;
import java.util.*;
import proyecto1.Dictionary;
import proyecto1.Graph;
import proyecto1.Node;
import proyecto1.SearchResult;
import proyecto1.WordPath;

/**
 *
 * @author jesus rodriguez
 */
public class BFSSearcher {
   private Graph graph;
    private Dictionary dictionary;
    private SearchResult result;
    private int nodesVisited;
    private LogWindow logWindow;
    
    public BFSSearcher() {
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
        result.setSearchAlgorithm("BFS");
        nodesVisited = 0;
        
        // Search from each cell in the grid
        for (int i = 0; i < graph.getRows(); i++) {
            for (int j = 0; j < graph.getCols(); j++) {
                Node startNode = graph.getNode(i, j);
                bfsFromNode(startNode);
            }
        }
        
        long endTime = System.currentTimeMillis();
        result.setSearchTimeMs(endTime - startTime);
        result.setNodesVisited(nodesVisited);
        result.setSearchCompleted(true);
        
        return result;
    }
    
    private void bfsFromNode(Node startNode) {
        Queue<SearchState> queue = new LinkedList<>();
        
        // Initialize with the starting node
        List<Node> initialPath = new ArrayList<>();
        initialPath.add(startNode);
        queue.add(new SearchState(startNode, initialPath, "" + startNode.getCharacter()));
        
        while (!queue.isEmpty()) {
            SearchState current = queue.poll();
            nodesVisited++;
            
            if (logWindow != null) {
                logWindow.appendLog("BFS visitando nodo: (" + current.node.getRow() + ", " + current.node.getCol() + ") letra: " + current.node.getCharacter() + " palabra actual: " + current.word);
            }
            
            // Check if current word matches any dictionary word
            if (dictionary.containsWord(current.word) && !result.isWordFound(current.word)) {
                WordPath wordPath = new WordPath(new ArrayList<>(current.path));
                result.addWordPath(wordPath);
                dictionary.markWordAsFound(current.word);
            }
            
            // Continue searching if we haven't found all words yet
            if (result.getFoundWordCount() < dictionary.getTotalWordCount()) {
                // Explore neighbors
                for (Node neighbor : current.node.getNeighbors()) {
                    if (!current.path.contains(neighbor)) {
                        List<Node> newPath = new ArrayList<>(current.path);
                        newPath.add(neighbor);
                        String newWord = current.word + neighbor.getCharacter();
                        queue.add(new SearchState(neighbor, newPath, newWord));
                    }
                }
            }
        }
    }
    
    /**
     * Finds a specific word using BFS and returns its path.
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
                
                // Only start if the first character matches and node is not used
                if (startNode.getCharacter() == word.charAt(0) && !startNode.isUsed()) {
                    if (logWindow != null) {
                        logWindow.appendLog("BFS: Intentando desde posición (" + i + "," + j + ") con letra '" + startNode.getCharacter() + "'");
                    }
                    
                    WordPath foundPath = bfsFromNode(startNode, word);
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

    private WordPath bfsFromNode(Node startNode, String targetWord) {
        if (startNode.isUsed()) {
            return null;
        }
        
        Queue<SearchState> queue = new LinkedList<>();
        Set<String> visitedStates = new HashSet<>(); // Para evitar estados duplicados
        
        List<Node> initialPath = new ArrayList<>();
        initialPath.add(startNode);
        SearchState initialState = new SearchState(startNode, initialPath, "" + startNode.getCharacter());
        queue.add(initialState);
        
        // Crear un estado único para evitar duplicados
        String initialStateKey = createStateKey(initialPath, initialState.word);
        visitedStates.add(initialStateKey);
        
        while (!queue.isEmpty()) {
            SearchState current = queue.poll();
            nodesVisited++;

            if (logWindow != null) {
                logWindow.appendLog("BFS: Visitando (" + current.node.getRow() + "," + current.node.getCol() + ") -> " + current.word);
            }

            if (current.word.equals(targetWord)) {
                return new WordPath(new ArrayList<>(current.path)); // ¡Palabra encontrada!
            }

            // Pruning: solo continuar si current.word es prefijo de targetWord
            if (targetWord.startsWith(current.word)) {
                // Explorar vecinos adyacentes
                List<Node> neighbors = getAdjacentNodes(current.node);
                for (Node neighbor : neighbors) {
                    // Verificar que el vecino no esté ya usado y no esté en el camino actual
                    if (!neighbor.isUsed() && !current.path.contains(neighbor)) {
                        List<Node> newPath = new ArrayList<>(current.path);
                        newPath.add(neighbor);
                        String newWord = current.word + neighbor.getCharacter();
                        
                        // Crear clave única para este estado
                        String stateKey = createStateKey(newPath, newWord);
                        
                        // Solo agregar si no hemos visitado este estado antes
                        if (!visitedStates.contains(stateKey)) {
                            visitedStates.add(stateKey);
                            queue.add(new SearchState(neighbor, newPath, newWord));
                        }
                    }
                }
            }
        }
        return null; // Word not found in this path
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
    
    private String createStateKey(List<Node> path, String word) {
        StringBuilder sb = new StringBuilder();
        for (Node node : path) {
            sb.append(node.getRow()).append(",").append(node.getCol()).append(";");
        }
        sb.append("|").append(word);
        return sb.toString();
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
    
    public TreeNode findWordAndBuildTree(String targetWord) {
        if (graph == null || targetWord == null || targetWord.isEmpty()) {
            return null;
        }

        for (int i = 0; i < graph.getRows(); i++) {
            for (int j = 0; j < graph.getCols(); j++) {
                Node startNode = graph.getNode(i, j);
                if (startNode.getCharacter() == targetWord.charAt(0)) {
                    TreeNode tree = bfsTreeFromNode(startNode, targetWord);
                    if (tree != null) {
                        return tree; // Retorna el primer árbol donde se encontró la palabra
                    }
                }
            }
        }
        return null;
    }

    private TreeNode bfsTreeFromNode(Node startNode, String targetWord) {
        Queue<SearchState> queue = new LinkedList<>();
        Set<Node> visitedInThisSearch = new HashSet<>();

        TreeNode root = new TreeNode(""+startNode.getCharacter(), ""+startNode.getCharacter(), 0, startNode);
        
        List<Node> initialPath = new ArrayList<>();
        initialPath.add(startNode);
        queue.add(new SearchState(startNode, initialPath, "" + startNode.getCharacter(), root));
        visitedInThisSearch.add(startNode);
        
        boolean wordFound = false;

        while (!queue.isEmpty()) {
            SearchState current = queue.poll();

            if (current.word.equals(targetWord)) {
                wordFound = true;
            }

            // Continuar explorando para construir el árbol completo de recorrido, pero solo hasta cierto punto si se quiere
            if (targetWord.startsWith(current.word)) {
                for (Node neighbor : current.node.getNeighbors()) {
                    if (!visitedInThisSearch.contains(neighbor)) {
                        visitedInThisSearch.add(neighbor);
                        String newWord = current.word + neighbor.getCharacter();
                        TreeNode childNode = new TreeNode(""+neighbor.getCharacter(), newWord, current.path.size(), neighbor);
                        current.treeNode.addChild(childNode);

                        List<Node> newPath = new ArrayList<>(current.path);
                        newPath.add(neighbor);
                        queue.add(new SearchState(neighbor, newPath, newWord, childNode));
                    }
                }
            }
        }
        return wordFound ? root : null;
    }
    
    private static class SearchState {
        Node node;
        List<Node> path;
        String word;
        TreeNode treeNode;
        
        SearchState(Node node, List<Node> path, String word) {
            this.node = node;
            this.path = path;
            this.word = word;
        }
        
        SearchState(Node node, List<Node> path, String word, TreeNode treeNode) {
            this.node = node;
            this.path = path;
            this.word = word;
            this.treeNode = treeNode;
        }
    }
}
