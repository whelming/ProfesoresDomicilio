package com.tdlzgroup.educasa.Solicitudes.SolicitudesModels;

public class ContentProfesoresInteresados {
    String id;
    String nombres;
    String descripcion;
    double precio;
    double puntuacion;
    String url_foto;

    public ContentProfesoresInteresados() {

    }

    public ContentProfesoresInteresados(String id, String nombres, String descripcion, double precio, double puntuacion, String url_foto) {
        this.id = id;
        this.nombres = nombres;
        this.descripcion = descripcion;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.url_foto = url_foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
