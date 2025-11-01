package pract1;

/**
 * Almacena la solución encontrada por los algoritmos de búsqueda.
 * Incluye el par de puntos más cercanos, el tiempo de ejecución y el número de
 * distancias calculadas.
 */
public class Solucion {
    /** El par de puntos con la distancia mínima encontrada */
    ParPuntos distMin;
    /** Tiempo de ejecución del algoritmo en milisegundos */
    float tiempo;
    /** Número total de distancias calculadas durante la ejecución */
    int distCalculadas;

    /**
     * Constructor que crea una nueva solución con los resultados del algoritmo.
     * 
     * @param distMin        Par de puntos más cercanos encontrados
     * @param tiempo         Tiempo de ejecución en milisegundos
     * @param distCalculadas Número de distancias calculadas durante la ejecución
     */
    public Solucion(ParPuntos distMin, float tiempo, int distCalculadas) {
        this.distMin = distMin;
        this.tiempo = tiempo;
        this.distCalculadas = distCalculadas;
    }

    @Override
    public String toString() {

        String tiempoFormateado = String.format("%.8f", tiempo);
        return "Puntos: " + distMin + " tiempo: " + tiempoFormateado + " iteraciones " + distCalculadas;

    }
}
