package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ConexionDB {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/AtletasOlimpic";
        String user = "root";
        String password = "amarillo07"; // Cambia según tu configuración
        return DriverManager.getConnection(url, user, password);
    }

    public static void crearTablas(Connection conn) throws SQLException {
        Statement st = conn.createStatement();

        String sqlAtletas = """
            CREATE TABLE IF NOT EXISTS atletas (
                id INT AUTO_INCREMENT PRIMARY KEY,
                cui VARCHAR(20) NOT NULL UNIQUE,
                nombre VARCHAR(100) NOT NULL,
                edad INT NOT NULL,
                disciplina VARCHAR(50) NOT NULL,
                departamento VARCHAR(50) NOT NULL,
                nacionalidad VARCHAR(50) NOT NULL,
                fecha_ingreso DATETIME NOT NULL
            );
        """;

        String sqlEntrenamientos = """
            CREATE TABLE IF NOT EXISTS entrenamientos (
                id INT AUTO_INCREMENT PRIMARY KEY,
                atleta_id INT NOT NULL,
                fecha DATETIME NOT NULL,
                tipo_entrenamiento VARCHAR(50) NOT NULL,
                valor_rendimiento DOUBLE NOT NULL,
                ubicacion ENUM('Nacional','Internacional') NOT NULL,
                pais VARCHAR(50),
                FOREIGN KEY (atleta_id) REFERENCES atletas(id) ON DELETE CASCADE
            );
        """;

        st.execute(sqlAtletas);
        st.execute(sqlEntrenamientos);
        st.close();
    }

    public static void guardarAtleta(Connection conn, Atleta atleta) throws SQLException {
        String sql = """
            INSERT INTO atletas (cui, nombre, edad, disciplina, departamento, nacionalidad, fecha_ingreso)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, atleta.getCui());
            ps.setString(2, atleta.getNombre());
            ps.setInt(3, atleta.getEdad());
            ps.setString(4, atleta.getDisciplina());
            ps.setString(5, atleta.getDepartamento());
            ps.setString(6, atleta.getNacionalidad());
            ps.setTimestamp(7, Timestamp.valueOf(atleta.getFechaIngreso()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    atleta.setId(rs.getInt(1));
                }
            }
        }
    }

    public static void guardarSesion(Connection conn, Atleta atleta, SesionEntrenamiento sesion) throws SQLException {
        String sql = """
            INSERT INTO entrenamientos (atleta_id, fecha, tipo_entrenamiento, valor_rendimiento, ubicacion, pais)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, atleta.getId());
            ps.setTimestamp(2, new Timestamp(sesion.getFecha().getTime()));
            ps.setString(3, sesion.getEntrenamiento().getClass().getSimpleName());
            ps.setDouble(4, sesion.getEntrenamiento().getMarca());
            // Separar Nacional e Internacional para la columna ENUM
            String ubicacionEnum = sesion.getUbicacion().startsWith("Internacional") ? "Internacional" : "Nacional";
            ps.setString(5, ubicacionEnum);
            ps.setString(6, sesion.getPais());
            ps.executeUpdate();
        }
    }

    public static Atleta[] cargarAtletas(Connection conn) throws SQLException {
        String sql = "SELECT * FROM atletas";
        List<Atleta> lista = new ArrayList<>();

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Atleta a = new Atleta(
                        rs.getString("cui"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("disciplina"),
                        rs.getString("departamento"),
                        rs.getString("nacionalidad"),
                        rs.getTimestamp("fecha_ingreso").toLocalDateTime()
                );
                a.setId(rs.getInt("id"));
                a.setSesiones(cargarSesiones(conn, a.getId()));
                lista.add(a);
            }
        }

        return lista.toArray(new Atleta[0]);
    }

    public static SesionEntrenamiento[] cargarSesiones(Connection conn, int atletaId) throws SQLException {
        String sql = "SELECT * FROM entrenamientos WHERE atleta_id = ?";
        List<SesionEntrenamiento> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, atletaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo_entrenamiento");
                    Entrenamiento ent;
                    switch (tipo) {
                        case "Tiempo" -> ent = new Tiempo(rs.getDouble("valor_rendimiento"));
                        case "Peso" -> ent = new Peso(rs.getDouble("valor_rendimiento"));
                        case "Puntuacion" -> ent = new Puntuacion((int) rs.getDouble("valor_rendimiento"));
                        case "Distancia" -> ent = new Distancia(rs.getDouble("valor_rendimiento"));
                        default -> ent = null;
                    }

                    SesionEntrenamiento s = new SesionEntrenamiento(
                            rs.getTimestamp("fecha").toLocalDateTime(),
                            ent,
                            rs.getString("ubicacion"),
                            rs.getString("pais")
                    );
                    lista.add(s);
                }
            }
        }

        return lista.toArray(new SesionEntrenamiento[0]);
    }
}







