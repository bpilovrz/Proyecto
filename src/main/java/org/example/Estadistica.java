package org.example;


import java.util.*;
import java.util.stream.Collectors;

public class Estadistica {

    public static double promedio(Atleta a) {
        if (a.getTotalSesiones() == 0) return 0;

        double suma = 0;
        int contador = 0;

        for (SesionEntrenamiento sesion : a.getSesiones()) {
            if (sesion != null && sesion.getEntrenamiento() != null) {
                suma += sesion.getEntrenamiento().getMarca();
                contador++;
            }
        }
        return contador > 0 ? suma / contador : 0;
    }

    public static double mejorMarca(Atleta a) {
        if (a.getTotalSesiones() == 0) return 0;

        double mejor = Double.MIN_VALUE;

        for (SesionEntrenamiento sesion : a.getSesiones()) {
            if (sesion != null && sesion.getEntrenamiento() != null) {
                mejor = Math.max(mejor, sesion.getEntrenamiento().getMarca());
            }
        }
        return mejor == Double.MIN_VALUE ? 0 : mejor;
    }

    public static List<SesionEntrenamiento> evolucion(Atleta a) {
        if (a.getTotalSesiones() == 0) return Collections.emptyList();

        return Arrays.stream(a.getSesiones())
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(SesionEntrenamiento::getFecha))
                .collect(Collectors.toList());
    }

    public static Map<String, Double> compararNacionalVsInternacional(Atleta a) {
        Map<String, Double> resultados = new HashMap<>();

        double sumaNacional = 0;
        int countNacional = 0;
        double sumaInternacional = 0;
        int countInternacional = 0;

        for (SesionEntrenamiento sesion : a.getSesiones()) {
            if (sesion != null && sesion.getEntrenamiento() != null) {
                if (sesion.getUbicacion().toLowerCase().contains("internacional")) {
                    sumaInternacional += sesion.getEntrenamiento().getMarca();
                    countInternacional++;
                } else {
                    sumaNacional += sesion.getEntrenamiento().getMarca();
                    countNacional++;
                }
            }
        }

        resultados.put("Promedio Nacional", countNacional > 0 ? sumaNacional / countNacional : 0);
        resultados.put("Promedio Internacional", countInternacional > 0 ? sumaInternacional / countInternacional : 0);

        return resultados;
    }
}


