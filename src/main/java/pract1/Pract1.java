/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pract1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
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
        while(!salir){
            System.out.println("Elige el algoritmo a probar:");
            System.out.println("    1.Exhaustivo");
            System.out.println("    2.Exhaustivo con poda");
            System.out.println("    3.Divide y venceras");
            System.out.println("    4.Divide y venceras con mejora");
            try {
                eleccion = keyboard.nextInt();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            launch(); 

        }
        
      
        /* 
          ArrayList<Punto> dataset = 
        //Algoritmos alg = new Algoritmos();      
      
        ParPuntos Solucion1 = Algoritmos.Exhaustivo(dataset);
        ParPuntos Solucion2 =Algoritmos.ExhaustivoPoda(dataset);
        ParPuntos Solucion3 =Algoritmos.DyV(dataset);

        System.out.println(Solucion1);
        System.out.println(Solucion2);
        System.out.println(Solucion3);
   */
        
    }
      @Override
    public void start(Stage stage) {
        File myObj = new File("berlin52.tsp");
        
        Lector prueba = new Lector (myObj);
        ArrayList<Punto> puntosprueba =prueba.LeePuntos();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> puntos = new XYChart.Series<>();
        for(Punto punto: puntosprueba ){
            puntos.getData().add(new XYChart.Data<>(punto.getX(), punto.getY()));
        }
        chart.getData().add(puntos);

        stage.setScene(new Scene(chart, 600, 400));
        stage.show();
    }
    
}
