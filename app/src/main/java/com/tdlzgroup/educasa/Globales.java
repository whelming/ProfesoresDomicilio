package com.tdlzgroup.educasa;

import android.app.Application;

public class Globales extends Application  {
    public String tipoUsuario;

    public Globales() {

    }

    public Globales(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
