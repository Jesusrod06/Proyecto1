/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Control panel with search buttons and options for the word search application.
 */
public class ControlPanel  extends JPanel  {
    private JButton dfsButton;
    private JButton bfsButton;
    private JButton loadFileButton;
    private JButton clearButton;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JList<String> wordList;
    private DefaultListModel<String> wordListModel;
    private JTextField specificWordField;
    private JButton specificWordButton;
    private SearchListener searchListener;
    
    public interface SearchListener {
        void onDFSSearch();
        void onBFSSearch();
        void onLoadFile();
        void onClear();
        void onSpecificWordSearch(String word);
    }
    
    public ControlPanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        dfsButton = new JButton("DFS Search");
        bfsButton = new JButton("BFS Search");
        loadFileButton = new JButton("Load File");
        clearButton = new JButton("Clear");
        
        specificWordField = new JTextField(15);
        specificWordButton = new JButton("Buscar Palabra");
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        
        statusLabel = new JLabel("Ready - Load a grid file to begin");
        statusLabel.setForeground(Color.BLUE);
        
        wordListModel = new DefaultListModel<>();
        wordList = new JList<>(wordListModel);
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.setVisibleRowCount(5);
        
        // Initial button states
        dfsButton.setEnabled(false);
        bfsButton.setEnabled(false);
        specificWordButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Controls"));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loadFileButton);
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(clearButton);
        
        // Panel para búsqueda específica
        JPanel specificSearchPanel = new JPanel(new FlowLayout());
        specificSearchPanel.setBorder(BorderFactory.createTitledBorder("Búsqueda Específica"));
        specificSearchPanel.add(new JLabel("Palabra:"));
        specificSearchPanel.add(specificWordField);
        specificSearchPanel.add(specificWordButton);
        
        // Status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(progressBar, BorderLayout.CENTER);

        // Word list panel
        JPanel wordPanel = new JPanel(new BorderLayout());
        wordPanel.setBorder(BorderFactory.createTitledBorder("Words to Find"));
        wordPanel.add(new JScrollPane(wordList), BorderLayout.CENTER);
        wordPanel.setPreferredSize(new Dimension(200, 100));
        
        // Main layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(wordPanel, BorderLayout.CENTER);
        topPanel.add(specificSearchPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        loadFileButton.addActionListener(e -> {
            if (searchListener != null) {
                searchListener.onLoadFile();
            }
        });
        
        dfsButton.addActionListener(e -> {
            if (searchListener != null) {
                searchListener.onDFSSearch();
            }
        });
        
        bfsButton.addActionListener(e -> {
            if (searchListener != null) {
                searchListener.onBFSSearch();
            }
        });
        
        clearButton.addActionListener(e -> {
            if (searchListener != null) {
                searchListener.onClear();
            }
        });
        
        specificWordButton.addActionListener(e -> {
            if (searchListener != null) {
                searchListener.onSpecificWordSearch(specificWordField.getText());
            }
        });
    }
    
    public void setSearchListener(SearchListener listener) {
        this.searchListener = listener;
    }
    
    public void updateWordList(List<String> words) {
        wordListModel.clear();
        for (String word : words) {
            wordListModel.addElement(word);
        }
        
        // Enable search buttons when words are loaded
        dfsButton.setEnabled(true);
        bfsButton.setEnabled(true);
        specificWordButton.setEnabled(true);
        statusLabel.setText("Grid loaded - Ready to search for " + words.size() + " words");
    }
    
    public void setSearchInProgress(boolean inProgress) {
        dfsButton.setEnabled(!inProgress);
        bfsButton.setEnabled(!inProgress);
        loadFileButton.setEnabled(!inProgress);
        specificWordButton.setEnabled(!inProgress);
        
        if (inProgress) {
            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            statusLabel.setText("Searching...");
        } else {
            progressBar.setVisible(false);
            progressBar.setIndeterminate(false);
            statusLabel.setText("Search completed");
        }
    }
    
    public void clear() {
        wordListModel.clear();
        dfsButton.setEnabled(false);
        bfsButton.setEnabled(false);
        specificWordButton.setEnabled(false);
        progressBar.setVisible(false);
        statusLabel.setText("Ready - Load a grid file to begin");
    }
    
    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}
