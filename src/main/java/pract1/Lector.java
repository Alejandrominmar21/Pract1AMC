package pract1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lector {
    File myObj;

    public Lector(File obj){
        myObj=obj;
    }

    public ArrayList<Punto> LeePuntos(){
        ArrayList<Punto> puntos = new ArrayList<>();
        try (Scanner myReader = new Scanner(myObj)) {
            boolean numeros = false;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("NODE_COORD_SECTION")) {
                    numeros = true;
                    continue;
                } else if ("EOF".equals(data)) {
                    //System.out.println("FINAL");
                    numeros = false;
                }

                if (numeros == true) {
                    String[] partes = data.trim().split("\\s+"); // separa por espacios
                    int id = Integer.parseInt(partes[0]);
                    double x = Double.parseDouble(partes[1]);
                    double y = Double.parseDouble(partes[2]);
                    //System.out.println("ID: " + id + ", X: " + x + ", Y: " + y);
                    puntos.add(new Punto(x,y));
                   
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        return puntos;
    }
}
