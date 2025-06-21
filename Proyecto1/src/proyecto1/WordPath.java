/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents a path of nodes that forms a word, including information about
 * 
 */
public class WordPath {
    private List<Node> path;
    private String word;
    private String direction;

    /**
     * Constructs a new WordPath object with an empty path, word, and direction.
     */
    public WordPath() {
        this.path = new ArrayList<>();
        this.word = "";
        this.direction = "";
    }

    /**
     * Constructs a new WordPath object with the specified path of nodes.
     *
     * @param path the list of nodes that make up the path
     */
    public WordPath(List<Node> path) {
        this.path = new ArrayList<>(path);
        this.word = buildWordFromPath();
        this.direction = calculateDirection();
    }

    /**
     * Adds a node to the path and updates the word and direction accordingly.
     *
     * @param node the node to be added to the path
     */
    public void addNode(Node node) {
        path.add(node);
        word = buildWordFromPath();
        direction = calculateDirection();
    }

    /**
     * Returns the list of nodes that make up the path.
     *
     * @return a list of nodes in the path
     */
    public List<Node> getPath() {
        return new ArrayList<>(path);
    }

    /**
     * Returns the word formed by the nodes in the path.
     *
     * @return the word formed by the path
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the direction of the path.
     *
     * @return the direction of the path
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Returns the starting node of the path.
     *
     * @return the starting node, or null if the path is empty
     */
    public Node getStartNode() {
        return path.isEmpty() ? null : path.get(0);
    }

    /**
     * Returns the ending node of the path.
     *
     * @return the ending node, or null if the path is empty
     */
    public Node getEndNode() {
        return path.isEmpty() ? null : path.get(path.size() - 1);
    }

    /**
     * Returns the length of the path (number of nodes).
     *
     * @return the length of the path
     */
    public int getLength() {
        return path.size();
    }

    /**
     * Checks if the path is empty.
     *
     * @return true if the path is empty, false otherwise
     */
    public boolean isEmpty() {
        return path.isEmpty();
    }

    /**
     * Builds the word from the characters of the nodes in the path.
     *
     * @return the word formed by the path
     */
    private String buildWordFromPath() {
        StringBuilder sb = new StringBuilder();
        for (Node node : path) {
            sb.append(node.getCharacter());
        }
        return sb.toString();
    }

    /**
     * Calculates the direction of the path based on the start and end nodes.
     *
     * @return a string representing the direction of the path
     */
    private String calculateDirection() {
        if (path.size() < 2) {
            return "N/A";
        }

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

    /**
     * Returns a string representation of the WordPath, including the word,
     * start and end coordinates, and direction.
     *
     * @return a string representation of the WordPath
     */
    @Override
    public String toString() {
        if (path.isEmpty()) {
            return "Empty path";
        }

        Node start = getStartNode();
        Node end = getEndNode();

        return String.format("Word: %s, Start: (%d,%d), End: (%d,%d), Direction: %s",
                word, start.getRow(), start.getCol(), end.getRow(), end.getCol(), direction);
    }
}
