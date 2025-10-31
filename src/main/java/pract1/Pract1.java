/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pract1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author usuario
 */
public class Pract1 extends Application {
    // static int eleccion = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * boolean salir = false;
         * Scanner keyboard = new Scanner(System.in);
         * 
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
        

        stage.setScene(crearMenu(stage));
        stage.setTitle("Análisis de Algoritmos");

        stage.show();
    }

    private Scene crearMenu(Stage stage) {
        File myObj = new File("berlin52.tsp");

        Lector prueba = new Lector(myObj);
        ArrayList<Punto> puntos = prueba.LeePuntos();


        Button comparar4Est = new Button("Comparar todas las estrategias (.tsp aleatorio)");
        Button cargarDataSet = new Button("");
        Button comprobarEstrategias = new Button("Comprobar todas las estrategias (dataset cargado)");
        Button estudiarEstrategia = new Button("Estudiar una estrategia (dataset cargado)");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic
        comparar4Est.setOnAction(e -> comparar4(stage));
        cargarDataSet.setOnAction(e -> comparar4(stage));
        comprobarEstrategias.setOnAction(e -> compararEstrategias(stage, puntos));
        estudiarEstrategia.setOnAction(e -> stage.setScene(estudiarEstrategia(stage, puntos)));

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox menu = new VBox(15, comparar4Est, cargarDataSet, comprobarEstrategias, estudiarEstrategia, btnSalir);
        menu.setAlignment(Pos.CENTER);

        return new Scene(menu, 1200, 800);
    }

    public void comparar4(Stage stage) {
        int[] Tallas = { 1000, 2000, 3000, 4000, 5000};
        Solucion ResultadosExhaustivo[] = new Solucion[Tallas.length];
        Solucion ResultadosExhaustivoPoda[]  = new Solucion[Tallas.length];
        Solucion ResultadosDyV[] = new Solucion[Tallas.length];
        Solucion ResultadosDyVMejorado[] = new Solucion[Tallas.length]; 

       
        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i],false);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");

            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            ResultadosExhaustivo[i] = Algoritmos.Exhaustivo(puntosDataset);
            ResultadosExhaustivoPoda[i]  = Algoritmos.ExhaustivoPoda(puntosDataset);
            ResultadosDyV[i]= Algoritmos.DyV(puntosDataset);
            ResultadosDyVMejorado[i] = Algoritmos.DyVMejorado(puntosDataset);

            
            System.out.println(ResultadosExhaustivo[i].toString());
            System.out.println(ResultadosExhaustivoPoda[i].toString());
            System.out.println(ResultadosDyV[i].toString());
            System.out.println(ResultadosDyVMejorado[i].toString());

            
        }
        comparar4Tabla(stage, Tallas, ResultadosExhaustivo, ResultadosExhaustivoPoda, ResultadosDyV, ResultadosDyVMejorado);
    }

    public void crearGrafica(ArrayList<Punto> puntosDataset, ParPuntos solucion, Stage stage) {
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

        //System.out.println("SOLUCION: " + p1 + " " + p2);
        XYChart.Series<Number, Number> linea = new XYChart.Series<>();
        linea.getData().add(new XYChart.Data<>(p1.getX(), p1.getY()));
        linea.getData().add(new XYChart.Data<>(p2.getX(), p2.getY()));
        chart.getData().add(linea);

        Scene scene = new Scene(chart, 1200, 800);
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

    private void compararEstrategias(Stage stage, ArrayList<Punto> puntos) {
        Solucion s1 = Algoritmos.Exhaustivo(puntos);
        Solucion s2 = Algoritmos.ExhaustivoPoda(puntos);
        Solucion s3 = Algoritmos.DyV(puntos);
        Solucion s4 = Algoritmos.DyVMejorado(puntos);

        // Crear etiquetas con los resultados
        Label titulo = new Label("Resultados de los Algoritmos");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        List<Map.Entry<String, Solucion>> soluciones = List.of(
                Map.entry("Exhaustivo", s1),
                Map.entry("ExhaustivoPoda", s2),
                Map.entry("DivideVenceras", s3),
                                                Map.entry("DyV Mejorado", s4)
                                               );

        TableView<Map.Entry<String, Solucion>> tabla = new TableView<>();

        TableColumn<Map.Entry<String, Solucion>, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getKey()));

        TableColumn<Map.Entry<String, Solucion>, String> colP1 = new TableColumn<>("Punto 1");
        colP1.setCellValueFactory(d -> {
            Punto p1 = d.getValue().getValue().distMin.getP1();
            return new SimpleStringProperty("(" + p1.getX() + ", " + p1.getY() + ")");
        });

        TableColumn<Map.Entry<String, Solucion>, String> colP2 = new TableColumn<>("Punto 2");
        colP2.setCellValueFactory(d -> {
            Punto p2 = d.getValue().getValue().distMin.getP2();
            return new SimpleStringProperty("(" + p2.getX() + ", " + p2.getY() + ")");
        });

        TableColumn<Map.Entry<String, Solucion>, String> colDist = new TableColumn<>("Distancia");
        colDist.setCellValueFactory(d -> {
            ParPuntos par = d.getValue().getValue().distMin;
            double distancia = par.getP1().distancia(par.getP2());
            return new SimpleStringProperty(String.format("%.8f", distancia));
        });

        TableColumn<Map.Entry<String, Solucion>, Integer> colCalc = new TableColumn<>("Calculadas");
        colCalc.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getValue().distCalculadas).asObject());

        TableColumn<Map.Entry<String, Solucion>, String> colTiempo = new TableColumn<>("Tiempo (mseg)");
        colTiempo.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("%.4f", d.getValue().getValue().tiempo)));

        tabla.getColumns().addAll(colEstrategia, colP1, colP2, colDist, colCalc, colTiempo);
        tabla.getItems().addAll(soluciones);

        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, new Label("Resultados de los Algoritmos"), tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 950, 400));
    }

    private Scene estudiarEstrategia(Stage stage, ArrayList<Punto> puntos) {
        Button exhaustivo = new Button("Exhaustivo");
        Button exhaustivoPoda = new Button("Exhaustivo poda");
        Button dyv = new Button("Divide y venceras");
        Button dyv2 = new Button("Divide y venceras ");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic
        exhaustivo.setOnAction(e -> crearGrafica(puntos, Algoritmos.Exhaustivo(puntos).distMin, stage));
        exhaustivoPoda.setOnAction(e -> crearGrafica(puntos, Algoritmos.ExhaustivoPoda(puntos).distMin, stage));
        dyv.setOnAction(e -> crearGrafica(puntos, Algoritmos.DyV(puntos).distMin, stage));
        dyv2.setOnAction(e -> stage.close());

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox estrategias = new VBox(15, exhaustivo, exhaustivoPoda, dyv, dyv2, btnSalir);
        estrategias.setAlignment(Pos.CENTER);

        return new Scene(estrategias, 1200, 800);
    }

    private void comparar4Tabla(Stage stage, int[] Tallas, 
        Solucion[] exhaustivo, Solucion[] poda, Solucion[] dyv, Solucion[] dyvMejorado) {

    Label titulo = new Label("Comparativa de Estrategias");
    titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    TableView<Integer> tabla = new TableView<>();

    TableColumn<Integer, Integer> colTalla = new TableColumn<>("Talla");
    colTalla.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue()).asObject());

    TableColumn<Integer, String> colEx = new TableColumn<>("Exhaustivo (ms)");
    colEx.setCellValueFactory(d -> new SimpleStringProperty(
            String.format("%.6f", exhaustivo[indexOf(Tallas, d.getValue())].tiempo)));

    TableColumn<Integer, String> colPoda = new TableColumn<>("ExhaustivoPoda (ms)");
    colPoda.setCellValueFactory(d -> new SimpleStringProperty(
            String.format("%.6f", poda[indexOf(Tallas, d.getValue())].tiempo)));

    TableColumn<Integer, String> colDyV = new TableColumn<>("DivideVenceras (ms)");
    colDyV.setCellValueFactory(d -> new SimpleStringProperty(
            String.format("%.6f", dyv[indexOf(Tallas, d.getValue())].tiempo)));

    TableColumn<Integer, String> colDyVMej = new TableColumn<>("DyV Mejorado (ms)");
    colDyVMej.setCellValueFactory(d -> new SimpleStringProperty(
            String.format("%.6f", dyvMejorado[indexOf(Tallas, d.getValue())].tiempo)));

    tabla.getColumns().addAll(colTalla, colEx, colPoda, colDyV, colDyVMej);
    tabla.getItems().addAll(Arrays.stream(Tallas).boxed().toList());

    Button volverBtn = new Button("Volver al menú");
    volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

    VBox layout = new VBox(15, titulo, tabla, volverBtn);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(15));

    stage.setScene(new Scene(layout, 900, 400));
}

// Método auxiliar
private int indexOf(int[] arr, int val) {
    for (int i = 0; i < arr.length; i++) if (arr[i] == val) return i;
    return -1;
}


}
