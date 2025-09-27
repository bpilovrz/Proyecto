package org.example;

public class Peso implements Entrenamiento {
    private double marca; // en kg

    public Peso() {}

    public Peso(double kilogramos) {
        this.marca = kilogramos;
    }

    @Override
    public double getMarca() {
        return marca;
    }

    @Override
    public String getDescripcion() {
        return marca + " kg";
    }

    public double getKilogramos() { return marca; }
    public void setKilogramos(double kilogramos) { this.marca = kilogramos; }
}

