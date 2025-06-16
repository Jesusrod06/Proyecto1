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
    
}
