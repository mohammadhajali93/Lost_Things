package com.mohammadhajali.mychat22;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AppFeatures extends AppCompatActivity {

    Button btn_next_to_splash,btn_back_to;
    private static final String TAG ="iiifeatures" ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_features);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

        if(sharedPreferences.contains("token")){
            startActivity(new Intent(getApplicationContext(), MainPage.class));
            finish();
        }


        btn_next_to_splash=(Button)findViewById(R.id.btn_next_to_splash);
        btn_back_to=(Button)findViewById(R.id.btn_back_to);


        btn_next_to_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                finish();
            }
        });

        btn_back_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder a_builder = new AlertDialog.Builder(AppFeatures.this, R.style.AlertDialog);
                a_builder.setMessage(" Do you want to Exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                moveTaskToBack ( true );
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert !!!");
                alert.show();

            }
        });
    }


}
