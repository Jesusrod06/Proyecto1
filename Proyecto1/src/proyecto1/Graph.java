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
public class Graph {
     private Node[][] grid;
    private int rows;
    private int cols;
    
    public Graph(char[][] charGrid) {
        this.rows = charGrid.length;
        this.cols = charGrid[0].length;
        this.grid = new Node[rows][cols];
        initializeNodes(charGrid);
        connectNodes();
    }
    
    private void initializeNodes(char[][] charGrid) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Node(i, j, charGrid[i][j]);
            }
        }
    }
    
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
    
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
    
    public Node getNode(int row, int col) {
        return grid[row][col];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public Node[][] getGrid() {
        return grid;
    }
    
    public void resetNodes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].reset();
            }
        }
    }
    
}
