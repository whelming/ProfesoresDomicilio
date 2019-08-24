package com.tdlzgroup.educasa.Inicio.InicioModels;

public class ContentListaProfesores {
    private String id;
    private String url_foto;
    private String nombres;
    private double puntuacion;

    public ContentListaProfesores(String id, String url_foto, String nombres, double puntuacion) {
        this.id = id;
        this.url_foto = url_foto;
        this.nombres = nombres;
        this.puntuacion = puntuacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }
}
