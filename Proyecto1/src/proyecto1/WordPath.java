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
public class WordPath {
    private List<Node> path;
    private String word;
    private String direction;
    
    public WordPath(){
        this.path = new ArrayList<>();
        this.word = "";
        this.direction = "";
    }
     public WordPath(List<Node> path) {
        this.path = new ArrayList<>(path);
        this.word = buildWordFromPath();
        this.direction = calculateDirection();
    }
    
    public void addNode(Node node) {
        path.add(node);
        word = buildWordFromPath();
        direction = calculateDirection();
    }
     public List<Node> getPath() {
        return new ArrayList<>(path);
    }
   
    public String getWord() {
        return word;
    }
    
    public String getDirection() {
        return direction;
    }
    
    private String buildWordFromPath() {
        StringBuilder sb = new StringBuilder();
        for (Node node : path) {
            sb.append(node.getCharacter());
        }
        return sb.toString();
    }
    
    private String calculateDirection() {
        if (path.size() < 2) return "N/A";
        
        Node start = path.get(0);
        Node end = path.get(path.size() - 1);
        
        int deltaRow = end.getRow() - start.getRow();
        int deltaCol = end.getCol() - start.getCol();
        
        if (deltaRow == 0 && deltaCol > 0) return "Horizontal Right";
        if (deltaRow == 0 && deltaCol < 0) return "Horizontal Left";
        if (deltaCol == 0 && deltaRow > 0) return "Vertical Down";
        if (deltaCol == 0 && deltaRow < 0) return "Vertical Up";
        if (deltaRow > 0 && deltaCol > 0) return "Diagonal Down-Right";
        if (deltaRow > 0 && deltaCol < 0) return "Diagonal Down-Left";
        if (deltaRow < 0 && deltaCol > 0) return "Diagonal Up-Right";
        if (deltaRow < 0 && deltaCol < 0) return "Diagonal Up-Left";
        
        return "Complex";
    }
}    