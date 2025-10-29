/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pract1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author usuario
 */
public class Algoritmos {/*TODO comprobar todas las estrategias, comparar dos estrategias... */

                        //TODO crear un fichero tsp aleatorio

                        //TODO baquero ha dicho que hay que hacer un javadoc, asique hay que comentar el codigo

    public static double distancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public Algoritmos() {
    }

    public static Solucion Exhaustivo(ArrayList<Punto> puntos) {
        long startTime = System.nanoTime();
        double distanciaMinima= distancia(puntos.get(0),puntos.get(1));
        ParPuntos puntosDistMin = new ParPuntos(puntos.get(0),puntos.get(1));
        int distCalculadas=0;
        
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i+1; j < puntos.size(); j++) {
                distCalculadas++;
                double dist = distancia(puntos.get(i),puntos.get(j));
                if(dist < distanciaMinima){
                    distanciaMinima=dist;
                    puntosDistMin =new ParPuntos(puntos.get(i),puntos.get(j));
                }
                //System.out.println("i: "+puntos.get(i).toString()+" j: "+puntos.get(j).toString()+ " distancia:" + dist );
                
            }
        }
        //System.out.println("min: " + distanciaMinima + "Puntos: " + puntosDistMin);
        long endTime = System.nanoTime();
        long executionTime =  (endTime - startTime);

        return new Solucion(puntosDistMin,((float) executionTime)/1000000,distCalculadas);// tiempo en ms

    }
    
      public static Solucion ExhaustivoPoda(ArrayList<Punto> puntos) {
        long startTime = System.nanoTime();
        int distCalculadas=0;
        if(puntos.size()<2){
            long endTime = System.nanoTime();
            long executionTime =  (endTime - startTime);
            return new Solucion(null,executionTime,0);
        }
        ParPuntos puntosDistMin = new ParPuntos(puntos.get(0),puntos.get(1));
        ArrayList<Punto> aux = new ArrayList<>(puntos);
        aux.sort(Comparator.comparingDouble(Punto::getX));
        
        double minDist = distancia(aux.get(0),aux.get(1));
        //Punto a= aux.get(0), b= aux.get(1); 
        
        for(int i= 0; i<aux.size()-i;i++){
            for(int j= i+1; j<aux.size();j++){
                distCalculadas++;
                double dx  = aux.get(j).getX() - aux.get(i).getX();
                if(dx >= minDist){//poda por X
                    break;
                }
                
                double d= distancia(aux.get(i),aux.get(j));
                if(d < minDist){
                    minDist = d; 
                    //a= aux.get(i);
                    //b= aux.get(j);
                    puntosDistMin = new ParPuntos(aux.get(i), aux.get(j));
                }
            }   
        }
        
        long endTime = System.nanoTime();
        long executionTime =  (endTime - startTime);
        return new Solucion(puntosDistMin,((float) executionTime)/1000000,distCalculadas);
          
      }
    
      
    public static Solucion DyV(ArrayList<Punto> puntos){
        if(puntos == null || puntos.size() < 2) { return new Solucion(null, 0, 0);}

        Punto[] arr = puntos.toArray(new Punto[0]);
        Arrays.sort(arr,Comparator.comparingDouble(Punto::getX));
        ArrayList<Punto> ordenadosX = new ArrayList<>(Arrays.asList(arr));
        
        return dyvRec(ordenadosX, 0, 0);
    }

    public static Solucion dyvRec(ArrayList<Punto> ordenadosX, long Tiempo, int iteraciones){
        int n = ordenadosX.size();
        int distCalculadas=0;
        
        if(n <= 3){
            return Exhaustivo(new ArrayList<>(ordenadosX));
        }

        int mid = n/2;

        ArrayList<Punto> izq = new ArrayList<>(ordenadosX.subList(0, mid));
        ArrayList<Punto> der = new ArrayList<>(ordenadosX.subList(mid, n));
        double xm = der.get(0).getX();

        
        long startTime = System.nanoTime();
        Solucion solIzq = dyvRec(izq,0,0);
        Solucion solDer = dyvRec(der,0,0);
        distCalculadas= distCalculadas + solIzq.distCalculadas + solDer.distCalculadas;

        Double dIzq = distancia(solIzq.distMin.getP1(), solIzq.distMin.getP2());
        double dDer = distancia(solDer.distMin.getP1(), solDer.distMin.getP2());
        double d = Math.min(dIzq,dDer); 
        
        ParPuntos mejor; 
        if(dIzq <= dDer){
            mejor = solIzq.distMin;
        }else{
            mejor = solDer.distMin;
        }  

        ArrayList<Punto> bandaIzq = new ArrayList<>();
        for(Punto p : izq) if(Math.abs(p.getX()-xm)<d) bandaIzq.add(p); 

        ArrayList<Punto> bandaDer = new ArrayList<>();
        for(Punto p : der) if(Math.abs(p.getX()-xm)<d) bandaDer.add(p); 

        for(Punto li : bandaIzq){
            for(Punto rd : bandaDer){
                distCalculadas++;
                double dist =distancia(li, rd);
                if(dist < d){
                    d=dist;
                    mejor = new ParPuntos(li, rd);
                }
            }
        }
        long endTime = System.nanoTime();
        long executionTime =  (endTime - startTime);
        executionTime= (long) (executionTime + solDer.tiempo + solIzq.tiempo);
        return  new Solucion(mejor,((float) executionTime)/1000000,distCalculadas);
    }
       
}
