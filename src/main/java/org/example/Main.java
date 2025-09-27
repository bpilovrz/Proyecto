package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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

        Connection conn = ConexionDB.getConnection();
        ConexionDB.crearTablas(conn);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar Atleta");
            System.out.println("2. Registrar Sesión de Entrenamiento");
            System.out.println("3. Mostrar Historial de un Atleta");
            System.out.println("4. Mostrar Estadísticas");
            System.out.println("5. Calcular Pago Mensual");
            System.out.println("6. Salir");
            System.out.println("7. Exportar datos a CSV");
            System.out.print("Opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                sc.nextLine();
            }
            opcion = sc.nextInt(); sc.nextLine();

            switch (opcion) {
                case 1 -> { // Registrar atleta
                    System.out.print("CUI: "); String cui = sc.nextLine();
                    System.out.print("Nombre: "); String nombre = sc.nextLine();
                    System.out.print("Edad: "); int edad = sc.nextInt(); sc.nextLine();
                    System.out.print("Disciplina: "); String disciplina = sc.nextLine();
                    System.out.print("Departamento: "); String depto = sc.nextLine();
                    System.out.print("Nacionalidad: "); String nacionalidad = sc.nextLine();
                    System.out.print("Fecha ingreso (dd/MM/yyyy): ");
                    LocalDateTime ingreso = LocalDate.parse(sc.nextLine(), formatter).atStartOfDay();

                    if (contadorAtletas < atletas.length) {
                        Atleta nuevo = new Atleta(cui, nombre, edad, disciplina, depto, nacionalidad, ingreso);
                        atletas[contadorAtletas++] = nuevo;
                        try {
                            ConexionDB.guardarAtleta(conn, nuevo);
                            System.out.println("Atleta registrado correctamente.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                }

                case 2 -> { // Registrar sesión
                    if (contadorAtletas == 0) { System.out.println("No hay atletas registrados."); break; }
                    mostrarAtletas(atletas, contadorAtletas);
                    int index = sc.nextInt(); sc.nextLine();
                    Atleta seleccionado = atletas[index];

                    System.out.print("Fecha (dd/MM/yyyy): ");
                    LocalDateTime fecha = LocalDate.parse(sc.nextLine(), formatter).atStartOfDay();

                    System.out.println("Tipo de entrenamiento: 1) Tiempo 2) Peso 3) Puntuación 4) Distancia");
                    int tipo = sc.nextInt(); sc.nextLine();
                    Entrenamiento ent = null;
                    switch (tipo) {
                        case 1 -> { System.out.print("Tiempo (segundos): "); ent = new Tiempo(sc.nextDouble()); }
                        case 2 -> { System.out.print("Peso (kg): "); ent = new Peso(sc.nextDouble()); }
                        case 3 -> { System.out.print("Puntuación: "); ent = new Puntuacion(sc.nextInt()); }
                        case 4 -> { System.out.print("Distancia (m): "); ent = new Distancia(sc.nextDouble()); }
                    }
                    sc.nextLine();

                    System.out.print("¿Entrenamiento internacional? (s/n): ");
                    String resp = sc.nextLine();
                    String ubicacion = "Nacional";
                    String pais = null;
                    if (resp.equalsIgnoreCase("s")) {
                        System.out.print("Ingrese país: ");
                        ubicacion = "Internacional";
                        pais = sc.nextLine();
                    }

                    SesionEntrenamiento sesion = new SesionEntrenamiento(fecha, ent, ubicacion, pais);
                    seleccionado.agregarSesion(sesion);
                    ConexionDB.guardarSesion(conn, seleccionado, sesion);
                }

                case 3 -> { // Mostrar historial
                    if (contadorAtletas == 0) break;
                    mostrarAtletas(atletas, contadorAtletas);
                    int index = sc.nextInt(); sc.nextLine();
                    Atleta aHist = atletas[index];

                    System.out.println("\nHistorial de " + aHist.getNombre());
                    for (SesionEntrenamiento s : aHist.getSesiones()) {
                        System.out.println("  " + s);
                    }
                }

                case 4 -> { // Estadísticas
                    if (contadorAtletas == 0) break;
                    mostrarAtletas(atletas, contadorAtletas);
                    int index = sc.nextInt(); sc.nextLine();
                    Atleta aEst = atletas[index];

                    System.out.println("\nEstadísticas de " + aEst.getNombre());
                    System.out.println("Promedio: " + Estadistica.promedio(aEst));
                    System.out.println("Mejor marca: " + Estadistica.mejorMarca(aEst));
                    System.out.println("Evolución:");
                    for (SesionEntrenamiento s : Estadistica.evolucion(aEst)) {
                        System.out.println("  " + s);
                    }
                }

                case 5 -> { // Pago mensual
                    if (contadorAtletas == 0) break;
                    mostrarAtletas(atletas, contadorAtletas);
                    int index = sc.nextInt(); sc.nextLine();
                    Atleta aPago = atletas[index];
                    Planilla planilla = new Planilla();
                    double pago = planilla.calcularPagoTotal(aPago);
                    System.out.println("Pago mensual de " + aPago.getNombre() + ": Q" + pago);
                }
                case 7  -> {
                    ExportarCSV.exportarAtletasCSV(conn, "atletas.csv");
                    ExportarCSV.exportarEntrenamientosCSV(conn, "entrenamientos.csv");
                }

                case 6 -> System.out.println("Saliendo...");

                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 6);

        // Guardar datos en JSON
        Atleta[] atletasValidos = Arrays.copyOf(atletas, contadorAtletas);
        mapper.writeValue(file, atletasValidos);
        System.out.println("Datos guardados en: " + file.getAbsolutePath());

        sc.close();
        conn.close();
    }

    private static void mostrarAtletas(Atleta[] atletas, int contador) {
        System.out.println("Seleccione atleta:");
        for (int i = 0; i < contador; i++) {
            System.out.println(i + ". " + atletas[i].getNombre());
        }
    }
}








