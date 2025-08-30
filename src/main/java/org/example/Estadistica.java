package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class Estadistica {

    public static double promedio(Atleta a){
        if(a.getTotalSesiones() == 0) return 0;
        double suma = 0;
        for(int i = 0; i < a.getTotalSesiones(); i++){
            suma += a.getSesiones()[i].getEntrenamiento().getMarca();
        }
        return suma/a.getTotalSesiones();
    }

    public static double mejorMarca(Atleta a){
        if(a.getTotalSesiones() == 0) return 0;
        double mejor = a.getSesiones()[0].getEntrenamiento().getMarca();
        for(int i = 0; i < a.getTotalSesiones(); i++){
            double marca = a.getSesiones()[i].getEntrenamiento().getMarca();
            if(marca > mejor) mejor = marca;
        }
        return mejor;
    }

    public static void mostrarEvolucion(Atleta a){
        if(a.getTotalSesiones() == 0){
            System.out.println("Sin sesiones registradas.");
            return;
        }

        SesionEntrenamiento[] copia = Arrays.copyOf(a.getSesiones(), a.getTotalSesiones());

        Arrays.sort(copia, Comparator.comparing(SesionEntrenamiento::getFecha));

        System.out.println("Evolución de rendimiento (ordenado por fecha)");
        for(SesionEntrenamiento s: copia){
            System.out.println("  " +s);
        }
    }
}
