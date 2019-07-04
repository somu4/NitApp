package com.example.nitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HostelFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    private Spinner spinnerComplaintType;
    private String complaintType,complaintDescription;
    private EditText editTextDescription;
    private Button buttonSubmitComplaint;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        View view =  inflater.inflate(R.layout.fragment_hostel, container,false);
        spinnerComplaintType = view.findViewById(R.id.spinner_complaint_type);
        editTextDescription= view.findViewById(R.id.edittext_complaint_description);
        buttonSubmitComplaint = view.findViewById(R.id.buttonSubmitComplaint);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.complaint_types,
                    android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComplaintType.setAdapter(adapter);
        spinnerComplaintType.setOnItemSelectedListener( this);

        buttonSubmitComplaint.setOnClickListener(this);
        return view;
    }

    private void submitComplaint(){
        complaintType = spinnerComplaintType.getSelectedItem().toString().trim();
        complaintDescription = editTextDescription.getText().toString().trim();
        if(checkAllPresent()){
            //Toast.makeText(getActivity(), "Submitting Complaint", Toast.LENGTH_SHORT).show();
            ComplaintDataClass complaintDataClass = new ComplaintDataClass(complaintType,complaintDescription,
                                mAuth.getCurrentUser().getUid());
            myRef = database.getReference("complaints").child(complaintType);
            myRef.setValue(complaintDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //progressBarProfessorSignUp.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Complaint Submission Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

    private  boolean checkAllPresent(){
        // check if Description is present or not
        if(complaintDescription.isEmpty()){
            Toast.makeText(getActivity(),"Enter Description",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSubmitComplaint:
                submitComplaint();
                break;
        }
    }
}
