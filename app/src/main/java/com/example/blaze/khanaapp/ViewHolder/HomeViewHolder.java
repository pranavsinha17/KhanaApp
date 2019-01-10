package com.example.blaze.khanaapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blaze.khanaapp.Interface.itemClickListener;
import com.example.blaze.khanaapp.R;

public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
private itemClickListener itemClickListener;
    public TextView menuName;
    public ImageView imageView;
    public HomeViewHolder(View itemView) {
        super(itemView);
        menuName=(TextView)itemView.findViewById(R.id.menu_name);
        imageView=itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(itemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
