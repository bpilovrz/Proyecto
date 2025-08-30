package org.example;

public class Puntuacion implements Entrenamiento {
    private int puntos;

    public Puntuacion() {}

    public Puntuacion(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public double getMarca() {
        return puntos;
    }

    @Override
    public String getDescripcion() {
        return puntos + " pts";
    }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
}
