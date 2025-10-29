package pract1;

public class Solucion {
    ParPuntos distMin;
    float tiempo;
    int distCalculadas;


    public Solucion(ParPuntos distMin, float tiempo, int distCalculadas){
        this.distMin=distMin;
        this.tiempo=tiempo;
        this.distCalculadas=distCalculadas;
    }

    @Override
    public String toString(){

        String tiempoFormateado = String.format("%.8f", tiempo);
        return "Puntos: " + distMin + " tiempo: " + tiempoFormateado + " iteraciones " + distCalculadas;

    }
}
