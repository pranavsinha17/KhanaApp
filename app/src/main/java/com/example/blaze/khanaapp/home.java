package com.example.blaze.khanaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaze.khanaapp.Common.Common;
import com.example.blaze.khanaapp.Interface.itemClickListener;
import com.example.blaze.khanaapp.ViewHolder.HomeViewHolder;
import com.example.blaze.khanaapp.model.Catagory;
import com.example.blaze.khanaapp.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
RecyclerView recycler_menu;
RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference catagory;
    FirebaseRecyclerAdapter<Catagory,HomeViewHolder> adapter;
    TextView fullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Firebase coding

   database=FirebaseDatabase.getInstance();
   catagory= database.getReference("Catagory");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent cartIntent=new Intent(home.this,OrderCart.class);
               startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setting the name of user
         View headerView=navigationView.getHeaderView(0);
        fullName =headerView.findViewById(R.id.textName);
        fullName.setText(Common.currentUser.getName());

        //Now loasd menu
        recycler_menu=findViewById(R.id.recycle);
        recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
    }

    private void loadMenu() {
        adapter=new FirebaseRecyclerAdapter<Catagory, HomeViewHolder>(Catagory.class,R.layout.menu_item,HomeViewHolder.class,catagory) {
            @Override
            protected void populateViewHolder(HomeViewHolder viewHolder, Catagory model, final int position) {
                viewHolder.menuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Catagory clickItem=model;
                viewHolder.setItemClickListener(new itemClickListener() {
                    @Override
                    public void onClick(View view, int osition, boolean isLongClick) {
                        //Toast.makeText(home.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                     //Get catagory id and send it to next activity
                        Intent food=new Intent(home.this,FoodList.class);
                        //becz catagory id is key
                        food.putExtra("CatagoryId",adapter.getRef(position).getKey());
                        startActivity(food);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cart=new Intent(home.this,OrderCart.class);
            startActivity(cart);

        } else if (id == R.id.nav_orders) {
            Intent order=new Intent(home.this,Order_status.class);
            startActivity(order);


        } else if (id == R.id.nav_logout) {
            Intent logout=new Intent(home.this,SignIn.class);
            logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logout);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
