/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.util.*;

/**
 *
 * @author jesus rodriguez
 */
public class BFSSearcher {
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
        result.setSearchAlgorithm("BFS");
        nodesVisited = 0;
        
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
        
        List<Node> initialPath = new ArrayList<>();
        initialPath.add(startNode);
        queue.add(new SearchState(startNode, initialPath, "" + startNode.getCharacter()));
        
        while (!queue.isEmpty()) {
            SearchState current = queue.poll();
            nodesVisited++;
            
            if (dictionary.containsWord(current.word) && !result.isWordFound(current.word)) {
                WordPath wordPath = new WordPath(new ArrayList<>(current.path));
                result.addWordPath(wordPath);
                dictionary.markWordAsFound(current.word);
            }
            
            if (result.getFoundWordCount() < dictionary.getTotalWordCount()) {
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
                    WordPath foundPath = bfsFromNode(startNode, word);
                    if (foundPath != null) {
                        return foundPath;
                    }
                }
            }
        }
        return null;
    }
    
    private WordPath bfsFromNode(Node startNode, String targetWord) {
        if (startNode.isUsed()) return null;
        
        Queue<SearchState> queue = new LinkedList<>();
        Set<String> visitedStates = new HashSet<>();
        
        List<Node> initialPath = new ArrayList<>();
        initialPath.add(startNode);
        SearchState initialState = new SearchState(startNode, initialPath, "" + startNode.getCharacter());
        queue.add(initialState);
        
        String initialStateKey = createStateKey(initialPath, initialState.word);
        visitedStates.add(initialStateKey);
        
        while (!queue.isEmpty()) {
            SearchState current = queue.poll();
            nodesVisited++;

            if (current.word.equals(targetWord)) {
                return new WordPath(new ArrayList<>(current.path));
            }

            if (targetWord.startsWith(current.word)) {
                for (Node neighbor : current.node.getNeighbors()) {
                    if (!neighbor.isUsed() && !current.path.contains(neighbor)) {
                        List<Node> newPath = new ArrayList<>(current.path);
                        newPath.add(neighbor);
                        String newWord = current.word + neighbor.getCharacter();
                        
                        String stateKey = createStateKey(newPath, newWord);
                        
                        if (!visitedStates.contains(stateKey)) {
                            visitedStates.add(stateKey);
                            queue.add(new SearchState(neighbor, newPath, newWord));
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private String createStateKey(List<Node> path, String word) {
        StringBuilder key = new StringBuilder();
        for (Node node : path) {
            key.append("(").append(node.getRow()).append(",").append(node.getCol()).append(")");
        }
        key.append(":").append(word);
        return key.toString();
    }
    
    private static class SearchState {
        Node node;
        List<Node> path;
        String word;
        
        SearchState(Node node, List<Node> path, String word) {
            this.node = node;
            this.path = path;
            this.word = word;
        }
    }
}
