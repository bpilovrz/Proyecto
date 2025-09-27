CREATE DATABASE IF NOT EXISTS AtletasOlimpic;
USE AtletasOlimpic;


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


CREATE TABLE IF NOT EXISTS entrenamientos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    atleta_id INT NOT NULL,
    fecha DATETIME NOT NULL,
    tipo_entrenamiento VARCHAR(50) NOT NULL,
    valor_rendimiento DOUBLE NOT NULL,
    descripcion VARCHAR(50),
    ubicacion ENUM('Nacional','Internacional') NOT NULL,
    pais VARCHAR(50),
    FOREIGN KEY (atleta_id) REFERENCES atletas(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS planilla (
    id INT AUTO_INCREMENT PRIMARY KEY,
    atleta_id INT NOT NULL,
    anio YEAR NOT NULL,
    mes TINYINT NOT NULL,
    total_entrenamientos INT NOT NULL,
    bonificacion_extranjero DOUBLE DEFAULT 0,
    bonificacion_mejor_marca DOUBLE DEFAULT 0,
    pago_total DOUBLE NOT NULL,
    FOREIGN KEY (atleta_id) REFERENCES atletas(id) ON DELETE CASCADE,
    UNIQUE(atleta_id, anio, mes)
);

