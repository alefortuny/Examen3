package examen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String archivoCSV = "C:/Users/arfor/Downloads/archive/emails.csv";
        String outputFile = "C:/Users/arfor/Downloads/archive/173913.txt";
        int startRow = 913;
        int numRows = 49;

        BufferedReader lector = null;
        String linea = "";
        String separador = ",";
        Map<String, Integer> contador = new HashMap<>();

        String[] columnNames = null; // declare an array to store the column names

        try {
            lector = new BufferedReader(new FileReader(archivoCSV));
            int filaActual = 0;
            while ((linea = lector.readLine()) != null && filaActual < startRow + numRows) {
                if (filaActual == 0) { // if it's the first row
                    columnNames = linea.split(separador); // split the column names
                } else if (filaActual >= startRow) {
                    String[] columna = linea.split(separador);
                    for (int i = 2; i < columna.length; i++) {
                        String word = columnNames[i];
                        int count = Integer.parseInt(columna[i]);
                        contador.put(word, contador.getOrDefault(word, 0) + count);
                    }
                }
                filaActual++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            FileWriter writer = new FileWriter(outputFile, false);
            for (String word : contador.keySet()) {
                String outputLine = word + ", " + contador.get(word) + "\n";
                writer.write(outputLine);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}