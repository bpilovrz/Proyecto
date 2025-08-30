package org.example;

import java.util.Date;

public class SesionEntrenamiento {
    private Date fecha;
    private Entrenamiento entrenamiento;

    public SesionEntrenamiento() {}

    public SesionEntrenamiento(Date fecha, Entrenamiento entrenamiento) {
        this.fecha = fecha;
        this.entrenamiento = entrenamiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    @Override
    public String toString() {
        return fecha.toString() + " " + (entrenamiento != null ? entrenamiento.getDescripcion() : "Sin entrenamiento");
    }
}


