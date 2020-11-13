package com.example.mvoting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvoting.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText etFname, etSurname, etAddress, etNic, etPin, etPhone, etEmail, etDob;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegst);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<UserModel> userLists = new ArrayList<>();

        etFname = findViewById(R.id.editTextTextPersonName10);
        etSurname = findViewById(R.id.editTextTextPersonName9);
        etAddress = findViewById(R.id.editTextTextMultiLine);
        etNic = findViewById(R.id.editTextTextPersonName8);
        etPin = findViewById(R.id.editTextNumberPassword);
        etPhone = findViewById(R.id.editTextPhone);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        etDob = findViewById(R.id.editTextDate3);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = etFname.getText().toString();
                String surname = etSurname.getText().toString();
                String address = etAddress.getText().toString();
                String nic = etNic.getText().toString();
                String pin = etPin.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String dob = etDob.getText().toString();

                Log.e("fname", fname);
                Log.e("surname", surname);
                Log.e("address", address);
                Log.e("nic", nic);
                Log.e("pin", pin);
                Log.e("phone", phone);
                Log.e("email", email);
                Log.e("dob", dob);

                Log.e("dob", mDatabase.getRoot()+"");
                String id = mDatabase.push().getKey();
                UserModel userModel = new UserModel(id, fname, surname, address, nic, pin, phone, email, dob, "0", false, "Public");
                //userLists.add(userModel);

                if(id != "") {
                    mDatabase.child("users").push().setValue(userModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.e("Data", "ADDED");

                        }
                    });
                    Toast.makeText(RegisterActivity.this, "Voter Registered Successfully....", Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent( RegisterActivity.this,
                            MainActivity.class);
                    startActivity(loginIntent);
                }else {
                    Toast.makeText(RegisterActivity.this, "Error not registered....", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

