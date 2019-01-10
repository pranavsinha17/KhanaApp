package com.example.blaze.khanaapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blaze.khanaapp.Interface.itemClickListener;
import com.example.blaze.khanaapp.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private itemClickListener itemClickListener;
    public TextView foodName;
    public ImageView foodview;

    public void setItemClickListener(itemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }


    public FoodViewHolder(View itemView) {
        super(itemView);
        foodName=(TextView)itemView.findViewById(R.id.food_name);
        foodview=itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
