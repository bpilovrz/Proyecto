package org.example;

public class Peso implements Entrenamiento {
    private double kilogramos;

    public Peso() {}

    public Peso(double kilogramos) {
        this.kilogramos = kilogramos;
    }

    @Override
    public double getMarca() {
        return kilogramos;
    }

    @Override
    public String getDescripcion() {
        return kilogramos + " kg";
    }

    public double getKilogramos() { return kilogramos; }
    public void setKilogramos(double kilogramos) { this.kilogramos = kilogramos; }
}
