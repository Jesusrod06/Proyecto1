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
 *Implements the Breadth-First Search (BFS) algorithm to find words in the grid.
 *
 *<p>This class uses the Breadth-First Search algorithm to systematically explore the grid,
 *processing all nodes at one level before moving to the next depth level.</p>
 *
 *<p><strong>Advantages of BFS algorithm:</strong></p>
 *<ul> 
 *<li>Guarantees finding the shortest path (in terms of number of steps)</li>
 *<li>Systematic level-by-level exploration</li>
 *<li>Ideal for building complete search trees</li>
 *<li>Predictable and deterministic behavior</li>
 *</ul>
 *<p><strong>Technical features:</strong></p>
 *<ul>
 *<li>Uses a Queue to manage pending states</li>
 *<li>Keeps track of visited states to avoid cycles</li>
 *<li>Builds search trees for visualization</li>
 *</ul>
 *<p><strong>Computational complexity:</strong></p>
 *<ul>
 *<li>Time: O(8^L) where L is the maximum word length</li>
 *<li>Space: O(8^L) due to the state queue</li>
 *</ul>
 *@author Search Algorithms Module
 *
 *@version 2.2
 *
 *@since 1.0
 *
 *@see DFSSearcher
 *
 * @see TreeVisualizer
 */
public class BFSSearcher {
  /** Graph representing the letter grid structure */
    private Graph puzzleGraph;
    
    /** Dictionary containing the target words for the search */
    private Dictionary wordDictionary;
    
    /** Contenedor para almacenar los resultados de búsqueda */
    private SearchResult searchResults;
    
    /** Contador de nodos examinados durante el proceso de búsqueda */
    private int totalNodesExamined;
    
    /** Interfaz de logging para monitorear el progreso del algoritmo */
    private LogWindow algorithmLogWindow;
    
    /**
     * Constructor que inicializa el buscador BFS con configuración por defecto.
     */
    public BFSSearcher() {
        this.searchResults = new SearchResult();
    }
    
    /**
     * Establece el grafo de la grilla para las operaciones de búsqueda.
     * 
     * @param grafoTablero el grafo que representa la estructura de la grilla
     */
    public void setGraph(Graph grafoTablero) {
        this.puzzleGraph = grafoTablero;
    }
    
    /**
     * 
     * Configures the dictionary of words to search for.
     *
     * @param diccionario the dictionary containing the target words
     */
    public void setDictionary(Dictionary diccionario) {
        this.wordDictionary = diccionario;
    }
    
    /**
     * Opens the logging window to monitor the algorithm.
     * 
     * @param ventanaLog The interface where process logs will be displayed
     */
    public void setLogWindow(LogWindow ventanaLog) {
        this.algorithmLogWindow = ventanaLog;
    }
    
    /**
     * Performs an exhaustive search of all dictionary words.
     * 
     * <p>This method implements the complete BFS strategy, initiating searches
     * from each grid cell and systematically exploring all possible paths
     * level by level.</p>
     * 
     *  <p><strong>Execution flow:</strong></p>
     * <ol>
     *    <li>Initialization of data structures and counters</li>
     *   <li>Iteration over each cell as starting point</li>
     *   <li>BFS execution from each initial position</li>
     *   <li>Collection of found words and statistics</li>
     *   <li>Construction of final result with performance metrics</li>
     * </ol>
     * 
     * @return SearchResult containing all found words,
     *         execution time, visited nodes, and completion status
     * @throws IllegalStateException if the graph or dictionary are not initialized
     */
    public SearchResult search() {
        if (puzzleGraph == null || wordDictionary == null) {
            return new SearchResult();
        }
        
        long tiempoInicio = System.currentTimeMillis();
        searchResults = new SearchResult();
        searchResults.setSearchAlgorithm("BFS");
        totalNodesExamined = 0;
        
        // Búsqueda desde cada celda en la grilla
        for (int i = 0; i < puzzleGraph.getRows(); i++) {
            for (int j = 0; j < puzzleGraph.getCols(); j++) {
                Node nodoInicial = puzzleGraph.getNode(i, j);
                bfsFromNode(nodoInicial);
            }
        }
        
        long tiempoFin = System.currentTimeMillis();
        searchResults.setSearchTimeMs(tiempoFin - tiempoInicio);
        searchResults.setNodesVisited(totalNodesExamined);
        searchResults.setSearchCompleted(true);
        
        return searchResults;
    }
    
    /**
     * Implements the core BFS algorithm logic starting from a specific node.
     * 
     * <p>Uses a FIFO queue to maintain pending exploration states,
     * ensuring the level-by-level processing characteristic of BFS.</p>
     * 
     * @param startNode the node from which to begin BFS exploration
     */
    private void bfsFromNode(Node nodoInicial) {
        Queue<SearchState> colaBusqueda = new LinkedList<>();
        
        // Inicializar con el nodo de inicio
        List<Node> caminoInicial = new ArrayList<>();
        caminoInicial.add(nodoInicial);
        colaBusqueda.add(new SearchState(nodoInicial, caminoInicial, "" + nodoInicial.getCharacter()));
        
        while (!colaBusqueda.isEmpty()) {
            SearchState estadoActual = colaBusqueda.poll();
            totalNodesExamined++;
            
            if (algorithmLogWindow != null) {
                algorithmLogWindow.appendLog("BFS visitando nodo: (" + estadoActual.node.getRow() + ", " + estadoActual.node.getCol() + ") letra: " + estadoActual.node.getCharacter() + " palabra actual: " + estadoActual.word);
            }
            
            // Verificar si la palabra actual coincide con alguna palabra del diccionario
            if (wordDictionary.containsWord(estadoActual.word) && !searchResults.isWordFound(estadoActual.word)) {
                WordPath caminoPalabra = new WordPath(new ArrayList<>(estadoActual.path));
                searchResults.addWordPath(caminoPalabra);
                wordDictionary.markWordAsFound(estadoActual.word);
            }
            
            // Continuar búsqueda si aún no hemos encontrado todas las palabras
            if (searchResults.getFoundWordCount() < wordDictionary.getTotalWordCount()) {
                // Explorar vecinos
                for (Node vecinoNodo : estadoActual.node.getNeighbors()) {
                    if (!estadoActual.path.contains(vecinoNodo)) {
                        List<Node> nuevoCamino = new ArrayList<>(estadoActual.path);
                        nuevoCamino.add(vecinoNodo);
                        String nuevaPalabra = estadoActual.word + vecinoNodo.getCharacter();
                        colaBusqueda.add(new SearchState(vecinoNodo, nuevoCamino, nuevaPalabra));
                    }
                }
            }
        }
    }
    
    /**
     * Searches for a specific word using BFS optimized with prefix pruning.
     * 
     * <p>This specialized version of the algorithm focuses on finding a single
     * word, implementing optimizations like early termination and pruning
     * of branches that cannot form the target word.</p>
     * 
     * <p><strong>Implemented optimizations:</strong></p>
     * <ul>
     *   <li>First character validation before starting search</li>
     *   <li>Prefix pruning to eliminate impossible branches</li>
     *   <li>Duplicate state detection to avoid re-exploration</li>
     *   <li>Immediate termination upon finding the word</li>
     * </ul>
     * 
     * @param palabra the specific word to locate (normalized to uppercase)
     * @return WordPath with the sequence of nodes forming the word,
     *         or null if not found
     * @throws IllegalArgumentException if the word is null, empty, or shorter than 3 characters
     */
    public WordPath findWord(String palabra) {
        if (puzzleGraph == null || palabra == null || palabra.length() < 3) {
            return null;
        }
        
        palabra = palabra.toUpperCase(); // Asegurar que la palabra esté en mayúsculas
        totalNodesExamined = 0;
        
        // Intentar desde cada posición en la grilla
        for (int i = 0; i < puzzleGraph.getRows(); i++) {
            for (int j = 0; j < puzzleGraph.getCols(); j++) {
                Node nodoInicial = puzzleGraph.getNode(i, j);
                
                // Solo comenzar si el primer carácter coincide y el nodo no está usado
                if (nodoInicial.getCharacter() == palabra.charAt(0) && !nodoInicial.isUsed()) {
                    if (algorithmLogWindow != null) {
                        algorithmLogWindow.appendLog("BFS: Intentando desde posición (" + i + "," + j + ") con letra '" + nodoInicial.getCharacter() + "'");
                    }
                    
                    WordPath caminoEncontrado = bfsFromNode(nodoInicial, palabra);
                    if (caminoEncontrado != null) {
                        if (algorithmLogWindow != null) {
                            algorithmLogWindow.appendLog("¡Palabra encontrada! Camino: " + pathToString(caminoEncontrado.getPath()));
                        }
                        return caminoEncontrado;
                    }
                }
            }
        }
        return null; // Palabra no encontrada
    }

    /**
     * Internal BFS implementation optimized for specific word search.
     * 
     * <p>Uses a set of visited states to avoid duplication
     *  and applies aggressive prefix-based pruning to improve performance.</p>
     * 
     * @param nodoInicial the node from which to start the search
     * @param palabraObjetivo the complete word being searched for
     * @return WordPath if the word is found, null otherwise
     */
    private WordPath bfsFromNode(Node nodoInicial, String palabraObjetivo) {
        if (nodoInicial.isUsed()) {
            return null;
        }
        
        Queue<SearchState> colaBusqueda = new LinkedList<>();
        Set<String> estadosVisitados = new HashSet<>(); // Para evitar estados duplicados
        
        List<Node> caminoInicial = new ArrayList<>();
        caminoInicial.add(nodoInicial);
        SearchState estadoInicial = new SearchState(nodoInicial, caminoInicial, "" + nodoInicial.getCharacter());
        colaBusqueda.add(estadoInicial);
        
        // Crear un estado único para evitar duplicados
        String claveEstadoInicial = createStateKey(caminoInicial, estadoInicial.word);
        estadosVisitados.add(claveEstadoInicial);
        
        while (!colaBusqueda.isEmpty()) {
            SearchState estadoActual = colaBusqueda.poll();
            totalNodesExamined++;

            if (algorithmLogWindow != null) {
                algorithmLogWindow.appendLog("BFS: Visitando (" + estadoActual.node.getRow() + "," + estadoActual.node.getCol() + ") -> " + estadoActual.word);
            }

            if (estadoActual.word.equals(palabraObjetivo)) {
                return new WordPath(new ArrayList<>(estadoActual.path)); // ¡Palabra encontrada!
            }

            // Poda: solo continuar si estadoActual.word es prefijo de palabraObjetivo
            if (palabraObjetivo.startsWith(estadoActual.word)) {
                // Explorar vecinos adyacentes
                List<Node> vecinosAdyacentes = getAdjacentNodes(estadoActual.node);
                for (Node vecinoNodo : vecinosAdyacentes) {
                    // Verificar que el vecino no esté ya usado y no esté en el camino actual
                    if (!vecinoNodo.isUsed() && !estadoActual.path.contains(vecinoNodo)) {
                        List<Node> nuevoCamino = new ArrayList<>(estadoActual.path);
                        nuevoCamino.add(vecinoNodo);
                        String nuevaPalabra = estadoActual.word + vecinoNodo.getCharacter();
                        
                        // Crear clave única para este estado
                        String claveEstado = createStateKey(nuevoCamino, nuevaPalabra);
                        
                        // Solo agregar si no hemos visitado este estado antes
                        if (!estadosVisitados.contains(claveEstado)) {
                            estadosVisitados.add(claveEstado);
                            colaBusqueda.add(new SearchState(vecinoNodo, nuevoCamino, nuevaPalabra));
                        }
                    }
                }
            }
        }
        return null; // Palabra no encontrada en este camino
    }
    
    /**
     * Retrieves all valid neighboring nodes using 8-way connectivity.
     * 
     * <p>Implements the Moore neighborhood pattern (8-connectivity), which includes
     * movements in all eight cardinal and diagonal directions.</p>
     * 
     * @param nodo The reference node whose neighbors should be retrieved
     * @return List of valid adjacent nodes within the grid boundaries
     */
    private List<Node> getAdjacentNodes(Node nodo) {
        List<Node> nodosAdyacentes = new ArrayList<>();
        int fila = nodo.getRow();
        int columna = nodo.getCol();
        
        // 8 direcciones: arriba, abajo, izquierda, derecha, y las 4 diagonales
        int[] desplazamientoX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] desplazamientoY = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            int nuevaFila = fila + desplazamientoX[i];
            int nuevaColumna = columna + desplazamientoY[i];
            
            if (isValidPosition(nuevaFila, nuevaColumna)) {
                nodosAdyacentes.add(puzzleGraph.getNode(nuevaFila, nuevaColumna));
            }
        }
        
        return nodosAdyacentes;
    }
    
    /**
     * Validates whether a position is within the grid boundaries.
     * 
     * @param row row index to check
     * @param column column index to check
     * @return true if the position is valid, false otherwise
     */
    private boolean isValidPosition(int fila, int columna) {
        return fila >= 0 && fila < puzzleGraph.getRows() && columna >= 0 && columna < puzzleGraph.getCols();
    }
    
    /**
     * Generates a unique key to identify search states.
     * 
     * <p>Combines the node path and current word to create a
     * unique identifier that enables duplicate state detection.</p>
     * 
     * @param camino the node sequence of the state
     * @param palabra the word constructed in this state
     * @return String that uniquely identifies this state
     */
    private String createStateKey(List<Node> camino, String palabra) {
        StringBuilder sb = new StringBuilder();
        for (Node nodo : camino) {
            sb.append(nodo.getRow()).append(",").append(nodo.getCol()).append(";");
        }
        sb.append("|").append(palabra);
        return sb.toString();
    }
    
    /**
     * Converts a node path into a human-readable text representation.
     * 
     * @param camino the list of nodes forming the path
     * @return String with the path representation in "(row,col) -> ..." format
     */
    private String pathToString(List<Node> camino) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camino.size(); i++) {
            Node nodo = camino.get(i);
            sb.append("(").append(nodo.getRow()).append(",").append(nodo.getCol()).append(")");
            if (i < camino.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }
    
    /**
     * Finds a word and builds the corresponding BFS exploration tree.
     * 
     * <p>This advanced functionality not only locates the word but also
     * constructs a tree representation of the entire exploration process,
     *  useful for visualization and analysis of the algorithm's behavior.</p>
     * 
     * <p><strong>Tree construction process:</strong></p>
     * <ol>
     *   <li>Starts BFS from each valid position</li>
     *   <li>Builds tree nodes during exploration</li>
     *   <li>Maintains parent-child references in the structure</li>
     *   <li>Returns the complete tree if the word is found</li>
     * </ol>
     * 
     * @param palabraObjetivo the word to search for and build the tree
     * @return TreeNode root of the exploration tree, or null if not found
     * @see TreeVisualizer.TreeNode
     */
    public TreeNode findWordAndBuildTree(String palabraObjetivo) {
        if (puzzleGraph == null || palabraObjetivo == null || palabraObjetivo.isEmpty()) {
            return null;
        }

        for (int i = 0; i < puzzleGraph.getRows(); i++) {
            for (int j = 0; j < puzzleGraph.getCols(); j++) {
                Node nodoInicial = puzzleGraph.getNode(i, j);
                if (nodoInicial.getCharacter() == palabraObjetivo.charAt(0)) {
                    TreeNode arbolBusqueda = bfsTreeFromNode(nodoInicial, palabraObjetivo);
                    if (arbolBusqueda != null) {
                        return arbolBusqueda; // Retorna el primer árbol donde se encontró la palabra
                    }
                }
            }
        }
        return null;
    }

    /**
     * Internal implementation for building BFS exploration trees.
     * 
     * <p>Executes BFS while simultaneously constructing a tree structure
     * that reflects the exploration order and hierarchy.</p>
     * 
     * @param nodoInicial the root node from which to build the tree
     * @param palabraObjetivo the word that determines exploration success
     * @return TreeNode with the complete tree, or null if the word is not found
     */
    private TreeNode bfsTreeFromNode(Node nodoInicial, String palabraObjetivo) {
        Queue<SearchState> colaBusqueda = new LinkedList<>();
        Set<Node> nodosVisitadosEnBusqueda = new HashSet<>();

        TreeNode raizArbol = new TreeNode(""+nodoInicial.getCharacter(), ""+nodoInicial.getCharacter(), 0, nodoInicial);
        
        List<Node> caminoInicial = new ArrayList<>();
        caminoInicial.add(nodoInicial);
        colaBusqueda.add(new SearchState(nodoInicial, caminoInicial, "" + nodoInicial.getCharacter(), raizArbol));
        nodosVisitadosEnBusqueda.add(nodoInicial);
        
        boolean palabraEncontrada = false;

        while (!colaBusqueda.isEmpty()) {
            SearchState estadoActual = colaBusqueda.poll();

            if (estadoActual.word.equals(palabraObjetivo)) {
                palabraEncontrada = true;
            }

            // Continuar explorando para construir el árbol completo de recorrido
            if (palabraObjetivo.startsWith(estadoActual.word)) {
                for (Node vecinoNodo : estadoActual.node.getNeighbors()) {
                    if (!nodosVisitadosEnBusqueda.contains(vecinoNodo)) {
                        nodosVisitadosEnBusqueda.add(vecinoNodo);
                        String nuevaPalabra = estadoActual.word + vecinoNodo.getCharacter();
                        TreeNode nodoHijo = new TreeNode(""+vecinoNodo.getCharacter(), nuevaPalabra, estadoActual.path.size(), vecinoNodo);
                        estadoActual.treeNode.addChild(nodoHijo);

                        List<Node> nuevoCamino = new ArrayList<>(estadoActual.path);
                        nuevoCamino.add(vecinoNodo);
                        colaBusqueda.add(new SearchState(vecinoNodo, nuevoCamino, nuevaPalabra, nodoHijo));
                    }
                }
            }
        }
        return palabraEncontrada ? raizArbol : null;
    }
    
    /**
     * Inner class that encapsulates the state of a BFS exploration step.
     * 
     * <p>Each instance represents a specific point in the search space,
     * including the current node, the path taken to reach it, the word
     * constructed so far, and optionally a tree node for visualization.</p>
     * 
     * @since 2.0
     */
    private static class SearchState {
     /** Current node in the grid */
        Node node;
        
        /** Sequence of visited nodes to reach this state */
        List<Node> path;
        
        /** Word constructed up to this point */
        String word;
        
        /** Reference to visualization tree node (optional) */
        TreeNode treeNode;
        
        /**
         * Constructor for basic search states.
         * 
         * @param node current node
         * @param path path taken
         * @param word word constructed
         */
        SearchState(Node node, List<Node> path, String word) {
            this.node = node;
            this.path = path;
            this.word = word;
        }
        
        /**
         * Extended constructor including visualization tree reference.
         * 
         * @param node current node
         * @param path path taken
         * @param word word constructed
         * @param treeNode corresponding node in the visualization tree
         */
        SearchState(Node node, List<Node> path, String word, TreeNode treeNode) {
            this.node = node;
            this.path = path;
            this.word = word;
            this.treeNode = treeNode;
        }
    }
}
