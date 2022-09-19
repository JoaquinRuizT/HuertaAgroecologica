package com.egg.HuertaAgroecologica.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    private String temperatura;
    private String agua;
    private String luz;
    private String suelo;
    private String estacion;
    private String viento;
    private String observaciones;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne
    private Foto imagenCultivo;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //tipoDeRiego
    public Cultivo() {
    }

    public Cultivo(String id, String nombre, String tipoCultivo, Date fecha, boolean alta, String temperatura, String agua, String luz, String suelo, String estacion, String viento, Foto imagenCultivo, String observaciones) {
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

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
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

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
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