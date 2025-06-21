/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Handles file input/output operations for loading grids and word dictionaries.
 * 
 * <p>This utility class provides static methods to manage data persistence
 * for the word search system. It primarily handles:</p>
 * <ul>
 *   <li>Loading letter grids from files with specific format</li>
 *   <li>Processing word dictionaries from various formats</li>
 *   <li>Saving search results to output files</li>
 *   <li>Generating sample files for testing</li>
 * </ul>
 * 
 * <p><strong>Expected File Format:</strong></p>
 * <pre>
 * &lt;tab&gt;
 * A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P
 * &lt;/tab&gt;
 * &lt;dic&gt;
 * PALABRA1
 * PALABRA2
 * PALABRA3
 * &lt;/dic&gt;
 * </pre>
 * 
 * @author Sistema de Gestión de Archivos
 * @version 1.8
 * @since 1.0
 */
public class FileManager {
    /**
     * Data structure that encapsulates loaded grid information.
     * 
     * <p>This inner class combines both the character grid and
     * the associated word list, facilitating data transport
     * from file loading methods.</p>
     * 
     * @since 1.0
     */
public static class GridData {
    /** Two-dimensional array representing the character grid */
    public char[][] grid;
    
    /** List of words extracted from the file dictionary */
    public List<String> words;
    
    /**
     * Constructor that initializes the structure with provided data.
     * 
     * @param puzzleGrid the puzzle's character grid
     * @param wordList the dictionary word list
     */
    public GridData(char[][] puzzleGrid, List<String> wordList) {
        this.grid = puzzleGrid;
        this.words = wordList;
    }
}
    
    /**
     * Loads a complete grid from a file with specific format.
     * 
     * <p>This method processes files containing both the letter grid
     * and word dictionary in a structured format. The grid
     * must be defined between &lt;tab&gt; and &lt;/tab&gt.
     * 
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>The grid must contain exactly 16 letters (4x4)</li>
     *   <li>Letters must be comma-separated</li>
     *   <li>Each dictionary word must be on a separate line</li>
     * </ul>
     * 
     * @param archivoRuta complete path of the file to load
     * @return GridData containing the grid and extracted words
     * @throws IOException if there are file reading problems or invalid format
     * @throws IllegalArgumentException if file doesn't contain expected format
     * 
     * @see #loadWordsFromFile(String)
     */
public static GridData loadGridFromFile(String archivoRuta) throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader fileReader = new BufferedReader(new FileReader(archivoRuta))) {
        String currentLine;
        while ((currentLine = fileReader.readLine()) != null) {
            contentBuilder.append(currentLine).append("\n");
        }
    }
    
    String fileContent = contentBuilder.toString();
    
    // Extraer palabras del diccionario
    List<String> palabrasLista = new ArrayList<>();
    try {
        String diccionarioContent = fileContent.split("dic\n")[1].split("/dic")[0];
        String[] lineasPalabras = diccionarioContent.trim().split("\n");
        for (String palabra : lineasPalabras) {
            if (!palabra.trim().isEmpty()) {
                palabrasLista.add(palabra.trim().toUpperCase());
            }
        }
    } catch (Exception e) {
        throw new IOException("Formato de diccionario (<dic>) inválido o no encontrado.", e);
    }

    // Extraer y procesar el tablero
    char[][] puzzleGrid;
    try {
        String tableroContent = fileContent.split("tab\n")[1].split("/tab")[0];
        String[] letrasArray = tableroContent.trim().split(",");
        if (letrasArray.length != 16) {
            throw new IOException("El tablero debe contener exactamente 16 letras (4x4).");
        }
        puzzleGrid = new char[4][4];
        for (int i = 0; i < 16; i++) {
            puzzleGrid[i / 4][i % 4] = letrasArray[i].trim().toUpperCase().charAt(0);
        }
    } catch (Exception e) {
        throw new IOException("Formato de tablero (<tab>) inválido o no encontrado.", e);
    }
    
    return new GridData(puzzleGrid, palabrasLista);
}
    /**
 * Loads only a word list from a plain text file.
 * 
 * <p>This method is more flexible than {@link #loadGridFromFile} since
 * it can process files with different word separation formats.
 * Words can be separated by spaces, commas, or in different lines.</p>
 * 
 * <p><strong>Supported formats:</strong></p>
 * <ul>
 *   <li>One word per line</li>
 *   <li>Multiple words separated by spaces</li>
 *   <li>Words separated by commas</li>
 *   <li>Combinations of the above</li>
 * </ul>
 * 
 * @param archivoRuta path of the file containing the words
 * @return List&lt;String&gt; with all found words (in uppercase)
 * @throws IOException if there are problems accessing the file
 * 
 * @see #loadGridFromFile(String)
 */
public static List<String> loadWordsFromFile(String archivoRuta) throws IOException {
    List<String> listaPalabras = new ArrayList<>();
    
    try (BufferedReader fileReader = new BufferedReader(new FileReader(archivoRuta))) {
        String currentLine;
        while ((currentLine = fileReader.readLine()) != null) {
            currentLine = currentLine.trim();
            if (!currentLine.isEmpty()) {
                String[] palabrasEnLinea = currentLine.split("[,\\s]+");
                for (String palabra : palabrasEnLinea) {
                    if (!palabra.trim().isEmpty()) {
                        listaPalabras.add(palabra.trim().toUpperCase());
                    }
                }
            }
        }
    }
    
    return listaPalabras;
}

/**
 * Saves search results to a text file.
 * 
 * <p>Useful for preserving results of complex searches or
 * generating reports that can be analyzed later.</p>
 * 
 * @param archivoRuta path where the results file will be saved
 * @param resultados results content in string format
 * @throws IOException if there are problems writing the file
 */
public static void saveResultsToFile(String archivoRuta, String resultados) throws IOException {
    try (PrintWriter fileWriter = new PrintWriter(new FileWriter(archivoRuta))) {
        fileWriter.println(resultados);
    }
}

/**
 * Generates a sample input file for system testing.
 * 
 * <p>This method automatically creates a file with valid format
 * that can be used to test the word search system functionalities
 * without needing to manually create the file.</p>
 * 
 * <p>The generated file includes:</p>
 * <ul>
 *   <li>A 5x5 grid with alphabet letters</li>
 *   <li>A basic set of English words</li>
 *   <li>Format compatible with {@link #loadGridFromFile}</li>
 * </ul>
 * 
 * @param nombreArchivo name and path where to create the sample file
 * @return String with a message indicating the operation result
 */
public static String createSampleInputFile(String nombreArchivo) {
    String contenidoEjemplo = 
        "A B C D E\n" +
        "F G H I J\n" +
        "K L M N O\n" +
        "P Q R S T\n" +
        "U V W X Y\n" +
        "\n" +
        "HELLO WORLD JAVA CODE SEARCH";
    
    try (PrintWriter fileWriter = new PrintWriter(new FileWriter(nombreArchivo))) {
        fileWriter.println(contenidoEjemplo);
        return "Archivo de ejemplo creado exitosamente en: " + nombreArchivo;
    } catch (IOException e) {
        return "Error al crear archivo de ejemplo: " + e.getMessage();
    }
  }
}
