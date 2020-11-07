package com.example.mvoting;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.mvoting.adapter.CandidateAdminAdapter;
import com.example.mvoting.database.DataBaseHelper;
import com.example.mvoting.model.CandidateModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<CandidateModel> alCandidateAdmin = new ArrayList<CandidateModel>();
    private CandidateAdminAdapter adapter;
    private ListView listViewAdmin;
    DataBaseHelper db = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listViewAdmin = findViewById(R.id.list2);
        populateList();

        adapter = new CandidateAdminAdapter(this, alCandidateAdmin);
        listViewAdmin.setAdapter(adapter);
        Log.e("qqq", ""+alCandidateAdmin.size());
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent( AdminActivity.this,
                        AddCandidateActivity.class );
                startActivity(addIntent);
                finish();
            }
        });
    }

    public void delete(int position){
        Log.e("DDDD", ""+alCandidateAdmin.size());

        alCandidateAdmin.remove(position);
        //Update listview based on item position
        listViewAdmin.invalidateViews();

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
                alCandidateAdmin.add(new CandidateModel(candidateName, partyName, 0, 0));
            }
        }
    }


}