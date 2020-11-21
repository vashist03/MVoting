package com.example.mvoting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mvoting.adapter.CandidateAdapter;
import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.CandidateModel;
import com.example.mvoting.model.UserModel;
import com.example.mvoting.model.VotingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PublicActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<CandidateModel> alCandidate = new ArrayList<CandidateModel>();
    private static ArrayList<Integer> alCount = new ArrayList<>();
    private CandidateAdapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("users");
    private Button btnVote;
    private VotingModel votingModel;
    DataBaseHelper db = new DataBaseHelper(this);
    String getName, getSurname, getNic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayList<String> fname = new ArrayList<>();
        ArrayList<String> surname = new ArrayList<>();
        ArrayList<String> nic = new ArrayList<>();

        if(getIntent().getExtras() != null) {
            getName = getIntent().getStringExtra("fname");
            getSurname = getIntent().getStringExtra("surname");
            getNic = getIntent().getStringExtra("nic");
        }

        String voted = db.checkVoted(getNic);
        if(voted.contains("1")){
            //Redirect to already voted page
            Intent loginIntent = new Intent( PublicActivity.this,
                    VotingCompleted.class );
            startActivity(loginIntent);
            finish();
        }else {


            listView = findViewById(R.id.LVCandidate);
            listView.setItemsCanFocus(true);

            populateList();

            adapter = new CandidateAdapter(this, alCandidate, alCount);
            listView.setAdapter(adapter);
        }


        btnVote = findViewById(R.id.btnVoting);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("AAAA", alCount.size()+"");
                for(int i = 0; i < alCandidate.size(); i++){
                    String candidate = alCandidate.get(i).getName();
                    String party = alCandidate.get(i).getParty();
                    int vote = alCandidate.get(i).getVote();
                    int unvote = alCandidate.get(i).getUnvote();
                    if(unvote == 1) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                    if(alCount.size() == 3) {
                        if(vote != 0) {
                            //Update user vote
                            updateUserVotingStatus();
                            db.updateUserVote(getNic);
                            //Add vote to candidate
                            String id = mDatabase.push().getKey();
                            votingModel = new VotingModel(candidate, party, 1);
                            db.addVoting(votingModel);
                            if(id != "") {
                                mDatabase.child("voting").push().setValue(votingModel, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Log.e("Data", "Voting ADDED");

                                    }
                                });
                            }
                            //Redirect to success page
                            Intent loginIntent = new Intent( PublicActivity.this,
                                    SuccessActivity.class );
                            startActivity(loginIntent);
                            finish();
                        }
                    }else if (alCandidate.size() > 3) {
                        Toast.makeText(PublicActivity.this, "Vote 3 candidates only.", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        alCount.clear();
                        finish();
                        startActivity(intent);
                    }

                }
            }
        });
    }

    public void populateList(){
        Cursor res = db.getCandidate();
        if(res.getCount() < 0){
            Log.e("Error","Not able to retrieve candidate data");
            alCandidate.add(new CandidateModel("", "", 0, 0));
        }
        else {
            while (res.moveToNext()) {
                String candidateName = res.getString(1);
                String partyName = res.getString(2);
                alCandidate.add(new CandidateModel(candidateName, partyName, 0, 0));
            }
        }
    }

    public void updateUserVotingStatus() {
        mDatabase1.orderByChild("nic").equalTo(getNic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String key = ds.getKey();
                    mDatabase1.child(key).child("voted").setValue(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", databaseError.getCode()+"");
            }
        });
    }

    public void getCheckedVote(String count) {
        String voted = count;
        Log.e("VOTED", voted+"");
    }
}