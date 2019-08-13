package com.example.educasa.Inicio.InicioModels;

public class ContentInicio {
    private int id;
    private String textodemo;
    private int id_categoria;
    private int imagen_categoria;


    public ContentInicio(int id, String textodemo,int id_categoria, int imagen_categoria ) {
        this.id = id;
        this.textodemo = textodemo;
        this.id_categoria = id_categoria;
        this.imagen_categoria= imagen_categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextodemo() {
        return textodemo;
    }

    public void setTextodemo(String textodemo) {
        this.textodemo = textodemo;
    }

    public int getId_categoria() {return id_categoria;}

    public void setId_categoria(int id_categoria) {this.id_categoria = id_categoria;}

    public int getImagen_categoria() {return imagen_categoria;}

    public void setImagen_categoria(int imagen_categoria) {this.imagen_categoria = imagen_categoria;}
}
