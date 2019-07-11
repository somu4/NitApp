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

public class ChangeSetterActivity extends AppCompatActivity {

    String branch,year;
    EditText teachername,teacherid;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_setter);

        teachername=findViewById(R.id.changesettername);
        teacherid=findViewById(R.id.changesetterid);

        branch=getIntent().getStringExtra("branch");
        year=getIntent().getStringExtra("year");

        databaseReference= FirebaseDatabase.getInstance().getReference("schedule").child(branch).child(year).child("setter");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s=dataSnapshot.getValue(String.class);
                String temp="";
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)!='@')
                    {
                        temp+=s.charAt(i);
                    }
                    else
                        break;
                }
                teacherid.setText(temp);
                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("professorname").child(temp);
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String f=dataSnapshot.getValue(String.class);
                        if(f!=null)
                        teachername.setText(f);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void changesetterid(View view) {

        final String temp=teacherid.getText().toString().trim()+"@p.com";

        final String temp1=teacherid.getText().toString().trim();

        final ProgressBar progressBar=findViewById(R.id.changeprogbar);
        progressBar.setVisibility(View.VISIBLE);



                    final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("professorname").child(temp1);
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String x=dataSnapshot.getValue(String.class);
                            if(x!=null)
                            {
                                databaseReference.setValue(temp);
                                teachername.setText(x);
                            }
                            else
                            {
                                Toast.makeText(ChangeSetterActivity.this, "NO SUCH TEACHER", Toast.LENGTH_SHORT).show();
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
