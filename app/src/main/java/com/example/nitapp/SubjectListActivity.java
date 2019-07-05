package com.example.nitapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubjectListActivity extends AppCompatActivity {

    String branch,year,uniqueid;
    TextView subjectlistinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        branch=getIntent().getStringExtra("branch");
        year=getIntent().getStringExtra("year");
        uniqueid=getIntent().getStringExtra("uniqueid");
        Toast.makeText(this, branch+" "+year+" "+uniqueid, Toast.LENGTH_SHORT).show();
        subjectlistinfo=findViewById(R.id.subjectlistinfo);
        subjectlistinfo.setText(year+"  "+branch);
    }

    public void f(View view) {

        String id=view.getResources().getResourceName(view.getId());
        String id1="";

        for(int i=0;i<id.length();i++)
        {
            if(id.charAt(i)=='/')
            {
                for(int j=i+1;j<id.length();j++)
                {
                    id1+=id.charAt(j);
                }
                break;
            }
        }

        Intent intent=new Intent(SubjectListActivity.this,EachSubjectActivity.class);

        intent.putExtra("branch",branch);
        intent.putExtra("year",year);
        intent.putExtra("subid",id1);
        intent.putExtra("uniqueid",uniqueid);

        startActivity(intent);



        Toast.makeText(this, id1, Toast.LENGTH_SHORT).show();
    }

    public void changesetter(View view) {

        final String useremail= FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();
        DatabaseReference hodRef=FirebaseDatabase.getInstance().getReference("schedule").child(branch).child("hod");
        hodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s=dataSnapshot.getValue(String.class);
                if(s.equals(useremail))
                {
                    Toast.makeText(SubjectListActivity.this, "You are hod! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SubjectListActivity.this, "You are not hod ! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
