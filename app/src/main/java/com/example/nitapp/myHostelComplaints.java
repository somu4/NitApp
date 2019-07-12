package com.example.nitapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myHostelComplaints extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerHostelComplaint;
    private ListView listViewProfessorShowCompalints;
    private  TextView textViewhostelWarden;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<ComplaintDataClass> ComplaintsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hostel_complaints);

        spinnerHostelComplaint  = findViewById(R.id.spinner_professor_show_complaint);
        listViewProfessorShowCompalints = findViewById(R.id.listview_professor_mycomplaints);
        ComplaintsList = new ArrayList<>();


        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.hostels, android.R.layout.simple_list_item_1);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHostelComplaint.setAdapter(adapter4);
        spinnerHostelComplaint.setOnItemSelectedListener( this);
    }




    @Override
     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        final String selectedHostel = (String) parent.getItemAtPosition(position);

        myRef = database.getReference("complaints");
        myRef = myRef.child(selectedHostel).child("setter");





//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String s = dataSnapshot.getValue(String.class);
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                if(s.equalsIgnoreCase(currentUser.getEmail())){
//                    Toast.makeText(getApplicationContext(),  "1",Toast.LENGTH_SHORT ).show();
//                }
//                else
//                    Toast.makeText(getApplicationContext(),  "00",Toast.LENGTH_SHORT ).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                FirebaseUser currentUser = mAuth.getCurrentUser();
                ComplaintsList.clear();
                if (s.equalsIgnoreCase(currentUser.getEmail())) {
                    DatabaseReference ref1 = database.getReference("complaints").child(selectedHostel).child("Electricity");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                            for (DataSnapshot mycomplaintsSnapshot : dataSnapshot2.getChildren()) {
                                ComplaintDataClass complaintDataClass = mycomplaintsSnapshot.getValue(ComplaintDataClass.class);
                                ComplaintsList.add(complaintDataClass);


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //


                     ref1 = database.getReference("complaints").child(selectedHostel).child("Lan");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                            for (DataSnapshot mycomplaintsSnapshot : dataSnapshot2.getChildren()) {
                                ComplaintDataClass complaintDataClass = mycomplaintsSnapshot.getValue(ComplaintDataClass.class);
                                ComplaintsList.add(complaintDataClass);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //


                    ref1 = database.getReference("complaints").child(selectedHostel).child("Plumbing");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                            for (DataSnapshot mycomplaintsSnapshot : dataSnapshot2.getChildren()) {
                                ComplaintDataClass complaintDataClass = mycomplaintsSnapshot.getValue(ComplaintDataClass.class);
                                ComplaintsList.add(complaintDataClass);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //

                    ref1 = database.getReference("complaints").child(selectedHostel).child("Carpentary");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                            for (DataSnapshot mycomplaintsSnapshot : dataSnapshot2.getChildren()) {
                                ComplaintDataClass complaintDataClass = mycomplaintsSnapshot.getValue(ComplaintDataClass.class);
                                ComplaintsList.add(complaintDataClass);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                     ref1 = database.getReference("complaints").child(selectedHostel).child("Washroom Cleaning");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                            for (DataSnapshot mycomplaintsSnapshot : dataSnapshot2.getChildren()) {
                                ComplaintDataClass complaintDataClass = mycomplaintsSnapshot.getValue(ComplaintDataClass.class);
                                ComplaintsList.add(complaintDataClass);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    ref1 = database.getReference("complaints").child(selectedHostel).child("Other");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                            for (DataSnapshot mycomplaintsSnapshot : dataSnapshot2.getChildren()) {
                                ComplaintDataClass complaintDataClass = mycomplaintsSnapshot.getValue(ComplaintDataClass.class);
                                ComplaintsList.add(complaintDataClass);

                            }

                            MyComplaintsListAdapter myComplaintsListAdapter =new MyComplaintsListAdapter(myHostelComplaints.this, ComplaintsList);
                            listViewProfessorShowCompalints.setAdapter(myComplaintsListAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





                }// end of if s==
                else {
                    Toast.makeText(getApplicationContext(), "You are not Authorized." , Toast.LENGTH_SHORT).show();
                    ComplaintsList.clear();
                    MyComplaintsListAdapter myComplaintsListAdapter =new MyComplaintsListAdapter(myHostelComplaints.this, ComplaintsList);
                    listViewProfessorShowCompalints.setAdapter(myComplaintsListAdapter);
                    return;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


