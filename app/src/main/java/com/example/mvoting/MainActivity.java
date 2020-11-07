package com.example.mvoting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    TextView txtNic, txtPin;
    private DataBaseHelper db = new DataBaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNic = findViewById(R.id.editTextTextPersonName);
        txtPin = findViewById(R.id.editTextNumberPassword2);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirect to register page
                Intent registerIntent = new Intent( MainActivity.this,
                        RegisterActivity.class );
                startActivity(registerIntent);
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nic = txtNic.getText().toString();
                String pin = txtPin.getText().toString();

                String res = db.checkUser(pin, nic);
                Log.e("R2", res);
               // if(res == "Public") {
                    Intent home = new Intent( MainActivity.this, PublicActivity.class );
                    startActivity(home);
                //}else if(res == "Admin") {
                    //Intent admin = new Intent( MainActivity.this, AdminActivity.class );
                    //startActivity(admin);
                //}
            }
        });
    }


}