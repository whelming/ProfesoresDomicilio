package com.example.educasa.Inicio.InicioModels;

public class ContentInicio {
    private int id;
    private String textodemo;

    public ContentInicio(int id, String textodemo) {
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
