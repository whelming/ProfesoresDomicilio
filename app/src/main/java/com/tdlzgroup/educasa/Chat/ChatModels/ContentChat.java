package com.tdlzgroup.educasa.Chat.ChatModels;

import java.io.Serializable;
import java.util.Date;

public class ContentChat implements Serializable {
    private String id;
    private String idAlum;
    private String idProf;
    private String nombreAlum;
    private String nombreProf;
    private String urlfotoAlum;
    private String urlfotoProf;
    private Date fechahora;

    public ContentChat() {}

    public ContentChat(String id, String idAlum, String idProf, String nombreAlum, String nombreProf, String urlfotoAlum, String urlfotoProf, Date fechahora) {
        this.id = id;
        this.idAlum = idAlum;
        this.idProf = idProf;
        this.nombreAlum = nombreAlum;
        this.nombreProf = nombreProf;
        this.urlfotoAlum = urlfotoAlum;
        this.urlfotoProf = urlfotoProf;
        this.fechahora = fechahora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAlum() {
        return idAlum;
    }

    public void setIdAlum(String idAlum) {
        this.idAlum = idAlum;
    }

    public String getIdProf() {
        return idProf;
    }

    public void setIdProf(String idProf) {
        this.idProf = idProf;
    }

    public String getNombreAlum() {
        return nombreAlum;
    }

    public void setNombreAlum(String nombreAlum) {
        this.nombreAlum = nombreAlum;
    }

    public String getNombreProf() {
        return nombreProf;
    }

    public void setNombreProf(String nombreProf) {
        this.nombreProf = nombreProf;
    }

    public String getUrlfotoAlum() {
        return urlfotoAlum;
    }

    public void setUrlfotoAlum(String urlfotoAlum) {
        this.urlfotoAlum = urlfotoAlum;
    }

    public String getUrlfotoProf() {
        return urlfotoProf;
    }

    public void setUrlfotoProf(String urlfotoProf) {
        this.urlfotoProf = urlfotoProf;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }
}
