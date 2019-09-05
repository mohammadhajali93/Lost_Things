package com.mohammadhajali.mychat22;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class post extends AppCompatActivity {
    String url="http://track-kids.com/post2.php";

RequestQueue requestQueue;
    EditText e1;
    Button btn;String massage;

    private final static String TAG = "Fragment Chats";
    String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        requestQueue = Volley.newRequestQueue(getApplication().getApplicationContext());
        e1 = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                x = FirebaseInstanceId.getInstance().getToken();

                Log.e(TAG,"Token is :" + x);

                send();

                massage=e1.getText().toString();
            }
        });

    }

    private void send() {

       // final ArrayList<contact> array_contact = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

    }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put("message",massage);
                params.put("token",x);
                Log.e(TAG,"params :" +params);
            return  params;
            }

        };
        requestQueue.add(stringRequest);

}


}
