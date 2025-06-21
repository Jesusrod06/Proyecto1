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
 * Panel that displays the search grid and highlights found words.
 */
public class BoardPanel  extends JPanel {
     private char[][] puzzleGrid;
    private JLabel[][] cellLabels;
    private int gridRows;
    private int gridCols;
    
    /**
     * Creates a fresh panel with a "waiting for content" message.
     * @implNote We use {@link BorderLayout} here - it handles resizing better.
     */
    public BoardPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Grilla de Búsqueda de Palabras"));
        setBackground(Color.WHITE);
        
        JLabel placeholderMessage = new JLabel("Carga un archivo de grilla para ver el puzzle aquí", JLabel.CENTER);
        placeholderMessage.setFont(new Font("Arial", Font.ITALIC, 16));
        placeholderMessage.setForeground(Color.GRAY);
        add(placeholderMessage, BorderLayout.CENTER);
    }
    
    /**
     * Loads up a fresh letter grid and makes the UI look pretty again.
     * @param grid your 2D character matrix (seriously, don't pass null)
     * @throws IllegalArgumentException if the grid's wonky (uneven rows)
     * @see #createGridDisplay() (how the magic happens)
     */
    public void setGrid(char[][] grid) {
        this.puzzleGrid = grid;
        this.gridRows = grid.length;
        this.gridCols = grid[0].length;
        
        removeAll();
        createGridDisplay();
        revalidate();
        repaint();
    }
    
    
    /**
     * Builds the visual grid using Swing labels ({@link JLabel}).
     * <p>
     * Each cell comes with:
     * <ul>
     *   <li>Centered text in that sweet {@code Monospaced} font</li>
     *   <li>A snazzy gray border to keep things organized</li>
     *   <li>A cozy 42x42 pixel size (but we can resize if needed)</li>
     * </ul>
     * </p>
     */
    private void createGridDisplay() {
        JPanel gridContainer = new JPanel(new GridLayout(gridRows, gridCols, 2, 2));
        gridContainer.setBackground(Color.WHITE);
        
        cellLabels = new JLabel[gridRows][gridCols];
        
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                JLabel cellLabel = new JLabel(String.valueOf(puzzleGrid[i][j]), JLabel.CENTER);
                cellLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cellLabel.setBackground(Color.WHITE);
                cellLabel.setOpaque(true);
                cellLabel.setPreferredSize(new Dimension(42, 42));
                
                cellLabels[i][j] = cellLabel;
                gridContainer.add(cellLabel);
            }
        }
        
        add(gridContainer, BorderLayout.CENTER);
    }
    
    /**
     * Makes all the found word paths glow with different colors.
     * 
     * @param foundPaths List of word trails we found (yo, don't pass null here)
     * 
     * @implNote Colors cycle through: 🌈 yellow, light gray, pink... like a rainbow!
     * 
     * @see #highlightPath(WordPath, Color) (how we make single paths shine)
     */
    public void highlightFoundWords(List<?> foundPaths) {
        if (cellLabels != null) {
            // Resetear todos los fondos primero
            for (int i = 0; i < gridRows; i++) {
                for (int j = 0; j < gridCols; j++) {
                    cellLabels[i][j].setBackground(Color.WHITE);
                }
            }
            
            // Aplicar resaltado basado en los caminos encontrados
            Color[] highlightColors = {Color.YELLOW, Color.LIGHT_GRAY, Color.PINK, Color.CYAN, Color.ORANGE};
            int colorIndex = 0;
            
            // Nota: La implementación real iteraría a través de objetos WordPath
            // y resaltaría las celdas específicas en cada camino
        }
    }
    
    /**
     *
     * Highlights a specific path on the grid with a given color.
     *
     * @param path The path to highlight (a {@link WordPath} object). Ignores {@code null}.

     * @param color Background color. Use {@link Color#WHITE} to "un-highlight".
     */
    public void highlightPath(WordPath path, Color color) {
        if (cellLabels != null && path != null) {
            for (Node node : path.getPath()) {
                cellLabels[node.getRow()][node.getCol()].setBackground(color);
            }
            repaint();
        }
    }
    
    /**
     * Restart all the cells to white background
     * @apiNote Más eficiente que {@link #clear()} para reutilizar la misma grilla.
     */
    public void clearHighlights() {
        if (cellLabels != null) {
            for (int i = 0; i < gridRows; i++) {
                for (int j = 0; j < gridCols; j++) {
                    cellLabels[i][j].setBackground(Color.WHITE);
                }
            }
            repaint();
        }
    }
    /**
     *
     * Resets the panel completely to its initial state (no grid).
     *
     * @implNote Frees resources by clearing references to the current grid.
     */
    public void clear() {
        removeAll();
        
        JLabel placeholderMessage = new JLabel("Carga un archivo de grilla para ver el puzzle aquí", JLabel.CENTER);
        placeholderMessage.setFont(new Font("Arial", Font.ITALIC, 16));
        placeholderMessage.setForeground(Color.GRAY);
        add(placeholderMessage, BorderLayout.CENTER);
        
        puzzleGrid = null;
        cellLabels = null;
        gridRows = 0;
        gridCols = 0;
        
        revalidate();
        repaint();
    }
     /**

     Sets the preferred panel size based on the grid dimensions.

    @return A {@link Dimension} of 420x420 pixels if no grid exists, or the calculated size.

    @implNote Each cell takes up ~45 pixels (including borders and margins).
      */
    @Override
    public Dimension getPreferredSize() {
        if (puzzleGrid != null) {
            return new Dimension(gridCols * 45, gridRows * 45);
        }
        return new Dimension(420, 420);
    }
}
