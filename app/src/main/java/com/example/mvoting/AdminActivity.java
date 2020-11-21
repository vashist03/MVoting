package com.example.mvoting;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private static ArrayList<CandidateModel> alCandidateAdmin;
    private static CandidateAdminAdapter adapter;
    private static ListView listViewAdmin;
    DataBaseHelper db = new DataBaseHelper(this);
    Button btnResult;

    @Override
    public void onBackPressed() {
        alCandidateAdmin.clear();
        Intent login = new Intent( AdminActivity.this,
                MainActivity.class );
        startActivity(login);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        alCandidateAdmin = new ArrayList<>();

        alCandidateAdmin.clear();
        listViewAdmin = findViewById(R.id.list2);

        populateList();
        adapter = new CandidateAdminAdapter(this, alCandidateAdmin);
        listViewAdmin.setAdapter(adapter);

        Log.e("qqq", ""+alCandidateAdmin.size());
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alCandidateAdmin.clear();
                Intent addIntent = new Intent( AdminActivity.this,
                        AddCandidateActivity.class );
                startActivity(addIntent);
                finish();
            }
        });

        btnResult = findViewById(R.id.btnCheckResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alCandidateAdmin.clear();
                Intent addIntent = new Intent( AdminActivity.this,
                        LiveResult.class );
                startActivity(addIntent);
                finish();
            }
        });
    }

    public void delete(int position){
        Log.e("ERR", alCandidateAdmin.get(position).getName()+"");
        db.deleteCandidate(alCandidateAdmin.get(position).getName());
        alCandidateAdmin.remove(position);

        //Update listview based on item position
        listViewAdmin.invalidateViews();
    }

    public void populateList(){
        Cursor res = db.getCandidate();
        if(res.getCount() < 0){
            alCandidateAdmin.add(new CandidateModel("", "", 0, 0));
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