/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.util.List;
import java.util.ArrayList;


/**
 * Graph represents a grid of nodes.
 * 
 */
public class Graph {
    private Node[][] grid;
    private int rows;
    private int cols;
    
    /**
     * Constructs a Graph from a 2D character array.
     * @param charGrid The 2D character array used to initialize the graph.
     */
    public Graph(char[][] charGrid) {
        this.rows = charGrid.length;
        this.cols = charGrid[0].length;
        this.grid = new Node[rows][cols];
        initializeNodes(charGrid);
        connectNodes();
    }
    
    /**
     * Initializes the nodes in the graph based on the provided character grid.
     * Each character in the grid corresponds to a node in the graph.
     * @param charGrid The 2D character array used to create nodes.
     */
    private void initializeNodes(char[][] charGrid) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Node(i, j, charGrid[i][j]);
            }
        }
    }
    
    /**
     * Connects each node to its neighboring nodes in the grid.
     * Neighbors are determined based on the 8 possible directions (N, NE, E, SE, S, SW, W, NW).
     */
    private void connectNodes() {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < 8; k++) {
                    int newX = i + dx[k];
                    int newY = j + dy[k];
                    
                    if (isValidPosition(newX, newY)) {
                        grid[i][j].addNeighbor(grid[newX][newY]);
                    }
                }
            }
        }
    }
    
    /**
     * Checks if the given position is valid within the grid boundaries.
     * @param x The row index to check.
     * @param y The column index to check.
     * @return true if the position is valid, false otherwise.
     */
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
    
    /**
     * Retrieves the node at the specified row and column.
     * @param row The row index of the node.
     * @param col The column index of the node.
     * @return The node at the specified position.
     */
    public Node getNode(int row, int col) {
        return grid[row][col];
    }
    
    /**
     * Returns the number of rows in the graph.
     * @return The number of rows.
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Returns the number of columns in the graph.
     * @return The number of columns.
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * Retrieves the grid of nodes.
     * @return A 2D array of nodes representing the graph.
     */
    public Node[][] getGrid() {
        return grid;
    }
    
    /**
     * Resets all nodes in the graph to their initial state.
     * This typically involves clearing any flags or states associated with the nodes.
     */
    public void resetNodes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].reset();
            }
        }
    }
}
