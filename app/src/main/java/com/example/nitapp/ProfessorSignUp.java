package com.example.nitapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ProfessorSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputLayoutName, textInputLayoutEmployeeID,textInputLayoutEmail,textInputLayoutContactNo,textInputLayoutUserName,textInputLayoutPassword;
    private Spinner spinnerDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_sign_up);
        spinnerDepartment=findViewById(R.id.spinner_department);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departments, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
        spinnerDepartment.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
