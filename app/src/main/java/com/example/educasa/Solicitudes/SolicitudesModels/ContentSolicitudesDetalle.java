package com.example.educasa.Solicitudes.SolicitudesModels;

public class ContentSolicitudesDetalle  {
    private String nombre;
    private String precio;
    private String descripcion;
    private int FotoPerfil;
    //private Button Aceptar;
    //private Button Cancelar;



    public ContentSolicitudesDetalle(String nombre, String precio, String descripcion, int fotoPerfil) {

        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        FotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFotoPerfil() {
        return FotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        FotoPerfil = fotoPerfil;
    }
}
