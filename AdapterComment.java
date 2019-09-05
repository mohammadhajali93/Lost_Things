package com.mohammadhajali.mychat22;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ProductViewHolder> {

    private  static  final  String TAG ="comment";
    private Context mCtx;
    RequestQueue requestQueue;
    String edit_omment;

    int mycolor;
    private List<Comments> comment_list;

    public AdapterComment(Context mCtx, List<Comments> comment_list) {
        this.mCtx = mCtx;
        this.comment_list = comment_list;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_comment, null);
        final ProductViewHolder viewHolder=new ProductViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Comments comments = comment_list.get(position);

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        // SharedPreferences.Editor editor = sharedPreferences.edit();


        int post_contect = Integer.parseInt(sharedPreferences.getString("id_post","null"));

        int post_comment= Integer.parseInt(comments.getId_post());

        //for (int i=0 ;i<comment_list.size();i++)
        //{
        if(post_comment==post_contect) {
            Log.i(TAG , post_comment+"............"+post_contect);

            holder.name.setText(comments.getName());
            holder.chatText.setText(comments.getText_comment());
            Glide.with(mCtx)
                    .asBitmap()
                    .load(comments.getImage())
                    .into(holder.user_image);
        }
        else if(post_comment!=post_contect) {
            holder.name.setVisibility(View.GONE);
            holder.chatText.setVisibility(View.GONE);
            holder.user_image.setVisibility(View.GONE);
            holder.edit_comment.setVisibility(View.GONE);
            holder.delete_comment.setVisibility(View.GONE);

        }else{
            holder.name.setVisibility(View.GONE);
            holder.chatText.setVisibility(View.GONE);
            holder.user_image.setVisibility(View.GONE);
            Toast.makeText(mCtx,"no comments yet",Toast.LENGTH_LONG).show();
        }

        holder.edit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                holder.new_update_comment.setVisibility(View.VISIBLE);
                holder.save_edit.setVisibility(View.VISIBLE);


            }
        });
//
//        holder.new_update_comment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        holder.save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences = mCtx.getSharedPreferences("user_info", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
                edit_omment= holder.new_update_comment.getText().toString();
//        editor.putString("new_update_comment",edit_omment);
//        editor.apply();

                if(!edit_omment.isEmpty()) {
                    Log.i(TAG, "edit_omment" + edit_omment);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://track-kids.com/edit_comment.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("id_comment", comments.getId_comment());
                            params.put("edit_omment", edit_omment);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }

                holder.new_update_comment.setVisibility(View.GONE);
                holder.save_edit.setVisibility(View.GONE);



            }
        });

        holder.delete_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest  = new StringRequest(Request.Method.POST, "http://track-kids.com/edit_comment.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id_comment2",comments.getId_comment());
                        //    params.put("edit_omment",edit_omment);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
                Toast.makeText(mCtx,"the  comment is deleted",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return comment_list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView user_image;
        TextView name,chatText ,edit_comment ,new_update_comment ,save_edit ,delete_comment;
        LinearLayout linearLayout;
        RelativeLayout RelativeLayout_comment;
        FloatingActionButton floatingActionButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            edit_comment = itemView.findViewById(R.id.edit_comment);
            delete_comment = itemView.findViewById(R.id.delete_comment);
            save_edit = itemView.findViewById(R.id.save_edit);
            new_update_comment = itemView.findViewById(R.id.new_update_comment);
            user_image = itemView.findViewById(R.id.iv_image);
            chatText = itemView.findViewById(R.id.tv_chat_text);
            RelativeLayout_comment=itemView.findViewById(R.id.RelativeLayout_comment);

            requestQueue = Volley.newRequestQueue(mCtx);
        }

    }
}