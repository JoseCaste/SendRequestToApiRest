package com.veterinaria.sendrequesttoapirest.ui.home;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.veterinaria.sendrequesttoapirest.R;
import com.veterinaria.sendrequesttoapirest.request.FileUtils;
import com.veterinaria.sendrequesttoapirest.request.InsertServicePost;
import com.veterinaria.sendrequesttoapirest.request.Service;
import com.veterinaria.sendrequesttoapirest.services.PostServices;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    Spinner spiner;
    Button btnSolicitar;
    TextView txtNombrePErro;
    TextView txtResponsable;
    TextView txtComentario;
    TextView txtNumTel;
    TextView txtPrecio;
    PostServices postServices;
    ImageView imagen;
    View root;
    String urlImage;
    Long idService;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] imageByte;
    private Uri selecUri;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        spiner= root.findViewById(R.id.spinner);
        txtNombrePErro=root.findViewById(R.id.txtNombrePerro);
        txtResponsable=root.findViewById(R.id.txtResponsable);
        txtComentario=root.findViewById(R.id.txtComentario);
        txtNumTel=root.findViewById(R.id.txtNumTel);
        txtPrecio=root.findViewById(R.id.txtPrecio);
        btnSolicitar=root.findViewById(R.id.btnSolicitar);
        imagen=root.findViewById(R.id.imgPerro);
        imagen.setOnClickListener(v -> {
            takePhoto();
        });
        btnSolicitar.setOnClickListener(v -> {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl("https://spring-boot-deploy-test.herokuapp.com/")
            .baseUrl("http://192.168.3.7:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            postServices=retrofit.create(PostServices.class);

            requestPost();
        });
        return root;
    }

    private void takePhoto() {
        Intent pickPhoto= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,1);

    }

    private void requestPost() {
        Service service= new Service();
        service.setNombrePerro(txtNombrePErro.getText().toString());
        service.setResponsable(txtResponsable.getText().toString());
        service.setComentarios(txtComentario.getText().toString());
        service.setPrecio(Integer.parseInt(txtPrecio.getText().toString()));
        service.setServicio(spiner.getSelectedItem().toString());
        service.setNumTel(txtNumTel.getText().toString());
        System.out.println("*******2"+service.toString());
        HashMap<String, Object> mapa= new HashMap<>();

        mapa.put("nombrePerro",service.getNombrePerro());
        mapa.put("responsable",service.getResponsable());
        mapa.put("comentarios",service.getComentarios());
        mapa.put("precio",service.getPrecio());
        mapa.put("servicio",service.getServicio());
        mapa.put("numTel",service.getNumTel());
        System.out.println("mapa "+mapa.toString());
        /**cuerpo del file**/
        File file= FileUtils.getFile(root.getContext(),selecUri);
        RequestBody requestFile= RequestBody.create(MediaType.parse(root.getContext().getContentResolver().getType(selecUri)),file);
        MultipartBody.Part bodyFile=MultipartBody.Part.createFormData("picture",file.getName(),requestFile);

        /**cuerpo del body**/
        //RequestBody body=RequestBody.create(MultipartBody.FORM, service.toString());


        Call<InsertServicePost> call= postServices.createService(mapa);

        call.enqueue(new Callback<InsertServicePost>() {
            @Override
            public void onResponse(Call<InsertServicePost> call, Response<InsertServicePost> response) {
                System.out.println("On response "+" "+response.body().getAt()+" "+response.body().getService().toString()+" "+response.body().getData());
                idService=response.body().getService().getId();
                Call<InsertServicePost> call2= postServices.insertImageService(idService,bodyFile);
                call2.enqueue(new Callback<InsertServicePost>() {
                    @Override
                    public void onResponse(Call<InsertServicePost> call, Response<InsertServicePost> response) {
                        System.out.println("String recibido: "+response.body().getService()+"data "+response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<InsertServicePost> call, Throwable t) {
                        System.out.println("fallo recibido: "+call.toString()+"--> "+t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<InsertServicePost> call, Throwable t) {
                Toast.makeText(getContext(),"Something Wrong",Toast.LENGTH_LONG);
                System.out.println("OnFailure "+call.toString()+"-----> "+t.getMessage()+" ----> "+t.toString());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && data!=null){
            selecUri= data.getData();
            Bitmap bitmap;
            String [] file={MediaStore.Images.Media.DATA};
            if(selecUri!=null){
                Cursor cursor= root.getContext().getContentResolver().query(selecUri,file,null,null,null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    //
                    urlImage=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));//url de la imagen del sd
                    System.out.println("---> "+ urlImage);
                    if (Build.VERSION.SDK_INT >= 29) {
                        try (ParcelFileDescriptor pfd = root.getContext().getContentResolver().openFileDescriptor(selecUri, "r")) {
                            if (pfd != null) {
                                bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                                imagen.setImageBitmap(bitmap);
                                cursor.close();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        int columnIndex = cursor.getColumnIndex(file[0]);
                        String picturePath = cursor.getString(columnIndex);
                        imagen.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        cursor.close();
                    }
                }
            }
        }

    }
}