package com.example.nitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfessorSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener{

    private TextInputLayout textInputLayoutProfessorName, textInputLayoutProfessorEmployeeID, textInputLayoutProfessorEmail, textInputLayoutProfessorContactNo, textInputLayoutProfessorPassword;
    private RadioGroup radioGroupProfessorGender;
    private RadioButton radioButtonProfessorGender;
    private ProgressBar progressBarProfessorSignUp;
    private Spinner spinnerDepartment;
    private Button buttonProfessorSignUp;

    private String professorName, professorDepartment, professorEmployeeID,
            professorEmail, professorContactNo, professorGender, professorPassword;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_sign_up);

        textInputLayoutProfessorName = findViewById(R.id.text_input_layout_professor_name);
        textInputLayoutProfessorEmployeeID = findViewById(R.id.text_input_layout_professor_id);
        textInputLayoutProfessorEmail = findViewById(R.id.text_input_layout_professor_email);
        textInputLayoutProfessorContactNo = findViewById(R.id.text_input_layout_professor_contactno);

        radioGroupProfessorGender = findViewById(R.id.radio_group_professor_gender);
        int selectedId = radioGroupProfessorGender.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButtonProfessorGender = findViewById(selectedId);
        textInputLayoutProfessorPassword = findViewById(R.id.text_input_layout_professor_password);
        progressBarProfessorSignUp = findViewById(R.id.progressbar_professor_fragment);
        buttonProfessorSignUp = findViewById(R.id.button_professor_signup);
        spinnerDepartment = findViewById(R.id.spinner_department);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        buttonProfessorSignUp.setOnClickListener(this);
        findViewById(R.id.text_view_professor_login).setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departments, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
        spinnerDepartment.setOnItemSelectedListener(this);

        radioGroupProfessorGender.setOnCheckedChangeListener( this);


    } /// end of onCreate


    private void professorSignUp() {

        if (checkValid()) {
            /*  Do User Registration Here. */
            progressBarProfessorSignUp.setVisibility(View.VISIBLE);

            final String userName = professorEmployeeID + "@p.com";
            mAuth.createUserWithEmailAndPassword( userName, professorPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        ProfessorDataClass professorDataClass = new ProfessorDataClass(professorName, professorDepartment, professorEmployeeID,
                                professorEmail, professorContactNo, professorGender);

                        myRef = database.getReference("professor").child(professorDepartment);
                        //change in somnath key child
                        String uid = mAuth.getCurrentUser().getUid();
                        professorDataClass.setUid(uid);

                        myRef.child(mAuth.getCurrentUser().getUid())
                                .setValue(professorDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBarProfessorSignUp.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfessorSignUp.this, "User  Registration Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(ProfessorSignUp.this, LoginActivity.class);
                                    intent.putExtra("role","professor");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        });

                        Toast.makeText(ProfessorSignUp.this, "Inserted"+userName, Toast.LENGTH_SHORT).show();

                        String user="";
                        for(int i=0;i<userName.length();i++)
                        {
                            if(userName.charAt(i)=='@')
                                break;
                            user+=userName.charAt(i);
                        }
                        DatabaseReference professorNameRef=FirebaseDatabase.getInstance().getReference("professorname").child(user);
                        professorNameRef.setValue(professorName);


                    } else {
                        progressBarProfessorSignUp.setVisibility(View.GONE);
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                            Toast.makeText(ProfessorSignUp.this, "You are already registered.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText( ProfessorSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        }

    } // end of professorSignUp


    private boolean checkValid() {
        professorName = textInputLayoutProfessorName.getEditText().getText().toString().trim();
        professorDepartment = spinnerDepartment.getSelectedItem().toString().trim();
        professorEmployeeID = textInputLayoutProfessorEmployeeID.getEditText().getText().toString().trim();
        professorEmail = textInputLayoutProfessorEmail.getEditText().getText().toString().trim();
        professorContactNo = textInputLayoutProfessorContactNo.getEditText().getText().toString().trim();
        professorGender = radioButtonProfessorGender.getText().toString().trim();
        professorPassword = textInputLayoutProfessorPassword.getEditText().getText().toString().trim();

        //Check if Name is Empty
        if (professorName.isEmpty()) {
            textInputLayoutProfessorName.setError("Name is required");
            textInputLayoutProfessorName.requestFocus();
            return false;
        } else {
            textInputLayoutProfessorName.setError(null);
        }

        //Check if Branch is not "Select Branch"
        if (professorDepartment.equals("Select Department")) {
            Toast.makeText( this , "Please select your Department.", Toast.LENGTH_SHORT).show();
            spinnerDepartment.requestFocus();
            return false;
        }


        //Check if E-mail is Empty
        if (professorEmail.isEmpty()) {
            textInputLayoutProfessorEmail.setError("E-mail is required.");
            textInputLayoutProfessorEmail.requestFocus();
            return false;
        } else {
            textInputLayoutProfessorEmail.setError(null);
        }

        // Validate E-mail Pattern
        if (!Patterns.EMAIL_ADDRESS.matcher(professorEmail).matches()) {
            textInputLayoutProfessorEmail.setError("Please enter a valid E-mail Address.");
            textInputLayoutProfessorEmail.requestFocus();
            return false;
        } else {
            textInputLayoutProfessorEmail.setError(null);
        }

        //Check Contact No.  is Empty
        if (professorContactNo.isEmpty()) {
            textInputLayoutProfessorContactNo.setError("Contact  No. is required.");
            textInputLayoutProfessorContactNo.requestFocus();
            return false;
        } else {
            textInputLayoutProfessorContactNo.setError(null);
        }

        //Check if  Password is Empty
        if (professorPassword.isEmpty()) {
            textInputLayoutProfessorPassword.setError("Password is required.");
            textInputLayoutProfessorPassword.requestFocus();
            return false;
        } else if (professorPassword.length() < 6) {
            textInputLayoutProfessorPassword.setError("Password length should be greater than 6.");
            textInputLayoutProfessorPassword.requestFocus();
            return false;
        } else {
            textInputLayoutProfessorPassword.setError(null);
        }

        return true;
    }// end of CheckValid()

//    private void updateDatabase(){
//        ProfessorDataClass professorDataClass = new ProfessorDataClass(professorName, professorDepartment, professorEmployeeID,
//                            professorEmail, professorContactNo, professorGender);
//
//        myRef = database.getReference("professor").child(professorDepartment);
//        //change in somnath key child
//        String uid = mAuth.getCurrentUser().getUid();
//        professorDataClass.setUid(uid);
//
//        myRef.child(uid).setValue(professorDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                progressBarProfessorSignUp.setVisibility(View.GONE);
//                if (task.isSuccessful()) {
//                    Toast.makeText( ProfessorSignUp.this, "User  Registration Successful", Toast.LENGTH_SHORT).show();
//                    finish();
//                    Intent intent = new Intent(ProfessorSignUp.this, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                }
//            }
//        });
//
//
//    } else {
//        progressBarProfessorFragment.setVisibility(View.GONE);
//        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//
//            Toast.makeText(getContext(), "You are already registered.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_professor_signup:
                professorSignUp();
                break;
            case R.id.text_view_professor_login:
                finish();
                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        radioButtonProfessorGender = findViewById(checkedId);
    }
}
