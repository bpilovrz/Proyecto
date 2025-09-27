package org.example;

public class Distancia implements Entrenamiento {
    private double marca; // metros

    public Distancia() {}

    public Distancia(double metros) {
        this.marca = metros;
    }

    @Override
    public double getMarca() {
        return marca;
    }

    @Override
    public String getDescripcion() {
        return marca + " m";
    }

    public double getMetros() { return marca; }
    public void setMetros(double metros) { this.marca = metros; }
}


