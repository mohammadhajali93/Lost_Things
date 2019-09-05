package com.mohammadhajali.mychat22;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Update_post extends AppCompatActivity {

    Bitmap bitmap;
    ImageView image_update;
    RequestQueue requestQueue;
    Button Update , back , btn_update_image;
    EditText update_text_post;
    String url="http://track-kids.com/Update_post.php";
    private  static  final  String TAG ="del";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        initial();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_post_from_Server();
                Toast.makeText(Update_post.this ,"the post Updated",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Update_post.this, MainPage.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Update_post.this, MainPage.class);
                startActivity(intent);
                finish();
            }
        });

        btn_update_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_update.setVisibility(View.VISIBLE);

            }
        });
        image_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent ,1);
            }
        });
    }

    private void Update_post_from_Server() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

                if(bitmap==null){
                    params.put("image","");
                }else {
                params.put("image",imgeToString(bitmap));}

                params.put("id_post_update",sharedPreferences.getString("id_post","null"));
                params.put("text",update_text_post.getText().toString());

                Log.i(TAG ,"params1"+params);

                return params;
            }
        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private String imgeToString(Bitmap bitmap){
        this.bitmap = bitmap;
        byte [] imgByte = new byte[0];
        if (bitmap==null) {
            bitmap=null;
        }
        else{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG ,20 ,byteArrayOutputStream);
       imgByte = byteArrayOutputStream.toByteArray();}

        return Base64.encodeToString(imgByte ,Base64.DEFAULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null) {
            Uri path = data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver() , path);
                image_update.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    private void initial() {
        Update=findViewById(R.id.btn_update);
        btn_update_image=findViewById(R.id.btn_update_imge);
        back=findViewById(R.id.btn_backto_post2);
        update_text_post=findViewById(R.id.update_text_post);
        image_update=findViewById(R.id.image_update);

    }
}