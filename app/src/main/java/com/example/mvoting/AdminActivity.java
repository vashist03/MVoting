package com.example.mvoting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.mvoting.adapter.CandidateAdminAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listViewAdmin = findViewById(R.id.list2);
        for(int i=0;i<10;i++){
            alCandidateAdmin.add(new CandidateModel("Varun Puttur "+i, "Labour Party", 0, 0));
        }

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
}