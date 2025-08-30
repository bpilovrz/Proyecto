package org.example;

public class Distancia implements Entrenamiento {
    private double metros;

    public Distancia() {}

    public Distancia(double metros) {
        this.metros = metros;
    }

    @Override
    public double getMarca() {
        return metros;
    }

    @Override
    public String getDescripcion() {
        return metros + " m";
    }

    public double getMetros() { return metros; }
    public void setMetros(double metros) { this.metros = metros; }
}

