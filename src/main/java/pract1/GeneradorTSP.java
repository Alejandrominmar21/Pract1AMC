package pract1;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Clase para generar archivos de formato TSP (Traveling Salesman Problem) con
 * puntos aleatorios.
 * Permite crear conjuntos de datos de prueba con diferentes distribuciones de
 * puntos.
 */
public class GeneradorTSP {

    /**
     * Crea un archivo TSP con n puntos aleatorios.
     * El archivo generado sigue el formato estándar TSP con coordenadas EUC_2D.
     * 
     * @param n      Número de puntos a generar
     * @param mismax Si es true, genera puntos con una distribución específica donde
     *               x=1 y y varía según una fórmula especial.
     *               Si es false, genera puntos con coordenadas aleatorias más
     *               uniformemente distribuidas.
     */
    public static void crearArchivoTSP(int n, boolean mismax) {
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("0.0000000000");
        String nombreArchivo = "dataset" + n + ".tsp";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("NAME: " + nombreArchivo + "\n");
            writer.write("TYPE: TSP\n");
            writer.write("COMMENT: " + n + " random points\n");
            writer.write("DIMENSION: " + n + "\n");
            writer.write("EDGE_WEIGHT_TYPE: EUC_2D\n");
            writer.write("NODE_COORD_SECTION\n");

            for (int i = 0; i < n; i++) {
                double x, y;

                if (mismax) {
                    double aux1 = rand.nextInt(1000) + 7;
                    y = aux1 / ((double) i + 1 + i * 0.100);
                    int num = rand.nextInt(3);
                    y += ((i % 500) - num * (rand.nextInt(100)));
                    x = 1;
                } else {
                    int num = rand.nextInt(4000) + 1;
                    int den = rand.nextInt(11) + 7;
                    x = num / ((double) den + 0.37);
                    y = (rand.nextInt(4000) + 1) / ((double) (rand.nextInt(11) + 7) + 0.37);
                }

                writer.write((i + 1) + " " + df.format(x) + " " + df.format(y) + "\n");
            }

            writer.write("EOF\n");
            System.out.println("Archivo generado correctamente: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
