package com.tdlzgroup.educasa.Solicitudes.SolicitudesModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContentSolicitudes implements Serializable {
    private String id;
    private String titulo;
    private double interesados;
    private Date fechahora;

    private String descripcion;
    private double precio;
    private List<String> urlsfotos;


    public ContentSolicitudes() {
        this.urlsfotos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getInteresados() {
        return interesados;
    }

    public void setInteresados(double interesados) {
        this.interesados = interesados;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
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

    public List<String> getUrlsfotos() {
        return urlsfotos;
    }

    public void setUrlsfotos(List<String> urlsfotos) {
        this.urlsfotos = urlsfotos;
    }
}
