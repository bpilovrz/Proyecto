package org.example;

public class Puntuacion implements Entrenamiento {
    private double marca; // puntos como double para getMarca()

    public Puntuacion() {}

    public Puntuacion(int puntos) {
        this.marca = puntos;
    }

    @Override
    public double getMarca() {
        return marca;
    }

    @Override
    public String getDescripcion() {
        return (int)marca + " pts"; // mostrar como entero
    }

    public int getPuntos() { return (int)marca; }
    public void setPuntos(int puntos) { this.marca = puntos; }
}

