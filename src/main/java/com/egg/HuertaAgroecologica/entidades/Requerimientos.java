package com.egg.HuertaAgroecologica.entidades;

public class Requerimientos {

    private int id;
    private double temperatura;
    private String agua;
    private String luz;
    private String suelo;
    private String estacion;
    private double viento;
    private Foto imagenCultivo;

    public Requerimientos() {
    }

    public Requerimientos(int id, double temperatura, String agua, String luz, String suelo, String estacion, double viento, Foto imagenCultivo) {
        this.id = id;
        this.temperatura = temperatura;
        this.agua = agua;
        this.luz = luz;
        this.suelo = suelo;
        this.estacion = estacion;
        this.viento = viento;
        this.imagenCultivo = imagenCultivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Requerimientos{" + "id=" + id + ", temperatura=" + temperatura + ", agua=" + agua + ", luz=" + luz + ", suelo=" + suelo + ", estacion=" + estacion + ", viento=" + viento + ", imagenCultivo=" + imagenCultivo + '}';
    }
    
    
    
}