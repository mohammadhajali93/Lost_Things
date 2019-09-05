package com.mohammadhajali.mychat22;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostData extends AppCompatActivity {
    private ProgressDialog dialog = null;
    private JSONObject jsonObject;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    String imageURI;
    SharedPreferences sharedPreferences;

    RequestQueue requestQueue;
    static final private String TAG = "hasan";
    String url = "http://track-kids.com/post_new.php" ;

    RadioGroup RG_type , RG_type_loster_OR_founder;
    Bitmap bitmap;
    RadioButton r_jewellery , r_wallet , r_money , r_mobile , r_other , r_document , r_loster , r_founder  ;
    EditText txt_type , txt_amount_money      ,txt_reward  ,txt_card_Id ,spacific_area , txt_spacification;
    Spinner area_name  , color;
    ImageView imge_chose  ;
    Button publish , btn_location;
    String  type_loster_OR_founder ="" ,  type_loste_post="";
    String amount_of_money   , currentTime , spacific_area_location , Area_Name , colors ,other_spacification ;
    double amount=0.01;

    String name_of_type , id_type , name_types , id_types;
    HashMap<String , String> name_of_type1=new HashMap<>();
    String x;

    String[] A = new String[18];


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CORSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    Double Latitude =0.0;
    Double Longitude=0.0;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data);

//            Calendar cal = Calendar.getInstance();
//            Date date=cal.getTime();
//            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//             currentTime=dateFormat.format(date);
//          //  Toast.makeText(this, currentTime, Toast.LENGTH_SHORT).show();
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
//        currentTime =  mdformat.format(calendar.getTime())+mdformat;
        currentTime = Calendar.getInstance().getTime().toString();



        init();

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocationUI();
                getLocationPermission();
                getDeviceLocation();
            }
        });


        r_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imge_chose.setVisibility(View.GONE);
                txt_amount_money.setVisibility(View.VISIBLE);
                txt_type.setVisibility(View.GONE);
                txt_card_Id.setVisibility(View.GONE);
                color.setVisibility(View.GONE);


            }

        });

        r_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_amount_money.setVisibility(View.VISIBLE);
                txt_card_Id.setVisibility(View.VISIBLE);
                imge_chose.setVisibility(View.VISIBLE);
                txt_type.setVisibility(View.GONE);
                color.setVisibility(View.GONE);

            }
        });
        r_jewellery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imge_chose.setVisibility(View.VISIBLE);
                txt_type.setVisibility(View.VISIBLE);
                color.setVisibility(View.GONE);
                txt_amount_money.setVisibility(View.GONE);
            }
        });
        r_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_amount_money.setVisibility(View.GONE);
                txt_card_Id.setVisibility(View.VISIBLE);
                txt_type.setVisibility(View.VISIBLE);
                imge_chose.setVisibility(View.VISIBLE);
                color.setVisibility(View.GONE);
            }
        });
        r_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_amount_money.setVisibility(View.GONE);
                txt_type.setVisibility(View.VISIBLE);
                imge_chose.setVisibility(View.VISIBLE);
                color.setVisibility(View.VISIBLE);
            }
        });
        r_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_amount_money.setVisibility(View.GONE);
                txt_type.setVisibility(View.VISIBLE);
                imge_chose.setVisibility(View.VISIBLE);
                color.setVisibility(View.VISIBLE);

            }
        });



        publish.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                spacific_area_location=spacific_area.getText().toString();
                Area_Name =area_name.getSelectedItem().toString();
                colors =color.getSelectedItem().toString();
                other_spacification=txt_spacification.getText().toString();

                //DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                // currentTime = df.format(Calendar.getInstance().getTime());


                Log.i(TAG , "time2"+currentTime);

                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("type_loster_OR_founder", type_loster_OR_founder);
                editor.putString("type_loste_post", type_loste_post);

                editor.apply();

                Log.i(TAG, "type1" + sharedPreferences.getString("type_loster_OR_founder", null));
                Log.i(TAG, "type2" + sharedPreferences.getString("type_loste_post", null));


//                if(id_type=="01"){
//                    Toast.makeText(PostData.this ,"yes",Toast.LENGTH_LONG).show();
//                }


                init_radio_lost_Founder();



//
//                    for (  int x =0 ;x<A.length ;x++) {
//                        if(A[x]==null){
//                            Toast.makeText(PostData.this ,"null  : " ,Toast.LENGTH_LONG).show();
//
//                                  // Log.i(TAG ,"fd"+x);
//                            }
//                            else {
//                            Toast.makeText(PostData.this ,x+"yes  : "+A[x] ,Toast.LENGTH_LONG).show();
//                        }
//                    }
//                 //   Toast.makeText(PostData.this ,"yes"+A[1],Toast.LENGTH_LONG).show();






            }
        });


        imge_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Choose application"), Utils.REQCODE);


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent ,1);

            }
        });


//        startActivity(new Intent(getApplicationContext(), MainPage.class));
//        finish();

    }

    private void getDeviceLocation() {
//        googleMap.setIndoorEnabled(false);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {

                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation!=null) {
                                Latitude = (currentLocation.getLatitude());
                                Longitude = (currentLocation.getLongitude());

                                Toast.makeText(PostData.this, " location is take " , Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,MapsActivity.class);
//                            intent.putExtra("lat",Latitude);
//                            intent.putExtra("long",Longitude);
                                //  Log.d(TAG, "lanted"+Latitude+" , "+Longitude);
//                            startActivity(intent);
//                            finish();
                                //connect database and send latitude and longitude
                            }  } else {
                            Log.d(TAG, "onComplete:current location is null");
                            //take last location database save if database new take defult 0,0
                            // Toast.makeText(ParentActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation:SecurityException:" + e.getMessage());
        }
    }

    private void getLocationPermission() {


        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    CORSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        mLocationPermissionsGranted = false;
//        switch (requestCode) {
//            case LOCATION_PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0) {
//                    for (int i = 0; i < grantResults.length; i++) {
//                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            mLocationPermissionsGranted = false;
//                            //if permission failed
//                            return;
//                        }
//                    }
//                    mLocationPermissionsGranted = true;
//                 }
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionsGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {

        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionsGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                // mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }

    }

    private void send_to_server() {


        //  sendImage();

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        final ArrayList<contact> array_contact = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // try {

                Log.i(TAG, "onResponse: " + response);
                //  int i=0;
                // JSONObject jsonObject = new JSONObject(response);

//                    JSONArray array = new JSONArray(response);
//                    //traversing through all the object
//                    for (int i = 0; i < array.length(); i++) {
//                        //getting product object from json array
//
//                        JSONObject jsonObject = array.getJSONObject(i);
////                        id_type=    jsonObject.getString("id");
////                        name_of_type  =jsonObject.getString("name");
////                       // Log.i(TAG, "name_of_type: " + name_of_type);
////
////                        name_of_type1.put(id_type ,name_of_type);
////
////                     for (HashMap.Entry<String, String> pair : name_of_type1.entrySet()) {
////
////                            //   Log.i(TAG , "yes" +pair.getKey()  + pair.getValue());
////
////                            for (int x = 0; x < A.length; x++) {
////                                A[x] = pair.getValue();
////                            }
////                            id_types = pair.getKey();
////                            name_types = pair.getValue();
//
//
////                        }
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                //  Log.i(TAG, "name_of_type1: " + name_of_type1);
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
               params.put("token_fire",   FirebaseInstanceId.getInstance().getToken());


                params.put("lost_found",type_loster_OR_founder);
                params.put("latitude",Latitude+"");
                params.put("longitude",""+Longitude);
                params.put("area_name",Area_Name);
                params.put("date_time",currentTime);
                //   params.put("date_time","524");
                params.put("reward",txt_reward.getText().toString());
                params.put("spacification",other_spacification);
                params.put("pacific_area",spacific_area_location);

                if( type_loste_post == "money" )
                {
                    params.put("table","money");
                    params.put("type","12");
                    params.put("amount",amount+"");

                }

                else if ( type_loste_post == "others" ){
                    if(bitmap==null){
                        params.put("image","");
                    }else {
                        params.put("image",imgeToString(bitmap));}
                    params.put("table","others");
                    params.put("color",colors);
                   // params.put("image",imgeToString(bitmap));
                    params.put("type" ,"4");
                    params.put("type_other" ,txt_type.getText().toString());

                }
                else if ( type_loste_post=="mobile" ){

                    if(bitmap==null){
                        params.put("image","");
                    }else {
                        params.put("image",imgeToString(bitmap));}
                    params.put("table","others");
                    params.put("color",colors);
                   // params.put("image",imgeToString(bitmap));
                    params.put("type" ,"1");
                    params.put("type_other" ,txt_type.getText().toString());

                }
                else if ( type_loste_post == "jewelery" ){
                    if(bitmap==null){
                        params.put("image","");
                    }else {
                        params.put("image",imgeToString(bitmap));}
                    params.put("table","jewelery");
                    //put in the DB_money the attrebuite
                  //  params.put("image",imgeToString(bitmap));
                    params.put("type" ,"10");
                    params.put("type_jewelery" ,txt_type.getText().toString());

                }

                else if ( type_loste_post == "offical_doc" ){

                    if(bitmap==null){
                        params.put("image","");
                    }else {
                        params.put("image",imgeToString(bitmap));}
                    params.put("table","offical_doc");
                   // params.put("image",imgeToString(bitmap));

                    params.put("card_id" ,txt_card_Id.getText().toString());

                    params.put("type","9");
                    params.put("type_doc",txt_type.getText().toString());

                }

                else if ( type_loste_post == "wallet" ){

                    if(bitmap==null){
                        params.put("image","");
                    }else {
                        params.put("image",imgeToString(bitmap));}
                    params.put("table","wallet");
                    params.put("amount",amount+"");
                    params.put("image",imgeToString(bitmap));
                  //  params.put("card_id" ,txt_card_Id.getText().toString());
                    params.put("type","11");

                }

                Log.i(TAG, "Params: " + params);
                return params ;
            }

        };
        MySingleton.getmInstance(PostData.this).addToRequestQueue(stringRequest);

    }

//    private void sendImage() {
//
//        if(r_other.isChecked()||r_mobile.isChecked()||r_document.isChecked()||r_jewellery.isChecked()||r_wallet.isChecked()) {
//
//            dialog.show();
//
//            JSONArray jsonArray = new JSONArray();
//
////            if (encodedImageList.isEmpty()) {
////                Toast.makeText(this, "Please select some images first.", Toast.LENGTH_SHORT).show();
////                //return;
////            }
//
//            for (String encoded : encodedImageList) {
//                jsonArray.put(encoded);
//            }
//
//            try {
//                jsonObject.put(Utils.imageName, spacific_area_location.trim());
//                jsonObject.put(Utils.imageList, jsonArray);
//            } catch (JSONException e) {
//                Log.e("JSONObject Here", e.toString());
//            }
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Utils.urlUpload, jsonObject,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                            Log.e("Message from server", jsonObject.toString());
//                            dialog.dismiss();
//                       //     Toast.makeText(getApplication(), "Images Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//                    Log.e("Message from server", volleyError.toString());
//                  //  Toast.makeText(getApplication(), "Error Occurred", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            });
//            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200 * 30000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            Log.i(TAG, "jas" + jsonObjectRequest);
//            Volley.newRequestQueue(this).add(jsonObjectRequest);
//        } }




    private void init_radio_lost_Founder() {


        if(r_founder.isChecked()==false && r_loster.isChecked()==false){
            Toast.makeText(PostData.this ,"please check type of Loster or Founder",Toast.LENGTH_LONG).show();
        }
        else  if(r_loster.isChecked()) {
            type_loster_OR_founder=   "loster";
        }

        else if(r_founder.isChecked()) {
            type_loster_OR_founder=   "founder";
        }


        if(r_document.isChecked()==false && r_mobile.isChecked()==false&& r_wallet.isChecked()==false&& r_money.isChecked()==false&& r_jewellery.isChecked()==false&& r_other.isChecked()==false){
            Toast.makeText(PostData.this ,"please check type of post the lost",Toast.LENGTH_LONG).show();
        }
        else if(r_jewellery.isChecked()) {
            type_loste_post = "jewelery" ;

        }
        else if(r_document.isChecked()) {
            type_loste_post = "offical_doc" ;
        }
        else if(r_money.isChecked()) {
            type_loste_post = "money" ;

        }
        else if(r_wallet.isChecked()) {
            type_loste_post = "wallet" ;

        }
        else if(r_mobile.isChecked()) {
            type_loste_post = "mobile" ;
        }
        else if(r_other.isChecked()) {
            type_loste_post = "others" ;
        }


        if(!type_loste_post.equals("")&&!type_loster_OR_founder.equals("")) {
            if (type_loste_post=="money"||type_loste_post=="wallet"){
                try {
                    amount = Double.parseDouble(amount_of_money=txt_amount_money.getText().toString());
                    Toast.makeText(PostData.this, "good send post", Toast.LENGTH_LONG).show();


                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(1500);
                                startActivity(new Intent(getApplicationContext(), MainPage.class));
                                finish();


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                    send_to_server();
                } catch (NumberFormatException e){
                    Toast.makeText(PostData.this ,"Enter A NUM in the Amount FIELD",Toast.LENGTH_LONG).show();
                }




            }

            if(type_loste_post=="jewelery"||type_loste_post=="offical_doc"||type_loste_post=="mobile"||type_loste_post=="others"){

                Toast.makeText(PostData.this, "good send post", Toast.LENGTH_LONG).show();


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1500);
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                            finish();


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                send_to_server();
            }
        }

    }

//
//    private void init_radio_type() {
//
//        if(r_other.isChecked()){
//        }else {
//        }
//        if (r_wallet.isChecked()){
//
//            txt_card_Id.setVisibility(View.GONE);
//        }}

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
                imge_chose.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    try {
//        // When an Image is picked
//        if (requestCode == Utils.REQCODE && resultCode == RESULT_OK
//                && null != data) {
//            // Get the Image from data
//
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            imagesUriList = new ArrayList<Uri>();
//            encodedImageList.clear();
//            if(data.getData()!=null) {
//
//                Uri mImageUri = data.getData();
//
//                // Get the cursor
//                Cursor cursor = getContentResolver().query(mImageUri,
//                        filePathColumn, null, null, null);
//                // Move to first row
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imageURI = cursor.getString(columnIndex);
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//                encodedImageList.add(encodedImage);
//                cursor.close();
//
//               imge_chose.setImageBitmap(bitmap);
//
//            }
//            else {
//                if (data.getClipData() != null) {
//                    ClipData mClipData = data.getClipData();
//                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
//                    for (int i = 0; i < mClipData.getItemCount(); i++) {
//
//                        ClipData.Item item = mClipData.getItemAt(i);
//                        Uri uri = item.getUri();
//                        mArrayUri.add(uri);
//                        // Get the cursor
//                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//                        // Move to first row
//                        cursor.moveToFirst();
//
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        imageURI  = cursor.getString(columnIndex);
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//                        encodedImageList.add(encodedImage);
//                        cursor.close();
//                    }
//                    noImage.setText("Selected Images: " + mArrayUri.size());
//                }
//            }
//        } else {
//            Toast.makeText(this, "You haven't picked Image",
//                    Toast.LENGTH_LONG).show();
//        }
//    } catch (Exception e) {
//        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
//    }
//
//        super.onActivityResult(requestCode, resultCode, data);
//}


    @SuppressLint("WrongViewCast")
    private void init() {

        dialog = new ProgressDialog(PostData.this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        jsonObject = new JSONObject();
        encodedImageList = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(getApplication().getApplicationContext());

        RG_type= (RadioGroup) findViewById(R.id.RG_type);
        RG_type_loster_OR_founder= (RadioGroup) findViewById(R.id.RG_type_loster_OR_founder);

        r_money= (RadioButton) findViewById(R.id.r_money);
        r_jewellery= (RadioButton) findViewById(R.id.r_jewellery);
        r_document= (RadioButton) findViewById(R.id.r_document);
        r_mobile= (RadioButton) findViewById(R.id.r_mobile);
        r_wallet= (RadioButton) findViewById(R.id.r_wallet);
        r_other= (RadioButton) findViewById(R.id.r_other);
        r_loster= (RadioButton) findViewById(R.id.r_loster);
        r_founder= (RadioButton) findViewById(R.id.r_founder);

        txt_amount_money = (EditText) findViewById(R.id.txt_amount_money);

        txt_type = (EditText) findViewById(R.id.txt_type);

        txt_reward = (EditText) findViewById(R.id.txt_reward);
        txt_card_Id = (EditText) findViewById(R.id.txt_card_id);
        spacific_area = (EditText) findViewById(R.id.spacific_area);
        area_name = (Spinner) findViewById(R.id.area_name);
        color = (Spinner) findViewById(R.id.color);
        txt_spacification=findViewById(R.id.txt_spacification);


        imge_chose = (ImageView) findViewById(R.id.imge_chose);
        publish = (Button) findViewById(R.id.btn_publish);
        btn_location=findViewById(R.id.location);



    }

}
