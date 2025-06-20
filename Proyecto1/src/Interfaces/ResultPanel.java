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
 *
 * @author jesus rodriguez
 */
public class ResultPanel extends JPanel{
    private JTextArea resultsArea;
    private JLabel algorithmLabel;
    private JLabel timeLabel;
    private JLabel wordsFoundLabel;
    private JLabel nodesVisitedLabel;
    private boolean hasResults;
    
    public ResultPanel() {
        initializeComponents();
        setupLayout();
        clear();
    }
    
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
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Search Results"));
        setPreferredSize(new Dimension(300, 0));
        
        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statsPanel.add(algorithmLabel);
        statsPanel.add(timeLabel);
        statsPanel.add(wordsFoundLabel);
        statsPanel.add(nodesVisitedLabel);
        
        // Results scroll pane
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Found Words"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(statsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void setResults(Object searchResult) {
        // Placeholder implementation - would normally take SearchResult object
        hasResults = true;
        
        // Mock data for demonstration
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
    
    public void clear() {
        hasResults = false;
        
        algorithmLabel.setText("Algorithm: -");
        timeLabel.setText("Time: -");
        wordsFoundLabel.setText("Words Found: -");
        nodesVisitedLabel.setText("Nodes Visited: -");
        
        resultsArea.setText("No search results yet.\n\nLoad a grid file and run a search to see results here.");
        resultsArea.setCaretPosition(0);
    }
    
    public boolean hasResults() {
        return hasResults;
    }
    
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
    
    public void updateStatistics(String algorithm, long timeMs, int wordsFound, int totalWords, int nodesVisited) {
        algorithmLabel.setText("Algorithm: " + algorithm);
        timeLabel.setText("Time: " + timeMs + " ms");
        wordsFoundLabel.setText("Words Found: " + wordsFound + "/" + totalWords);
        nodesVisitedLabel.setText("Nodes Visited: " + nodesVisited);
    }
    
    public void appendResult(String result) {
        resultsArea.append(result + "\n");
        resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
    }
    
}
