/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.time.LocalDateTime;

/**
 *
 * @author judit
 */
public class Video {
    private String nombre;
    private String ruta;
    private int duracion;
    private LocalDateTime fechaAgregado;
    private boolean reproducido;

    public Video(String nombre, String ruta, int duracion, LocalDateTime fechaAgregado) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.duracion = duracion;
        this.fechaAgregado = fechaAgregado;
        this.reproducido = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public boolean isReproducido() {
        return reproducido;
    }

    public void setReproducido(boolean reproducido) {
        this.reproducido = reproducido;
    }

    @Override
    public String toString() {
        return "Video{" + "nombre=" + nombre + ", ruta=" + ruta + ", duracion=" + duracion + ", fechaAgregado=" + fechaAgregado + ", reproducido=" + reproducido + '}';
    }
    
    
    
}
