package com.example.mvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mvoting.adapter.CandidateAdapter;
import com.example.mvoting.model.CandidateModel;

import java.util.ArrayList;

public class PublicActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<CandidateModel> alCandidate;
    private CandidateAdapter adapter;
    private Button btnVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        alCandidate = new ArrayList<CandidateModel>();
        listView = findViewById(R.id.LVCandidate);
        listView.setItemsCanFocus(true);
        for(int i=0;i<10;i++){
            alCandidate.add(new CandidateModel("Varun Puttur "+i, "Labour Party", 0, 0));
        }

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
                        Log.e("selected values ", "candidate: " + candidate + "party: " + party + "vote: " + vote);
                    }
                }
            }
        });
    }
}