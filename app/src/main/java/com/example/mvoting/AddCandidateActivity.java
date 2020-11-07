package com.example.mvoting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.CandidateModel;
import com.example.mvoting.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddCandidateActivity extends AppCompatActivity {
    Button btnBack, btnAddCandidate;
    EditText txtCandidateName, txtPartyName;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseCandidate = FirebaseDatabase.getInstance().getReference().child("candidate");
    private DataBaseHelper db = new DataBaseHelper(this);
    private CandidateModel candidate;
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
                db.addCandidate(candidateModel);
                if(id != "") {
                    mDatabase.child("candidate").push().setValue(candidateModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.e("Data", "Candidate ADDED");

                        }
                    });

                    mDatabaseCandidate.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {

                                String fbName = ds.child("name").getValue().toString();
                                String fbParty= ds.child("party").getValue().toString();
                                int fbVote = Integer.parseInt(ds.child("vote").getValue().toString());
                                int fbUnvote = Integer.parseInt(ds.child("unvote").getValue().toString());

                                candidate = new CandidateModel(fbName, fbParty, fbVote, fbUnvote);
                                db.addCandidate(candidate);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("error", databaseError.getCode()+"");
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
