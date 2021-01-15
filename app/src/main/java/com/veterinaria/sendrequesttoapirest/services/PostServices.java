package com.veterinaria.sendrequesttoapirest.services;

import com.veterinaria.sendrequesttoapirest.request.InsertServicePost;
import com.veterinaria.sendrequesttoapirest.request.ServiceWithImage;

import java.util.HashMap;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface PostServices {
    @Multipart
    @POST("insertImageService/{id}")
    Call<ServiceWithImage> insertImageService(@Path("id") Long id, @Part MultipartBody.Part file);

    @POST("insertService2")
    Call<InsertServicePost> createService(@Body HashMap<String, Object> service);
}
