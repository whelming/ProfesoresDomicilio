package com.example.educasa.Chat.ChatModels;

public class ContentChat {
    private int id;
    private String textodemo;

    public ContentChat(int id, String textodemo) {
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
