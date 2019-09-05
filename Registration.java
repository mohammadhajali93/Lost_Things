//package com.mohammadhajali.mychat22;
//
//import android.app.DatePickerDialog;
//import android.app.DatePickerDialog.OnDateSetListener;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Layout;
//import android.util.Base64;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.bumptech.glide.Glide;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static android.widget.Toast.*;
//
//public class Registration extends AppCompatActivity {
//
//    private CircleImageView image_register;
//    Bitmap bitmap;
//    Button btn_next_to_main;
//    TextView error;
//    EditText txt_first_name,txt_last_name,txt_pass,txt_repass;
//    Spinner txt_country,txt_area;
//    RadioGroup rg;
//    RadioButton rb;
//    int year,month,day;
//    RequestQueue requestQueue;
//    String phone_number,token,last_refresh,first_name,last_name,password,country="",area="",gender="",dateOfBirth="",status,message="";
//    boolean c=true;
//    String server_url="http://track-kids.com/insert_info.php";
//
//    AlertDialog.Builder builder;
//
//
//    public static final String TAG="fffffRegReg";
//    SharedPreferences sharedPreferences;
//
//    // FirebaseAuth mAuth;
//    long time;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        initial();
//        //  googleLogin();
//
//
//        image_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent ,1);
//            }
//        });
//        btn_next_to_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                sumdata();
//                if(! validation (c)) {
//                    error.setText(message);
//
//                }
//                else {
//                    sendData();
//                    startActivity(new Intent(getApplicationContext(), MainPage.class));
//                    finish();
//                }
//
//
//            }
//        });
//
//
//    }
//
//
//    public void exit() {
//
//        if (time + 2000 > System.currentTimeMillis ()) {
//            moveTaskToBack ( true );
//            android.os.Process.killProcess(android.os.Process.myPid());
//
//            System.exit(1);
//
//
//        }
//        else {
//            Toast.makeText ( this,"اضغط مرة أخرى للخروج" ,Toast.LENGTH_SHORT).show ();
//
//        }
//        time = System.currentTimeMillis ();
//    }
//
//
////    private void googleLogin() {
////
////
////
////        mAuth = FirebaseAuth.getInstance();
////
////        FirebaseUser user = mAuth.getCurrentUser();
////
////
////
////
////
////
//////        btn_logout.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View view) {
//////                mAuth.signOut();
//////                finish();
//////                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//////            }
//////        });
////    }
//
//
//    public boolean validation(boolean c){
//        c=true;
//        message="";
//        if(txt_first_name.getText().toString().isEmpty()) {
//            message+="Please Enter Your First Name \n";
//            c=false;
//        }
//        else if(txt_first_name.getText().toString().contains("1") || txt_first_name.getText().toString().contains("0") || txt_first_name.getText().toString().contains("1") || txt_first_name.getText().toString().contains("2") || txt_first_name.getText().toString().contains("3") || txt_first_name.getText().toString().contains("4") || txt_first_name.getText().toString().contains("5") || txt_first_name.getText().toString().contains("6") || txt_first_name.getText().toString().contains("7") || txt_first_name.getText().toString().contains("8") || txt_first_name.getText().toString().contains("9")) {
//            message+="Please Enter Your First Name Correctly \n";
//            c=false;
//
//        }
//
//        if(txt_last_name.getText().toString().isEmpty()) {
//            message+="Please Enter Your last Name \n";
//            c=false;
//
//        }
//        else if(txt_last_name.getText().toString().contains("1") || txt_last_name.getText().toString().contains("0") || txt_last_name.getText().toString().contains("1") || txt_last_name.getText().toString().contains("2") || txt_last_name.getText().toString().contains("3") || txt_last_name.getText().toString().contains("4") || txt_last_name.getText().toString().contains("5") || txt_last_name.getText().toString().contains("6") || txt_last_name.getText().toString().contains("7") || txt_last_name.getText().toString().contains("8") || txt_last_name.getText().toString().contains("9")) {
//            message+="Please Enter Your last Name Correctly \n";
//            c=false;
//
//        }
//        if(txt_pass.getText().toString().isEmpty()){
//            message+="Please Enter Password \n";
//            c=false;
//
//        }
//        else if(txt_pass.getText().toString().length()<7 ) {
//            message+="Length of Password is Short \n";
//            c=false;
//
//        }
//        if(txt_repass.getText().toString().isEmpty()){
//            message+="Please Enter RePassword \n";
//            c=false;
//
//        }
//        else if(!txt_repass.getText().toString().equals(password) ) {
//            message+="RePassword isn't identical\n";
//            c=false;
//
//        }
//        return  c;
//
//    }
//    public void initial(){
//
//
//        btn_next_to_main=(Button)findViewById(R.id.btn_next_to_main);
//        txt_first_name=(EditText)findViewById(R.id.txt_first_name);
//        txt_last_name=(EditText)findViewById(R.id.txt_last_name);
//        txt_pass=(EditText)findViewById(R.id.txt_pass);
//        txt_repass=(EditText)findViewById(R.id.txt_repass);
//        txt_country=(Spinner)findViewById(R.id.txt_country);
//        txt_area=(Spinner)findViewById(R.id.txt_area);
//        rg=(RadioGroup)findViewById(R.id.radiogroup);
//        error=(TextView)findViewById(R.id.error);
//        image_register=findViewById(R.id.image_register);
//
//    }
//
//
//    public void sumdata(){
//
//
//        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        phone_number= sharedPreferences.getString("phone_number",null);
//        status=sharedPreferences.getString("status",null);
//
//        Intent intent_get_phone_number=getIntent();
//        token= intent_get_phone_number.getStringExtra("token");
//        editor.putString("token",token);
//        editor.commit();
//
//
//        last_refresh= intent_get_phone_number.getStringExtra("last_refresh");
//        first_name=txt_first_name.getText().toString();
//        last_name=txt_last_name.getText().toString();
//        password=txt_pass.getText().toString();
//        country=txt_country.getSelectedItem().toString();
//        area=txt_area.getSelectedItem().toString();
//
//        int selected=rg.getCheckedRadioButtonId();
//        if(selected!=-1) {
//            Log.i(TAG, "selected: "+selected);
//            rb = (RadioButton) findViewById(selected);
//            gender = rb.getText().toString();
//        }
//
//
//
//
//        editor.putString("email",phone_number);
//        editor.putString("status",status);
//        editor.putString("firs_name",first_name);
//        editor.putString("last_name",last_name);
//        editor.commit();
//
//
//
//
////        Log.i(TAG, "phone_number: "+phone_number);
////        Log.i(TAG, "token: "+token);
////        Log.i(TAG, "lastRefresh: "+lastRefresh);
////        Log.i(TAG, "first name: "+first_name);
////        Log.i(TAG, "last name: "+last_name);
////        Log.i(TAG, "password: "+password);
////        Log.i(TAG, "country: "+country);
////        Log.i(TAG, "area: "+area);
////        Log.i(TAG, "gender: "+gender);
////        Log.i(TAG, "dateOfBirth: "+dateOfBirth);
////        Log.i(TAG, "status: "+status);
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public void getDate(View view) {
//
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(1996, 11, 13);
//        Calendar calProduct = Calendar.getInstance();
//        calProduct.set(1960, 1, 1);
//        Calendar calExpired = Calendar.getInstance();
//        calExpired.set(2000, 1, 1);
//
//
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this,R.style.AlertDialog,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        dateOfBirth=(day + "/" + (month ) + "/" + year);
//                    }
//                }, year, month, day);
//        datePickerDialog.getDatePicker().setMinDate(calProduct.getTimeInMillis());
//        datePickerDialog.getDatePicker().setMaxDate(calExpired.getTimeInMillis());
//
//        datePickerDialog.show();
//    };
//
//    private String imgeToString(Bitmap bitmap){
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG ,20 ,byteArrayOutputStream);
//        byte [] imgByte = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(imgByte ,Base64.DEFAULT);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data!=null) {
//            Uri path = data.getData();
//            try {
//                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver() , path);
//                image_register.setImageBitmap(bitmap);
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void sendData() {
//
//        builder=new AlertDialog.Builder(Registration.this);
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//                        builder.setTitle("Server Response");
//                        builder.setMessage("Response:");
//                        builder.setPositiveButton("Send Successfuly", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                txt_first_name.setText("");
//                                txt_last_name.setText("");
//                                txt_pass.setText("");
//                                txt_repass.setText("");
//                                txt_country.clearFocus();
//                                txt_area.clearFocus();
//                                rg.clearCheck();
//
//
//                            }
//                        });
//
//                        AlertDialog alertDialog=builder.create();
//                        alertDialog.show();
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                makeText(Registration.this, "Error", LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String,String>();
//                params.put("first_name",first_name);
//                params.put("last_name",last_name);
//                params.put("phone_number",phone_number);
//                params.put("token_fire",  FirebaseInstanceId.getInstance().getToken());
//                params.put("token",token);
//                params.put("last_refresh",last_refresh);
//                params.put("password",password);
//                params.put("country",country);
//                params.put("area",area);
//                params.put("gender",gender);
//                params.put("date_of_birth",dateOfBirth);
//                params.put("status",status);
//                if(bitmap==null){
//                    params.put("image","");
//                }else {
//                    params.put("image",imgeToString(bitmap));}
//
//
//
//                Log.i(TAG, "getParams: "+params);
//                return params;
//            }
//        }
//                ;
//
//
//        //  requestQueue.add(stringRequest);
//
//        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//
//
//
//    }
//    @Override
//    public void onBackPressed() {
//
//
//        exit();
//    }
//
//
//
//}
package com.mohammadhajali.mychat22;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.*;

public class Registration extends AppCompatActivity {

    private CircleImageView image_register;
    Bitmap bitmap;
    Button btn_next_to_main;
    TextView error;
    EditText txt_first_name,txt_last_name,txt_pass,txt_repass;
    Spinner txt_country,txt_area;
    RadioGroup rg;
    RadioButton rb;
    int year,month,day;
    RequestQueue requestQueue;
    String phone_number,token,last_refresh,first_name,last_name,password,country="",area="",gender="",dateOfBirth="",status,message="";
    boolean c=true;
    String server_url="http://track-kids.com/insert_info.php";

    AlertDialog.Builder builder;


    public static final String TAG="fffffRegReg";
    SharedPreferences sharedPreferences;

    // FirebaseAuth mAuth;
    long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initial();
        //  googleLogin();


        image_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent ,1);
            }
        });
        btn_next_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sumdata();
                if(! validation (c)) {
                    error.setText(message);

                }
                else {
                    sendData();
                    startActivity(new Intent(getApplicationContext(), MainPage.class));
                    finish();
                }


            }
        });


    }


    public void exit() {

        if (time + 2000 > System.currentTimeMillis ()) {
            moveTaskToBack ( true );
            android.os.Process.killProcess(android.os.Process.myPid());

            System.exit(1);


        }
        else {
            Toast.makeText ( this,"اضغط مرة أخرى للخروج" ,Toast.LENGTH_SHORT).show ();

        }
        time = System.currentTimeMillis ();
    }


//    private void googleLogin() {
//
//
//
//        mAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//
//
//
//
//
////        btn_logout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                mAuth.signOut();
////                finish();
////                startActivity(new Intent(getApplicationContext(), MainActivity.class));
////            }
////        });
//    }


    public boolean validation(boolean c){
        c=true;
        message="";
        if(txt_first_name.getText().toString().isEmpty()) {
            message+="Please Enter Your First Name \n";
            c=false;
        }
        else if(txt_first_name.getText().toString().contains("1") || txt_first_name.getText().toString().contains("0") || txt_first_name.getText().toString().contains("1") || txt_first_name.getText().toString().contains("2") || txt_first_name.getText().toString().contains("3") || txt_first_name.getText().toString().contains("4") || txt_first_name.getText().toString().contains("5") || txt_first_name.getText().toString().contains("6") || txt_first_name.getText().toString().contains("7") || txt_first_name.getText().toString().contains("8") || txt_first_name.getText().toString().contains("9")) {
            message+="Please Enter Your First Name Correctly \n";
            c=false;

        }

        if(txt_last_name.getText().toString().isEmpty()) {
            message+="Please Enter Your last Name \n";
            c=false;

        }
        else if(txt_last_name.getText().toString().contains("1") || txt_last_name.getText().toString().contains("0") || txt_last_name.getText().toString().contains("1") || txt_last_name.getText().toString().contains("2") || txt_last_name.getText().toString().contains("3") || txt_last_name.getText().toString().contains("4") || txt_last_name.getText().toString().contains("5") || txt_last_name.getText().toString().contains("6") || txt_last_name.getText().toString().contains("7") || txt_last_name.getText().toString().contains("8") || txt_last_name.getText().toString().contains("9")) {
            message+="Please Enter Your last Name Correctly \n";
            c=false;

        }
        if(txt_pass.getText().toString().isEmpty()){
            message+="Please Enter Password \n";
            c=false;

        }
        else if(txt_pass.getText().toString().length()<7 ) {
            message+="Length of Password is Short \n";
            c=false;

        }
        if(txt_repass.getText().toString().isEmpty()){
            message+="Please Enter RePassword \n";
            c=false;

        }
        else if(!txt_repass.getText().toString().equals(password) ) {
            message+="RePassword isn't identical\n";
            c=false;

        }
        return  c;

    }
    public void initial(){


        btn_next_to_main=(Button)findViewById(R.id.btn_next_to_main);
        txt_first_name=(EditText)findViewById(R.id.txt_first_name);
        txt_last_name=(EditText)findViewById(R.id.txt_last_name);
        txt_pass=(EditText)findViewById(R.id.txt_pass);
        txt_repass=(EditText)findViewById(R.id.txt_repass);
        txt_country=(Spinner)findViewById(R.id.txt_country);
        txt_area=(Spinner)findViewById(R.id.txt_area);
        rg=(RadioGroup)findViewById(R.id.radiogroup);
        error=(TextView)findViewById(R.id.error);
        image_register=findViewById(R.id.image_register);

    }


    public void sumdata(){


        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        phone_number= sharedPreferences.getString("phone_number",null);
        status=sharedPreferences.getString("status",null);

        Intent intent_get_phone_number=getIntent();
        token= intent_get_phone_number.getStringExtra("token");
        editor.putString("token",token);
        editor.commit();


        last_refresh= intent_get_phone_number.getStringExtra("last_refresh");
        first_name=txt_first_name.getText().toString();
        last_name=txt_last_name.getText().toString();
        password=txt_pass.getText().toString();
        country=txt_country.getSelectedItem().toString();
        area=txt_area.getSelectedItem().toString();

        int selected=rg.getCheckedRadioButtonId();
        if(selected!=-1) {
            Log.i(TAG, "selected: "+selected);
            rb = (RadioButton) findViewById(selected);
            gender = rb.getText().toString();
        }




        editor.putString("email",phone_number);
        editor.putString("status",status);
        editor.putString("firs_name",first_name);
        editor.putString("last_name",last_name);
        editor.commit();




//        Log.i(TAG, "phone_number: "+phone_number);
//        Log.i(TAG, "token: "+token);
//        Log.i(TAG, "lastRefresh: "+lastRefresh);
//        Log.i(TAG, "first name: "+first_name);
//        Log.i(TAG, "last name: "+last_name);
//        Log.i(TAG, "password: "+password);
//        Log.i(TAG, "country: "+country);
//        Log.i(TAG, "area: "+area);
//        Log.i(TAG, "gender: "+gender);
//        Log.i(TAG, "dateOfBirth: "+dateOfBirth);
//        Log.i(TAG, "status: "+status);


    }















    public void getDate(View view) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, 11, 13);
        Calendar calProduct = Calendar.getInstance();
        calProduct.set(1960, 1, 1);
        Calendar calExpired = Calendar.getInstance();
        calExpired.set(2000, 1, 1);


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this,R.style.AlertDialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateOfBirth=(day + "/" + (month ) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calProduct.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(calExpired.getTimeInMillis());

        datePickerDialog.show();
    };

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
                image_register.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendData() {

        builder=new AlertDialog.Builder(Registration.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        builder.setTitle("Server Response");
                        builder.setMessage("Response:");
                        builder.setPositiveButton("Send Successfuly", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txt_first_name.setText("");
                                txt_last_name.setText("");
                                txt_pass.setText("");
                                txt_repass.setText("");
                                txt_country.clearFocus();
                                txt_area.clearFocus();
                                rg.clearCheck();


                            }
                        });

                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                makeText(Registration.this, "Error", LENGTH_SHORT).show();
                error.printStackTrace();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("first_name",first_name);
                params.put("last_name",last_name);
                params.put("phone_number",phone_number);
                params.put("token_fire",  FirebaseInstanceId.getInstance().getToken());
                params.put("token",token);
                params.put("last_refresh",last_refresh);
                params.put("password",password);
                params.put("country",country);
                params.put("area",area);
                params.put("gender",gender);
                params.put("date_of_birth",dateOfBirth);
                params.put("status",status);
                if(bitmap==null){
                    params.put("image","");
                }else {
                    params.put("image",imgeToString(bitmap));}



                Log.i(TAG, "getParams: "+params);
                return params;
            }
        }
                ;


        //  requestQueue.add(stringRequest);

        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
    @Override
    public void onBackPressed() {


        exit();
    }


//0798217736
}

