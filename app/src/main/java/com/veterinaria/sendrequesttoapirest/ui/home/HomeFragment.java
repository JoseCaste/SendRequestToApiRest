package com.veterinaria.sendrequesttoapirest.ui.home;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
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

import com.veterinaria.sendrequesttoapirest.R;
import com.veterinaria.sendrequesttoapirest.request.InsertServicePost;
import com.veterinaria.sendrequesttoapirest.request.Service;
import com.veterinaria.sendrequesttoapirest.services.PostServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] imageByte;


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
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://spring-boot-deploy-test.herokuapp.com/")
            //.baseUrl("http://192.168.3.7:8084/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postServices=retrofit.create(PostServices.class);

            requestPost();
        });
        return root;
    }

    private void takePhoto() {
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(root.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
        Intent pickPhoto= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,1);

    }

    private void requestPost() {
        Service service= new Service();
        /*service.setTitle("foo");
        service.setBody("bar");
        service.setUserId(1);*/

        service.setNombrePerro(txtNombrePErro.getText().toString());
        service.setResponsable(txtResponsable.getText().toString());
        service.setComentarios(txtComentario.getText().toString());
        service.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
        service.setServicio(spiner.getSelectedItem().toString());
        service.setNumTel(txtNumTel.getText().toString());


        /*imagen.buildDrawingCache();
        Bitmap bmap = imagen.getDrawingCache();
        ByteArrayOutputStream array= new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        imageByte= array.toByteArray();

        String i="";
        for (byte au:imageByte
             ) {
            i=i+au;

        }
        service.setData(imageByte);*/
        System.out.println("*******2"+service.toString());
        HashMap<String, Object> mapa= new HashMap<>();

        mapa.put("nombrePerro",service.getNombrePerro());
        mapa.put("responsable",service.getResponsable());
        mapa.put("comentarios",service.getComentarios());
        mapa.put("precio",service.getPrecio());
        mapa.put("servicio",service.getServicio());
        mapa.put("numTel",service.getNumTel());
        //mapa.put("data",service.getData());
        Call<InsertServicePost> call= postServices.createService(mapa);

        call.enqueue(new Callback<InsertServicePost>() {
            @Override
            public void onResponse(Call<InsertServicePost> call, Response<InsertServicePost> response) {
                System.out.println("On response "+response.body().getAt());
                System.out.println("On response "+response.body().getService().toString());
                //Toast.makeText(getContext(),"Reserva lista "+response.body(),Toast.LENGTH_LONG).show();

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
        /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            ByteArrayOutputStream array= new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
            imageByte= array.toByteArray();*/
            /*Bitmap b= BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
            imagen.setImageBitmap(b);*/
        //}
        if(resultCode==RESULT_OK && data!=null){
            Uri selecUri= data.getData();
            Bitmap bitmap;
            String [] file={MediaStore.Images.Media.DATA};
            if(selecUri!=null){
                Cursor cursor= root.getContext().getContentResolver().query(selecUri,file,null,null,null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    if (Build.VERSION.SDK_INT >= 29) {
                        // You can replace '0' by 'cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)'
                        // Note that now, you read the column '_ID' and not the column 'DATA'
                        Uri imageUri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));

                        // now that you have the media URI, you can decode it to a bitmap
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