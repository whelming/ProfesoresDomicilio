package com.tdlzgroup.educasa.Inicio.InicioModels;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ContentListaProfesores implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private String profesion;
    private String urlfoto;
    private double puntaje;
    private double votos;
    private List<String> materias;
    private List<String> medallas;
    private List<String> categorias;
    private Date creacion;
    private double sexo;

    public ContentListaProfesores(String id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public List<String> getMaterias() {
        return materias;
    }

    public void setMaterias(List<String> materias) {
        this.materias = materias;
    }

    public List<String> getMedallas() {
        return medallas;
    }

    public void setMedallas(List<String> medallas) {
        this.medallas = medallas;
    }

    public Date getCreacion() {
        return creacion;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public double getSexo() {
        return sexo;
    }

    public void setSexo(double sexo) {
        this.sexo = sexo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public double getVotos() {
        return votos;
    }

    public void setVotos(double votos) {
        this.votos = votos;
    }
}
