/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pract1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author usuario
 */
public class Pract1 extends Application {
    static int eleccion = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean salir = false;
        Scanner keyboard = new Scanner(System.in);
        /*
         * while(!salir){
         * System.out.println("Elige el algoritmo a probar:");
         * System.out.println("    1.Exhaustivo");
         * System.out.println("    2.Exhaustivo con poda");
         * System.out.println("    3.Divide y venceras");
         * System.out.println("    4.Divide y venceras con mejora");
         * try {
         * eleccion = keyboard.nextInt();
         * } catch (Exception e) {
         *
         * e.printStackTrace();
         * }
         * 
         * launch();
         * 
         * }
         */
        launch();

        /*
         * ArrayList<Punto> dataset =
         * //Algoritmos alg = new Algoritmos();
         * 
         * ParPuntos Solucion1 = Algoritmos.Exhaustivo(dataset);
         * ParPuntos Solucion2 =Algoritmos.ExhaustivoPoda(dataset);
         * ParPuntos Solucion3 =Algoritmos.DyV(dataset);
         * 
         * System.out.println(Solucion1);
         * System.out.println(Solucion2);
         * System.out.println(Solucion3);
         */

    }

    @Override
    public void start(Stage stage) {
        File myObj = new File("berlin52.tsp");

        Lector prueba = new Lector(myObj);
        ArrayList<Punto> puntosDataset = prueba.LeePuntos();
        System.out.println("Exhaustivo "+Algoritmos.Exhaustivo(puntosDataset).toString());
        System.out.println("Poda "+Algoritmos.ExhaustivoPoda(puntosDataset).toString());
        System.out.println("DyV "+Algoritmos.DyV(puntosDataset).toString());
        //ParPuntos solucion = Algoritmos.Exhaustivo(puntosDataset).distMin;
        
        //crearGrafica(puntosDataset, solucion,stage);

        stage.show();
    }

    public void crearGrafica(ArrayList<Punto> puntosDataset,ParPuntos solucion, Stage stage){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setCreateSymbols(true); // mostrar puntos

        XYChart.Series<Number, Number> puntos = new XYChart.Series<>();
        for (Punto punto : puntosDataset) {
            puntos.getData().add(new XYChart.Data<>(punto.getX(), punto.getY()));
        }
        chart.getData().add(puntos);
        // Serie que será la línea entre los dos puntos más cercanos
        
        Punto p1 = solucion.getP1();
        Punto p2 = solucion.getP2();

        System.out.println("SOLUCION: " + p1 + " " + p2);
        XYChart.Series<Number, Number> linea = new XYChart.Series<>();
        linea.getData().add(new XYChart.Data<>(p1.getX(), p1.getY()));
        linea.getData().add(new XYChart.Data<>(p2.getX(), p2.getY()));
        chart.getData().add(linea);

        Scene scene = new Scene(chart, 600, 400);
        stage.setScene(scene);
        

        // Estilo: ocultar la línea de la serie de puntos y colorear la serie de la
        // línea
        Platform.runLater(() -> {
            // primera serie = puntos -> ocultar trazo (solo símbolos)
            chart.lookupAll(".series0.chart-series-line").forEach(n -> n.setStyle("-fx-stroke: transparent;"));
            // segunda serie = línea -> hacerla roja y gruesa
            chart.lookupAll(".series1.chart-series-line")
                    .forEach(n -> n.setStyle("-fx-stroke: red; -fx-stroke-width: 2;"));
        });

    }

}
