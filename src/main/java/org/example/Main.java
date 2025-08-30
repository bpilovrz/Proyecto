package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        // Ignorar campos desconocidos en el JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Atleta[] atletas = new Atleta[50];
        int contadorAtletas = 0;

        File file = new File("atletas.json");

        if (file.exists() && file.length() > 0) {
            Atleta[] cargados = mapper.readValue(file, Atleta[].class);
            for (int i = 0; i < cargados.length && cargados[i] != null; i++) {
                atletas[i] = cargados[i];
                contadorAtletas++;
            }
        }

        Scanner imprimir = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar Atleta");
            System.out.println("2. Registrar Sesión de Entrenamiento");
            System.out.println("3. Mostrar Historial de un Atleta");
            System.out.println("4. Mostrar Estadísticas");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            opcion = imprimir.nextInt();
            imprimir.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Cui: ");
                    String cui = imprimir.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = imprimir.nextLine();
                    System.out.print("Edad: ");
                    int edad = imprimir.nextInt();
                    imprimir.nextLine();
                    System.out.print("Disciplina: ");
                    String disciplina = imprimir.nextLine();

                    if (contadorAtletas < atletas.length) {
                        atletas[contadorAtletas] = new Atleta(cui, nombre, edad, disciplina);
                        contadorAtletas++;
                        System.out.println("Atleta registrado correctamente.");
                    } else {
                        System.out.println("No se pueden agregar más atletas.");
                    }
                    break;

                case 2:
                    if (contadorAtletas == 0) {
                        System.out.println("No hay atletas registrados.");
                        break;
                    }
                    System.out.println("Seleccione atleta:");
                    for (int i = 0; i < contadorAtletas; i++) {
                        System.out.println(i + ". " + atletas[i]);
                    }
                    int idx = imprimir.nextInt();
                    imprimir.nextLine();
                    Atleta seleccionado = atletas[idx];

                    System.out.print("Fecha (dd/MM/yyyy): ");
                    String f = imprimir.nextLine();
                    Date fecha = sdf.parse(f);

                    System.out.println("Tipo de entrenamiento: 1) Tiempo  2) Peso  3) Puntuación  4) Distancia");
                    int tipo = imprimir.nextInt();
                    imprimir.nextLine();

                    Entrenamiento ent = null;
                    switch (tipo) {
                        case 1:
                            System.out.print("Tiempo (segundos): ");
                            ent = new Tiempo(imprimir.nextDouble());
                            break;
                        case 2:
                            System.out.print("Peso (kg): ");
                            ent = new Peso(imprimir.nextDouble());
                            break;
                        case 3:
                            System.out.print("Puntuación: ");
                            ent = new Puntuacion(imprimir.nextInt());
                            break;
                        case 4:
                            System.out.print("Distancia (m): ");
                            ent = new Distancia(imprimir.nextDouble());
                            break;
                    }
                    imprimir.nextLine();
                    seleccionado.agregarSesion(new SesionEntrenamiento(fecha, ent));
                    break;

                case 3:
                    if (contadorAtletas == 0) break;
                    System.out.println("Seleccione atleta:");
                    for (int i = 0; i < contadorAtletas; i++) {
                        System.out.println(i + ". " + atletas[i]);
                    }
                    int idHist = imprimir.nextInt();
                    imprimir.nextLine();
                    Atleta aHist = atletas[idHist];

                    System.out.println("\nHistorial de " + aHist);
                    for (int j = 0; j < aHist.getTotalSesiones(); j++) {
                        System.out.println("  " + aHist.getSesiones()[j]);
                    }
                    break;

                case 4:
                    if (contadorAtletas == 0) break;
                    System.out.println("Seleccione atleta:");
                    for (int i = 0; i < contadorAtletas; i++) {
                        System.out.println(i + ". " + atletas[i]);
                    }
                    int idEst = imprimir.nextInt();
                    imprimir.nextLine();
                    Atleta aEst = atletas[idEst];

                    System.out.println("\nEstadísticas de " + aEst);
                    System.out.println("Promedio: " + Estadistica.promedio(aEst));
                    System.out.println("Mejor marca: " + Estadistica.mejorMarca(aEst));
                    Estadistica.mostrarEvolucion(aEst);
                    break;
            }
        } while (opcion != 5);

        Atleta[] atletasValidos = new Atleta[contadorAtletas];
        for (int i = 0; i < contadorAtletas; i++) {
            atletasValidos[i] = atletas[i];
        }
        mapper.writeValue(file, atletasValidos);
        System.out.println("Datos guardados en: " + file.getAbsolutePath());

        imprimir.close();
    }
}


