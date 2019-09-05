package com.mohammadhajali.mychat22;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Delete_post extends AppCompatActivity {


    RequestQueue requestQueue;
    Button Del , back;
    String url="http://track-kids.com/delete_post.php";
    private  static  final  String TAG ="del";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_post);

        initial();

        Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_post_from_Server();
                Toast.makeText(Delete_post.this ,"the post Deleted",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Delete_post.this, MainPage.class);
                startActivity(intent);
               finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Delete_post.this, MainPage.class);
                startActivity(intent);
               finish();
            }
        });
    }private void Delete_post_from_Server() {

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

                params.put("id_post_del",sharedPreferences.getString("id_post","null"));

                Log.i(TAG ,"params1"+params);

                return params;
            }
        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


    private void initial() {
        Del=findViewById(R.id.btn_delete);
        back=findViewById(R.id.btn_backto_post);
    }
}
