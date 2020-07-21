package com.tdlzgroup.educasa;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Globales extends Application  {
    public String tipoUsuario;
    public String urlFotoUser;

    public boolean servicioNotificacion;
    public int numberNotifSolicitu;

    public Globales() {
        servicioNotificacion = false;
        numberNotifSolicitu = 0;
    }

    public static String cleanTextSpecial(String texto){
        String cadenaNormalize = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
    }
    public static void showSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if(activity.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public String formatHour( Date fechahora) {

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        SimpleDateFormat hora = new SimpleDateFormat("HH");

        String dateString = format.format(fechahora);
        String hora24 = hora.format(fechahora);
        String ampm;

        if(Integer.parseInt(hora24)> 12){
            ampm = " pm";
        }
        else {
            ampm = " am";
        }
        dateString+=ampm;

        return dateString;
    }

    public String formatDate( Date fechahora) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        String dateString = format.format(fechahora);
        return dateString;
    }

    public class perfilUser{
        public double tipo;
        public String nombre;
        public String email;
        public String dni;
        public String celular;
        public Date creacion;
        public String urlfoto;
        public String departamento;
        public String provincia;
        public String distrito;

        public perfilUser() {
        }

        public double getTipo() {
            return tipo;
        }

        public void setTipo(double tipo) {
            this.tipo = tipo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDni() {
            return dni;
        }

        public void setDni(String dni) {
            this.dni = dni;
        }

        public String getCelular() {
            return celular;
        }

        public void setCelular(String celular) {
            this.celular = celular;
        }

        public Date getCreacion() {
            return creacion;
        }

        public void setCreacion(Date creacion) {
            this.creacion = creacion;
        }

        public String getUrlfoto() {
            return urlfoto;
        }

        public void setUrlfoto(String urlfoto) {
            this.urlfoto = urlfoto;
        }

        public String getDepartamento() {
            return departamento;
        }

        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getDistrito() {
            return distrito;
        }

        public void setDistrito(String distrito) {
            this.distrito = distrito;
        }
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getUrlFotoUser() {
        return urlFotoUser;
    }

    public void setUrlFotoUser(String urlFotoUser) {
        this.urlFotoUser = urlFotoUser;
    }

    public boolean isServicioNotificacion() {
        return servicioNotificacion;
    }

    public void setServicioNotificacion(boolean servicioNotificacion) {
        this.servicioNotificacion = servicioNotificacion;
    }

    public int getNumberNotifSolicitu() {
        return numberNotifSolicitu;
    }

    public void setNumberNotifSolicitu(int numberNotifSolicitu) {
        this.numberNotifSolicitu = numberNotifSolicitu;
    }
}
