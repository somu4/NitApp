package com.example.nitapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class StudentSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private TextInputLayout textInputLayoutStudentName, textInputLayoutStudentEmail, textInputLayoutStudentContactNo, textInputLayoutStudentPassword;
    private Spinner spinnerBranch, spinnerYear, spinnerRollNo, spinnerHostel;
    private RadioGroup radioGroupGender;
    private Button buttonStudentSignUp;
    EditText editTextRoomNo;
    RadioButton radioButtonStudentGender;
    ProgressBar progressBarStudentActivity;

    private String username, email, contactNumber,
             gender, roomNumber, year,password , rollNumber;
    String hostel;
    int branch;

    String branchCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        textInputLayoutStudentName = findViewById(R.id.text_input_layout_student_name);
        textInputLayoutStudentEmail = findViewById(R.id.text_input_layout_student_email);
        textInputLayoutStudentContactNo = findViewById(R.id.text_input_layout_student_contactno);
        textInputLayoutStudentPassword = findViewById(R.id.text_input_layout_student_password);
        spinnerBranch = findViewById(R.id.spinner_branch);
        spinnerYear = findViewById(R.id.spinner_year);
        spinnerRollNo = findViewById(R.id.spinner_rollno);
        spinnerHostel = findViewById(R.id.spinner_hostel);
        editTextRoomNo = findViewById(R.id.edit_text_roomno);
        radioGroupGender = findViewById(R.id.gender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButtonStudentGender = findViewById(selectedId);
        buttonStudentSignUp = findViewById(R.id.buttonStudentSignUp);
        progressBarStudentActivity = findViewById(R.id.progressbar_student_activity);







        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branches, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        spinnerBranch.setOnItemSelectedListener(this);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= thisYear - 4; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter2);
        spinnerYear.setOnItemSelectedListener(this);

        ArrayList<String> rollnos = new ArrayList<String>();
        for (int i = 1; i <= 150; i++) {
            if (i < 10)
                rollnos.add("00" + i);
            else if (i < 100)
                rollnos.add("0" + i);
            else
                rollnos.add(i + "");
        }

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rollnos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRollNo.setAdapter(adapter3);
        spinnerRollNo.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.hostels, android.R.layout.simple_list_item_1);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHostel.setAdapter(adapter4);
        spinnerHostel.setOnItemSelectedListener(this);

        radioGroupGender.setOnCheckedChangeListener( this);
        buttonStudentSignUp.setOnClickListener( this);



    }

    public void userSignUp() {

        if (checkValid()) {

            progressBarStudentActivity.setVisibility(View.VISIBLE);

            String userId = year + "UG" + branchCode + rollNumber;
            String password = textInputLayoutStudentPassword.getEditText().getText().toString().trim();


            mAuth.createUserWithEmailAndPassword(userId + "@p.com", password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                StudentDataClass studentDataClass = new StudentDataClass(username, email, contactNumber,
                                        rollNumber,branch,  year, hostel, roomNumber, gender);

                                myRef = database.getReference("student").child(branchCode).child(year + "");


                                String uid = mAuth.getCurrentUser().getUid();
                                studentDataClass.setUid( uid);

                                myRef.child(uid).setValue(studentDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBarStudentActivity.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Toast.makeText(StudentSignUp.this, "User  Registration Successful", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(StudentSignUp.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("role","student");
                                            startActivity(intent);
                                        }
                                    }
                                });


                            } else {
                                progressBarStudentActivity.setVisibility(View.GONE);
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                    Toast.makeText(StudentSignUp.this, "You are already registered.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText( StudentSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });


        }

    }

    // end of professorSignUp

    public boolean checkValid() {
        username = textInputLayoutStudentName.getEditText().getText().toString().trim();
        email = textInputLayoutStudentEmail.getEditText().getText().toString().trim();
        contactNumber = textInputLayoutStudentContactNo.getEditText().getText().toString().trim();
        roomNumber = editTextRoomNo.getText().toString().trim();
        password = textInputLayoutStudentPassword.getEditText().getText().toString().trim();
        year = spinnerYear.getSelectedItem().toString().trim();
        rollNumber = spinnerRollNo.getSelectedItem().toString().trim();
        hostel = spinnerHostel.getSelectedItem().toString().trim();
        roomNumber = editTextRoomNo.getText().toString();
        gender = radioButtonStudentGender.getText().toString().trim();


        //Check if Name is Empty
        if (username.isEmpty()) {
            textInputLayoutStudentName.setError("Name is required");
            return false;
        } else {
            textInputLayoutStudentName.setError(null);
        }

        //Check if E-mail is Empty
        if (email.isEmpty()) {
            textInputLayoutStudentEmail.setError("E-mail is required.");
            return false;
        } else {
            textInputLayoutStudentEmail.setError(null);
        }

        // Validate E-mail Pattern
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutStudentEmail.setError("Please enter a valid E-mail Address.");
            return false;
        } else {
            textInputLayoutStudentEmail.setError(null);
        }

        //Check if contact is Empty
        if (contactNumber.isEmpty()) {
            textInputLayoutStudentContactNo.setError("Contact Number is required.");
            return false;

        } else {
            textInputLayoutStudentContactNo.setError(null);
        }

        //Check if Room number is Empty
        if (roomNumber.isEmpty()) {
            Toast.makeText(StudentSignUp.this, "Enter your Room Number", Toast.LENGTH_SHORT).show();
            editTextRoomNo.requestFocus();
            return false;
        }

        //Check if  Password is Empty
        if (password.isEmpty()) {
            textInputLayoutStudentPassword.setError("Password is required.");
            return false;
        } else if (password.length() < 6) {
            textInputLayoutStudentPassword.setError("Password length should be greater than 6.");
            return false;
        } else {
            textInputLayoutStudentPassword.setError(null);
        }

        if (branch == 0) {
            Toast.makeText(this, "Please select Branch", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.spinner_branch) {
            branch = i;

            if (i == 1)
                branchCode = "CE";
            else if (i == 2)
                branchCode = "CS";
            else if (i == 3)
                branchCode = "EC";
            else if (i == 4)
                branchCode = "EE";
            else if (i == 5)
                branchCode = "ME";
            else if (i == 6)
                branchCode = "MM";
            else if (i == 7)
                branchCode = "PI";


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void backtologin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        radioButtonStudentGender = findViewById(checkedId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStudentSignUp:
                userSignUp();
                break;
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}