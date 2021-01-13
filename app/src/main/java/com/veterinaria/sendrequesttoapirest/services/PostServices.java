package com.veterinaria.sendrequesttoapirest.services;

import com.veterinaria.sendrequesttoapirest.request.InsertServicePost;
import com.veterinaria.sendrequesttoapirest.request.Service;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;


public interface PostServices {
    @Multipart
    @POST("insertImageService/{id}")
    Call<InsertServicePost> insertImageService(@Path("id") Long id, @Part MultipartBody.Part file);

    @POST("insertService2")
    Call<InsertServicePost> createService(@Body HashMap<String, Object> service);
}
