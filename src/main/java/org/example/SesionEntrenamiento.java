package org.example;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SesionEntrenamiento {
    private Date fecha;
    private Entrenamiento entrenamiento;
    private String ubicacion; // Nacional o Internacional
    private String pais;       // Solo si es internacional

    public SesionEntrenamiento() {}

    public SesionEntrenamiento(LocalDateTime fecha, Entrenamiento entrenamiento, String ubicacion, String pais) {
        this.fecha = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        this.entrenamiento = entrenamiento;
        this.ubicacion = ubicacion;
        this.pais = pais;
    }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Entrenamiento getEntrenamiento() { return entrenamiento; }
    public void setEntrenamiento(Entrenamiento entrenamiento) { this.entrenamiento = entrenamiento; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        String infoPais = (pais != null && !pais.isEmpty()) ? " - País: " + pais : "";
        return (fecha != null ? fecha.toString() : "Sin fecha") + " | " +
                (entrenamiento != null ? entrenamiento.getDescripcion() : "Sin entrenamiento") +
                " | Ubicación: " + (ubicacion != null ? ubicacion : "Sin ubicación") + infoPais;
    }
}








