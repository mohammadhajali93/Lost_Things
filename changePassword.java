package com.mohammadhajali.mychat22;







import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class changePassword extends AppCompatActivity {


    EditText txt_pass,txt_re_pass;
    Button btn_login;
    TextView txt_error;

    private static final String TAG ="ffff" ;
    String token,last_refresh,message="",password,phone_number;
    String url="http://track-kids.com/change_pass.php";

    boolean c=true;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initial();
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        phone_number= sharedPreferences.getString("phone_number",null);

        Intent I= new Intent();
        I=getIntent();
        token=I.getStringExtra("token" );
        last_refresh=I.getStringExtra("last_refresh" );


        Log.i(TAG, "token: "+token);
        Log.i(TAG, "last_refresh: "+last_refresh);

        check_user();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sumData();

                if(! validation (c)) {
                    txt_error.setText(message);

                }
                else {
                    change_pass();
                }



            }
        });




//        if
//        Intent intent_send_phone_number = new Intent(getApplicationContext(), Registration.class);
//
//        intent_send_phone_number.putExtra("token", token);
//        intent_send_phone_number.putExtra("lastRefresh", lastRefresh);
//
//
//        Log.i(TAG, "token: " + token);
//        Log.i(TAG, "lastRefresh: " + lastRefresh);
//
//
//        startActivity(intent_send_phone_number);
//        finish();






    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void check_user() {


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message= jsonObject.getString("x");
                            Log.i(TAG, "message "+message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i(TAG, "response: "+response);
                        Log.i(TAG, "message: "+message);

                        if(message.equals("Username isn't Exist")) {

                            AlertDialog.Builder a_builder = new AlertDialog.Builder(changePassword.this, R.style.AlertDialog);
                            a_builder.setMessage("User does not exist, do you want to register as a new user ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent_send_phone_number = new Intent(getApplicationContext(), Registration.class);

                                            intent_send_phone_number.putExtra("token", token);
                                            intent_send_phone_number.putExtra("last_refresh", last_refresh);

                                            Log.i(TAG, "token:1 " + token);
                                            Log.i(TAG, "last_refresh:1 " + last_refresh);

                                            startActivity(intent_send_phone_number);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getApplicationContext(), login.class));
                                            finish();
                                        }
                                    }) ;
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("Alert !!!");
                            alert.show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                makeText(getApplicationContext(), "Error", LENGTH_SHORT).show();
                error.printStackTrace();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("phone_number",phone_number);



                Log.i(TAG, "getParams: "+params);
                return params;
            }
        };


        //  requestQueue.add(stringRequest);

        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public boolean validation(boolean c){
        c=true;
        message="";

        if(txt_pass.getText().toString().isEmpty()){
            message+="Please Enter Password \n";
            c=false;

        }
        else if(txt_pass.getText().toString().length()<7 ) {
            message+="Length of Password is Short \n";
            c=false;

        }
        if(txt_re_pass.getText().toString().isEmpty()){
            message+="Please Enter RePassword \n";
            c=false;

        }
        else if(!txt_re_pass.getText().toString().equals(password) ) {
            message+="RePassword isn't identical\n";
            c=false;

        }
        return  c;

    }

    public void sumData(){



        password=txt_pass.getText().toString();





    }
    public void initial(){
        btn_login=(Button)findViewById(R.id.btn_login);
        txt_pass=(EditText)findViewById(R.id.txt_password);
        txt_re_pass=(EditText)findViewById(R.id.txt_re_password);
        txt_error=(TextView)findViewById(R.id.txt_error);
    }







    public void change_pass(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message= jsonObject.getString("x");
                            Log.i(TAG, "message "+message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i(TAG, "response: "+response);
                        Log.i(TAG, "message: "+message);

                        if(message.equals("Change Password Successfuly")) {
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                            finish();
                        }

                        else
                        {
                            txt_error.setText(message);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                makeText(getApplicationContext(), "Error", LENGTH_SHORT).show();
                error.printStackTrace();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("password",password);
                params.put("phone_number",phone_number);



                Log.i(TAG, "getParams: "+params);
                return params;
            }
        };


        //  requestQueue.add(stringRequest);

        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}
