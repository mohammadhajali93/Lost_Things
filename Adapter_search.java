package com.mohammadhajali.mychat22;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public  class Adapter_search extends RecyclerView.Adapter<Adapter_search.ProductViewHolder>  {

    RequestQueue requestQueue;
    Bitmap bitmap;
    private Context mContext;
    private ArrayList<String> imge = new ArrayList<String>();
    private ArrayList<com.mohammadhajali.mychat22.contact> contacts = new ArrayList<com.mohammadhajali.mychat22.contact>();

    String id_user , id_post;
    private static  final  String TAG ="hg";
    com.mohammadhajali.mychat22.contact contact;

    public Adapter_search(Context mContext, ArrayList<String> imge, ArrayList<com.mohammadhajali.mychat22.contact> contacts) {
        this.mContext = mContext;
        this.imge = imge;
        this.contacts = contacts;
    }

    public  void  updatelist(ArrayList<com.mohammadhajali.mychat22.contact> newlist){

        contacts=new ArrayList<>();
        contacts.addAll(newlist);
        notifyDataSetChanged();

    }

    public void  setfilter( ArrayList<com.mohammadhajali.mychat22.contact> newlist ){

        contacts=new ArrayList<>();
        contacts.addAll(newlist);


        notifyDataSetChanged();
    }



    @Override
    public Adapter_search.ProductViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.post_search, null);
        Adapter_search.ProductViewHolder viewHolder = new Adapter_search.ProductViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder,  int i) {
        contact = contacts.get(i);

        productViewHolder.txt_post.setText(contact.getTxt_post());
        productViewHolder.txt_loster_founder.setText(contact.getTxt_loster_founder());
        productViewHolder.txt_type_lost.setText(contact.getTxt_type_lost());
       // productViewHolder.txt_area.setText(contact.getTxt_area());
        productViewHolder.txt_date.setText(contact.getTxt_date());
        productViewHolder.txt_rewerd.setText(contact.getTxt_rewerd());

        productViewHolder.location.setText(contact.getLocation_longitude());

        productViewHolder.text_user_name.setText(contact.getText_user_name());
//         final Double lan= Double.valueOf(contact.getLocation_latitude());
//        final Double longe= Double.valueOf(contact.getLocation_longitude());
                  String x =contact.getLocation_latitude();


//
//        productViewHolder.btn_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Double latitude= Double.valueOf(contact.getLocation_latitude());
//                Double longitude= Double.valueOf(contact.getLocation_longitude());
//
//                Intent intent = new Intent(mContext,MapsActivity.class);
//                intent.putExtra("lat",latitude);
//                intent.putExtra("long",longitude);
//                Log.i(TAG, "lanted"+latitude+" , "+longitude);
//
//                // Log.i(TAG, "lanted"+latitude+" , "+longitude);
//
//                mContext.startActivity(intent);
//
//            }
//        });


        final String id_user=contact.getId_user();
        final String id_post=contact.getId_post();


//        if(imge.equals("0")){
//            productViewHolder.img_post.setVisibility(View.GONE);
//        }
//        else {
        productViewHolder.btn_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //}
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_info",Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_user",id_user);
                editor.putString("id_post", id_post);

                editor.apply();
                Intent intent= new Intent( mContext, comment.class);
                mContext.startActivity(intent);

            }
        });

       // productViewHolder.img_post.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .asBitmap()
                    .load(contact.getImg_post())
                    .into(productViewHolder.img_post);

        Glide.with(mContext)
                .asBitmap()
                .load(contact.getImge_user_name())
                .into(productViewHolder.imge_user_name);

        Log.i(TAG ,"id_user"+id_post+","+id_user);


        productViewHolder.img_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int currentposition =  i;
//                contact c1 = contacts.get(i);
                //removeitem(c1);
//
//                savephoto();
//                Toast.makeText(mContext ,"t "+contact.getTxt_date(),Toast.LENGTH_LONG).show();
            }
        });





    }
public  void  removeitem( com.mohammadhajali.mychat22.contact c){
        int position = contacts.indexOf(c);
        contacts.remove(position);
        notifyItemRemoved(position);
}

    public void onBindViewHolder(Adapter_post.ProductViewHolder productViewHolder, int i) {



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
        ImageView imge_user_name, img_post;
        TextView text_user_name, txt_rewerd, txt_date, txt_area, txt_type_lost, location, txt_loster_founder, txt_post;
        Button btn_coment , black_devide ,btn_location;

        RelativeLayout relative_post;


        public ProductViewHolder( View itemView) {

            super(itemView);
            imge_user_name = itemView.findViewById(R.id.imge_user_name1);
            img_post = itemView.findViewById(R.id.img_post1);
            text_user_name = itemView.findViewById(R.id.text_user_name1);
            txt_rewerd = itemView.findViewById(R.id.txt_rewerd1);
            txt_date = itemView.findViewById(R.id.txt_date1);
          //  txt_area = itemView.findViewById(R.id.txt_area1);
            txt_type_lost = itemView.findViewById(R.id.txt_type_lost1);
            location = itemView.findViewById(R.id.location1);
            txt_loster_founder = itemView.findViewById(R.id.txt_loster_founder1);
            txt_post = itemView.findViewById(R.id.txt_post1);
            btn_coment=itemView.findViewById(R.id.btn_comment1);
           // black_devide=itemView.findViewById(R.id.black_devide1);
            relative_post = itemView.findViewById(R.id.relative_post1);
          //  btn_location=itemView.findViewById(R.id.btn_location1);

        }
    }
}
