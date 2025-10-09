/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pract1;

/**
 *
 * @author usuario
 */
public class ParPuntos {
    private Punto p1;
    private Punto p2;

    public ParPuntos(Punto p1, Punto p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setP1(Punto p1) {
        this.p1 = p1;
    }

    public void setP2(Punto p2) {
        this.p2 = p2;
    }

  

  
    @Override
    public String toString() {
        return "ParPuntos{" + "p1=" + p1 + ", p2=" + p2 + '}';
    }
    
    
}
