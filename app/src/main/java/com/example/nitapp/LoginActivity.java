package com.example.nitapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout rollET, passwordET;
    int tim = 0;
    ProgressBar progressBarLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        rollET = findViewById(R.id.roll_login);
        passwordET = findViewById(R.id.password_login);
        progressBarLoginActivity = findViewById(R.id.progressbar_login_activity);
    }

    @Override
    protected void onResume() {
        if (tim == 0) {
            Intent splashIntent = new Intent(this, SplashScreen.class);
            startActivity(splashIntent);
            tim = 1;
        }
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    public void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            //Toast.makeText(this, "logging in", Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            //Toast.makeText(this, "NULL User", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view) {
        Dialog intermediate_dialog = new Dialog(this);
        intermediate_dialog.setContentView(R.layout.intermediate_dialog);
        Button reg_student_btn = intermediate_dialog.getWindow().findViewById(R.id.reg_student);
        reg_student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentSignUp.class);
                startActivity(intent);
                finish();
            }
        });
        Button reg_teacher_btn = intermediate_dialog.getWindow().findViewById(R.id.reg_teacher);
        reg_teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfessorSignUp.class);
                startActivity(intent);
                finish();
            }
        });
        intermediate_dialog.setCanceledOnTouchOutside(true);
        intermediate_dialog.show();
    }

    public void login(View view) {
        String roll = rollET.getEditText().getText().toString();
        String password = passwordET.getEditText().getText().toString();

        if (roll.isEmpty()) {
            Toast.makeText(this, "Roll-no is not Entered !!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is not Entered !!", Toast.LENGTH_SHORT).show();
        } else {
            progressBarLoginActivity.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(roll + "@p.com", password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBarLoginActivity.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                //Log.w("logging in", "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                        }
                    });
        }
    }
}
