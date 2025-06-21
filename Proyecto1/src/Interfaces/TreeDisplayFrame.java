/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import Controller.TreeVisualizer.TreeNode;
import java.awt.*;

/**
 * TreeDisplayFrame is a JFrame that displays a visual representation of a search tree.
 * 
 */
public class TreeDisplayFrame extends JFrame {
    private JTree tree;

    /**
     * Constructs the TreeDisplayFrame and initializes its components.
     * 
     */
    public TreeDisplayFrame() {
        setTitle("Visualización del Árbol de Búsqueda BFS");
        setSize(500, 700);
        setLocationRelativeTo(null); // Centrar en la ventana principal
        
        // El árbol se inicializa vacío
        tree = new JTree(new DefaultMutableTreeNode("El árbol de búsqueda aparecerá aquí."));
        JScrollPane scrollPane = new JScrollPane(tree);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays the search tree in the JTree component.
     * If the root is null, it shows a message indicating that no tree was found.
     * @param root The root node of the search tree to be displayed.
     */
    public void displayTree(TreeNode root) {
        if (root == null) {
            tree.setModel(new JTree(new DefaultMutableTreeNode("No se encontró la palabra o no se pudo generar el árbol.")).getModel());
            return;
        }
        
        DefaultMutableTreeNode swingRoot = new DefaultMutableTreeNode(root.getLabel());
        buildSwingTree(swingRoot, root);
        
        tree.setModel(new JTree(swingRoot).getModel());
        expandAllNodes(tree, 0, tree.getRowCount());
    }

    /**
     * Recursively builds the Swing tree from the application tree node.
     * @param swingNode The current Swing node to which children will be added.
     * @param appNode The application tree node from which to get children.
     */
    private void buildSwingTree(DefaultMutableTreeNode swingNode, TreeNode appNode) {
        for (TreeNode child : appNode.getChildren()) {
            DefaultMutableTreeNode newSwingNode = new DefaultMutableTreeNode(child.getLabel());
            swingNode.add(newSwingNode);
            buildSwingTree(newSwingNode, child);
        }
    }

    /**
     * Expands all nodes in the JTree starting from a given index.
     * @param tree The JTree to expand nodes in.
     * @param startingIndex The index to start expanding from.
     * @param rowCount The total number of rows in the tree.
     */
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
}
