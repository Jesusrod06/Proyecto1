/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.swing.*;
import java.awt.*;
import proyecto1.Node;
import proyecto1.WordPath;
import java.util.List;
import java.util.ArrayList;

/**
 * Visualizes the search tree using tree data structure.
 * Shows how search algorithms traverse the grid.
 * 
 * <p>This class is responsible for converting search paths into
 * visualizable tree structures, providing different output formats
 * for search algorithm analysis and debugging.</p>
 * 
 * <p><strong>Main features:</strong></p>
 * <ul>
 *   <li>Search tree construction from word paths</li>
 *   <li>DOT format (GraphViz) representation generation</li>
 *   <li>Conversion to readable text structures</li>
 *   <li>Automatic level-based tree coloring</li>
 * </ul>
 * 
 * @author Visualization Module
 * @version 2.3
 * @since 1.2
 */
public class TreeVisualizer {
    
    /**
 * Represents an individual node in the visualization tree.
 * 
 * <p>Each node maintains information about its position in the tree,
 * its relationship with the corresponding grid node, and the
 * connections to its child nodes.</p>
 * 
 * <p>The structure allows building hierarchical trees that reflect
 * the exploration order of search algorithms.</p>
 * 
 * @since 1.2
 */
public static class TreeNode {
    /** Unique node identifier in the tree */
    private String nodeId;
    
    /** Descriptive node label (includes character and position) */
    private String nodeLabel;
    
    /** Node level in the tree (depth from root) */
    private int treeLevel;
    
    /** List of child nodes in the tree */
    private List<TreeNode> childNodes;
        
        /** Reference to the corresponding node in the original grid */
private Node correspondingGridNode;

/**
 * Constructor that initializes a new tree node.
 * 
 * @param id unique identifier for this node
 * @param label descriptive node label
 * @param level depth level in the tree (0 = root)
 * @param gridNode corresponding node in the original grid
 */
public TreeNode(String id, String label, int level, Node gridNode) {
    this.nodeId = id;
    this.nodeLabel = label;
    this.treeLevel = level;
    this.correspondingGridNode = gridNode;
    this.childNodes = new ArrayList<>();
}

/**
 * Adds a child node to this tree node.
 * 
 * @param childNode the child node to add
 */
public void addChild(TreeNode childNode) {
    childNodes.add(childNode);
}

// Access methods

/**
 * Gets the unique node identifier.
 * 
 * @return the node ID
 */
public String getId() { return nodeId; }

/**
 * Gets the descriptive node label.
 * 
 * @return the node label
 */
public String getLabel() { return nodeLabel; }

/**
 * Gets the node depth level in the tree.
 * 
 * @return the node level (0 for root)
 */
public int getLevel() { return treeLevel; }

/**
 * Gets the list of child nodes.
 * 
 * @return immutable list of child nodes
 */
public List<TreeNode> getChildren() { return childNodes; }

/**
 * Gets the corresponding node in the original grid.
 * 
 * @return the associated grid node
 */
public Node getGridNode() { return correspondingGridNode; }
}

/** Root node of the current tree */
private TreeNode rootTreeNode;

/** Counter for generating unique node IDs */
private int nodeIdCounter;

/**
 * Constructor that initializes the visualizer with an empty tree.
 */
public TreeVisualizer() {
    this.nodeIdCounter = 0;
}

/**
 * Creates a search tree from a found word path.
 * 
 * <p>This method converts a linear sequence of nodes (WordPath) into
 * a hierarchical tree structure, where each node represents a
 * step in the search process.</p>
 * 
 * <p>The resulting tree has the following characteristics:</p>
 * <ul>
 *   <li>The root represents the search starting point</li>
 *   <li>Each level represents an additional step in the word</li>
 *   <li>The leaves represent the end of the found word</li>
 * </ul>
 * 
 * @param caminoPalabra the node path forming the found word
 * @return TreeNode representing the tree root, or null if path is empty
 * @throws IllegalArgumentException if caminoPalabra is null
 */
public TreeNode createSearchTree(WordPath caminoPalabra) {
    List<Node> pathNodes = caminoPalabra.getPath();
    if (pathNodes.isEmpty()) {
        return null;
    }
        
        nodeIdCounter = 0;
        Node nodoInicial = pathNodes.get(0);
        rootTreeNode = new TreeNode(
            generateNodeId(),
            "Inicio: " + nodoInicial.getCharacter() + " (" + nodoInicial.getRow() + "," + nodoInicial.getCol() + ")",
            0,
            nodoInicial
        );
        
        TreeNode currentTreeNode = rootTreeNode;
        for (int i = 1; i < pathNodes.size(); i++) {
            Node gridNode = pathNodes.get(i);
            TreeNode childTreeNode = new TreeNode(
                generateNodeId(),
                gridNode.getCharacter() + " (" + gridNode.getRow() + "," + gridNode.getCol() + ")",
                i,
                gridNode
            );
            currentTreeNode.addChild(childTreeNode);
            currentTreeNode = childTreeNode;
        }
        
        return rootTreeNode;
    }
    
    /**
     * Generates a tree representation in DOT format (GraphViz).
     * 
     * <p>The DOT format is standard for graph visualization and can
     * be processed by tools like GraphViz to generate images
     * of the search tree.</p>
     * 
     * <p><strong>Output features:</strong></p>
     * <ul>
     *   <li>Top-to-bottom orientation (rankdir=TB)</li>
     *   <li>Circular nodes with level-based colors</li>
     *   <li>Descriptive labels on each node</li>
     * </ul>
     * 
     * @param rootNode the root of the tree to convert
     * @return String with the DOT code, or empty string if rootNode is null
     * 
     * @see <a href="https://graphviz.org/">GraphViz Documentation</a>
     */
public String generateDotGraph(TreeNode rootNode) {
    if (rootNode == null) {
        return "";
    }
    
    StringBuilder dotBuilder = new StringBuilder();
    dotBuilder.append("digraph SearchTree {\n");
    dotBuilder.append("    rankdir=TB;\n");
    dotBuilder.append("    node [shape=circle, style=filled];\n");
    
    generateDotNodes(rootNode, dotBuilder);
    generateDotEdges(rootNode, dotBuilder);
    
    dotBuilder.append("}\n");
    return dotBuilder.toString();
}
    
    /**
     * Genera las definiciones de nodos para el formato DOT.
     * 
     * <p>Método auxiliar recursivo que procesa todos los nodos del árbol
     * y genera sus definiciones con colores apropiados según su nivel.</p>
     * 
     * @param treeNode el nodo actual a procesar
     * @param dotBuilder el StringBuilder donde acumular el output DOT
     */
    private void generateDotNodes(TreeNode treeNode, StringBuilder dotBuilder) {
        String nodeColor = getNodeColor(treeNode.getLevel());
        dotBuilder.append("    ").append(treeNode.getId())
           .append(" [label=\"").append(treeNode.getLabel())
           .append("\", fillcolor=\"").append(nodeColor).append("\"];\n");
        
        for (TreeNode child : treeNode.getChildren()) {
            generateDotNodes(child, dotBuilder);
        }
    }
    
    /**
 * Generates edge definitions for DOT format.
 * 
 * <p>Recursive helper method that creates connections between parent
 * and child nodes in the DOT representation.</p>
 * 
 * @param treeNode current node to process
 * @param dotBuilder StringBuilder to accumulate DOT output
 */
private void generateDotEdges(TreeNode treeNode, StringBuilder dotBuilder) {
    for (TreeNode child : treeNode.getChildren()) {
        dotBuilder.append("    ").append(treeNode.getId())
           .append(" -> ").append(child.getId()).append(";\n");
        generateDotEdges(child, dotBuilder);
    }
}

/**
 * Determines the appropriate color for a node based on its tree level.
 * 
 * <p>Uses a cyclic color scheme to visually differentiate
 * different depth levels in the search tree.</p>
 * 
 * @param nivelNodo node depth level (0 = root)
 * @return String with color name in DOT-compatible format
 */
private String getNodeColor(int nivelNodo) {
    String[] availableColors = {"lightblue", "lightgreen", "lightyellow", "lightpink", "lightgray"};
    return availableColors[nivelNodo % availableColors.length];
}

/**
 * Generates a unique identifier for a new tree node.
 * 
 * <p>Generated IDs follow the pattern "nodo1", "nodo2", etc.</p>
 * 
 * @return String with unique node ID
 */
private String generateNodeId() {
    return "nodo" + (++nodeIdCounter);
}

/**
 * Prints the tree structure to console in ASCII format.
 * 
 * <p>Uses special characters to create a visual representation
 * of the tree that clearly shows node hierarchy.</p>
 * 
 * @param treeNode root node of the tree to print
 * @param prefijo indentation prefix (internal use)
 */
public void printTreeStructure(TreeNode treeNode, String prefijo) {
    if (treeNode == null) return;
    
    System.out.println(prefijo + treeNode.getLabel());
    for (int i = 0; i < treeNode.getChildren().size(); i++) {
        TreeNode childNode = treeNode.getChildren().get(i);
        String nuevoPrefijo = prefijo + (i == treeNode.getChildren().size() - 1 ? "└── " : "├── ");
        printTreeStructure(childNode, nuevoPrefijo);
    }
}

/**
 * Converts the tree to a list of ASCII-formatted strings.
 * 
 * <p>Similar to {@link #printTreeStructure} but returns results
 * as a string list instead of direct printing.</p>
 * 
 * @param treeNode root node of the tree to convert
 * @return List&lt;String&gt; with each line of tree representation
 */
public List<String> getTreeAsList(TreeNode treeNode) {
    List<String> treeStringList = new ArrayList<>();
    if (treeNode != null) {
        collectTreeNodes(treeNode, "", treeStringList);
    }
    return treeStringList;
}

/**
 * Recursive helper method to collect nodes in list format.
 * 
 * @param treeNode current node to process
 * @param prefijo current indentation prefix
 * @param resultado list to accumulate result lines
 */
private void collectTreeNodes(TreeNode treeNode, String prefijo, List<String> resultado) {
    resultado.add(prefijo + treeNode.getLabel());
    for (int i = 0; i < treeNode.getChildren().size(); i++) {
        TreeNode childNode = treeNode.getChildren().get(i);
        String nuevoPrefijo = prefijo + (i == treeNode.getChildren().size() - 1 ? "└── " : "├── ");
        collectTreeNodes(childNode, nuevoPrefijo, resultado);
    }
}

/**
 * Gets the root node of the current tree.
 * 
 * @return TreeNode representing the root, or null if no tree exists
 */
public TreeNode getRootNode() {
    return rootTreeNode;
}
}