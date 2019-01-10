package com.example.blaze.khanaapp;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.blaze.khanaapp.Database.Database;
import com.example.blaze.khanaapp.model.Food;
import com.example.blaze.khanaapp.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {
TextView foodName,food_price,food_description;
ImageView foodImage;
 CollapsingToolbarLayout collapsingToolbarLayout;
 FloatingActionButton btnCart;
 ElegantNumberButton numButton;
 String FoodId="";
 FirebaseDatabase database;
 DatabaseReference foods;
 Food currentfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        //Firebase Code
        database=FirebaseDatabase.getInstance();
        foods=database.getReference("Foods");
        //Initializing view
        numButton=findViewById(R.id.numberButton);
        btnCart=findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        FoodId,
                        currentfood.getName(),
                        numButton.getNumber(),currentfood.getPrice(),
                        currentfood.getDiscount()

                ));
                Toast.makeText(FoodDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });
   food_description=findViewById(R.id.food_description);
        foodName=findViewById(R.id.food_name);
        food_price=findViewById(R.id.food_price);
        foodImage=findViewById(R.id.foodImg);
        collapsingToolbarLayout=findViewById(R.id.collapse);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        //Get food id
        if(getIntent()!=null)
        {
            FoodId=getIntent().getStringExtra("FoodId");
            if(!FoodId.isEmpty())
            {
                getDetailFood(FoodId);
            }
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentfood=dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentfood.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(currentfood.getName());
                foodName.setText(currentfood.getName());
                food_price.setText(currentfood.getPrice());
                food_description.setText(currentfood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
