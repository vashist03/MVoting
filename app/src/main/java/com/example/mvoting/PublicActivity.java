package com.example.mvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mvoting.adapter.CandidateAdapter;
import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.CandidateModel;

import java.util.ArrayList;

public class PublicActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<CandidateModel> alCandidate;
    private CandidateAdapter adapter;
    private Button btnVote;
    DataBaseHelper db = new DataBaseHelper(this);
    String getName, getSurname, getNic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        ArrayList<String> fname = new ArrayList<>();
        ArrayList<String> surname = new ArrayList<>();
        ArrayList<String> nic = new ArrayList<>();

        if(getIntent().getExtras() != null) {
            getName = getIntent().getStringExtra("fname");
            getSurname = getIntent().getStringExtra("surname");
            getNic = getIntent().getStringExtra("nic");
        }

        alCandidate = new ArrayList<CandidateModel>();
        listView = findViewById(R.id.LVCandidate);
        listView.setItemsCanFocus(true);

        populateList();

        adapter = new CandidateAdapter(this, alCandidate);
        listView.setAdapter(adapter);

        btnVote = findViewById(R.id.btnVoting);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("pos", alCandidate.size()+"");
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
                    if(vote != 0) {
                        //Update user vote
                        //Add vote to candidate
                        Log.e("selected values ", "candidate: " + candidate + "party: " + party + "vote: " + vote);
                    }
                }
            }
        });
    }

    public void populateList(){
        Cursor res = db.getCandidate();
        if(res.getCount() < 0){
            Log.e("Error","Not able to retrieve candidate data");
        }
        else {
            while (res.moveToNext()) {
                String candidateName = res.getString(1);
                String partyName = res.getString(2);
                alCandidate.add(new CandidateModel(candidateName, partyName, 0, 0));
            }
        }
    }
}