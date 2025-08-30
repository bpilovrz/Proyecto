package org.example;

import java.util.Arrays;

public class Atleta {
    private String cui;
    private String nombre;
    private int edad;
    private String disciplina;

    private SesionEntrenamiento[] sesiones;
    private int contadorSesiones;

    public Atleta() {
        this.sesiones = new SesionEntrenamiento[100];
        this.contadorSesiones = 0;
    }

    public Atleta(String cui, String nombre, int edad, String disciplina) {
        this.cui = cui;
        this.nombre = nombre;
        this.edad = edad;
        this.disciplina = disciplina;
        this.sesiones = new SesionEntrenamiento[100];
        this.contadorSesiones = 0;
    }

    public void agregarSesion(SesionEntrenamiento s){
        if(contadorSesiones < sesiones.length) {
            sesiones[contadorSesiones] = s;
            contadorSesiones++;
        } else {
            System.out.println("No se ha podido agregar más sesiones");
        }
    }

    public String getCui() { return cui; }
    public void setCui(String cui) { this.cui = cui; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public SesionEntrenamiento[] getSesiones() {
        return Arrays.copyOf(sesiones, contadorSesiones);
    }

    public void setSesiones(SesionEntrenamiento[] sesiones) {
        this.sesiones = new SesionEntrenamiento[100];
        if (sesiones != null) {
            int i = 0;
            for (; i < sesiones.length && sesiones[i] != null && i < 100; i++) {
                this.sesiones[i] = sesiones[i];
            }
            this.contadorSesiones = i;
        } else {
            this.contadorSesiones = 0;
        }
    }

    public int getContadorSesiones() { return contadorSesiones; }
    public void setContadorSesiones(int contadorSesiones) { this.contadorSesiones = contadorSesiones; }

    public int getTotalSesiones() { return contadorSesiones; }

    @Override
    public String toString() {
        return "Atleta [Cui:" + cui + ", Nombre:" + nombre + ", Edad:" + edad + ", Disciplina:" + disciplina + "]";
    }
}


