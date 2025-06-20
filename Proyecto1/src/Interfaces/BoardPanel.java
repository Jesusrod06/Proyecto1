/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import proyecto1.Node;
import proyecto1.WordPath;

/**
 *
 * @author jesus rodriguez
 */
public class BoardPanel  extends JPanel {
     private char[][] grid;
    private JLabel[][] gridLabels;
    private int rows;
    private int cols;
    
    public BoardPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Word Search Grid"));
        setBackground(Color.WHITE);
        
        JLabel placeholderLabel = new JLabel("Load a grid file to see the puzzle here", JLabel.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        placeholderLabel.setForeground(Color.GRAY);
        add(placeholderLabel, BorderLayout.CENTER);
    }
    
    public void setGrid(char[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        
        removeAll();
        createGridDisplay();
        revalidate();
        repaint();
    }
    
    private void createGridDisplay() {
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 2, 2));
        gridPanel.setBackground(Color.WHITE);
        
        gridLabels = new JLabel[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JLabel label = new JLabel(String.valueOf(grid[i][j]), JLabel.CENTER);
                label.setFont(new Font("Monospaced", Font.BOLD, 18));
                label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                label.setBackground(Color.WHITE);
                label.setOpaque(true);
                label.setPreferredSize(new Dimension(40, 40));
                
                gridLabels[i][j] = label;
                gridPanel.add(label);
            }
        }
        
        add(gridPanel, BorderLayout.CENTER);
    }
    
    public void highlightFoundWords(List<?> foundPaths) {
        // Placeholder for highlighting functionality
        // Will highlight cells that are part of found words
        if (gridLabels != null) {
            // Reset all backgrounds first
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    gridLabels[i][j].setBackground(Color.WHITE);
                }
            }
            
            // Apply highlighting based on found paths
            // This is a simplified implementation
            Color[] highlightColors = {Color.YELLOW, Color.LIGHT_GRAY, Color.PINK, Color.CYAN, Color.ORANGE};
            int colorIndex = 0;
            
            // Note: Actual implementation would iterate through WordPath objects
            // and highlight the specific cells in each path
        }
    }
    
    public void highlightPath(WordPath path, Color color) {
        if (gridLabels != null && path != null) {
            for (Node node : path.getPath()) {
                gridLabels[node.getRow()][node.getCol()].setBackground(color);
            }
            repaint();
        }
    }
    
    public void clearHighlights() {
        if (gridLabels != null) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    gridLabels[i][j].setBackground(Color.WHITE);
                }
            }
            repaint();
        }
    }
    
    public void clear() {
        removeAll();
        
        JLabel placeholderLabel = new JLabel("Load a grid file to see the puzzle here", JLabel.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        placeholderLabel.setForeground(Color.GRAY);
        add(placeholderLabel, BorderLayout.CENTER);
        
        grid = null;
        gridLabels = null;
        rows = 0;
        cols = 0;
        
        revalidate();
        repaint();
    }
    
    public Dimension getPreferredSize() {
        if (grid != null) {
            return new Dimension(cols * 45, rows * 45);
        }
        return new Dimension(400, 400);
    }
}
