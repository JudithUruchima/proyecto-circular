/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import DBControlador.TurnoControlador;

/**
 *
 * @author judit
 */
public class Turno {

    private int numeroTicket;
    private String nombre;
    private String apellido;
    private Enfermedad sintoma;
    private int prioridad;
    private Boolean atendido;
    private long timestamp;

    private TurnoControlador coneccion = new TurnoControlador();

    public Turno(int numeroTicket, String nombre, String apellido, Enfermedad sintoma, int prioridad, Boolean atendido) {
        this.numeroTicket = numeroTicket;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sintoma = sintoma;
        this.prioridad = prioridad;
        this.atendido = atendido;
    }

    public Turno(String nombre, String apellido, Enfermedad sintoma, int prioridad, Boolean atendido) {
        this.numeroTicket = coneccion.generarCodigo();
        this.nombre = nombre;
        this.apellido = apellido;
        this.sintoma = sintoma;
        this.prioridad = prioridad;
        this.atendido = atendido;
    }

    public int getNumeroTicket() {
        return numeroTicket;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Enfermedad getSintoma() {
        return sintoma;
    }

    public void setSintoma(Enfermedad sintoma) {
        this.sintoma = sintoma;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean getAtendido() {
        return atendido;
    }

    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + ", " + sintoma;
    }

}
