package com.example.mvoting;

import android.content.Intent;
import android.os.Bundle;

import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT= 7000;
    private DataBaseHelper dataBaseHelper;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    private UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(SplashActivity.this, "Application starting... ", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        dataBaseHelper = new DataBaseHelper(this);

        mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {

                            String fbId = ds.child("id").getValue().toString();
                            String fbNic = ds.child("nic").getValue().toString();
                            String fbPin = ds.child("pin").getValue().toString();
                            String fbFname = ds.child("fname").getValue().toString();
                            String fbSurname = ds.child("surname").getValue().toString();
                            String fbVote = ds.child("vote").getValue().toString();
                            Boolean fbVoted = Boolean.parseBoolean(ds.child("voted").getValue().toString());
                            String fbDob = ds.child("dob").getValue().toString();
                            String fbAddress = ds.child("address").getValue().toString();
                            String fbEmail = ds.child("email").getValue().toString();
                            String fbPhone = ds.child("phone").getValue().toString();
                            String fbUtype = ds.child("uType").getValue().toString();

                            user = new UserModel(fbId, fbFname, fbSurname, fbAddress, fbNic, fbPin, fbPhone, fbEmail, fbDob, fbVote, fbVoted, fbUtype);
                            dataBaseHelper.addUser(user);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("error", databaseError.getCode()+"");
                    }
                });

        new Handler().postDelayed (new Runnable () {
            @Override
            public void run () {
                //Redirect to login page
                Intent loginIntent = new Intent( SplashActivity.this,
                        MainActivity.class );
                startActivity(loginIntent);
                finish();

            }
        } , SPLASH_TIME_OUT );
    }
}