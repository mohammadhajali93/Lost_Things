package com.mohammadhajali.mychat22;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

SharedPreferences sharedPreferences;
private String status ="";
    private String TAG="tttttttttt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        Thread thread= new Thread (  ){

            @Override
            public void run(){
                try {
                    sleep ( 5000 );
//                    sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
//
//                    status=sharedPreferences.getString("status",null);
//                    Log.i(TAG, "status: "+status);
//
//                    if(status=="login"){
//                        startActivity(new Intent(getApplicationContext(), MainPage.class));
//                        finish();
//                    }
//                    else  {
                        startActivity(new Intent(getApplicationContext(), login.class));
                        finish();
//                    }


                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        };

        thread.start ();

    }
}
