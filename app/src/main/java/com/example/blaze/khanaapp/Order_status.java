package com.example.blaze.khanaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.blaze.khanaapp.Common.Common;
import com.example.blaze.khanaapp.ViewHolder.OrderHolder;
import com.example.blaze.khanaapp.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Order_status extends AppCompatActivity {

    public RecyclerView  recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference order;
    FirebaseRecyclerAdapter<Request,OrderHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        database=FirebaseDatabase.getInstance();
        order=database.getReference("Requests");
         recyclerView=findViewById(R.id.listOrder);
         recyclerView.setHasFixedSize(true);
         layoutManager=new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
           loadOrder(Common.currentUser.getPhone());
    }

    private void loadOrder(String phone) {
        adapter=new FirebaseRecyclerAdapter<Request, OrderHolder>(
                Request.class,R.layout.order_layout,OrderHolder.class,order.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderHolder viewHolder, Request model, int position) {
                viewHolder.orderId.setText(adapter.getRef(position).getKey());
                viewHolder.orderStatus.setText(convertCodetoStatus(model.getStatus()));
                viewHolder.orderAddress.setText(model.getAddress());
                viewHolder.orderPhone.setText(model.getPhone());

            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodetoStatus(String status) {
        if(Objects.equals(status, "0"))
        {
            return "Order Placed";
        }
        else if(Objects.equals(status, "1")) {
            return "On the way";
        }
        else{
            return "Shipped";
        }
    }
}
