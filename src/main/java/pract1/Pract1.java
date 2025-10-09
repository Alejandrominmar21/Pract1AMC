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
        dataset.add(new Punto(2,3));
        dataset.add(new Punto(3,4));
        dataset.add(new Punto(5,5));
        
        Algoritmos alg = new Algoritmos();
        
        alg.Echaustivo(dataset);
    }
    
}
