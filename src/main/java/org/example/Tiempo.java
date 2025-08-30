package org.example;

public class Tiempo implements Entrenamiento {
    private double segundos;

    public Tiempo() {}
    public Tiempo(double segundos) {
        this.segundos = segundos;
    }

    @Override
    public double getMarca() {
        return segundos;
    }

    @Override
    public String getDescripcion() {
        return segundos + " seg";
    }

    public double getSegundos() { return segundos; }
    public void setSegundos(double segundos) { this.segundos = segundos; }
}
