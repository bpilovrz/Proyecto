package org.example;


public class Tiempo implements Entrenamiento {
    private double marca; // mejor usar "marca" para ser consistente con getMarca()

    public Tiempo() {}

    public Tiempo(double segundos) {
        this.marca = segundos;
    }

    @Override
    public double getMarca() {
        return marca;
    }

    @Override
    public String getDescripcion() {
        return marca + " seg";
    }

    public double getSegundos() { return marca; }
    public void setSegundos(double segundos) { this.marca = segundos; }
}
