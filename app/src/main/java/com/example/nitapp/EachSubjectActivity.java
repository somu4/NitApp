package com.example.nitapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EachSubjectActivity extends AppCompatActivity {

    EditText subjectname, subjectcode, subjectteacher,subjectteacherid;


    String branch, year, subid, uniqueid;

    DatabaseReference subjectListRef, setterRef;
    DatabaseReference subjectcodeRef, subjectnameRef, teachernameRef,teacheridRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_subject);
        subjectcode = findViewById(R.id.eachsubjectcode);
        subjectname = findViewById(R.id.eachsubjectsubjectname);
        subjectteacher = findViewById(R.id.eachsubjectteachername);
        subjectteacherid=findViewById(R.id.eachsubjectteacherid);

        subjectteacher.setClickable(false);

        branch = getIntent().getStringExtra("branch");
        year = getIntent().getStringExtra("year");
        subid = getIntent().getStringExtra("subid");
        uniqueid = getIntent().getStringExtra("uniqueid");

        subjectListRef = FirebaseDatabase.getInstance().getReference("schedule").child(branch).child(year).child("table").child("subjects");
        setterRef = FirebaseDatabase.getInstance().getReference("schedule").child(branch).child(year).child("setter");

        subjectcodeRef = subjectListRef.child(subid).child("subcode");
        subjectcodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                f(s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        subjectnameRef = subjectListRef.child(subid).child("subjectname");
        subjectnameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                g(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        teachernameRef = subjectListRef.child(subid).child("teachername");
        teachernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                h(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        teacheridRef=subjectListRef.child(subid).child("teacherid");


        teacheridRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s=dataSnapshot.getValue(String.class);
                i(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void i(String s) {
        String t="";
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='@')
                break;
            t+=s.charAt(i);
        }
        subjectteacherid.setText(t);
    }

    private void f(String s) {
        subjectcode.setText(s);
    }

    private void g(String s) {
        subjectname.setText(s);
    }

    private void h(String s) {
        subjectteacher.setText(s);
    }

    public void changesubjectcode(View view) {

        Toast.makeText(this, "**", Toast.LENGTH_SHORT).show();

        final String temp=subjectcode.getText().toString().trim();


        final String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();

        setterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);

                if (s.equals(uid)) {

                    subjectcodeRef.setValue(temp);

                } else {
                    Toast.makeText(EachSubjectActivity.this, s+"*You are not a setter ! "+uid, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changesubjectname(View view) {

        final String temp=subjectname.getText().toString().trim();


        final String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();
        setterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);

                if (s.equals(uid)) {

                    subjectnameRef.setValue(temp);

                } else {
                    Toast.makeText(EachSubjectActivity.this, "You are not a setter ! ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changesubjectteachername(View view) {

        final String temp=subjectteacher.getText().toString().trim();

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();
        setterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);

                if (s.equals(uid)) {

                    teachernameRef.setValue(temp);

                } else {
                    Toast.makeText(EachSubjectActivity.this, "You are not a setter ! "+s, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changesubjectteacherid(View view) {

        final String temp=subjectteacherid.getText().toString().trim()+"@p.com";

        final String temp1=subjectteacherid.getText().toString().trim();

        final ProgressBar progressBar=findViewById(R.id.eachprogbar);
        progressBar.setVisibility(View.VISIBLE);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();
        setterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);

                if (s.equals(uid)) {

                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("professorname").child(temp1);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String x=dataSnapshot.getValue(String.class);
                            if(x!=null)
                            {
                                teacheridRef.setValue(temp);
                                teachernameRef.setValue(x);
                                subjectteacher.setText(x);
                            }
                            else
                            {
                                Toast.makeText(EachSubjectActivity.this, "NO SUCH TEACHER", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(EachSubjectActivity.this, "You are not a setter ! "+s, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
