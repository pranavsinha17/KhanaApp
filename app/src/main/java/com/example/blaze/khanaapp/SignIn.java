package com.example.blaze.khanaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blaze.khanaapp.Common.Common;
import com.example.blaze.khanaapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
MaterialEditText editphone,editpass;
Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editpass=(MaterialEditText)findViewById(R.id.pass);
        editphone=(MaterialEditText)findViewById(R.id.editphone);
        btnlogin=findViewById(R.id.btnsignin);
        //firebase code->
        FirebaseDatabase fd=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=fd.getReference("User");
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user exist in databasde or not
                        if (dataSnapshot.child(editphone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            //Get the info of user
                            User user = dataSnapshot.child(editphone.getText().toString()).getValue(User.class);
                            user.setPhone(editphone.getText().toString());
                            if (user.getPassword().equals( editpass.getText().toString())) {
                                Intent it2=new Intent(SignIn.this,home.class);
                                Common.currentUser=user;
                                startActivity(it2);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
