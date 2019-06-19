package com.example.nitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;

    EditText rollET,passwordET;


    int tim=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Students");

        mAuth = FirebaseAuth.getInstance();

        rollET=findViewById(R.id.roll_login);
        passwordET=findViewById(R.id.password_login);
    }

    @Override
    protected void onResume() {
        if(tim==0) {
            Intent splashIntent = new Intent(this, SplashScreen.class);
            startActivity(splashIntent);
            tim=1;
        }
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser firebaseUser)
    {
        if(firebaseUser!=null)
        {
            Toast.makeText(this,"NotNULL",Toast.LENGTH_LONG).show();
        }
        else
        {

            Toast.makeText(this,"NULL",Toast.LENGTH_LONG).show();
        }
    }


    public void register(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
