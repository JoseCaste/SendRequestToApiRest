package com.veterinaria.sendrequesttoapirest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {
   /* private int id;

    private String title;
    private String body;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }*/
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
    private Double precio;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("data")
    private byte[] data;
    @SerializedName("at")
    private Object at;


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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Service{" +
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

