package com.mohammadhajali.mychat22;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.accountkit.AccountKit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private Button btn_chat;

    SwipeRefreshLayout refresh_post;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    Button fillter ;
    ImageView image_menue;
    Adapter_post adapter;
    private final static String TAG = "contactbbbbbb";
    String url = "http://track-kids.com/post_test.php" ;
    String url2="http://track-kids.com/retrive_profile.php";

    Bitmap bitmap;
    SharedPreferences sharedPreferences;

    private ArrayList<contact> text=new ArrayList<contact>();
    private ArrayList<String> url_Img=new ArrayList<String>();
    private ArrayList<String> url_Img_user=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        btn_chat = findViewById(R.id.chat);

        initReecycleView();
        load_from_erver();

        // load_id_user();

//        btn_all_post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                load_from_erver();
//            }
//        });


//        btn_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gochat();
//            }
//        });




//        fillter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // send_server();
//
//                startActivity(new Intent(getApplicationContext(), Filter.class));
//            }
//        });
        refresh_post.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                load_from_erver();

                if (refresh_post.isRefreshing()) {
                    refresh_post.setRefreshing(false);
//            }
                }
            }
        });


    }

//    private void retrive_image_menue() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//
//                Log.i(TAG, "onResponse: " + response);
//
//
//                try {
//
//                    // JSONObject jsonObject = new JSONObject(response);
//                    JSONArray arry = new JSONArray(response);
//
//                    for (int i =0 ; i<arry.length(); i++) {
//
//                        JSONObject jsonObject = arry.getJSONObject(i);
//
//                        contact contact = new contact();
//
//
//
////                        UserName.setText(jsonObject.getString("first_name"));
//
//
//
//                        String image=jsonObject.getString("image");
////                        user_email_phone.setText(jsonObject.getString("phone_number"));
//
//                        Glide.with(MainPage.this)
//                                .load(image)
//                                .into(image_menue);
//                        //Picasso.get().load(image).placeholder(R.drawable.profile_image).into(ProfileImage);
//
//
//                    }} catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String , String> params = new HashMap<>();
//
//
//                sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                params.put("token",sharedPreferences.getString("token",null));
//                //  params.put("token","EMAWcED56ILSrZC0h2Dgq1WMm9IhIUnd58yCRgbFCx6q6YXYiZB7IDRqCle5XeWEnM5m5ps2kC0RMgkZBkcsYz1g5AN7hNswVIRMrkZCvOVwZDZD");
//
//                //0798217736
//
//
//
//                Log.i(TAG ,"paramspro"+params);
//                return params ;
//            }
//
//        };
//        MySingleton.getmInstance(MainPage.this).addToRequestQueue(stringRequest);
//
//    }

    public void logout() {

        AccountKit.logOut();
        // mAuth.signOut();

        String status = "logout";

        Log.i(TAG, "status: "+status);

        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status",status);
        editor.commit();


        finish();
        startActivity(new Intent(getApplicationContext(), login.class));



    }
    private void gochat() {

        Intent chat = new Intent(MainPage.this,RegisterActivity.class);
        startActivity(chat);
    }


    ArrayList<contact> array_contact1 = new ArrayList<>();

    private void load_from_erver1() {




        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "onResponse: " + response);


                try {

                    // JSONObject jsonObject = new JSONObject(response);
                    JSONArray arry = new JSONArray(response);

                    for (int i =0 ; i<arry.length(); i++) {

                        JSONObject jsonObject = arry.getJSONObject(i);

                        int put_post= Integer.parseInt(jsonObject.getString("x"));
                        contact contact = new contact();


                        if(put_post==1) {
                            url_Img_user.add(jsonObject.getString("image1"));
                            contact.setText_user_name(jsonObject.getString("first_name"));

                            contact.setId_user(jsonObject.getString("id"));
                            contact.setId_post(jsonObject.getString("id_post"));
                            contact.setTxt_post(jsonObject.getString("spacification"));
                            contact.setLocation(jsonObject.getString("latitude"));

                            contact.setTxt_area(jsonObject.getString("area_name"));
                            contact.setTxt_date(jsonObject.getString("date_time"));
                            contact.setTxt_rewerd(jsonObject.getString("reward"));
                            contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                            contact.setImg_post(jsonObject.getString("image1"));
                            contact.setTxt_amount(jsonObject.getString("amount"));

                            text.add(contact);
                            url_Img.add (jsonObject.getString("image"));
                        }
//                            if(im==null) {
//
//                                url_Img.add("data:image/jpeg;base64,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");
//
//                            }else {
//                                url_Img.add(im);
//                            }
                        else if(put_post==2){
                            url_Img_user.add(jsonObject.getString("image1"));
                            contact.setText_user_name(jsonObject.getString("first_name"));

                            contact.setId_user(jsonObject.getString("id"));
                            contact.setId_post(jsonObject.getString("id_post"));
                            contact.setTxt_post(jsonObject.getString("spacification"));
                            contact.setLocation(jsonObject.getString("latitude"));
                            contact.setTxt_area(jsonObject.getString("area_name"));
                            contact.setTxt_date(jsonObject.getString("date_time"));
                            contact.setTxt_rewerd(jsonObject.getString("reward"));
                            contact.setTxt_type_lost(jsonObject.getString("type"));
                            contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                            contact.setTxt_amount(jsonObject.getString("amount"));
                            url_Img.add (jsonObject.getString("image"));


                        }

                        else if(put_post==3){
                            url_Img_user.add(jsonObject.getString("image1"));
                            contact.setText_user_name(jsonObject.getString("first_name"));

                            contact.setId_user(jsonObject.getString("id"));
                            contact.setId_post(jsonObject.getString("id_post"));
                            contact.setTxt_post(jsonObject.getString("spacification"));
                            contact.setLocation(jsonObject.getString("latitude"));
                            contact.setTxt_area(jsonObject.getString("area_name"));
                            contact.setTxt_date(jsonObject.getString("date_time"));
                            contact.setTxt_rewerd(jsonObject.getString("reward"));
                            contact.setTxt_type_lost(jsonObject.getString("type"));
                            contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                            contact.setType_other(jsonObject.getString("type_other"));
                            url_Img.add (jsonObject.getString("image"));
                        }

                        else if(put_post==4){
                            url_Img_user.add(jsonObject.getString("image1"));
                            contact.setText_user_name(jsonObject.getString("first_name"));

                            contact.setId_user(jsonObject.getString("id"));
                            contact.setId_post(jsonObject.getString("id_post"));
                            contact.setTxt_post(jsonObject.getString("spacification"));
                            contact.setLocation(jsonObject.getString("latitude"));
                            contact.setTxt_area(jsonObject.getString("area_name"));
                            contact.setTxt_date(jsonObject.getString("date_time"));
                            contact.setTxt_rewerd(jsonObject.getString("reward"));
                            contact.setTxt_type_lost(jsonObject.getString("type"));
                            contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                            contact.setTxt_type_lost(jsonObject.getString("type"));
                            contact.setType_other(jsonObject.getString("type_jewelery"));
                            url_Img.add (jsonObject.getString("image"));
                        }

                        else if(put_post==5){
                            url_Img_user.add(jsonObject.getString("image1"));
                            contact.setText_user_name(jsonObject.getString("first_name"));

                            contact.setId_user(jsonObject.getString("id"));
                            contact.setId_post(jsonObject.getString("id_post"));
                            contact.setTxt_post(jsonObject.getString("spacification"));
                            contact.setLocation(jsonObject.getString("latitude"));
                            contact.setTxt_area(jsonObject.getString("area_name"));
                            contact.setTxt_date(jsonObject.getString("date_time"));
                            contact.setTxt_rewerd(jsonObject.getString("reward"));
                            contact.setTxt_type_lost(jsonObject.getString("type"));
                            contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                            contact.setTxt_type_lost(jsonObject.getString("card_id"));
                            contact.setTxt_type_lost(jsonObject.getString("type"));
                            contact.setType_other(jsonObject.getString("type_doc"));
                            url_Img.add (jsonObject.getString("image"));

                        }


//                        array_contact1.add(contact);
//                     adapter.setfilter(array_contact1);
                        adapter = new Adapter_post(getApplication() ,url_Img ,url_Img_user ,array_contact);
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(url_Img.size()-1);
                        adapter.notifyDataSetChanged();


                    }} catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("search","no_search");




                return params;
            }
        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }

    private void send_server() {

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
                params.put("search","search");



                return params;
            }
        };
        MySingleton.getmInstance(MainPage.this).addToRequestQueue(stringRequest);

    }

    ArrayList<contact> array_contact = new ArrayList<>();

    private void load_from_erver() {
        //    url_Img.add("data:image/jpeg;base64,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");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);

                try {

                    // JSONObject jsonObject = new JSONObject(response);
                    JSONObject Object = new JSONObject(response);

                    // JSONObject object1 = Object.getJSONObject("type");

                    JSONArray arry = Object.getJSONArray("11");
                    //   JSONArray arry = new JSONArray(response);

                    for (int i = 0; i < arry.length(); i++) {

                        JSONObject jsonObject = arry.getJSONObject(i);
                        int type = Integer.parseInt(jsonObject.getString("y"));

                        if (type == 1) {
                            int put_post = Integer.parseInt(jsonObject.getString("x"));
                            contact contact = new contact();
                            if (put_post == 1) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setImg_post(jsonObject.getString("image1"));
                                contact.setTxt_amount(jsonObject.getString("amount"));

                                text.add(contact);
                                url_Img.add(jsonObject.getString("image"));
                            }
//                            if(im==null) {
//
//                                url_Img.add("data:image/jpeg;base64,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");
//
//                            }else {
//                                url_Img.add(im);
//                            }
                            else if (put_post == 2) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setTxt_amount(jsonObject.getString("amount"));
                                url_Img.add(jsonObject.getString("image"));

                            } else if (put_post == 3) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setType_other(jsonObject.getString("type_other"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 4) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_jewelery"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 5) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("card_id"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_doc"));
                                url_Img.add(jsonObject.getString("image"));

                            }


                            array_contact.add(contact);
                            adapter = new Adapter_post(getApplication(), url_Img, url_Img_user, array_contact);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            //recyclerView.scrollToPosition(url_Img.size()-1);

                        }
                        else if(type==2){

                            int put_post = Integer.parseInt(jsonObject.getString("x"));
                            contact contact = new contact();
                            if (put_post == 1) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setType_other(jsonObject.getString("type_other"));
                                url_Img.add(jsonObject.getString("image"));



                            }
//                            if(im==null) {
//
//                                url_Img.add("data:image/jpeg;base64,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");
//
//                            }else {
//                                url_Img.add(im);
//                            }
                            else if (put_post == 2) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setTxt_amount(jsonObject.getString("amount"));
                                url_Img.add(jsonObject.getString("image"));

                            } else if (put_post == 3) {

                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setImg_post(jsonObject.getString("image1"));
                                contact.setTxt_amount(jsonObject.getString("amount"));

                                text.add(contact);
                                url_Img.add(jsonObject.getString("image"));

                            } else if (put_post == 4) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_jewelery"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 5) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("card_id"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_doc"));
                                url_Img.add(jsonObject.getString("image"));

                            }


                            array_contact.add(contact);
                            adapter = new Adapter_post(getApplication(), url_Img, url_Img_user, array_contact);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        }
                        else if(type==3){
                            int put_post = Integer.parseInt(jsonObject.getString("x"));
                            contact contact = new contact();
                            if (put_post == 1) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_jewelery"));
                                url_Img.add(jsonObject.getString("image"));

                            }
//                            if(im==null) {
//
//                                url_Img.add("data:image/jpeg;base64,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");
//
//                            }else {
//                                url_Img.add(im);
//                            }
                            else if (put_post == 2) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setTxt_amount(jsonObject.getString("amount"));
                                url_Img.add(jsonObject.getString("image"));

                            } else if (put_post == 3) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setType_other(jsonObject.getString("type_other"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 4) {

                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setImg_post(jsonObject.getString("image1"));
                                contact.setTxt_amount(jsonObject.getString("amount"));

                                text.add(contact);
                                url_Img.add(jsonObject.getString("image"));

                            } else if (put_post == 5) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("card_id"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_doc"));
                                url_Img.add(jsonObject.getString("image"));

                            }


                            array_contact.add(contact);
                            adapter = new Adapter_post(getApplication(), url_Img, url_Img_user, array_contact);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                        else if(type==4){
                            int put_post = Integer.parseInt(jsonObject.getString("x"));
                            contact contact = new contact();
                            if (put_post == 1) {

                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setTxt_amount(jsonObject.getString("amount"));
                                url_Img.add(jsonObject.getString("image"));


                            }
//                            if(im==null) {
//
//                                url_Img.add("data:image/jpeg;base64,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");
//
//                            }else {
//                                url_Img.add(im);
//                            }
                            else if (put_post == 2) {


                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setImg_post(jsonObject.getString("image1"));
                                contact.setTxt_amount(jsonObject.getString("amount"));

                                text.add(contact);
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 3) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setType_other(jsonObject.getString("type_other"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 4) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_jewelery"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 5) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("card_id"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_doc"));
                                url_Img.add(jsonObject.getString("image"));

                            }


                            array_contact.add(contact);
                            adapter = new Adapter_post(getApplication(), url_Img, url_Img_user, array_contact);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                        else if(type==5){
                            int put_post = Integer.parseInt(jsonObject.getString("x"));
                            contact contact = new contact();
                            if (put_post == 1) {

                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("card_id"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_doc"));
                                url_Img.add(jsonObject.getString("image"));




                            }
//                            if(im==null) {
//
//                                url_Img.add("data:image/jpeg;base64,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");
//
//                            }else {
//                                url_Img.add(im);
//                            }
                            else if (put_post == 2) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setTxt_amount(jsonObject.getString("amount"));
                                url_Img.add(jsonObject.getString("image"));

                            } else if (put_post == 3) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));
                                contact.setType_other(jsonObject.getString("type_other"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 4) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setTxt_type_lost(jsonObject.getString("type"));
                                contact.setType_other(jsonObject.getString("type_jewelery"));
                                url_Img.add(jsonObject.getString("image"));
                            } else if (put_post == 5) {
                                url_Img_user.add(jsonObject.getString("image1"));
                                contact.setText_user_name(jsonObject.getString("first_name"));

                                contact.setId_user(jsonObject.getString("id"));
                                contact.setId_post(jsonObject.getString("id_post"));
                                contact.setTxt_post(jsonObject.getString("spacification"));

                                contact.setLocation_latitude(jsonObject.getString("latitude"));
                                contact.setLocation_longitude(jsonObject.getString("longitude"));

                                contact.setTxt_area(jsonObject.getString("area_name"));
                                contact.setTxt_date(jsonObject.getString("date_time"));
                                contact.setTxt_rewerd(jsonObject.getString("reward"));
                                contact.setTxt_loster_founder(jsonObject.getString("lost_found"));

                                contact.setImg_post(jsonObject.getString("image1"));
                                contact.setTxt_amount(jsonObject.getString("amount"));

                                text.add(contact);
                                url_Img.add(jsonObject.getString("image"));
                            }


                            array_contact.add(contact);
                            adapter = new Adapter_post(getApplication(), url_Img, url_Img_user, array_contact);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }} catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("search","no_search");




                return params;
            }
        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


//    public void plus(View view) {
//
//
//        startActivity(new Intent(getApplicationContext(), PostData.class));
//       // finish();
//    }

    private void initReecycleView() {
      //  image_menue=findViewById(R.id.image_menue);
        refresh_post=findViewById(R.id.refresh_post);
        //fillter=findViewById(R.id.fillter);
        requestQueue = Volley.newRequestQueue(getApplication().getApplicationContext());
        recyclerView = findViewById(R.id.recycle_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.home) {

        }
        else if (id == R.id.img_plus) {
            startActivity(new Intent(getApplicationContext(), PostData.class));
            //finish();

        }
        else if (id == R.id.fillter) {
            startActivity(new Intent(getApplicationContext(), Filter.class));
            //finish();

        }

        else if (id == R.id.chat) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            //finish();

        }
        // else if (id == R.id.setting) {

        //}
        else if (id == R.id.help) {

            Intent intent = new Intent(MainPage.this,helpActivity.class);
            startActivity(intent);

        } else if (id == R.id.share) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("Text/plain");
            String ShareBody = "your body here";
            String ShareSub = "your body here";

            intent.putExtra(Intent.EXTRA_TEXT,ShareBody);
            intent.putExtra(Intent.EXTRA_SUBJECT,ShareSub);
            startActivity(Intent.createChooser(intent,"Share Using"));
        }
        else if (id == R.id.profile) {

            Intent intent = new Intent(MainPage.this,Profile.class);
            startActivity(intent);
            finish();

        }
        else if (id == R.id.exit) {

            logout();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //    public void load(View view) {
//        //url_Img.add("data:image/jpeg;base64,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");
//
////        Adapter_post adapter = new Adapter_post(getApplication(),url_Img ,text);
////        recyclerView.setAdapter(adapter);
//        //recyclerView.scrollToPosition(array_contact.size()-1);
//        //load_from_erver();
//
//    }

}
