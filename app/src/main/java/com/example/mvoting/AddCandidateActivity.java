package com.example.mvoting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvoting.model.CandidateModel;
import com.example.mvoting.model.UserModel;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddCandidateActivity extends AppCompatActivity {
    Button btnBack, btnAddCandidate;
    EditText txtCandidateName, txtPartyName;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_candidate);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtCandidateName = findViewById(R.id.txtCandidateName);
        txtPartyName = findViewById(R.id.txtPartyName);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent( AddCandidateActivity.this,
                        AdminActivity.class );
                startActivity(addIntent);
                finish();
            }
        });

        btnAddCandidate = findViewById(R.id.btnAddCandidate);
        btnAddCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candidateName = txtCandidateName.getText().toString();
                String partyName = txtPartyName.getText().toString();

                String id = mDatabase.push().getKey();
                CandidateModel candidateModel = new CandidateModel(candidateName, partyName, 0, 0);

                if(id != "") {
                    mDatabase.child("candidate").push().setValue(candidateModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.e("Data", "Candidate ADDED");

                        }
                    });

                    Intent addIntent = new Intent( AddCandidateActivity.this,
                            AdminActivity.class );
                    startActivity(addIntent);
                    finish();
                }else {
                    Toast.makeText(AddCandidateActivity.this, "Error candidate not registered....", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
