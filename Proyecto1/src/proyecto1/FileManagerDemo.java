/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;
import java.io.IOException;


/**
 *
 * @author jesus rodriguez
 */
public class FileManagerDemo {
    private static void printGrid(char[][] grid) {
           for (char[] row : grid) {
               for (char c : row) {
                   System.out.print(c + " ");
               }
               System.out.println();
           }
       }

       public static void main(String[] args) {
           if (args.length == 0) {
               System.out.println("Uso: java FileManagerDemo <ruta-archivo>");
               return;
           }

           String path = args[0];
           try {
               FileManager.GridData data = FileManager.loadGridFromFile(path);

               System.out.println("=== GRID ===");
               printGrid(data.grid);

               System.out.println("\n=== WORDS ===");
               data.words.forEach(System.out::println);

               System.out.println("\nCarga completada sin errores ✔");
           } catch (IOException e) {
               System.err.println("Error al cargar el archivo:");
               e.printStackTrace();
           }
       }
    
    
}
