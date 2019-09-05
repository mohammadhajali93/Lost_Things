package com.mohammadhajali.mychat22;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {


    private CircleImageView ProfileImage;
    private TextView UserName,UserStatus,user_email_phone;
    private Button edit_profile , change_image;
    String url="http://track-kids.com/profile.php";
    String url2="http://track-kids.com/retrive_profile.php";
    Bitmap bitmap;
    String first_name,last_name,user_name,status,image,email,phone_number;
    SharedPreferences sharedPreferences;
    DataSnapshot dataSnapshot;

    FirebaseAuth mAuth;
    private static final String TAG = "aaaaProfile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        initial();
        retrive_profile();
        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent ,1);
            }
        });
        //RetrieveUserInfo();
        //addData();

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_to_server();
                Intent intent = new Intent(Profile.this,MainPage.class);
                startActivity(intent);
                finish();
            }

            private void send_to_server() {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Log.i(TAG, "onResponse: " + response);



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String , String> params = new HashMap<>();


                        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        params.put("token",sharedPreferences.getString("token",null));
                        // params.put("token","EMAWcED56ILSrZC0h2Dgq1WMm9IhIUnd58yCRgbFCx6q6YXYiZB7IDRqCle5XeWEnM5m5ps2kC0RMgkZBkcsYz1g5AN7hNswVIRMrkZCvOVwZDZD");


                        params.put("image",imgeToString(bitmap));


                        Log.i(TAG ,"paramspro"+params);
                        return params ;
                    }

                };
                MySingleton.getmInstance(Profile.this).addToRequestQueue(stringRequest);

            }
        });







        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent ,1);

            }
        });






    }

    private void retrive_profile() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.i(TAG, "onResponse: " + response);


                try {

                    // JSONObject jsonObject = new JSONObject(response);
                    JSONArray arry = new JSONArray(response);

                    for (int i =0 ; i<arry.length(); i++) {

                        JSONObject jsonObject = arry.getJSONObject(i);

                        contact contact = new contact();



                        UserName.setText(jsonObject.getString("first_name"));



                        String image=jsonObject.getString("image");
                        user_email_phone.setText(jsonObject.getString("phone_number"));

                        Glide.with(Profile.this)
                                .load(image)
                                .into(ProfileImage);
                        //Picasso.get().load(image).placeholder(R.drawable.profile_image).into(ProfileImage);


                    }} catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();


                sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                params.put("token",sharedPreferences.getString("token",null));
                //  params.put("token","EMAWcED56ILSrZC0h2Dgq1WMm9IhIUnd58yCRgbFCx6q6YXYiZB7IDRqCle5XeWEnM5m5ps2kC0RMgkZBkcsYz1g5AN7hNswVIRMrkZCvOVwZDZD");

                //0798217736



                Log.i(TAG ,"paramspro"+params);
                return params ;
            }

        };
        MySingleton.getmInstance(Profile.this).addToRequestQueue(stringRequest);

    }

    private void addData() {
        UserName.setText(user_name);
        UserStatus.setText(status);
        user_email_phone.setText(email);

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(ProfileImage);
        }



    }

    private String imgeToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG ,20 ,byteArrayOutputStream);
        byte [] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte ,Base64.DEFAULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null) {
            Uri path = data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver() , path);
                ProfileImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void initial() {

        ProfileImage = findViewById(R.id.profile_image);
        UserName = findViewById(R.id.user_name);
        UserStatus = findViewById(R.id.profile_status);
        user_email_phone=findViewById(R.id.user_email_phone);
        edit_profile = findViewById(R.id.edit_profile);
        change_image = findViewById(R.id.change_image);


    }




    private void RetrieveUserInfo() {

        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        first_name= sharedPreferences.getString("firs_name",null);
        Log.i(TAG, "first_name: "+first_name);
        last_name= sharedPreferences.getString("last_name",null);
        user_name= first_name+"\t"+last_name;
        status=sharedPreferences.getString("status",null);
        email=sharedPreferences.getString("email",null);
        phone_number=sharedPreferences.getString("phone_number",null);
        image=sharedPreferences.getString("image",null);


    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //if the user is not logged in
//        //opening the login activity
//        if (mAuth.getCurrentUser() == null) {
//            finish();
//            startActivity(new Intent(this, MainPage.class));
//        }
//
//
//    }




}

