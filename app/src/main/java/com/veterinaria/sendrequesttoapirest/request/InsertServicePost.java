package com.veterinaria.sendrequesttoapirest.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertServicePost {

    @SerializedName("at")
    private String at;
    @SerializedName("service")
    private Service service;

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}

