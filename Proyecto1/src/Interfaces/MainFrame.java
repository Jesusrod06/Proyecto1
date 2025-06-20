/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import proyecto1.Graph;
import proyecto1.Dictionary;
import Controller.DFSSearcher;
import Controller.BFSSearcher;
import proyecto1.WordPath;
import proyecto1.Node;
import Controller.FileManager;
import Controller.TreeVisualizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import proyecto1.Proyecto1;



/**
 *
 * @author jesus rodriguez
 */
public class MainFrame {
    private Proyecto1 proyecto1;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    private ResultPanel resultPanel;
    private TreeVisualizer treeFrame;
    private LogWindow logWindow;
    private TreeDisplayFrame treeDisplayFrame;
    
    public MainFrame() {
        initializeFrame();
        initializeComponents();
        logWindow = new LogWindow();
        treeDisplayFrame = new TreeDisplayFrame();
        proyecto1.setLogWindow(logWindow);
        setupLayout();
        setupEventHandlers();
        setWindowProperties();
        setupMenuBar();
    }
    
    private void initializeFrame() {
        setTitle("Word Search Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Create placeholder panels
        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Board Panel - Grid will be displayed here"));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Word Search Grid"));
        
        JPanel eastPanel = new JPanel();
        eastPanel.add(new JLabel("Results Panel"));
        eastPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
        eastPanel.setPreferredSize(new Dimension(300, 0));
        
        JPanel southPanel = new JPanel();
        southPanel.add(new JLabel("Control Panel - Search buttons will be here"));
        southPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        
        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        
        // Create simple menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
    
    private void initializeComponents() {
        wordSearcher = new WordSearcher();
        boardPanel = new BoardPanel();
        controlPanel = new ControlPanel();
        resultPanel = new ResultPanel();
        treeFrame = new TreeVisualizer();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create main panel with board and results
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Create menu bar
        setupMenuBar();
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadFileItem = new JMenuItem("Load Grid File");
        JMenuItem saveResultsItem = new JMenuItem("Save Results");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        fileMenu.add(loadFileItem);
        fileMenu.addSeparator();
        fileMenu.add(saveResultsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // View menu
        JMenu viewMenu = new JMenu("View");
        JMenuItem showTreeItem = new JMenuItem("Show Search Tree");
        JMenuItem clearHighlightsItem = new JMenuItem("Clear Highlights");
        JMenuItem showLogItem = new JMenuItem("Show Log Window");
        
        viewMenu.add(showTreeItem);
        viewMenu.add(clearHighlightsItem);
        viewMenu.add(showLogItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem helpItem = new JMenuItem("Help");
        
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
        
        // Menu event handlers
        loadFileItem.addActionListener(e -> loadGridFile());
        saveResultsItem.addActionListener(e -> saveResults());
        exitItem.addActionListener(e -> System.exit(0));
        showTreeItem.addActionListener(e -> showTreeVisualization());
        clearHighlightsItem.addActionListener(e -> clearHighlights());
        aboutItem.addActionListener(e -> showAboutDialog());
        helpItem.addActionListener(e -> showHelpDialog());
        showLogItem.addActionListener(e -> logWindow.setVisible(true));
    }
    
    private void setupEventHandlers() {
        controlPanel.setSearchListener(new ControlPanel.SearchListener() {
            @Override
            public void onDFSSearch() {
                performDFSSearch();
            }
            
            @Override
            public void onBFSSearch() {
                performBFSSearch();
            }
            
            @Override
            public void onLoadFile() {
                loadGridFile();
            }
            
            @Override
            public void onClear() {
                clearAll();
            }

            @Override
            public void onSpecificWordSearch(String word) {
                performSpecificWordSearch(word.toUpperCase());
            }
        });
    }
    
    private void setWindowProperties() {
        setTitle("Word Search Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
    }
    
    private void loadGridFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("resources"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                System.out.println("Loading grid file...");
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                FileManager.GridData gridData = FileManager.loadGridFromFile(filename);
                
                wordSearcher.setGrid(gridData.grid);
                Dictionary dictionary = new Dictionary();
                dictionary.addWords(gridData.words);
                wordSearcher.setDictionary(dictionary);
                
                boardPanel.setGrid(gridData.grid);
                controlPanel.updateWordList(gridData.words);
                resultPanel.clear();
                
                JOptionPane.showMessageDialog(this, "Grid loaded successfully!");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading file: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void performDFSSearch() {
        performSearch("DFS");
    }
    
    private void performBFSSearch() {
        performSearch("BFS");
    }
    
    private void performSearch(String algorithm) {
        if (!wordSearcher.hasValidConfiguration()) {
            JOptionPane.showMessageDialog(this, "Please load a grid file first.");
            return;
        }
        
        // Resetear el estado de los nodos antes de cada búsqueda
        wordSearcher.getGraph().resetNodes();

        logWindow.clearLog();
        logWindow.appendLog("Iniciando búsqueda " + algorithm + " secuencial...");
        boardPanel.clearHighlights();
        resultPanel.clear();

        java.util.List<String> wordsToSearch = new ArrayList<>(wordSearcher.getDictionary().getAllWords());

        // Clase interna para pasar datos al hilo de la UI
        class HighlightData {
            final WordPath path;
            final Color color;
            HighlightData(WordPath path, Color color) { this.path = path; this.color = color; }
        }

        SwingWorker<Void, HighlightData> worker = new SwingWorker<>() {
            private final Color[] colors = {Color.YELLOW, Color.CYAN, new Color(144, 238, 144), Color.ORANGE, Color.PINK, new Color(211, 211, 211)};
            private int colorIndex = 0;

            @Override
            protected Void doInBackground() throws Exception {
                controlPanel.setSearchInProgress(true);
                int wordsFound = 0;
                long totalTime = 0;

                for (String word : wordsToSearch) {
                    logWindow.appendLog("\nBuscando palabra: " + word);
                    long startTime = System.currentTimeMillis();
                    WordPath path = wordSearcher.findWord(word, algorithm);
                    totalTime += (System.currentTimeMillis() - startTime);

                    if (path != null) {
                        wordsFound++;
                        logWindow.appendLog("Palabra '" + word + "' encontrada! Ruta: " + path.toString());
                        
                        // Marcar nodos como usados
                        for (com.wordsearch.model.Node node : path.getPath()) {
                            node.setUsed(true);
                        }

                        Color color = colors[colorIndex++ % colors.length];
                        publish(new HighlightData(path, color)); // Publica para resaltar

                    } else {
                        logWindow.appendLog("Palabra '" + word + "' no encontrada.");
                    }
                    
                    final int currentWordsFound = wordsFound;
                    final long currentTotalTime = totalTime;
                    SwingUtilities.invokeLater(() -> {
                         resultPanel.updateStatistics(algorithm, currentTotalTime, currentWordsFound, wordsToSearch.size(), 0);
                         resultPanel.appendResult(word + " - " + (path != null ? "Encontrada" : "No encontrada"));
                    });
                    
                    Thread.sleep(500); // Pausa para visualización
                }
                return null;
            }

            @Override
            protected void process(java.util.List<HighlightData> chunks) {
                for (HighlightData data : chunks) {
                    boardPanel.highlightPath(data.path, data.color);
                }
            }

            @Override
            protected void done() {
                controlPanel.setSearchInProgress(false);
                logWindow.appendLog("\nBúsqueda completada.");
                JOptionPane.showMessageDialog(MainFrame.this, "Búsqueda secuencial completada.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        worker.execute();
    }
    
    private void displayResults(SearchResult result) {
        resultPanel.setResults(result);
        boardPanel.highlightFoundWords(result.getFoundPaths());
    }
    
    private void saveResults() {
        if (resultPanel.hasResults()) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String filename = fileChooser.getSelectedFile().getAbsolutePath();
                    FileManager.saveResultsToFile(filename, resultPanel.getResultsAsText());
                    JOptionPane.showMessageDialog(this, "Results saved successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error saving results: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No results to save.");
        }
    }
    
    private void showTreeVisualization() {
        if (resultPanel.hasResults()) {
            treeFrame.printTreeStructure(treeFrame.getRootNode(), "");
        } else {
            JOptionPane.showMessageDialog(this, "No search results to visualize.");
        }
    }
    
    private void clearHighlights() {
        boardPanel.clearHighlights();
    }
    
    private void clearAll() {
        boardPanel.clear();
        resultPanel.clear();
        controlPanel.clear();
        wordSearcher.reset();
    }
    
    private void showAboutDialog() {
        String message = "Word Search Application\n" +
                        "Version 1.0\n\n" +
                        "A graphical word search puzzle solver\n" +
                        "using DFS and BFS algorithms.\n\n" +
                        "Features:\n" +
                        "- Graph-based word searching\n" +
                        "- Visual search tree representation\n" +
                        "- Performance comparison\n" +
                        "- File I/O support";
        
        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showHelpDialog() {
        String message = "How to use:\n\n" +
                        "1. Load a grid file using File -> Load Grid File\n" +
                        "2. Choose DFS or BFS search algorithm\n" +
                        "3. Click Search to find words\n" +
                        "4. View results in the right panel\n" +
                        "5. Use View -> Show Search Tree to see visualization\n\n" +
                        "Grid file format:\n" +
                        "- First section: grid letters (space-separated)\n" +
                        "- Empty line\n" +
                        "- Second section: words to find (space or comma-separated)";
        
        JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void performSpecificWordSearch(String word) {
        if (word == null || word.trim().length() < 3) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una palabra de al menos 3 letras.", "Palabra Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!wordSearcher.hasValidConfiguration()) {
            JOptionPane.showMessageDialog(this, "Por favor, cargue un archivo de tablero primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final String wordToSearch = word.trim();
        logWindow.clearLog();
        boardPanel.clearHighlights();
        wordSearcher.getGraph().resetNodes();

        // Mostrar el árbol de recorrido BFS
        logWindow.appendLog("Generando árbol de recorrido BFS para: " + wordToSearch);
        TreeVisualizer.TreeNode treeRoot = wordSearcher.findWordAndBuildTree(wordToSearch);
        treeDisplayFrame.displayTree(treeRoot);
        treeDisplayFrame.setVisible(true);

        SwingWorker<WordPath, Void> worker = new SwingWorker<>() {
            @Override
            protected WordPath doInBackground() throws Exception {
                controlPanel.setSearchInProgress(true);
                return wordSearcher.findWord(wordToSearch, "DFS");
            }

            @Override
            protected void done() {
                try {
                    WordPath path = get();
                    if (path != null) {
                        boardPanel.highlightPath(path, Color.MAGENTA);
                        if (!wordSearcher.getDictionary().containsWord(wordToSearch)) {
                            wordSearcher.getDictionary().addWord(wordToSearch);
                            controlPanel.updateWordList(new ArrayList<>(wordSearcher.getDictionary().getAllWords()));
                        }
                        JOptionPane.showMessageDialog(MainFrame.this, "¡Palabra '" + wordToSearch + "' encontrada!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Palabra '" + wordToSearch + "' no encontrada.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error durante la búsqueda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    controlPanel.setSearchInProgress(false);
                }
            }
        };
        worker.execute();
    }
    
    public LogWindow getLogWindow() {
        return logWindow;
    } 
}
