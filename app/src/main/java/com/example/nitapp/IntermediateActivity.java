package com.example.nitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IntermediateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);
    }

    public void student(View view) {

        Intent intent=new Intent(this,StudentSignUp.class);
        startActivity(intent);

    }

    public void professor(View view) {

        Intent intent=new Intent(this,ProfessorSignUp.class);
        startActivity(intent);
    }
}
