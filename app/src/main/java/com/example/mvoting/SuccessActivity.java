package com.example.mvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessActivity extends AppCompatActivity {
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        btnBack = findViewById(R.id.backButton2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirect to already voted page
                Intent loginIntent = new Intent( SuccessActivity.this,
                        VotingCompleted.class );
                startActivity(loginIntent);
                finish();
            }
        });
    }
}