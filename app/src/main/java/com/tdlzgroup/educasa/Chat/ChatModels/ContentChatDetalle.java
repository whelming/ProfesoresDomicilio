package com.tdlzgroup.educasa.Chat.ChatModels;

import java.util.Date;

public class ContentChatDetalle {
    String id;
    double usuario;
    String mensaje;
    Date fechahora;

    public ContentChatDetalle() {

    }


    public ContentChatDetalle(String id, double usuario, String mensaje, Date fechahora) {
        this.id = id;
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.fechahora = fechahora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getUsuario() {
        return usuario;
    }

    public void setUsuario(double usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }
}
