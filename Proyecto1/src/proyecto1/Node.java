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
public class Node {
     private int row;
    private int col;
    private char character;
    private List<Node> neighbors;
    private boolean visited;
    private boolean isUsed;
    
    public Node(int row, int col, char character) {
        this.row = row;
        this.col = col;
        this.character = character;
        this.neighbors = new ArrayList<>();
        this.visited = false;
        this.isUsed = false;
    }
    
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }
    
    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public char getCharacter() {
        return character;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void reset() {
        this.visited = false;
        this.isUsed = false;
    }
    
    @Override
    public String toString() {
        return "(" + row + "," + col + ":" + character + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return row == node.row && col == node.col;
    }
    
    @Override
    public int hashCode() {
        return row * 31 + col;
    }
    
}
