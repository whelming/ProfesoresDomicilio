package com.tdlzgroup.educasa.Inicio.InicioModels;

import java.io.Serializable;

public class ContentInicio implements Serializable {
    private String id;
    private String nombre_materia;
    private String url_imagen_materia;

    public ContentInicio(String id, String nombre_materia, String url_imagen_materia) {
        this.id = id;
        this.nombre_materia = nombre_materia;
        this.url_imagen_materia = url_imagen_materia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public String getUrl_imagen_materia() {
        return url_imagen_materia;
    }

    public void setUrl_imagen_materia(String url_imagen_materia) {
        this.url_imagen_materia = url_imagen_materia;
    }
}
