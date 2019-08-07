package com.example.educasa.MisClases.MisClasesModels;

import java.util.Date;

public class ContentMisClases {

    private String id;
    private String categoria;
    private String profesor;
    private String foto;
    private Date horafecha;

    public ContentMisClases(String id, String categoria, String profesor, String foto, Date horafecha) {
        this.id = id;
        this.categoria = categoria;
        this.profesor = profesor;
        this.foto = foto;
        this.horafecha = horafecha;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getHorafecha() {
        return horafecha;
    }

    public void setHorafecha(Date horafecha) {
        this.horafecha = horafecha;
    }
}
