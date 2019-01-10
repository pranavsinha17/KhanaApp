package com.example.blaze.khanaapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaze.khanaapp.Common.Common;
import com.example.blaze.khanaapp.Database.Database;
import com.example.blaze.khanaapp.ViewHolder.CartAdapter;
import com.example.blaze.khanaapp.model.Order;
import com.example.blaze.khanaapp.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class OrderCart extends AppCompatActivity {
RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
FirebaseDatabase database;
DatabaseReference requests;
FButton btnPlaceOrder;
TextView totalPrice;
List<Order> cart=new ArrayList<>();
CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
        //Firebase Code
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");


        //init firebase

        recyclerView=findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        totalPrice=findViewById(R.id.total);
        btnPlaceOrder=findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogshow();

            }
        });
        loadListFood();
    }

    private void AlertDialogshow() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(OrderCart.this);
        alertDialog.setTitle("Just one more to go");
        alertDialog.setMessage("Enter Your Address : ");
        final EditText address=new EditText(OrderCart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        address.setLayoutParams(lp);
        alertDialog.setView(address);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Create new Request
                Request request=new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        address.getText().toString(),
                        totalPrice.getText().toString(),
                        cart
                );

                //Submit to the Firebase by using
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(OrderCart.this,"Bolo Tarara..!! Order Placed",Toast.LENGTH_SHORT).show();
                finish();





            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood() {
            cart=new Database(this).getCarts();
            cartAdapter=new CartAdapter(cart,this);
            recyclerView.setAdapter(cartAdapter);
            //total price calculation
        int total=0;
        for(Order order:cart)
        {
            total=total+(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            Locale locale=new Locale("en","US");
            NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
            totalPrice.setText(fmt.format(total));
        }

    }
}
