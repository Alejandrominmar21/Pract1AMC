/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pract1;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class Pract1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<Punto> dataset = new ArrayList<>();
        dataset.add(new Punto(0,0));
        dataset.add(new Punto(2,2));
        dataset.add(new Punto(4,4));
        dataset.add(new Punto(5,5));
        dataset.add(new Punto(5,6));
        dataset.add(new Punto(8,6));
        dataset.add(new Punto(7,4));
        dataset.add(new Punto(5,7));
        
        //Algoritmos alg = new Algoritmos();      
      
        ParPuntos Solucion1 = Algoritmos.Exhaustivo(dataset);
        ParPuntos Solucion2 =Algoritmos.ExhaustivoPoda(dataset);
        ParPuntos Solucion3 =Algoritmos.DyV(dataset);

        System.out.println(Solucion1);
        System.out.println(Solucion2);
        System.out.println(Solucion3);

        
    }
    
}
