package com.tdlzgroup.educasa.MisClases.MisClasesModels;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class ContentMisClases {

    private String id;
    private String nombremateria;
    private String nombreprofesor;
    private String urlfoto;
    private Timestamp fechahora;

    public ContentMisClases(String id, Timestamp fechahora) {
        this.id = id;
        this.fechahora = fechahora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombremateria() {
        return nombremateria;
    }

    public void setNombremateria(String nombremateria) {
        this.nombremateria = nombremateria;
    }

    public String getNombreprofesor() {
        return nombreprofesor;
    }

    public void setNombreprofesor(String nombreprofesor) {
        this.nombreprofesor = nombreprofesor;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public Timestamp getFechahora() {
        return fechahora;
    }

    public void setFechahora(Timestamp fechahora) {
        this.fechahora = fechahora;
    }
}
