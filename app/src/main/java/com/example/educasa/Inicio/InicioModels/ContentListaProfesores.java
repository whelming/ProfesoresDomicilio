package com.example.educasa.Inicio.InicioModels;

public class ContentListaProfesores {
    private int id;
    private String textodemo;

    public ContentListaProfesores(int id, String textodemo) {
        this.id = id;
        this.textodemo = textodemo;
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
}
