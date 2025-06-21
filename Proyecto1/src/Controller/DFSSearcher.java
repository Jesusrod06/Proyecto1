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
 * Implements the Depth-First Search (DFS) algorithm to find words in the letter grid.
 * 
 * <p>This specialized class uses the Depth-First Search algorithm to recursively
 * explore the letter grid, fully exploring each branch before backtracking.</p>
 * 
 * <p><strong>DFS Algorithm Characteristics:</strong></p>
 * <ul>
 *   <li>Explores depth before breadth</li>
 *   <li>Uses backtracking to explore all possibilities</li>
 *   <li>More memory efficient compared to BFS</li>
 *   <li>Can find solutions faster if they're in deep branches</li>
 * </ul>
 * 
 * <p><strong>Complexity:</strong></p>
 * <ul>
 *   <li>Time: O(8^L) where L is the maximum word length</li>
 *   <li>Space: O(L) due to recursion</li>
 * </ul>
 * 
 * @version 2.0
 * @since 1.0
 * @see BFSSearcher
 */
public class DFSSearcher {
  /** Graph representing the puzzle's letter grid */
    private Graph puzzleGraph;
    
    /** Dictionary containing the target words to search for */
    private Dictionary wordDictionary;
    
    /** Container for current search results */
    private SearchResult searchResults;
    
    /** Counter for nodes visited during search */
    private int totalNodesVisited;
    
    /** Log window to record algorithm progress */
    private LogWindow algorithmLogWindow;
    
    /**
     * Constructor that initializes the DFS searcher with empty results.
     */
    public DFSSearcher() {
        this.searchResults = new SearchResult();
    }
    
    /**
     * Sets the grid graph for search operations.
     * 
     * @param grafoTablero the graph representing the letter grid
     */
    public void setGraph(Graph grafoTablero) {
        this.puzzleGraph = grafoTablero;
    }
    
    /**
     * Sets the target word dictionary.
     * 
     * @param diccionario the dictionary with words to search for
     */
    public void setDictionary(Dictionary diccionario) {
        this.wordDictionary = diccionario;
    }
    
    /**
     * Sets up the logging window to record the algorithm's progress.
     * 
     * @param ventanaLog the window where search logs will be displayed
     */
    public void setLogWindow(LogWindow ventanaLog) {
        this.algorithmLogWindow = ventanaLog;
    }
    /**
     * Performs a complete search of all dictionary words using DFS.
     * 
     * <p>This method initiates DFS searches from each grid cell,
     *  recursively exploring all possible paths to find dictionary words.</p>
     * 
     * <p><strong>Search process:</strong></p>
     *  <ol>
     *   <li>Initializes statistics and counters</li>
     *   <li>Iterates over each grid cell as starting point</li>
     *   <li>Executes recursive DFS from each position</li>
     *   <li>Uses backtracking to explore all routes</li>
     *   <li>Collects performance statistics</li>
     * </ol>
     * 
     * @return SearchResult containing all found words and performance statistics
     * @throws IllegalStateException if the graph or dictionary are not configured
     */
        public SearchResult search() {
        if (puzzleGraph == null || wordDictionary == null) {
            return new SearchResult();
        }
        
        long tiempoInicio = System.currentTimeMillis();
        searchResults = new SearchResult();
        searchResults.setSearchAlgorithm("DFS");
        totalNodesVisited = 0;
        
        // Búsqueda desde cada celda en la grilla
        for (int i = 0; i < puzzleGraph.getRows(); i++) {
            for (int j = 0; j < puzzleGraph.getCols(); j++) {
                Node nodoInicial = puzzleGraph.getNode(i, j);
                List<Node> caminoActual = new ArrayList<>();
                dfsFromNode(nodoInicial, caminoActual, "");
            }
        }
        
        long tiempoFin = System.currentTimeMillis();
        searchResults.setSearchTimeMs(tiempoFin - tiempoInicio);
        searchResults.setNodesVisited(totalNodesVisited);
        searchResults.setSearchCompleted(true);
        
        return searchResults;
    }
    
    /**
 * Main recursive method implementing the DFS logic.
 * 
 * <p>Recursively explores from a given node, building words
 * character by character and checking for dictionary matches.</p>
 * 
 * @param nodoActual current node in the exploration
 * @param caminoActual list of nodes visited so far
 * @param palabraActual word constructed up to this point
 */
private void dfsFromNode(Node nodoActual, List<Node> caminoActual, String palabraActual) {
    if (nodoActual == null || caminoActual.contains(nodoActual)) {
        return;
    }
    
    totalNodesVisited++;
    caminoActual.add(nodoActual);
    palabraActual += nodoActual.getCharacter();
    
    if (algorithmLogWindow != null) {
        algorithmLogWindow.appendLog("DFS visitando nodo: (" + nodoActual.getRow() + ", " + nodoActual.getCol() + ") letra: " + nodoActual.getCharacter() + " palabra actual: " + palabraActual);
    }
    
    // Verificar si la palabra actual coincide con alguna palabra del diccionario
    if (wordDictionary.containsWord(palabraActual) && !searchResults.isWordFound(palabraActual)) {
        WordPath caminoPalabra = new WordPath(new ArrayList<>(caminoActual));
        searchResults.addWordPath(caminoPalabra);
        wordDictionary.markWordAsFound(palabraActual);
    }
    
    // Continuar búsqueda si aún no hemos encontrado todas las palabras
    if (searchResults.getFoundWordCount() < wordDictionary.getTotalWordCount()) {
        // Explorar vecinos
        for (Node vecinoNodo : nodoActual.getNeighbors()) {
            dfsFromNode(vecinoNodo, caminoActual, palabraActual);
        }
    }
    
    // Retroceder (backtrack)
    caminoActual.remove(caminoActual.size() - 1);
}

    /**
 * Finds a specific word using DFS and returns its path.
 * 
 * <p>This optimized method searches for a single specific word instead
 * of the entire dictionary, allowing early termination when the
 * target word is found.</p>
 * 
 * <p><strong>Implemented optimizations:</strong></p>
 * <ul>
 *   <li>Prefix pruning: stops branches that cannot form the word</li>
 *   <li>First character validation before starting search</li>
 *   <li>Visited matrix for each independent search</li>
 * </ul>
 * 
 * @param palabra The word to search (automatically converted to uppercase)
 * @return WordPath if found, null otherwise
 * @throws IllegalArgumentException if the word is null or shorter than 3 characters
 */
public WordPath findWord(String palabra) {
    if (puzzleGraph == null || palabra == null || palabra.length() < 3) {
        return null;
    }
    
    palabra = palabra.toUpperCase(); // Asegurar que la palabra esté en mayúsculas
    totalNodesVisited = 0;
    
    // Intentar desde cada posición en la grilla
    for (int i = 0; i < puzzleGraph.getRows(); i++) {
        for (int j = 0; j < puzzleGraph.getCols(); j++) {
            Node nodoInicial = puzzleGraph.getNode(i, j);
            
            // Solo comenzar si el primer carácter coincide
            if (nodoInicial.getCharacter() == palabra.charAt(0) && !nodoInicial.isUsed()) {
                List<Node> caminoBusqueda = new ArrayList<>();
                boolean[][] nodosVisitados = new boolean[puzzleGraph.getRows()][puzzleGraph.getCols()];
                
                if (algorithmLogWindow != null) {
                    algorithmLogWindow.appendLog("Intentando desde posición (" + i + "," + j + ") con letra '" + nodoInicial.getCharacter() + "'");
                }
                
                WordPath caminoEncontrado = dfsRecursive(nodoInicial, caminoBusqueda, "", palabra, nodosVisitados);
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
 * Optimized recursive DFS implementation for a specific word.
 * 
 * <p>Uses prefix pruning to significantly improve performance,
 * avoiding exploration of branches that cannot lead to the target word.</p>
 * 
 * @param nodoActual current node in the exploration
 * @param caminoActual path of visited nodes
 * @param palabraActual word constructed up to this point
 * @param palabraObjetivo complete word being searched for
 * @param nodosVisitados matrix of nodes visited in this search
 * @return WordPath if word is found, null otherwise
 */
private WordPath dfsRecursive(Node nodoActual, List<Node> caminoActual, String palabraActual, String palabraObjetivo, boolean[][] nodosVisitados) {
    // Verificar condiciones base
    if (nodoActual == null || nodoActual.isUsed() || 
        nodosVisitados[nodoActual.getRow()][nodoActual.getCol()]) {
        return null;
    }

    totalNodesVisited++;
    
    // Marcar como visitado en este camino
    nodosVisitados[nodoActual.getRow()][nodoActual.getCol()] = true;
    caminoActual.add(nodoActual);
    String nuevaPalabra = palabraActual + nodoActual.getCharacter();

    if (algorithmLogWindow != null) {
        algorithmLogWindow.appendLog("DFS: Visitando (" + nodoActual.getRow() + "," + nodoActual.getCol() + ") -> " + nuevaPalabra);
    }

    // ¿Hemos encontrado la palabra completa?
    if (nuevaPalabra.equals(palabraObjetivo)) {
        return new WordPath(new ArrayList<>(caminoActual)); // ¡Palabra encontrada!
    }
    
    // Poda: si nuevaPalabra no es un prefijo de palabraObjetivo, parar
    if (!palabraObjetivo.startsWith(nuevaPalabra)) {
        // Retroceder
        caminoActual.remove(caminoActual.size() - 1);
        nodosVisitados[nodoActual.getRow()][nodoActual.getCol()] = false;
        return null;
    }

    // Explorar vecinos adyacentes
    List<Node> vecinosAdyacentes = getAdjacentNodes(nodoActual);
    for (Node vecinoNodo : vecinosAdyacentes) {
        if (!vecinoNodo.isUsed() && !nodosVisitados[vecinoNodo.getRow()][vecinoNodo.getCol()]) {
            WordPath caminoEncontrado = dfsRecursive(vecinoNodo, caminoActual, nuevaPalabra, palabraObjetivo, nodosVisitados);
            if (caminoEncontrado != null) {
                return caminoEncontrado; // Propagar el camino encontrado
            }
        }
    }

    // Retroceder
    caminoActual.remove(caminoActual.size() - 1);
    nodosVisitados[nodoActual.getRow()][nodoActual.getCol()] = false;
    return null;
}

/**
 * Gets all valid adjacent nodes to a given node.
 * 
 * <p>Implements 8-direction connectivity (including diagonals),
 * checking grid boundaries to avoid invalid accesses.</p>
 * 
 * @param nodo node from which to get neighbors
 * @return List with all valid adjacent nodes
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
 * Checks if a position is within valid grid boundaries.
 * 
 * @param fila row to check
 * @param columna column to check
 * @return true if position is valid, false otherwise
 */
private boolean isValidPosition(int fila, int columna) {
    return fila >= 0 && fila < puzzleGraph.getRows() && columna >= 0 && columna < puzzleGraph.getCols();
}

/**
 * Converts a node path into a readable string representation.
 * 
 * <p>Useful for logging and debugging, showing the sequence of coordinates
 * that form the path of a found word.</p>
 * 
 * @param camino list of nodes forming the path
 * @return String with path representation
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
}
