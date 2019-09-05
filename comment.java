package com.mohammadhajali.mychat22;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class comment extends AppCompatActivity {

    SwipeRefreshLayout refresh_comment;
    private  final  static  String TAG ="coment";
    RecyclerView recyclerView;
    ImageButton btn_send_comment;
    EditText txt_send_comment;
    Button refresh;
    AdapterComment adapterComment;
    // ScrollView scrollView;
    RequestQueue requestQueue;
    String url="http://track-kids.com/comment.php";
    String url2="http://track-kids.com/comment_send.php";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView = findViewById(R.id.recylcerView_chats);
        refresh_comment = findViewById(R.id.refresh_comment);
        btn_send_comment = findViewById(R.id.btn_send_chat);
        txt_send_comment= findViewById(R.id.txt_sent_chat);
        refresh= findViewById(R.id.refresh);
        // scrollView= findViewById(R.id.scroll);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());

        load_comment();
        btn_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                load_comment();
                send_comment();
                txt_send_comment.setText("");
            //    Toast.makeText(comment.this ,"your commented" , Toast.LENGTH_LONG).show();

            }
        });
//        for (int i = 0; i < 100; i++) {
//            Bitmap bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
//        }

        refresh_comment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                load_comment();
              //    send_comment();
                if (refresh_comment.isRefreshing()) {
                    refresh_comment.setRefreshing(false);
//            }
                }
            }
        });



//        refresh_comment.post(new Runnable() {
//            @Override
//            public void run() {
//                load_comment();
//                refresh_comment.setRefreshing(true);
//
//            }
//        });
//


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                load_comment();
            }
        });
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                load_comment();
//            }
//        });

    }


    private void load_comment() {
//comment_send.php
        //   final List<Comments> commentsList = new ArrayList<>();
        final ArrayList<Comments> commentsList = new ArrayList<>();

        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse1" + response);
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject jsonObject = array.getJSONObject(i);
                                Comments comments = new Comments();
                                comments.setId_comment(jsonObject.getString("id_comment"));
                                comments.setId_user(jsonObject.getString("id"));
                                comments.setId_post(jsonObject.getString("id_post"));
                                comments.setName(jsonObject.getString("first_name"));
                                comments.setImage(jsonObject.getString("image"));
                                comments.setText_comment(jsonObject.getString("text"));
                                commentsList.add(comments);




                            }
                            adapterComment= new AdapterComment(getApplication(),commentsList);
                            recyclerView.setAdapter(adapterComment);
                            //dont duplecate the arraylist(commentslist) when refresh loading from server
                            adapterComment.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //traversing through all the object



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

                params.put("id_user",sharedPreferences.getString("id_user","null"));
                params.put("id_post",sharedPreferences.getString("id_post","null"));
                // params.put("new_update_comment",sharedPreferences.getString("new_update_comment","null"));



                Log.i(TAG, "getParams: " + params);
                return params;
            }

        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void send_comment() {
        final    ArrayList<Comments> commentsList = new ArrayList<>();

        //  Intent intent= getIntent();
        //final String id_user= intent.getStringExtra("id_user");
        //final String id_post= intent.getStringExtra("id_post");
        final String comment_text = txt_send_comment.getText().toString();
        if(comment_text.equals("")){
            Toast.makeText(comment.this ,"cannot send Empty",Toast.LENGTH_LONG).show();
        }
        else {

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: " + response);

//                try {
//                    JSONArray array = new JSONArray(response);
//
//                    for (int i = 0; i < array.length(); i++) {
//                        //getting product object from json array
//                        JSONObject jsonObject = array.getJSONObject(i);
//                        Comments comments = new Comments();
//                        comments.setId_comment(jsonObject.getString("id"));
//                        comments.setId_post(jsonObject.getString("id_post"));
//                        comments.setName(jsonObject.getString("id_user"));
//                        comments.setText_comment(jsonObject.getString("text"));
//                        commentsList.add(comments);
//
//                    }
//                    adapterComment= new AdapterComment(getApplication(),commentsList);
//                    recyclerView.setAdapter(adapterComment);
//                    // adapterComment.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                    //traversing through all the object

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

                    params.put("id_user", sharedPreferences.getString("id_user", "null"));
                    params.put("id_post", sharedPreferences.getString("id_post", "null"));
                    //   params.put("new_update_comment",sharedPreferences.getString("new_update_comment","null"));

                    params.put("comment_text", comment_text);

                    Log.i(TAG, "getParams: " + params);
                    return params;
                }
            };
            requestQueue.add(request);
            txt_send_comment.setText("");
            Toast.makeText(comment.this ,"your commented now",Toast.LENGTH_LONG).show();
        }
    }
}
