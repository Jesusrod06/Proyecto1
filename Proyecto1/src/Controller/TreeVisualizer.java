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
 *
 * @author jesus rodriguez
 */
public class TreeVisualizer {
    public static class TreeNode {
        private String id;
        private String label;
        private int level;
        private List<TreeNode> children;
        private Node gridNode;
        
        public TreeNode(String id, String label, int level, Node gridNode) {
            this.id = id;
            this.label = label;
            this.level = level;
            this.gridNode = gridNode;
            this.children = new ArrayList<>();
        }
        
        public void addChild(TreeNode child) {
            children.add(child);
        }
        
        // Getters
        public String getId() { return id; }
        public String getLabel() { return label; }
        public int getLevel() { return level; }
        public List<TreeNode> getChildren() { return children; }
        public Node getGridNode() { return gridNode; }
    }
    
    private TreeNode rootNode;
    private int nodeCounter;
    
    public TreeVisualizer() {
        this.nodeCounter = 0;
    }
    
    public TreeNode createSearchTree(WordPath wordPath) {
        List<Node> path = wordPath.getPath();
        if (path.isEmpty()) {
            return null;
        }
        
        nodeCounter = 0;
        Node firstNode = path.get(0);
        rootNode = new TreeNode(
            generateNodeId(),
            "Start: " + firstNode.getCharacter() + " (" + firstNode.getRow() + "," + firstNode.getCol() + ")",
            0,
            firstNode
        );
        
        TreeNode currentTreeNode = rootNode;
        for (int i = 1; i < path.size(); i++) {
            Node gridNode = path.get(i);
            TreeNode childNode = new TreeNode(
                generateNodeId(),
                gridNode.getCharacter() + " (" + gridNode.getRow() + "," + gridNode.getCol() + ")",
                i,
                gridNode
            );
            currentTreeNode.addChild(childNode);
            currentTreeNode = childNode;
        }
        
        return rootNode;
    }
    
    public String generateDotGraph(TreeNode root) {
        if (root == null) {
            return "";
        }
        
        StringBuilder dot = new StringBuilder();
        dot.append("digraph SearchTree {\n");
        dot.append("    rankdir=TB;\n");
        dot.append("    node [shape=circle, style=filled];\n");
        
        generateDotNodes(root, dot);
        generateDotEdges(root, dot);
        
        dot.append("}\n");
        return dot.toString();
    }
    
    private void generateDotNodes(TreeNode node, StringBuilder dot) {
        String color = getNodeColor(node.getLevel());
        dot.append("    ").append(node.getId())
           .append(" [label=\"").append(node.getLabel())
           .append("\", fillcolor=\"").append(color).append("\"];\n");
        
        for (TreeNode child : node.getChildren()) {
            generateDotNodes(child, dot);
        }
    }
    
    private void generateDotEdges(TreeNode node, StringBuilder dot) {
        for (TreeNode child : node.getChildren()) {
            dot.append("    ").append(node.getId())
               .append(" -> ").append(child.getId()).append(";\n");
            generateDotEdges(child, dot);
        }
    }
    
    private String getNodeColor(int level) {
        String[] colors = {"lightblue", "lightgreen", "lightyellow", "lightpink", "lightgray"};
        return colors[level % colors.length];
    }
    
    private String generateNodeId() {
        return "node" + (++nodeCounter);
    }
    
    public void printTreeStructure(TreeNode node, String prefix) {
        if (node == null) return;
        
        System.out.println(prefix + node.getLabel());
        for (int i = 0; i < node.getChildren().size(); i++) {
            TreeNode child = node.getChildren().get(i);
            String newPrefix = prefix + (i == node.getChildren().size() - 1 ? "└── " : "├── ");
            printTreeStructure(child, newPrefix);
        }
    }
    
    public List<String> getTreeAsList(TreeNode node) {
        List<String> result = new ArrayList<>();
        if (node != null) {
            collectTreeNodes(node, "", result);
        }
        return result;
    }
    
    private void collectTreeNodes(TreeNode node, String prefix, List<String> result) {
        result.add(prefix + node.getLabel());
        for (int i = 0; i < node.getChildren().size(); i++) {
            TreeNode child = node.getChildren().get(i);
            String newPrefix = prefix + (i == node.getChildren().size() - 1 ? "└── " : "├── ");
            collectTreeNodes(child, newPrefix, result);
        }
    }
    
    public TreeNode getRootNode() {
        return rootNode;
    }
}