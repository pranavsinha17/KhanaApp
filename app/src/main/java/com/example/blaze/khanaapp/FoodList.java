package com.example.blaze.khanaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.blaze.khanaapp.Interface.itemClickListener;
import com.example.blaze.khanaapp.ViewHolder.FoodViewHolder;
import com.example.blaze.khanaapp.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class FoodList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference food;
    String catagoreyId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    //search
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchadapter;
    List<String> sugList = new ArrayList<String>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase code
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Foods");
        recyclerView = findViewById(R.id.recycle_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //Getting intent
        if (getIntent() != null) {
            catagoreyId = getIntent().getStringExtra("CatagoryId");
        }
        if (!catagoreyId.isEmpty() && catagoreyId != null) {
            loadListFood(catagoreyId);
        }

        materialSearchBar = findViewById(R.id.search);
        materialSearchBar.setHint("Search the food");
        //materialSearchBar.setSpeechMode(false);
        loadSuggest();
        materialSearchBar.setLastSuggestions(sugList);
        materialSearchBar.setCardViewElevation(100);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for (String search : sugList) {
                    //after typing by user it will change the suggest list
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //afet closing of search bar restore the original suggest adapter
                if (!enabled) {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    private void startSearch(CharSequence text) {
        searchadapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class, R.layout.food_menu, FoodViewHolder.class, food.orderByChild("Name").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.foodview);
                final Food local = model;
                viewHolder.setItemClickListener(new itemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start New Activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", searchadapter.getRef(position).getKey());//wedid this to send food id to the new activity
                        startActivity(foodDetail);

                    }
                });
            }
        };
        recyclerView.setAdapter(searchadapter);
    }

    private void loadSuggest() {
        food.orderByChild("MenuId").equalTo(catagoreyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())

                {
                    Food item = postSnapshot.getValue(Food.class);

                        sugList.add(item.getName());          //Add name of food to suggest list
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void loadListFood(String catagoreyId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.food_menu, FoodViewHolder.class,
                food.orderByChild("MenuId").equalTo(catagoreyId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model,  int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.foodview);
                final Food local = model;
                viewHolder.setItemClickListener(new itemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start New Activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());//wedid this to send food id to the new activity
                        startActivity(foodDetail);


                    }
                });
            }
        };
        //set Adapter
        recyclerView.setAdapter(adapter);
    }
}

