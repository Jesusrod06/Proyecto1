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
 *
 * @author jesus rodriguez
 */
public class TreeDisplayFrame extends JFrame{
     private JTree tree;

    public TreeDisplayFrame() {
        setTitle("Visualización del Árbol de Búsqueda BFS");
        setSize(500, 700);
        setLocationRelativeTo(null); // Centrar en la ventana principal
        
        // El árbol se inicializa vacío
        tree = new JTree(new DefaultMutableTreeNode("El árbol de búsqueda aparecerá aquí."));
        JScrollPane scrollPane = new JScrollPane(tree);
        
        add(scrollPane, BorderLayout.CENTER);
    }

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

    private void buildSwingTree(DefaultMutableTreeNode swingNode, TreeNode appNode) {
        for (TreeNode child : appNode.getChildren()) {
            DefaultMutableTreeNode newSwingNode = new DefaultMutableTreeNode(child.getLabel());
            swingNode.add(newSwingNode);
            buildSwingTree(newSwingNode, child);
        }
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
    
}
