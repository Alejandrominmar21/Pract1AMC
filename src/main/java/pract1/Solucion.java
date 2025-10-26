package pract1;

public class Solucion {
    ParPuntos distMin;
    long tiempo;
    int distCalculadas;


    public Solucion(ParPuntos distMin, long tiempo, int distCalculadas){
        this.distMin=distMin;
        this.tiempo=tiempo;
        this.distCalculadas=distCalculadas;
    }

    public String toString(){
        return "Puntos: " + distMin + " timepo: " + tiempo + " iteraciones "+ distCalculadas;

    }
}
