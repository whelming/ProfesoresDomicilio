package com.example.educasa.MisClases.MisClasesModels;

public class ContentMisClases {
    private int id;
    private String textodemo;

    public ContentMisClases(int id, String textodemo) {
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
