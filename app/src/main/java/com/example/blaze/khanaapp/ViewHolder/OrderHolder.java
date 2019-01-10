package com.example.blaze.khanaapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.blaze.khanaapp.Interface.itemClickListener;
import com.example.blaze.khanaapp.R;

public class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView orderId,orderStatus,orderPhone,orderAddress;
    private itemClickListener itemClickListener;
    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        orderId=itemView.findViewById(R.id.order_id);
        orderStatus=itemView.findViewById(R.id.orderStatus);
        orderAddress=itemView.findViewById(R.id.orderAddress);
        orderPhone=itemView.findViewById(R.id.orderPhone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(com.example.blaze.khanaapp.Interface.itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
