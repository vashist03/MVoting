package com.example.mvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.mvoting.adapter.CandidateAdminAdapter;
import com.example.mvoting.adapter.LiveResultAdapter;
import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.CandidateModel;
import com.example.mvoting.model.VotingModel;

import java.util.ArrayList;

public class LiveResult extends AppCompatActivity {
    private ArrayList<VotingModel> alVoting = new ArrayList<VotingModel>();
    private LiveResultAdapter adapter;
    private ListView listViewResult;
    DataBaseHelper db = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_result);

        listViewResult = findViewById(R.id.LVResult);
        populateList();

        adapter = new LiveResultAdapter(this, alVoting);
        listViewResult.setAdapter(adapter);
    }

    public void populateList(){
        Cursor res = db.getResult();
        if(res.getCount() < 0){
            Log.e("Error","Not able to retrieve candidate data");
        }
        else {
            while (res.moveToNext()) {
                String candidateName = res.getString(0);
                String partyName = res.getString(1);
                String vote = res.getString(2);
                alVoting.add(new VotingModel(candidateName, partyName, Integer.parseInt(vote)));
            }
        }
    }
}