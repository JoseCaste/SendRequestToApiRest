package com.veterinaria.sendrequesttoapirest.services;

import com.veterinaria.sendrequesttoapirest.request.InsertServicePost;
import com.veterinaria.sendrequesttoapirest.request.Service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface PostServices {


    @POST("insertService2")
    Call<InsertServicePost> createService(@Body HashMap<String,Object> service);
    /*@POST("posts")
    Call<Service> createService(@Body Service service);*/

}
