package com.egg.HuertaAgroecologica.entidades;

import java.util.Date;

public abstract class Cultivo {

    private int id;
    private String nombre;
    private String tipoCultivo;
    private Date fecha;
    private boolean alta;
    private String observaciones;

    public Cultivo() {
    }

    public Cultivo(int id, String nombre, String tipoCultivo, Date fecha, boolean alta, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.tipoCultivo = tipoCultivo;
        this.fecha = fecha;
        this.alta = alta;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Cultivo{" + "id=" + id + ", nombre=" + nombre + ", tipoCultivo=" + tipoCultivo + ", fecha=" + fecha + ", alta=" + alta + ", observaciones=" + observaciones + '}';
    }   
    
}