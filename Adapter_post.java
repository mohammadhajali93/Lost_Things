package com.mohammadhajali.mychat22;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Adapter_post extends RecyclerView.Adapter<Adapter_post.ProductViewHolder> {          // 22 /6 /19  ////o
    private AlertDialog dialog;
    RequestQueue requestQueue;
    Bitmap bitmap;
    private Context mContext;
    private ArrayList<String> imge = new ArrayList<String>();
    private ArrayList<String> url_Img_user = new ArrayList<String>();
    private ArrayList<contact> contacts = new ArrayList<contact>();
    String url2 = "http://track-kids.com/id_user.php" ;
    String id_user , id_post;
    private static  final  String TAG ="hg";
    contact contact;

    public Adapter_post(Context mContext, ArrayList<String> imge,ArrayList<String> url_Img_user, ArrayList<contact> contacts) {
        this.mContext = mContext;
        this.imge = imge;
        this.url_Img_user = url_Img_user;
        this.contacts = contacts;
    }



    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post,  null);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.post, null);
        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;
    }

    public void  setfilter( ArrayList<contact> newlist ){

        contacts=new ArrayList<>();
        contacts.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder productViewHolder, int i) {

        contact = contacts.get(i);
        // productViewHolder.txt_date.setText(contact.getTxt_amount());
        //        productViewHolder .text_user_name.setText(contact.getText_user_name());

        productViewHolder.txt_post.setText(contact.getTxt_post());
        productViewHolder.txt_loster_founder.setText(contact.getTxt_loster_founder());
        productViewHolder.txt_type_lost.setText(contact.getTxt_type_lost());
        //  productViewHolder.txt_area.setText(contact.getTxt_area());
        productViewHolder.txt_date.setText(contact.getTxt_date());
        productViewHolder.txt_rewerd.setText(contact.getTxt_rewerd());
        final Double lan= Double.valueOf(contact.getLocation_latitude());
        final Double longe= Double.valueOf(contact.getLocation_longitude());
        productViewHolder.location.setText(contact.getLocation());

        productViewHolder.text_user_name.setText(contact.getText_user_name());

        productViewHolder.btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double latitude= Double.valueOf(contact.getLocation_latitude());
                Double longitude= Double.valueOf(contact.getLocation_longitude());

                Intent intent = new Intent(mContext,MapsActivity.class);
                intent.putExtra("lat",lan);
                intent.putExtra("long",longe);
                Log.i(TAG, "lanted"+lan+" , "+longe);

                // Log.i(TAG, "lanted"+latitude+" , "+longitude);

                mContext.startActivity(intent);

            }
        });
//imge.add(contact.getImg_post());
        final int id_user= Integer.parseInt(contact.getId_user());
        final String id_post=contact.getId_post();
//        if(imge.equals("0")){
//            productViewHolder.img_post.setVisibility(View.VISIBLE);
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(imge.get(i))
//                    .into(productViewHolder.img_post);
//
//            // productViewHolder.img_post.setVisibility(View.GONE);
//        }
//        else {
//            productViewHolder.img_post.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .asBitmap()
                .load(imge.get(i))
                .into(productViewHolder.img_post);
        // }

        Glide.with(mContext)
                .asBitmap()
                .load(url_Img_user.get(i))
                .into(productViewHolder.imge_user_name);



        Log.i(TAG ,"id_user"+id_post+","+id_user);

        productViewHolder.img_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        if(ActivityCompat.checkSelfPermission(mContext , Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_DENIED){
////            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
////                requestpermissions
////            }
////        }
                savephoto();
                // Toast.makeText(mContext ,"t "+contact.getTxt_date(),Toast.LENGTH_LONG).show();
            }
        });
        productViewHolder.img_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_image_server();


                StringRequest stringRequest = new StringRequest(Request.Method.POST,  "http://track-kids.com/loading_image.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray arry = new JSONArray(response);

                            for (int i =0 ; i<arry.length(); i++) {

                                JSONObject jsonObject = arry.getJSONObject(i);

                                String image= jsonObject.getString("image");


                                if(image.equals("no_image")){
                                    Toast.makeText(mContext ,"no image of money", Toast.LENGTH_LONG).show();

                                }
                                else{
                                    Uri uri = Uri.parse(image); // missing 'http://' will cause crashed
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    mContext.startActivity(intent);}
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

                        params.put("id_post",id_post);

                        Log.i(TAG ,"params1"+params);

                        return params;
                    }
                };
                MySingleton.getmInstance(mContext).addToRequestQueue(stringRequest);



            }
        });



        productViewHolder.btn_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_info",Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_user",id_user+"");
                editor.putString("id_post", id_post);

                editor.apply();

                Intent intent= new Intent( mContext, comment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("id_user",id_user);
//                intent.putExtra("id_post",id_post);
                mContext.startActivities(new Intent[]{intent});
                //  ((MainPage)mContext).finish();
//                StringRequest request = new StringRequest(Request.Method.POST, "http://track-kids.com/comment.php", new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, "onResponse: " + response);
//                    }
//
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String, String> params = new HashMap<>();
//
//                        params.put("id_post_1",id_post);
//
//                        Log.i(TAG, "getParams: " + params);
//                        return params;
//                    }
//                };
//                requestQueue.add(request);
            }
        });
// final int item = productViewHolder.sp_up_Del.getSelectedItemPosition();

        productViewHolder.sp_up_Del.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences2 =mContext. getSharedPreferences("user", Context.MODE_PRIVATE);
                load_id_user();
                int id_user_new= sharedPreferences2.getInt("id_user",0);

                if(position==1){

                    SharedPreferences   sharedPreferences = mContext.getSharedPreferences("user_info",Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id_user",id_user+"");
                    editor.putString("id_post", id_post);
                    editor.putString("id_user_new","" +id_user_new);

                    Log.i(TAG,"id_post"+id_post+"...."+id_user+"///"+id_user_new);

                    editor.apply();

                    if(id_user==id_user_new){

                        Intent intent= new Intent( mContext, Delete_post.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivities(new Intent[]{intent});
                        //  ((MainPage)mContext).finish();
                    }
                    else{
                        Toast.makeText(mContext,"its not your post , connat your delete",Toast.LENGTH_LONG).show();
                    }
                }
                else  if (position==2){

                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_info",Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id_user",id_user+"");
                    editor.putString("id_post", id_post);

                    if(id_user==id_user_new){
                        editor.apply();
                        Intent intent= new Intent( mContext, Update_post.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivities(new Intent[]{intent});
                        //    ((MainPage)mContext).finish();
                    }
                    else{
                        Toast.makeText(mContext,"its not your post , connat your Edit",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                        SharedPreferences sharedPreferences =mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
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
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);

                params.put("token",sharedPreferences.getString("token","null"));

                Log.i(TAG ,"params1"+params);

                return params;
            }
        };
        MySingleton.getmInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void loading_image_server() {



    }

    private void savephoto() {

        try {
            ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG ,50 ,bytearray);
            Calendar calendar = Calendar.getInstance();
            String fleName="img "+String.valueOf(calendar.getTimeInMillis())+".jpeg";
            File storgeDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            new File(storgeDir +"/FolderName").mkdir();
            File outputfile =new File(storgeDir+"/FolderName/",fleName );
            FileOutputStream flo = new FileOutputStream(outputfile);
            flo.write(bytearray.toByteArray());
            flo.close();

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imge_user_name,img_post;
        TextView text_user_name, txt_rewerd, txt_date, txt_type_lost, location, txt_loster_founder, txt_post;
        Button   black_devide;
        Spinner  sp_up_Del;
        Button btn_coment ,btn_location;
        RelativeLayout relative_post;


        public ProductViewHolder( View itemView) {

            super(itemView);
            imge_user_name = itemView.findViewById(R.id.imge_user_name);
            img_post = itemView.findViewById(R.id.img_post);
            text_user_name = itemView.findViewById(R.id.text_user_name);
            txt_rewerd = itemView.findViewById(R.id.txt_rewerd);
            txt_date = itemView.findViewById(R.id.txt_date);
            //   txt_area = itemView.findViewById(R.id.txt_area);
            txt_type_lost = itemView.findViewById(R.id.txt_type_lost);
            location = itemView.findViewById(R.id.location);
            txt_loster_founder = itemView.findViewById(R.id.txt_loster_founder);
            txt_post = itemView.findViewById(R.id.txt_post);
            btn_coment=itemView.findViewById(R.id.btn_comment);
            black_devide=itemView.findViewById(R.id.black_devide);
            btn_location=itemView.findViewById(R.id.btn_location);

            relative_post = itemView.findViewById(R.id.relative_post);
            sp_up_Del=itemView.findViewById(R.id.Spinner_up_Del);

        }


    }
}