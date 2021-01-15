package com.veterinaria.sendrequesttoapirest.request;

import com.google.gson.annotations.SerializedName;

public class ServiceWithImage {

    @SerializedName("service")
    private Service service;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
