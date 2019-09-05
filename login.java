package com.mohammadhajali.mychat22;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
//import com.facebook.appevents.AppEventsLogger;
//import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;




import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.HttpMethod;
//import com.facebook.appevents.AppEventsLogger;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
//import de.hdodenhof.circleimageview.CircleImageView;




import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class login extends AppCompatActivity {

    //Tag for the logs optional
    private static final String TAG ="fffffflogin" ;
    TextView need_new_account_link,forget_password_link,txt_error;
    EditText txt_phone,txt_password;
    Button btn_login;
    String message,username,password,email,first_name,last_name,token,image,status="login",phone_number,from,last_refresh;
    boolean c=true,b=false,isExpired;
    long time;

    public static int APP_REQUEST_CODE = 99;
    SharedPreferences sharedPreferences;
    AccessToken accessToken;


    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    //And also a Firebase Auth object
    FirebaseAuth mAuth;

    AlertDialog.Builder builder;
    String url_login="http://track-kids.com/login.php";
    String url_insert="http://track-kids.com/insert_info.php";
    String url_check_phone="http://track-kids.com/check_phone.php";
    String url2 = "http://track-kids.com/id_user.php" ;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //final RequestQueue requestQueue= Volley.newRequestQueue(login.this);
        load_id_user();
        initial();
        googleLogin();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getData();

                if(! validation (c)) {
                    txt_error.setText(message);

                }
                else {
                    login();
                }



            }
        });

        need_new_account_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from="new_account";
                tow();


                accessToken = AccountKit.getCurrentAccessToken();


                if (accessToken != null) {
                    //Handle Returning
                    token=accessToken.getToken();

                    sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token",token);
                    editor.commit();
                    Log.i(TAG ,"token"+sharedPreferences);

                    last_refresh=accessToken.getLastRefresh().toString();



                } else {
                    //Handle new or logged out user
                    AccountKit.logOut();

                }




            }
        });

        forget_password_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from="forget_password";
                tow();

            }
        });
    }


    private void load_id_user() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG ,"response1"+response);
                try {

                    // JSONObject jsonObject = new JSONObject(response);
                    JSONArray arry = new JSONArray(response);

                    for (int i =0 ; i<arry.length(); i++) {

                        JSONObject jsonObject = arry.getJSONObject(i);

                        int id_user= Integer.parseInt(jsonObject.getString("id"));

                        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id_user",id_user);
                        editor.apply();

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
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

                params.put("token",sharedPreferences.getString("token","null"));

                Log.i(TAG ,"params1"+params);

                return params;
            }
        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

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
    private void initial() {
        need_new_account_link=(TextView)findViewById(R.id.need_new_account_link);
        forget_password_link=(TextView)findViewById(R.id.forget_password_link);
        txt_error=(TextView)findViewById(R.id.txt_error);
        txt_phone=(EditText)findViewById(R.id.txt_email_phone);
        txt_password=(EditText)findViewById(R.id.txt_password);
        btn_login=(Button)findViewById(R.id.login_button);

    }

    public void login(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "response: "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message= jsonObject.getString("x");
                            first_name=jsonObject.getString("first_name");
                            last_name=jsonObject.getString("last_name");
                            // token=jsonObject.getString("token");

                            sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token",token);
                            editor.commit();

                            Log.i(TAG ,"token"+token);
                            Log.i(TAG, "first_nameonResponse: "+first_name);
                            Log.i(TAG, "last_nameonResponse: "+last_name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i(TAG, "message: "+message);

                        if(message.equals("success")) {





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

                makeText(login.this, "Error", LENGTH_SHORT).show();
                error.printStackTrace();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("username",username);
                params.put("password",password);


                Log.i(TAG, "getParams: "+params);
                return params;
            }
        };


        //  requestQueue.add(stringRequest);

        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
    public void getData(){

        username=txt_phone.getText().toString();
        password=txt_password.getText().toString();
    }


    public boolean validation(boolean c){
        c=true;
        message="";
        if(txt_phone.getText().toString().isEmpty()) {
            message+="Please Enter Phone Number \n";
            c=false;
        }

        if(txt_password.getText().toString().isEmpty()){
            message+="Please Enter Password \n";
            c=false;

        }
        else if(txt_password.getText().toString().length()<7 ) {
            message+="Length OF Password is Short \n";
            c=false;

        }

        return  c;

    }




    private void googleLogin() {


        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
        Log.i(TAG, "mAuth: "+mAuth);
        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Now we will attach a click listener to the sign_in_button
        //and inside onClick() method we are calling the signIn() method that will open
        //google sign in intent
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();


            }
        });

    }




    public void phoneLogin() {

        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);

    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //if the user is already signed in
//        //we will close this activity
//        //and take the user to profile activity
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainPage.class));
//        }
//    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //if(!isExpired) {
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "User Signed In", Toast.LENGTH_SHORT).show();
                            //}
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    public void tow() {


        phoneLogin();

        accessToken = AccountKit.getCurrentAccessToken();

        Log.i(TAG, "accessToken: "+accessToken);
        if (accessToken != null) {
            //Handle Returning
            token=accessToken.getToken();
            last_refresh=accessToken.getLastRefresh().toString();
            sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token",token);
            editor.commit();


            Log.i(TAG, "token: " + token);
            Log.i(TAG, "lastRefresh: " + last_refresh);

        } else {
            //Handle new or logged out user
            AccountKit.logOut();

        }
    }



    private void sendData() {


        builder=new AlertDialog.Builder(login.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_insert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        builder.setTitle("Server Response");
                        builder.setMessage("Response:");
                        builder.setPositiveButton("Send Successfuly", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });

                        AlertDialog alertDialog=builder.create();
                        // alertDialog.show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                makeText(login.this, "Error", LENGTH_SHORT).show();
                error.printStackTrace();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("first_name",first_name);
                params.put("last_name",last_name);
                params.put("token",token);
                params.put("token_fire",  FirebaseInstanceId.getInstance().getToken());
                params.put("status",status);
                params.put("email",email);
                params.put("image",image);




                Log.i(TAG, "params: "+params);
                return params;
            }
        }
                ;


        //requestQueue.add(stringRequest);

        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i(TAG, "getEmail: "+account.getEmail());
                Log.i(TAG, "getFamilyName: "+account.getFamilyName());
                Log.i(TAG, "getGivenName: "+account.getGivenName());
                Log.i(TAG, "getIdToken: "+account.getIdToken());
                Log.i(TAG, "getPhotoUrl: "+account.getPhotoUrl());
                Log.i(TAG, "isExpired: "+account.isExpired());

                email=account.getEmail();
                first_name=account.getGivenName();
                last_name=account.getFamilyName();
                token=account.getIdToken();
                image=account.getPhotoUrl().toString();
                status="login";
                isExpired=account.isExpired();

                Log.i(TAG, "email: "+email);
                Log.i(TAG, "firstName: "+first_name);
                Log.i(TAG, "lastName: "+last_name);
                Log.i(TAG, "token: "+token);
                Log.i(TAG, "image: "+image);
                Log.i(TAG, "status: "+status);
                Log.i(TAG, "isExpired: "+isExpired);



                sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",email);
                editor.putString("status",status);
                editor.putString("firs_name",first_name);
                editor.putString("token",token);
                editor.putString("image",image);
                editor.putString("last_name",last_name);
                editor.commit();


                sendData();

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }




        else if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            final AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                //showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {

                        @Override

                        public void onSuccess(Account account) {


                            phone_number= account.getPhoneNumber().toString();

                            status="login";
                            Log.i(TAG, "phone_number "+phone_number);
                            Log.i(TAG, "status: "+status);


                            check_user();

                            sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("phone_number",phone_number);
                            editor.putString("status",status);


                            editor.commit();


                            //   Toast.makeText(LoginChoice.this, account.getPhoneNumber().toString(), Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {


                        }
                    });
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode());
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                //goToMyLoggedInActivity();

                // Log.i(TAG, "accessToken: "+token);
                // Log.i(TAG, "getLastRefresh: "+lastRefresh);



                if(from.equals("new_account")) {



                    Intent intent_send_phone_number = new Intent(getApplicationContext(), Registration.class);

                    intent_send_phone_number.putExtra("token", token);
                    intent_send_phone_number.putExtra("last_refresh", last_refresh);


                    Log.i(TAG, "token: " + token);
                    Log.i(TAG, "last_refresh: " + last_refresh);


                    startActivity(intent_send_phone_number);
                    finish();


                }






                else if(from.equals("forget_password")) {
                    Intent intent_send_phone_number = new Intent(getApplicationContext(), changePassword.class);

                    intent_send_phone_number.putExtra("token", token);
                    intent_send_phone_number.putExtra("last_refresh", last_refresh);

                    Log.i(TAG, "token:1 " + token);
                    Log.i(TAG, "last_refresh:1 " + last_refresh);

                    startActivity(intent_send_phone_number);
                    finish();


                }
                else
                {
                    toastMessage = "Some Error has happend ...." ;

                }




            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

        }





    }



    private void check_user( ) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_check_phone,
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

                        if(message.equals("Username is Exist")) {



                            makeText(login.this, "Phone Number is exist, try again ...", LENGTH_SHORT).show();
                            Intent intent_send_phone_number = new Intent(getApplicationContext(), login.class);
                            startActivity(intent_send_phone_number);
                            finish();
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


    @Override
    public void onBackPressed() {


        exit();
    }

}


//package com.mohammadhajali.mychat22;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
////import com.facebook.AccessTokenTracker;
////import com.facebook.CallbackManager;
////import com.facebook.FacebookCallback;
////import com.facebook.FacebookException;
////import com.facebook.FacebookSdk;
//import com.facebook.accountkit.AccessToken;
//import com.facebook.accountkit.Account;
//import com.facebook.accountkit.AccountKit;
//import com.facebook.accountkit.AccountKitCallback;
//import com.facebook.accountkit.AccountKitError;
//import com.facebook.accountkit.AccountKitLoginResult;
//import com.facebook.accountkit.ui.AccountKitActivity;
//import com.facebook.accountkit.ui.AccountKitConfiguration;
//import com.facebook.accountkit.ui.LoginType;
////import com.facebook.appevents.AppEventsLogger;
////import com.facebook.login.widget.LoginButton;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//
//
//
//
//import android.content.Intent;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.firebase.iid.FirebaseInstanceId;
////import com.facebook.AccessTokenTracker;
////import com.facebook.CallbackManager;
////import com.facebook.FacebookCallback;
////import com.facebook.FacebookException;
////import com.facebook.FacebookSdk;
////import com.facebook.GraphRequest;
////import com.facebook.GraphResponse;
////import com.facebook.HttpMethod;
////import com.facebook.appevents.AppEventsLogger;
////import com.facebook.login.LoginManager;
////import com.facebook.login.LoginResult;
////import com.facebook.login.widget.LoginButton;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.util.Arrays;
////import de.hdodenhof.circleimageview.CircleImageView;
//
//
//
//
//import static android.widget.Toast.LENGTH_SHORT;
//import static android.widget.Toast.makeText;
//
//public class login extends AppCompatActivity {
//
//    //Tag for the logs optional
//    private static final String TAG ="fffffflogin" ;
//    TextView need_new_account_link,forget_password_link,txt_error;
//    EditText txt_phone,txt_password;
//    Button btn_login;
//    String message,username,password,email,first_name,last_name,token,image,status="login",phone_number,from,last_refresh;
//    boolean c=true,b=false,isExpired;
//    long time;
//
//    public static int APP_REQUEST_CODE = 99;
//    SharedPreferences sharedPreferences;
//    AccessToken accessToken;
//
//
//    //a constant for detecting the login intent result
//    private static final int RC_SIGN_IN = 234;
//    //creating a GoogleSignInClient object
//    GoogleSignInClient mGoogleSignInClient;
//    //And also a Firebase Auth object
//    FirebaseAuth mAuth;
//
//    AlertDialog.Builder builder;
//    String url_login="http://track-kids.com/login.php";
//    String url_insert="http://track-kids.com/insert_info.php";
//    String url_check_phone="http://track-kids.com/check_phone.php";
//    String url2 = "http://track-kids.com/id_user.php" ;
//
//
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        //final RequestQueue requestQueue= Volley.newRequestQueue(login.this);
//        load_id_user();
//        initial();
//        googleLogin();
//
//
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                getData();
//
//                if(! validation (c)) {
//                    txt_error.setText(message);
//
//                }
//                else {
//                    login();
//                }
//
//
//
//            }
//        });
//
//        need_new_account_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                from="new_account";
//                tow();
//
//
//                accessToken = AccountKit.getCurrentAccessToken();
//
//
//                if (accessToken != null) {
//                    //Handle Returning
//                    token=accessToken.getToken();
//
//                    sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("token",token);
//                    editor.commit();
//                    Log.i(TAG ,"token"+sharedPreferences);
//
//                    last_refresh=accessToken.getLastRefresh().toString();
//
//
//
//                } else {
//                    //Handle new or logged out user
//                    AccountKit.logOut();
//
//                }
//
//
//
//
//            }
//        });
//
//        forget_password_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                from="forget_password";
//                tow();
//
//            }
//        });
//    }
//
//
//    private void load_id_user() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i(TAG ,"response1"+response);
//                try {
//
//                    // JSONObject jsonObject = new JSONObject(response);
//                    JSONArray arry = new JSONArray(response);
//
//                    for (int i =0 ; i<arry.length(); i++) {
//
//                        JSONObject jsonObject = arry.getJSONObject(i);
//
//                        int id_user= Integer.parseInt(jsonObject.getString("id"));
//
//                        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("id_user",id_user);
//                        editor.apply();
//
//                    }} catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String , String> params = new HashMap<>();
//                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
//
//                params.put("token",sharedPreferences.getString("token","null"));
//
//                Log.i(TAG ,"params1"+params);
//
//                return params;
//            }
//        };
//        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
//    private void initial() {
//        need_new_account_link=(TextView)findViewById(R.id.need_new_account_link);
//        forget_password_link=(TextView)findViewById(R.id.forget_password_link);
//        txt_error=(TextView)findViewById(R.id.txt_error);
//        txt_phone=(EditText)findViewById(R.id.txt_email_phone);
//        txt_password=(EditText)findViewById(R.id.txt_password);
//        btn_login=(Button)findViewById(R.id.login_button);
//
//    }
//
//    public void login(){
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_login,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, "response: "+response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            message= jsonObject.getString("x");
//                            first_name=jsonObject.getString("first_name");
//                            last_name=jsonObject.getString("last_name");
//                           // token=jsonObject.getString("token");
//
//                            sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("token",token);
//                            editor.commit();
//
//                            Log.i(TAG ,"token"+token);
//                            Log.i(TAG, "first_nameonResponse: "+first_name);
//                            Log.i(TAG, "last_nameonResponse: "+last_name);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        Log.i(TAG, "message: "+message);
//
//                        if(message.equals("success")) {
//
//
//
//
//
//                            startActivity(new Intent(getApplicationContext(), MainPage.class));
//                            finish();
//                        }
//                        else
//                        {
//                            txt_error.setText(message);
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                makeText(login.this, "Error", LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String,String>();
//
//                params.put("username",username);
//                params.put("password",password);
//
//
//                Log.i(TAG, "getParams: "+params);
//                return params;
//            }
//        };
//
//
//        //  requestQueue.add(stringRequest);
//
//        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//
//
//
//    }
//    public void getData(){
//
//        username=txt_phone.getText().toString();
//        password=txt_password.getText().toString();
//    }
//
//
//    public boolean validation(boolean c){
//        c=true;
//        message="";
//        if(txt_phone.getText().toString().isEmpty()) {
//            message+="Please Enter Phone Number \n";
//            c=false;
//        }
//
//        if(txt_password.getText().toString().isEmpty()){
//            message+="Please Enter Password \n";
//            c=false;
//
//        }
//        else if(txt_password.getText().toString().length()<7 ) {
//            message+="Length OF Password is Short \n";
//            c=false;
//
//        }
//
//        return  c;
//
//    }
//
//
//
//
//    private void googleLogin() {
//
//
//        //first we intialized the FirebaseAuth object
//        mAuth = FirebaseAuth.getInstance();
//        Log.i(TAG, "mAuth: "+mAuth);
//        //Then we need a GoogleSignInOptions object
//        //And we need to build it as below
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        //Then we will get the GoogleSignInClient object from GoogleSignIn class
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        //Now we will attach a click listener to the sign_in_button
//        //and inside onClick() method we are calling the signIn() method that will open
//        //google sign in intent
//        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//
//
//            }
//        });
//
//    }
//
//
//
//
//    public void phoneLogin() {
//
//        final Intent intent = new Intent(this, AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
//                new AccountKitConfiguration.AccountKitConfigurationBuilder(
//                        LoginType.PHONE,
//                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
//        // ... perform additional configuration ...
//        intent.putExtra(
//                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
//                configurationBuilder.build());
//        startActivityForResult(intent, APP_REQUEST_CODE);
//
//    }
//
//
////    @Override
////    protected void onStart() {
////        super.onStart();
////
////        //if the user is already signed in
////        //we will close this activity
////        //and take the user to profile activity
////        if (mAuth.getCurrentUser() != null) {
////            finish();
////            startActivity(new Intent(getApplicationContext(), MainPage.class));
////        }
////    }
//
//
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//
//        //getting the auth credential
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//
//        //Now using firebase we are signing in the user here
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            //if(!isExpired) {
//                                startActivity(new Intent(getApplicationContext(), MainPage.class));
//                                finish();
//                                Toast.makeText(getApplicationContext(), "User Signed In", Toast.LENGTH_SHORT).show();
//                            //}
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.i(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(getApplicationContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        // ...
//                    }
//                });
//    }
//
//    //this method is called on click
//    private void signIn() {
//        //getting the google signin intent
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        //starting the activity for result
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//
//
//    public void tow() {
//
//
//        phoneLogin();
//
//        accessToken = AccountKit.getCurrentAccessToken();
//
//        Log.i(TAG, "accessToken: "+accessToken);
//        if (accessToken != null) {
//            //Handle Returning
//            token=accessToken.getToken();
//            last_refresh=accessToken.getLastRefresh().toString();
//            sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("token",token);
//            editor.commit();
//
//
//            Log.i(TAG, "token: " + token);
//            Log.i(TAG, "lastRefresh: " + last_refresh);
//
//        } else {
//            //Handle new or logged out user
//            AccountKit.logOut();
//
//        }
//    }
//
//
//
//    private void sendData() {
//
//
//        builder=new AlertDialog.Builder(login.this);
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_insert,
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
//
//
//                            }
//                        });
//
//                        AlertDialog alertDialog=builder.create();
//                       // alertDialog.show();
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                makeText(login.this, "Error", LENGTH_SHORT).show();
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
//                params.put("token",token);
//                params.put("token_fire",  FirebaseInstanceId.getInstance().getToken());
//                params.put("status",status);
//                params.put("email",email);
//                params.put("image",image);
//
//
//
//
//                Log.i(TAG, "params: "+params);
//                return params;
//            }
//        }
//                ;
//
//
//         //requestQueue.add(stringRequest);
//
//        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//
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
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//
//
//        //if the requestCode is the Google Sign In code that we defined at starting
//        if (requestCode == RC_SIGN_IN) {
//
//            //Getting the GoogleSignIn Task
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                //Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.i(TAG, "getEmail: "+account.getEmail());
//                Log.i(TAG, "getFamilyName: "+account.getFamilyName());
//                Log.i(TAG, "getGivenName: "+account.getGivenName());
//                Log.i(TAG, "getIdToken: "+account.getIdToken());
//                Log.i(TAG, "getPhotoUrl: "+account.getPhotoUrl());
//                Log.i(TAG, "isExpired: "+account.isExpired());
//
//                email=account.getEmail();
//                first_name=account.getGivenName();
//                last_name=account.getFamilyName();
//                token=account.getIdToken();
//                image=account.getPhotoUrl().toString();
//                status="login";
//                isExpired=account.isExpired();
//
//                Log.i(TAG, "email: "+email);
//                Log.i(TAG, "firstName: "+first_name);
//                Log.i(TAG, "lastName: "+last_name);
//                Log.i(TAG, "token: "+token);
//                Log.i(TAG, "image: "+image);
//                Log.i(TAG, "status: "+status);
//                Log.i(TAG, "isExpired: "+isExpired);
//
//
//
//                sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("email",email);
//                editor.putString("status",status);
//                editor.putString("firs_name",first_name);
//                editor.putString("token",token);
//                editor.putString("image",image);
//                editor.putString("last_name",last_name);
//                editor.commit();
//
//
//                sendData();
//
//                //authenticating with firebase
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//
//
//        else if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
//            final AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
//            String toastMessage;
//            if (loginResult.getError() != null) {
//                toastMessage = loginResult.getError().getErrorType().getMessage();
//                //showErrorActivity(loginResult.getError());
//            } else if (loginResult.wasCancelled()) {
//                toastMessage = "Login Cancelled";
//            } else {
//                if (loginResult.getAccessToken() != null) {
//                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
//                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//
//                        @Override
//
//                        public void onSuccess(Account account) {
//
//
//                            phone_number= account.getPhoneNumber().toString();
//
//                            status="login";
//                            Log.i(TAG, "phone_number "+phone_number);
//                            Log.i(TAG, "status: "+status);
//
//
//                            check_user();
//
//                            sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("phone_number",phone_number);
//                            editor.putString("status",status);
//
//
//                            editor.commit();
//
//                            Log.i(TAG, "sharedPreferences login: "+sharedPreferences.getString("status",null));
//                            //   Toast.makeText(LoginChoice.this, account.getPhoneNumber().toString(), Toast.LENGTH_SHORT).show();
//
//
//
//                        }
//
//                        @Override
//                        public void onError(AccountKitError accountKitError) {
//
//
//                        }
//                    });
//                } else {
//                    toastMessage = String.format(
//                            "Success:%s...",
//                            loginResult.getAuthorizationCode());
//                }
//
//                // If you have an authorization code, retrieve it from
//                // loginResult.getAuthorizationCode()
//                // and pass it to your server and exchange it for an access token.
//
//                // Success! Start your next activity...
//                //goToMyLoggedInActivity();
//
//                // Log.i(TAG, "accessToken: "+token);
//                // Log.i(TAG, "getLastRefresh: "+lastRefresh);
//
//
//
//                if(from.equals("new_account")) {
//
//
//
//                    Intent intent_send_phone_number = new Intent(getApplicationContext(), Registration.class);
//
//                    intent_send_phone_number.putExtra("token", token);
//                    intent_send_phone_number.putExtra("last_refresh", last_refresh);
//
//
//                    Log.i(TAG, "token: " + token);
//                    Log.i(TAG, "last_refresh: " + last_refresh);
//
//
//                    startActivity(intent_send_phone_number);
//                    finish();
//
//
//                }
//
//
//
//
//
//
//                else if(from.equals("forget_password")) {
//                    Intent intent_send_phone_number = new Intent(getApplicationContext(), changePassword.class);
//
//                    intent_send_phone_number.putExtra("token", token);
//                    intent_send_phone_number.putExtra("last_refresh", last_refresh);
//
//                    Log.i(TAG, "token:1 " + token);
//                    Log.i(TAG, "last_refresh:1 " + last_refresh);
//
//                    startActivity(intent_send_phone_number);
//                    finish();
//
//
//                }
//                else
//                {
//                    toastMessage = "Some Error has happend ...." ;
//
//                }
//
//
//
//
//            }
//
//            // Surface the result to your user in an appropriate way.
//            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
//
//        }
//
//
//
//
//
//    }
//
//
//
//    private void check_user( ) {
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url_check_phone,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            message= jsonObject.getString("x");
//                            Log.i(TAG, "message "+message);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        Log.i(TAG, "response: "+response);
//                        Log.i(TAG, "message: "+message);
//
//                        if(message.equals("Username is Exist")) {
//
//
//
//                            makeText(login.this, "Phone Number is exist, try again ...", LENGTH_SHORT).show();
//                            Intent intent_send_phone_number = new Intent(getApplicationContext(), login.class);
//                                            startActivity(intent_send_phone_number);
//                                            finish();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                makeText(getApplicationContext(), "Error", LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String,String>();
//
//                params.put("phone_number",phone_number);
//
//
//
//                Log.i(TAG, "getParams: "+params);
//                return params;
//            }
//        };
//
//
//        //  requestQueue.add(stringRequest);
//
//        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//
//    }
//
//
//    @Override
//    public void onBackPressed() {
//
//
//        exit();
//    }
//
//}
