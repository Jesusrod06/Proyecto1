/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.util.List;
import java.util.ArrayList;


/**
 *this is the class node.
 * 
 */

public class Node {
    private int row;
    private int col;
    private char character;
    private List<Node> neighbors;
    private boolean visited;
    private boolean isUsed;
    
    /**
     * Constructs a Node with the specified row, column, and character value.
     * @param row The row index of the node.
     * @param col The column index of the node.
     * @param character The character value associated with the node.
     */
    public Node(int row, int col, char character) {
        this.row = row;
        this.col = col;
        this.character = character;
        this.neighbors = new ArrayList<>();
        this.visited = false;
        this.isUsed = false;
    }
    
    /**
     * Adds a neighbor node to this node's list of neighbors.
     * @param neighbor The neighbor node to add.
     */
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }
    
    /**
     * Retrieves the list of neighbor nodes.
     * @return A list of neighboring nodes.
     */
    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    /**
     * Retrieves the row index of the node.
     * @return The row index.
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Retrieves the column index of the node.
     * @return The column index.
     */
    public int getCol() {
        return col;
    }
    
    /**
     * Retrieves the character value of the node.
     * @return The character value.
     */
    public char getCharacter() {
        return character;
    }
    
    /**
     * Checks if the node has been visited.
     * @return true if the node has been visited, false otherwise.
     */
    public boolean isVisited() {
        return visited;
    }
    
    /**
     * Sets the visited status of the node.
     * @param visited The visited status to set.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    /**
     * Checks if the node is marked as used.
     * @return true if the node is used, false otherwise.
     */
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * Sets the used status of the node.
     * @param used The used status to set.
     */
    public void setUsed(boolean used) {
        isUsed = used;
    }

    /**
     * Resets the visited and used status of the node to false.
     */
    public void reset() {
        this.visited = false;
        this.isUsed = false;
    }
    
    /**
     * Returns a string representation of the node in the format (row,col:character).
     * @return A string representation of the node.
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ":" + character + ")";
    }
    
    /**
     * Checks if this node is equal to another object.
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return row == node.row && col == node.col;
    }
    
    /**
     * Returns the hash code of the node based on its row and column indices.
     * @return The hash code of the node.
     */
    @Override
    public int hashCode() {
        return row * 31 + col;
    }
}
