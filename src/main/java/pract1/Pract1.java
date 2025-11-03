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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Interfaz gráfica principal para el análisis de algoritmos de búsqueda de puntos más cercanos.
 * Esta clase implementa una aplicación JavaFX que permite:
 * - Comparar diferentes estrategias de búsqueda
 * - Visualizar resultados en tablas y gráficos
 * - Analizar rendimiento con diferentes tamaños de datos
 * - Estudiar cada estrategia individualmente
 * 
 * @author usuario
 */
public class Pract1 extends Application {

    /**
     * Punto de entrada principal de la aplicación.
     * Inicia la interfaz gráfica JavaFX.
     * 
     * @param args argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {

        launch();

    }

    /**
     * Inicializa y configura la ventana principal de la aplicación.
     * 
     * @param stage El escenario principal de JavaFX donde se mostrará la aplicación
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(crearMenu(stage));
        stage.setTitle("Análisis de Algoritmos");
        stage.show();
    }

    /**
     * Crea y configura el menú principal de la aplicación.
     * 
     * @param stage Ventana principal donde se mostrará el menú
     * @return Scene Escena JavaFX con el menú principal
     */
    private Scene crearMenu(Stage stage) {
        File myObj = new File("berlin52.tsp");

        Lector prueba = new Lector(myObj);
        ArrayList<Punto> puntos = prueba.LeePuntos();

        Button comparar4Est = new Button("Comparar todas las estrategias (.tsp aleatorio)");
        Button comparar4EstPeor = new Button("Comparar todas las estrategias en caso peor(.tsp aleatorio)");
        Button comparar2Est = new Button("Comparar dos estrategias (.tsp aleatorio)");
        Button comparar2EstPeor = new Button("Comparar dos estrategias en caso peor(.tsp aleatorio)");
        Button comprobarEstrategias = new Button("Comprobar todas las estrategias (dataset cargado)");
        Button estudiarEstrategia = new Button("Estudiar una estrategia (dataset cargado)");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic
        comparar4Est.setOnAction(e -> comparar4(stage,false));
        comparar4EstPeor.setOnAction(e -> comparar4(stage,true));
        comparar2Est.setOnAction(e -> compararDos(stage,false));
        comparar2EstPeor.setOnAction(e -> compararDos(stage,true));
        comprobarEstrategias.setOnAction(e -> compararEstrategias(stage, puntos));
        estudiarEstrategia.setOnAction(e -> stage.setScene(estudiarEstrategia(stage, puntos)));

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox menu = new VBox(15, comparar4Est,comparar4EstPeor, comparar2Est,comparar2EstPeor, comprobarEstrategias, estudiarEstrategia, btnSalir);
        menu.setAlignment(Pos.CENTER);

        return new Scene(menu, 1200, 800);
    }

    /**
     * Realiza una comparación completa de las cuatro estrategias implementadas.
     * Genera datasets de diferentes tamaños y ejecuta todas las estrategias sobre ellos.
     * 
     * @param stage Ventana donde se mostrarán los resultados
     */
    public void comparar4(Stage stage,boolean casoPeor) {
        int[] Tallas = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };
        Solucion ResultadosExhaustivo[] = new Solucion[Tallas.length];
        Solucion ResultadosExhaustivoPoda[] = new Solucion[Tallas.length];
        Solucion ResultadosDyV[] = new Solucion[Tallas.length];
        Solucion ResultadosDyVMejorado[] = new Solucion[Tallas.length];

        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i], casoPeor);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");

            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            ResultadosExhaustivo[i] = Algoritmos.Exhaustivo(puntosDataset);
            ResultadosExhaustivoPoda[i] = Algoritmos.ExhaustivoPoda(puntosDataset);
            ResultadosDyV[i] = Algoritmos.DyV(puntosDataset);
            ResultadosDyVMejorado[i] = Algoritmos.DyVMejorado(puntosDataset);

            System.out.println(ResultadosExhaustivo[i].toString());
            System.out.println(ResultadosExhaustivoPoda[i].toString());
            System.out.println(ResultadosDyV[i].toString());
            System.out.println(ResultadosDyVMejorado[i].toString());

        }
        comparar4Tabla(stage, Tallas, ResultadosExhaustivo, ResultadosExhaustivoPoda, ResultadosDyV,
                ResultadosDyVMejorado);
    }

    /**
     * Crea una gráfica que muestra todos los puntos y resalta el par de puntos más cercanos.
     * 
     * @param puntosDataset Lista de todos los puntos a mostrar
     * @param solucion Par de puntos más cercanos a resaltar
     * @param stage Ventana donde se mostrará la gráfica
     */
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

    /**
     * Permite al usuario seleccionar y comparar dos estrategias diferentes.
     * Muestra un menú con desplegables para seleccionar las estrategias a comparar.
     * 
     * @param stage Ventana donde se mostrará la interfaz de comparación
     */
    private void compararDos(Stage stage, boolean casopeor) {
        Label titulo = new Label("Comparar dos estrategias");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Lista de estrategias
        ObservableList<String> estrategias = FXCollections.observableArrayList(
                "Exhaustivo",
                "ExhaustivoPoda",
                "Divide y Vencerás",
                "DyV Mejorado");

        // Desplegables
        ComboBox<String> combo1 = new ComboBox<>(estrategias);
        combo1.setPromptText("Estrategia 1");

        ComboBox<String> combo2 = new ComboBox<>(estrategias);
        combo2.setPromptText("Estrategia 2");

        Button compararBtn = new Button("Comparar");
        compararBtn.setOnAction(e -> {
            String e1 = combo1.getValue();
            String e2 = combo2.getValue();

            if (e1 == null || e2 == null || e1.equals(e2)) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Selecciona dos estrategias distintas.");
                alerta.show();
                return;
            }

            // Aquí llamas a los métodos de comparación o resultados
            mostrarComparacion2(stage, e1, e2,casopeor);
        });

        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, combo1, combo2, compararBtn, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 1200, 800));
    }
    

    
    /**
     * Ejecuta la comparación entre dos estrategias seleccionadas.
     * Genera datasets de diferentes tamaños y aplica ambas estrategias.
     * 
     * @param stage Ventana donde se mostrarán los resultados
     * @param e1 Nombre de la primera estrategia
     * @param e2 Nombre de la segunda estrategia
     */
    public void mostrarComparacion2(Stage stage, String e1, String e2, boolean  casopeor) {
        int[] Tallas = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
        Solucion Estrategia1[] = new Solucion[Tallas.length];
        Solucion Estrategia2[] = new Solucion[Tallas.length];

        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i], casopeor);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");

            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            switch (e1) {
                case "Exhaustivo":
                    Estrategia1[i] = Algoritmos.Exhaustivo(puntosDataset);
                    break;
                case "ExhaustivoPoda":
                    Estrategia1[i] = Algoritmos.ExhaustivoPoda(puntosDataset);
                    break;
                case "Divide y Vencerás":
                    Estrategia1[i] = Algoritmos.DyV(puntosDataset);
                    break;
                case "DyV Mejorado":
                    Estrategia1[i] = Algoritmos.DyVMejorado(puntosDataset);
                    break;
                default:
                    throw new AssertionError();
            }
            switch (e2) {
                case "Exhaustivo":
                    Estrategia2[i] = Algoritmos.Exhaustivo(puntosDataset);
                    break;
                case "ExhaustivoPoda":
                    Estrategia2[i] = Algoritmos.ExhaustivoPoda(puntosDataset);
                    break;
                case "Divide y Vencerás":
                    Estrategia2[i] = Algoritmos.DyV(puntosDataset);
                    break;
                case "DyV Mejorado":
                    Estrategia2[i] = Algoritmos.DyVMejorado(puntosDataset);
                    break;
                default:
                    throw new AssertionError();
            }

        }

        mostrarComparacion2(stage, e1, e2, Tallas, Estrategia1, Estrategia2);
    }

    private void mostrarComparacion2(Stage stage, String e1, String e2,
            int[] Tallas, Solucion[] Estrategia1, Solucion[] Estrategia2) {

        Label titulo = new Label("Comparativa: " + e1 + " vs " + e2);
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Integer> tabla = new TableView<>();

        TableColumn<Integer, Integer> colTalla = new TableColumn<>("Talla");
        colTalla.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue()).asObject());

        TableColumn<Integer, String> colTiempo1 = new TableColumn<>(e1 + " (ms)");
        colTiempo1.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("%.6f", Estrategia1[indexOf(Tallas, d.getValue())].tiempo)));

        TableColumn<Integer, String> colTiempo2 = new TableColumn<>(e2 + " (ms)");
        colTiempo2.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("%.6f", Estrategia2[indexOf(Tallas, d.getValue())].tiempo)));

        TableColumn<Integer, Integer> colDist1 = new TableColumn<>("Distancias " + e1);
        colDist1.setCellValueFactory(d -> new SimpleIntegerProperty(
                Estrategia1[indexOf(Tallas, d.getValue())].distCalculadas).asObject());

        TableColumn<Integer, Integer> colDist2 = new TableColumn<>("Distancias " + e2);
        colDist2.setCellValueFactory(d -> new SimpleIntegerProperty(
                Estrategia2[indexOf(Tallas, d.getValue())].distCalculadas).asObject());

        tabla.getColumns().addAll(colTalla, colTiempo1, colTiempo2, colDist1, colDist2);
        tabla.getItems().addAll(Arrays.stream(Tallas).boxed().toList());

        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 950, 400));
    }

    /**
     * Compara todas las estrategias implementadas usando un conjunto de puntos dado.
     * Muestra una tabla con los resultados detallados de cada estrategia.
     * 
     * @param stage Ventana donde se mostrarán los resultados
     * @param puntos Conjunto de puntos sobre el que se ejecutarán las estrategias
     */
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
                Map.entry("DyV Mejorado", s4));

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

        stage.setScene(new Scene(layout, 1200, 800));
    }

    /**
     * Crea una interfaz para estudiar cada estrategia individualmente.
     * Permite seleccionar una estrategia y visualizar gráficamente sus resultados.
     * 
     * @param stage Ventana principal
     * @param puntos Conjunto de puntos a analizar
     * @return Scene Escena con los botones para seleccionar la estrategia
     */
    private Scene estudiarEstrategia(Stage stage, ArrayList<Punto> puntos) {
        Button exhaustivo = new Button("Exhaustivo");
        Button exhaustivoPoda = new Button("Exhaustivo poda");
        Button dyv = new Button("Divide y venceras");
        Button dyv2 = new Button("Divide y venceras mejorado");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic
        exhaustivo.setOnAction(e -> crearGrafica(puntos, Algoritmos.Exhaustivo(puntos).distMin, stage));
        exhaustivoPoda.setOnAction(e -> crearGrafica(puntos, Algoritmos.ExhaustivoPoda(puntos).distMin, stage));
        dyv.setOnAction(e -> crearGrafica(puntos, Algoritmos.DyV(puntos).distMin, stage));
        dyv2.setOnAction(e -> crearGrafica(puntos, Algoritmos.DyVMejorado(puntos).distMin, stage));

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox estrategias = new VBox(15, exhaustivo, exhaustivoPoda, dyv, dyv2, btnSalir);
        estrategias.setAlignment(Pos.CENTER);

        return new Scene(estrategias, 1200, 800);
    }

    /**
     * Muestra una tabla comparativa con los resultados de las cuatro estrategias.
     * 
     * @param stage Ventana donde se mostrará la tabla
     * @param Tallas Array con los diferentes tamaños de datasets analizados
     * @param exhaustivo Resultados del algoritmo exhaustivo
     * @param poda Resultados del algoritmo exhaustivo con poda
     * @param dyv Resultados del algoritmo divide y vencerás
     * @param dyvMejorado Resultados del algoritmo divide y vencerás mejorado
     */
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

        stage.setScene(new Scene(layout, 1200, 800));
    }

    /**
     * Método auxiliar para encontrar el índice de un valor en un array.
     * 
     * @param arr Array donde buscar
     * @param val Valor a buscar
     * @return índice del valor en el array, o -1 si no se encuentra
     */
    private int indexOf(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == val)
                return i;
        return -1;
    }

}
