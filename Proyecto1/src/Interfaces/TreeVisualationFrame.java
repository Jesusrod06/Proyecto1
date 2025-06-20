/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author jesus rodriguez
 */
public class TreeVisualationFrame extends JFrame{
    private JTextArea treeArea;
    private JScrollPane scrollPane;
    
    public TreeVisualationFrame() {
        initializeComponents();
        setupLayout();
        setWindowProperties();
    }
    
    private void initializeComponents() {
        treeArea = new JTextArea();
        treeArea.setEditable(false);
        treeArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        treeArea.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(treeArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Search Tree Visualization", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add close button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> setVisible(false));
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setWindowProperties() {
        setTitle("Search Tree Visualization");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public void displayTree(String treeString) {
        treeArea.setText(treeString);
        treeArea.setCaretPosition(0);
    }
    
    public void clear() {
        treeArea.setText("No tree data available.\n\nPerform a search to see the tree visualization here.");
    }
}
