package com.tdlzgroup.educasa.Solicitudes.SolicitudesModels;


import com.google.firebase.Timestamp;

public class ContentSolicitudes {
    private String id;
    private String titulo;
    private double interesados;
    private Timestamp fechahora;

    public ContentSolicitudes(String id, String titulo, double interesados, Timestamp fechahora) {
        this.id = id;
        this.titulo = titulo;
        this.interesados = interesados;
        this.fechahora = fechahora;
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

    public Timestamp getFechahora() {
        return fechahora;
    }

    public void setFechahora(Timestamp fechahora) {
        this.fechahora = fechahora;
    }
}
