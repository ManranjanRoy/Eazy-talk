package com.ventrux.eazetalk.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ventrux.eazetalk.Activity.AstrologerDetailsActivity;
import com.ventrux.eazetalk.Api.ApiLinks;
import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.R;
import com.ventrux.eazetalk.model.AstrologerModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AstrologerListAdaptor extends RecyclerView.Adapter<AstrologerListAdaptor.ImageViewHolder> {
private Context mContext;
private List<AstrologerModel> mUploads;
    private OnitemClickListner mlistner;

    public interface  OnitemClickListner{

        void OnitemClick(int position);
    }
    public  void setonItemClickListner(OnitemClickListner listner){
        mlistner=listner;

    }

public AstrologerListAdaptor(Context mContext, List<AstrologerModel> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
        }

@Override
public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_astrologer, parent, false);
        return new ImageViewHolder(v,mlistner);
        }

@Override
public void onBindViewHolder(final ImageViewHolder holder, int position) {

        final AstrologerModel uploadCurrent = mUploads.get(position);
    String imgurl= ApiLinks.baseimgurl+uploadCurrent.getImage();
    Picasso.with(mContext)
            .load(imgurl)
            .placeholder(R.drawable.ic_launcher_background)
            .fit()
            .into(holder.catimg);
            // holder.imgca.setImageDrawable(mContext.getResources().getDrawable(uploadCurrent.getCode()));
            holder.name.setText(uploadCurrent.getName());
            holder.exp.setText("Exp  : "+uploadCurrent.getExp());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(mContext, AstrologerDetailsActivity.class);
            i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            //i.putExtra("url",uploadCurrent.getId());
            StaticData.singleastrologer=uploadCurrent;
            mContext.startActivity(i);
        }
    });



        }

@Override
public int getItemCount() {
        return mUploads.size();
        }

public class ImageViewHolder extends RecyclerView.ViewHolder {
    public TextView name,exp;
    ImageView catimg;
    LinearLayout ll;
    public ImageViewHolder(View itemView, final OnitemClickListner listner) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        exp=itemView.findViewById(R.id.exp);
        catimg=itemView.findViewById(R.id.profilepic);
        //ll=itemView.findViewById(R.id.videocard);
        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner !=null){
                    int position =getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listner.OnitemClick(position);
                    }
                }
            }
        });*/


    }


}


}