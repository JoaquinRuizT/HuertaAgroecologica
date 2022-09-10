package com.egg.HuertaAgroecologica.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Cultivo implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String tipoCultivo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    private boolean alta;
    private double temperatura;
    private String agua;
    private String luz;
    private String suelo;
    private String estacion;
    private String viento;
    private Foto imagenCultivo;
    private String observaciones;
    //tipoDeRiego
    
    public Cultivo() {
    }

    public Cultivo(String id, String nombre, String tipoCultivo, Date fecha, boolean alta, double temperatura, String agua, String luz, String suelo, String estacion, double viento, Foto imagenCultivo, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.tipoCultivo = tipoCultivo;
        this.fecha = fecha;
        this.alta = alta;
        this.temperatura = temperatura;
        this.agua = agua;
        this.luz = luz;
        this.suelo = suelo;
        this.estacion = estacion;
        this.viento = viento;
        this.imagenCultivo = imagenCultivo;
        this.observaciones = observaciones;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getAgua() {
        return agua;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    public String getLuz() {
        return luz;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

    public String getSuelo() {
        return suelo;
    }

    public void setSuelo(String suelo) {
        this.suelo = suelo;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public double getViento() {
        return viento;
    }

    public void setViento(double viento) {
        this.viento = viento;
    }

    public Foto getImagenCultivo() {
        return imagenCultivo;
    }

    public void setImagenCultivo(Foto imagenCultivo) {
        this.imagenCultivo = imagenCultivo;
    }

  

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "Cultivo{" + "id=" + id + ", nombre=" + nombre + ", tipoCultivo=" + tipoCultivo + ", fecha=" + fecha + ", alta=" + alta + ", temperatura=" + temperatura + ", agua=" + agua + ", luz=" + luz + ", suelo=" + suelo + ", estacion=" + estacion + ", viento=" + viento + ", imagenCultivo=" + imagenCultivo + ", observaciones=" + observaciones + '}';
    }

   
}