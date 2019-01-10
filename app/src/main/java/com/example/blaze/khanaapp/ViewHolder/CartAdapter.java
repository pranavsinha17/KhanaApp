package com.example.blaze.khanaapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.blaze.khanaapp.Interface.itemClickListener;
import com.example.blaze.khanaapp.OrderCart;
import com.example.blaze.khanaapp.R;
import com.example.blaze.khanaapp.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
//import java.util.List;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView cart_name,cart_price;
    public ImageView cart_count;
    private itemClickListener itemClickListener;

    public void setCart_name(TextView cart_name) {
        this.cart_name = cart_name;
    }




    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cart_name=itemView.findViewById(R.id.itemName);
        cart_price=itemView.findViewById(R.id.itemPrice);
        cart_count=(ImageView)itemView.findViewById(R.id.itemCount);
    }
    @Override
    public void onClick(View v) {

    }


}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
   private List<Order> listData;



    private Context context;



    public CartAdapter(List<Order> listData ,Context context)
    {

        this.listData = new ArrayList<>();
        this.listData=listData;
        this.context=context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.card_layout,viewGroup,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        TextDrawable drawable=TextDrawable.builder().buildRound(""+listData.get(i).getQuantity(),Color.RED);
        cartViewHolder.cart_count.setImageDrawable(drawable);
        Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        int price=(Integer.parseInt(listData.get(i).getPrice()))*(Integer.parseInt(listData.get(i).getQuantity()));
        cartViewHolder.cart_price.setText(fmt.format(price));
        cartViewHolder.cart_name.setText(listData.get(i).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
