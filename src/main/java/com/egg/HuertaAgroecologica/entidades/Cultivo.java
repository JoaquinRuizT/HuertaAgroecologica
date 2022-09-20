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
    private int estado;
    private double temperatura;
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

    public Cultivo(String id, String nombre, String tipoCultivo, Date fecha, int estado, double temperatura, String agua, String luz, String suelo, String estacion, String viento, String observaciones, Usuario usuario, Foto imagenCultivo) {
        this.id = id;
        this.nombre = nombre;
        this.tipoCultivo = tipoCultivo;
        this.fecha = fecha;
        this.estado = estado;
        this.temperatura = temperatura;
        this.agua = agua;
        this.luz = luz;
        this.suelo = suelo;
        this.estacion = estacion;
        this.viento = viento;
        this.observaciones = observaciones;
        this.usuario = usuario;
        this.imagenCultivo = imagenCultivo;
    }    
    
    //tipoDeRiego
    public Cultivo() {
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Foto getImagenCultivo() {
        return imagenCultivo;
    }

    public void setImagenCultivo(Foto imagenCultivo) {
        this.imagenCultivo = imagenCultivo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Cultivo{" + "id=" + id + ", nombre=" + nombre + ", tipoCultivo=" + tipoCultivo + ", fecha=" + fecha + ", estado=" + estado + ", temperatura=" + temperatura + ", agua=" + agua + ", luz=" + luz + ", suelo=" + suelo + ", estacion=" + estacion + ", viento=" + viento + ", observaciones=" + observaciones + ", usuario=" + usuario + ", imagenCultivo=" + imagenCultivo + '}';
    }

    

}