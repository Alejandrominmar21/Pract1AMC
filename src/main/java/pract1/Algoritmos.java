/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pract1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Clase que implementa diferentes algoritmos para encontrar el par de puntos
 * más cercanos.
 * Incluye implementaciones de algoritmos exhaustivos y divide y vencerás.
 * 
 * @author usuario
 */
public class Algoritmos {

    /**
     * Calcula la distancia euclidiana entre dos puntos en un plano 2D.
     * 
     * @param p1 Primer punto
     * @param p2 Segundo punto
     * @return La distancia euclidiana entre los dos puntos
     */
    public static double distancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    /**
     * Constructor por defecto de la clase Algoritmos.
     */
    public Algoritmos() {
    }

    /**
     * Implementa el algoritmo exhaustivo para encontrar el par de puntos más
     * cercanos.
     * Compara todas las posibles parejas de puntos y encuentra la distancia mínima.
     * Complejidad temporal: O(n²)
     * 
     * @param puntos Lista de puntos a analizar
     * @return Un objeto Solucion con el par de puntos más cercanos, tiempo de
     *         ejecución y número de distancias calculadas
     */
    public static Solucion Exhaustivo(ArrayList<Punto> puntos) {
        long startTime = System.nanoTime();
        double distanciaMinima = distancia(puntos.get(0), puntos.get(1));
        ParPuntos puntosDistMin = new ParPuntos(puntos.get(0), puntos.get(1));
        int distCalculadas = 0;

        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                distCalculadas++;
                double dist = distancia(puntos.get(i), puntos.get(j));
                if (dist < distanciaMinima) {
                    distanciaMinima = dist;
                    puntosDistMin = new ParPuntos(puntos.get(i), puntos.get(j));
                }
                // System.out.println("i: "+puntos.get(i).toString()+" j:
                // "+puntos.get(j).toString()+ " distancia:" + dist );

            }
        }
        // System.out.println("min: " + distanciaMinima + "Puntos: " + puntosDistMin);
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime);

        return new Solucion(puntosDistMin, ((float) executionTime) / 1000000, distCalculadas);// tiempo en ms

    }

    /**
     * Implementa una versión mejorada del algoritmo exhaustivo con poda.
     * Ordena los puntos por coordenada X y utiliza esta ordenación para podar
     * búsquedas innecesarias.
     * 
     * @param puntos Lista de puntos a analizar
     * @return Un objeto Solucion con el par de puntos más cercanos, tiempo de
     *         ejecución y número de distancias calculadas
     */
    public static Solucion ExhaustivoPoda(ArrayList<Punto> puntos) {
        long startTime = System.nanoTime();
        int distCalculadas = 0;
        if (puntos.size() < 2) {
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime);
            return new Solucion(null, executionTime, 0);
        }
        ParPuntos puntosDistMin = new ParPuntos(puntos.get(0), puntos.get(1));
        ArrayList<Punto> aux = new ArrayList<>(puntos);
        aux.sort(Comparator.comparingDouble(Punto::getX));

        double minDist = distancia(aux.get(0), aux.get(1));
        // Punto a= aux.get(0), b= aux.get(1);

        for (int i = 0; i < aux.size() - i; i++) {
            for (int j = i + 1; j < aux.size(); j++) {
                distCalculadas++;
                double dx = aux.get(j).getX() - aux.get(i).getX();
                if (dx >= minDist) {// poda por X
                    break;
                }

                double d = distancia(aux.get(i), aux.get(j));
                if (d < minDist) {
                    minDist = d;
                    // a= aux.get(i);
                    // b= aux.get(j);
                    puntosDistMin = new ParPuntos(aux.get(i), aux.get(j));
                }
            }
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime);
        return new Solucion(puntosDistMin, ((float) executionTime) / 1000000, distCalculadas);

    }

    /**
     * Implementa el algoritmo de Divide y Vencerás para encontrar el par de puntos
     * más cercanos.
     * Divide el conjunto de puntos en dos mitades, resuelve recursivamente y
     * combina las soluciones.
     * Complejidad temporal: O(n log n)
     * 
     * @param puntos Lista de puntos a analizar
     * @return Un objeto Solucion con el par de puntos más cercanos, tiempo de
     *         ejecución y número de distancias calculadas
     */
    public static Solucion DyV(ArrayList<Punto> puntos) {
        if (puntos == null || puntos.size() < 2) {
            return new Solucion(null, 0, 0);
        }

        Punto[] arr = puntos.toArray(new Punto[0]);
        Arrays.sort(arr, Comparator.comparingDouble(Punto::getX));
        ArrayList<Punto> ordenadosX = new ArrayList<>(Arrays.asList(arr));

        return dyvRec(ordenadosX, 0, 0);
    }

    public static Solucion dyvRec(ArrayList<Punto> ordenadosX, long Tiempo, int iteraciones) {
        int n = ordenadosX.size();
        int distCalculadas = 0;

        if (n <= 3) {
            return Exhaustivo(new ArrayList<>(ordenadosX));
        }

        int mid = n / 2;

        ArrayList<Punto> izq = new ArrayList<>(ordenadosX.subList(0, mid));
        ArrayList<Punto> der = new ArrayList<>(ordenadosX.subList(mid, n));
        double xm = der.get(0).getX();

        long startTime = System.nanoTime();
        Solucion solIzq = dyvRec(izq, 0, 0);
        Solucion solDer = dyvRec(der, 0, 0);
        distCalculadas = distCalculadas + solIzq.distCalculadas + solDer.distCalculadas;

        Double dIzq = distancia(solIzq.distMin.getP1(), solIzq.distMin.getP2());
        double dDer = distancia(solDer.distMin.getP1(), solDer.distMin.getP2());
        double d = Math.min(dIzq, dDer);

        ParPuntos mejor;
        if (dIzq <= dDer) {
            mejor = solIzq.distMin;
        } else {
            mejor = solDer.distMin;
        }

        ArrayList<Punto> bandaIzq = new ArrayList<>();
        for (Punto p : izq)
            if (Math.abs(p.getX() - xm) < d)
                bandaIzq.add(p);

        ArrayList<Punto> bandaDer = new ArrayList<>();
        for (Punto p : der)
            if (Math.abs(p.getX() - xm) < d)
                bandaDer.add(p);

        for (Punto li : bandaIzq) {
            for (Punto rd : bandaDer) {
                distCalculadas++;
                double dist = distancia(li, rd);
                if (dist < d) {
                    d = dist;
                    mejor = new ParPuntos(li, rd);
                }
            }
        }
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime);
        executionTime = (long) (executionTime + solDer.tiempo + solIzq.tiempo);
        return new Solucion(mejor, ((float) executionTime) / 1000000, distCalculadas);
    }

    /**
     * Aplica el algoritmo divide y vencerás mejorado para encontrar el par de
     * puntos más cercano.
     * Convierte la lista de puntos a un arreglo, los ordena por coordenada X y
     * llama al método recursivo.
     *
     * @param puntos Lista de puntos a analizar.
     * @return Un objeto {@link Solucion} con el par de puntos más cercano, el
     *         tiempo de ejecución y el número de distancias calculadas.
     */

    public static Solucion DyVMejorado(ArrayList<Punto> puntos) {
        if (puntos == null || puntos.size() < 2) {
            return new Solucion(null, 0, 0);
        }

        Punto[] arr = puntos.toArray(new Punto[0]);
        Arrays.sort(arr, Comparator.comparingDouble(Punto::getX));
        ArrayList<Punto> ordenadosX = new ArrayList<>(Arrays.asList(arr));

        return dyvRecMejorado(ordenadosX, 0, 0);
    }

    /**
     * Método recursivo del algoritmo divide y vencerás mejorado para encontrar el
     * par de puntos más cercano.
     * Divide el conjunto de puntos en dos mitades, calcula la solución óptima en
     * cada mitad y combina los resultados
     * considerando los puntos cercanos a la línea divisoria.
     *
     * @param ordenadosX  Lista de puntos ordenados por coordenada X.
     * @param Tiempo      Tiempo acumulado en milisegundos (se usa para mediciones
     *                    internas).
     * @param iteraciones Número de iteraciones realizadas (se usa para estadísticas
     *                    internas).
     * @return Un objeto {@link Solucion} con el par de puntos más cercano, el
     *         tiempo total y el número de distancias calculadas.
     */
    public static Solucion dyvRecMejorado(ArrayList<Punto> ordenadosX, long Tiempo, int iteraciones) {
        int n = ordenadosX.size();
        if (n <= 3) {
            return Exhaustivo(new ArrayList<>(ordenadosX));
        }

        int mid = n / 2;
        ArrayList<Punto> izq = new ArrayList<>(ordenadosX.subList(0, mid));
        ArrayList<Punto> der = new ArrayList<>(ordenadosX.subList(mid, n));
        double xm = der.get(0).getX();

        long startTime = System.nanoTime();
        Solucion solIzq = dyvRecMejorado(izq, 0, 0);
        Solucion solDer = dyvRecMejorado(der, 0, 0);

        int distCalculadas = solIzq.distCalculadas + solDer.distCalculadas;
        double dIzq = distancia(solIzq.distMin.getP1(), solIzq.distMin.getP2());
        double dDer = distancia(solDer.distMin.getP1(), solDer.distMin.getP2());
        double d = Math.min(dIzq, dDer);
        ParPuntos mejor = dIzq <= dDer ? solIzq.distMin : solDer.distMin;

        // Franja intermedia
        ArrayList<Punto> franja = new ArrayList<>();
        for (Punto p : ordenadosX) {
            if (Math.abs(p.getX() - xm) < d) {
                franja.add(p);
            }
        }

        // Ordenar por Y
        franja.sort(Comparator.comparingDouble(Punto::getY));

        // Comparar cada punto con los 11 siguientes
        for (int i = 0; i < franja.size(); i++) {
            for (int j = i + 1; j < Math.min(i + 12, franja.size()); j++) {
                distCalculadas++;
                double dist = distancia(franja.get(i), franja.get(j));
                if (dist < d) {
                    d = dist;
                    mejor = new ParPuntos(franja.get(i), franja.get(j));
                }
            }
        }

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime + (long) (solIzq.tiempo * 1000000) + (long) (solDer.tiempo * 1000000);

        return new Solucion(mejor, ((float) executionTime) / 1000000, distCalculadas);

    }

}
