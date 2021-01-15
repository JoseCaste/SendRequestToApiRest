package com.veterinaria.sendrequesttoapirest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Blob;

public class Service {
    @SerializedName("id")
    private Long id;
    @SerializedName("nombrePerro")
    private String nombrePerro;
    @SerializedName("responsable")
    private String responsable;
    @SerializedName("servicio")
    private String servicio;
    @SerializedName("comentarios")
    private String comentarios;
    @SerializedName("numTel")
    private String numTel;
    @SerializedName("precio")
    private int precio;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("data")
    private String data;
    @SerializedName("at")
    private Object at;
    @SerializedName("imgBase64")
    private String imgaBase64;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String  getData() {
        return data;
    }

    public void setData(String  data) {
        this.data = data;
    }

    public String getImgaBase64() {
        return imgaBase64;
    }

    public void setImgaBase64(String imgaBase64) {
        this.imgaBase64 = imgaBase64;
    }

    public Object getAt() {
        return at;
    }

    public void setAt(Object at) {
        this.at = at;
    }

    public String getNombrePerro() {
        return nombrePerro;
    }

    public void setNombrePerro(String nombrePerro) {
        this.nombrePerro = nombrePerro;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                "nombrePerro='" + nombrePerro + '\'' +
                ", responsable='" + responsable + '\'' +
                ", servicio='" + servicio + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", numTel='" + numTel + '\'' +
                ", precio=" + precio +
                ", fecha='" + fecha + '\'' +
                ", data=" + data +
                '}';
    }
}

