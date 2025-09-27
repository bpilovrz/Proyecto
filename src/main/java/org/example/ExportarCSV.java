package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class ExportarCSV {

    public static void exportarAtletasCSV(Connection conn, String rutaArchivo) {
        String sql = "SELECT * FROM atletas";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileWriter writer = new FileWriter(rutaArchivo)) {

            writer.append("ID,CUI,Nombre,Edad,Disciplina,Departamento,Nacionalidad,FechaIngreso\n");

            while (rs.next()) {
                writer.append(rs.getInt("id") + ",");
                writer.append(rs.getString("cui") + ",");
                writer.append(rs.getString("nombre") + ",");
                writer.append(rs.getInt("edad") + ",");
                writer.append(rs.getString("disciplina") + ",");
                writer.append(rs.getString("departamento") + ",");
                writer.append(rs.getString("nacionalidad") + ",");
                writer.append(rs.getTimestamp("fecha_ingreso")
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                writer.append("\n");
            }

            System.out.println(" Atletas exportados a " + rutaArchivo);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportarEntrenamientosCSV(Connection conn, String rutaArchivo) {
        String sql = "SELECT * FROM entrenamientos";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileWriter writer = new FileWriter(rutaArchivo)) {

            // Encabezado
            writer.append("ID,AtletaID,Fecha,TipoEntrenamiento,ValorRendimiento,Ubicacion,Pais\n");

            while (rs.next()) {
                writer.append(rs.getInt("id") + ",");
                writer.append(rs.getInt("atleta_id") + ",");
                writer.append(rs.getTimestamp("fecha")
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ",");
                writer.append(rs.getString("tipo_entrenamiento") + ",");
                writer.append(rs.getDouble("valor_rendimiento") + ",");
                writer.append(rs.getString("ubicacion") + ",");
                writer.append(rs.getString("pais") != null ? rs.getString("pais") : "");
                writer.append("\n");
            }

            System.out.println(" Entrenamientos exportados a " + rutaArchivo);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
