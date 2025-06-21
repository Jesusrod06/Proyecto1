/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * ResultPanel is a JPanel that displays the results of the word search operation.
 * It shows the algorithm used, the time taken, the number of words found,
 * and the number of nodes visited during the search. It also provides a text area
 * to display the found words and their details.
 */
public class ResultPanel extends JPanel {
    private JTextArea resultsArea;
    private JLabel algorithmLabel;
    private JLabel timeLabel;
    private JLabel wordsFoundLabel;
    private JLabel nodesVisitedLabel;
    private boolean hasResults;
    
    /**
     * Constructs the ResultPanel and initializes its components and layout.
     */
    public ResultPanel() {
        initializeComponents();
        setupLayout();
        clear();
    }
    
    /**
     * Initializes the components of the ResultPanel, including labels and text area.
     */
    private void initializeComponents() {
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultsArea.setBackground(Color.WHITE);
        
        algorithmLabel = new JLabel("Algorithm: -");
        timeLabel = new JLabel("Time: -");
        wordsFoundLabel = new JLabel("Words Found: -");
        nodesVisitedLabel = new JLabel("Nodes Visited: -");
        
        hasResults = false;
    }
    
    /**
     * Sets up the layout of the ResultPanel, organizing the statistics and results area.
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Search Results"));
        setPreferredSize(new Dimension(300, 0));
        
        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statsPanel.add(algorithmLabel);
        statsPanel.add(timeLabel);
        statsPanel.add(wordsFoundLabel);
        statsPanel.add(nodesVisitedLabel);
        
        // Panel de resultados con scroll
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Found Words"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(statsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Sets the results of the search operation.
     * This method updates the statistics labels and the results area.
     * @param searchResult The result of the search operation (placeholder implementation).
     */
    public void setResults(Object searchResult) {
        // Implementación de marcador de posición - normalmente tomaría un objeto SearchResult
        hasResults = true;
        
        // Datos simulados para demostración
        algorithmLabel.setText("Algorithm: DFS");
        timeLabel.setText("Time: 45 ms");
        wordsFoundLabel.setText("Words Found: 3/5");
        nodesVisitedLabel.setText("Nodes Visited: 156");
        
        resultsArea.setText("Found Words:\n\n" +
                           "WORD1 - Start: (0,0), End: (0,4), Direction: Horizontal\n" +
                           "WORD2 - Start: (1,1), End: (3,3), Direction: Diagonal\n" +
                           "WORD3 - Start: (2,0), End: (4,0), Direction: Vertical\n\n" +
                           "Search completed successfully.");
        
        resultsArea.setCaretPosition(0);
    }
    
    /**
     * Clears the results and resets the statistics labels.
     */
    public void clear() {
        hasResults = false;
        
        algorithmLabel.setText("Algorithm: -");
        timeLabel.setText("Time: -");
        wordsFoundLabel.setText("Words Found: -");
        nodesVisitedLabel.setText("Nodes Visited: -");
        
        resultsArea.setText("No search results yet.\n\nLoad a grid file and run a search to see results here.");
        resultsArea.setCaretPosition(0);
    }
    
    /**
     * Checks if there are results available.
     * @return true if there are results, false otherwise.
     */
    public boolean hasResults() {
        return hasResults;
    }
    
    /**
     * Returns the results as a formatted text string.
     * @return A string containing the results of the search.
     */
    public String getResultsAsText() {
        if (!hasResults) {
            return "No results available";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("WORD SEARCH RESULTS\n");
        sb.append("==================\n\n");
        sb.append(algorithmLabel.getText()).append("\n");
        sb.append(timeLabel.getText()).append("\n");
        sb.append(wordsFoundLabel.getText()).append("\n");
        sb.append(nodesVisitedLabel.getText()).append("\n\n");
        sb.append("DETAILED RESULTS:\n");
        sb.append(resultsArea.getText());
        
        return sb.toString();
    }
    
    /**
     * Updates the statistics displayed in the panel.
     * @param algorithm The algorithm used for the search.
     * @param timeMs The time taken for the search in milliseconds.
     * @param wordsFound The number of words found during the search.
     * @param totalWords The total number of words to search for.
     * @param nodesVisited The number of nodes visited during the search.
     */
    public void updateStatistics(String algorithm, long timeMs, int wordsFound, int totalWords, int nodesVisited) {
        algorithmLabel.setText("Algorithm: " + algorithm);
        timeLabel.setText("Time: " + timeMs + " ms");
        wordsFoundLabel.setText("Words Found: " + wordsFound + "/" + totalWords);
        nodesVisitedLabel.setText("Nodes Visited: " + nodesVisited);
    }
    
    /**
     * Appends a result to the results area.
     * @param result The result string to append.
     */
    public void appendResult(String result) {
        resultsArea.append(result + "\n");
        resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
    }
}
