/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author jesus rodriguez
 */
public class FileManager {
    public static class GridData {
        public char[][] grid;
        public List<String> words;
        
        public GridData(char[][] grid, List<String> words) {
            this.grid = grid;
            this.words = words;
        }
    }
    
    public static GridData loadGridFromFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        
        String content = contentBuilder.toString();
        List<String> words = new ArrayList<>();
        try {
            String dicContent = content.split("dic\n")[1].split("/dic")[0];
            String[] wordLines = dicContent.trim().split("\n");
            for (String word : wordLines) {
                if (!word.trim().isEmpty()) {
                    words.add(word.trim().toUpperCase());
                }
            }
        } catch (Exception e) {
            throw new IOException("Formato de diccionario (<dic>) inválido o no encontrado.", e);
        }

        char[][] grid;
        try {
            String tabContent = content.split("tab\n")[1].split("/tab")[0];
            String[] letters = tabContent.trim().split(",");
            if (letters.length != 16) {
                throw new IOException("El tablero debe contener exactamente 16 letras (4x4).");
            }
            grid = new char[4][4];
            for (int i = 0; i < 16; i++) {
                grid[i / 4][i % 4] = letters[i].trim().toUpperCase().charAt(0);
            }
        } catch (Exception e) {
            throw new IOException("Formato de tablero (<tab>) inválido o no encontrado.", e);
        }
        
        return new GridData(grid, words);
    }
    
    public static List<String> loadWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] wordsInLine = line.split("[,\\s]+");
                    for (String word : wordsInLine) {
                        if (!word.trim().isEmpty()) {
                            words.add(word.trim().toUpperCase());
                        }
                    }
                }
            }
        }
        
        return words;
    }
    
    public static void saveResultsToFile(String filename, String results) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(results);
        }
    }
    
    public static String createSampleInputFile(String filename) {
        String sampleContent = 
            "A B C D E\n" +
            "F G H I J\n" +
            "K L M N O\n" +
            "P Q R S T\n" +
            "U V W X Y\n" +
            "\n" +
            "HELLO WORLD JAVA CODE SEARCH";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(sampleContent);
            return "Sample file created successfully at: " + filename;
        } catch (IOException e) {
            return "Error creating sample file: " + e.getMessage();
        }
    }
    
}
