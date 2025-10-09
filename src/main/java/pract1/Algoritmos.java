/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pract1;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author usuario
 */
public class Algoritmos {

    public static double distancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public Algoritmos() {
    }

    public static ParPuntos Echaustivo(ArrayList<Punto> puntos) {
        double distanciaMinima= distancia(puntos.get(0),puntos.get(1));
        ParPuntos puntosDistMin = new ParPuntos(puntos.get(0),puntos.get(1));
        
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i+1; j < puntos.size(); j++) {
                double dist = distancia(puntos.get(i),puntos.get(j));
                if(dist < distanciaMinima){
                    distanciaMinima=dist;
                    puntosDistMin =new ParPuntos(puntos.get(i),puntos.get(j));
                }
                System.out.println("i: "+puntos.get(i).toString()+" j: "+puntos.get(j).toString()+ " distancia:" + dist );
                
                
          
            }
        }
        System.out.println("min: " + distanciaMinima + "Puntos: " + puntosDistMin);

        return puntosDistMin;

    }
    
      public static ParPuntos EchaustivoPoda(ArrayList<Punto> puntos) {
        if(puntos.size()<2){
            return null;
        }
        ParPuntos puntosDistMin = new ParPuntos(puntos.get(0),puntos.get(1));
        ArrayList<Punto> aux = new ArrayList<>(puntos);
        aux.sort(Comparator.comparingDouble(Punto::getX));
        
        double minDist = distancia(aux.get(0),aux.get(1));
        //Punto a= aux.get(0), b= aux.get(1); 
        
        for(int i= 0; i<aux.size()-i;i++){
            for(int j= i+1; j<aux.size();j++){
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
        
        
        return puntosDistMin; 
          
      }

}
