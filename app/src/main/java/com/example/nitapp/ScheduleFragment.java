package com.example.nitapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static android.widget.Toast.LENGTH_LONG;

public class ScheduleFragment extends Fragment {

    public String MYPREFERENCE = "mypref";
    public String SubcodeKey = "subcode";
    public String SubnameKey = "subname";
    public String TeachernameKey = "teachername";
    public String RoomnoKey = "roomno";
    boolean access = false;

    DatabaseReference yearRef,branchRef,scheduleRef,tableRef,subnumberRef;

    String yearString,branchString,uniqueid;


    Context context;
    MainActivity mainActivity;


    class Node {
        String teacher_name, subject_name, subject_code, room_no;

        public Node() {
            this.subject_code = "subject_code";
            this.subject_name = "subject_name";
            this.teacher_name = "Teacher Name";
            this.room_no = "room_no";
        }
    }

    Spinner spin_year;
    Spinner spin_branch;
    Spinner spin_subjectlist;

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);


        progressBar=view.findViewById(R.id.progressbar_dialog);

        context = getContext();
        mainActivity = (MainActivity) getActivity();

        yearString=mainActivity.myYear.toUpperCase();
        branchString=mainActivity.myBranch.toUpperCase();

        Toast.makeText(getContext(), mainActivity.myBranch + mainActivity.myYear + "!", Toast.LENGTH_SHORT).show();
        spin_year = view.findViewById(R.id.spinnerScheduleYear);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= thisYear - 4; i--) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, years);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_year.setAdapter(adapter1);
        spin_year.setScrollBarSize(100);

        spin_branch = view.findViewById(R.id.spinnerScheduleBranch);
        ArrayList<String> branchList = new ArrayList<String>();
        String[] branchesArray={"CS","MM","ME","CE","EE","EC","PI"};
        for(int i=0;i<7;i++)
        {
            branchList.add(branchesArray[i]);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, branchesArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_branch.setAdapter(adapter2);

        spin_subjectlist=view.findViewById(R.id.spinnerScheduleSubjectList);
        ArrayList<String> subjectList=new ArrayList<>();
        subjectList.add("----");
        subjectList.add("SubjectList");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, subjectList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_subjectlist.setAdapter(adapter3);



        return view;
    }



    public void fetchingButtonInfo() {

        char x = 'b', y = '1';
        for (int i = 1; i <= 40; i++) {

            final String sId = Character.toString(x) + "" + Character.toString(y);
            final int j = i;



            progressBar.setVisibility(View.VISIBLE);
            stopProgressBarVariable=78;

            DatabaseReference myRef2, myRef3;

            myRef2 = tableRef.child(sId).child("subcode");
            myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String s = dataSnapshot.getValue(String.class);
                    String subjpath="sub"+s+"";

                    DatabaseReference x=tableRef.child("subjects").child(subjpath).child("subcode");

                    x.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp=dataSnapshot.getValue(String.class);
                            setButtonText(sId, temp, getView());
                            stopProgressBar();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Failed to load ! ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getContext(), "Error in fetching Schedule Fragment Setting ButtonText", Toast.LENGTH_SHORT).show();
                }
            });

            myRef3 = tableRef.child(sId).child("lecture");
            myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean value = dataSnapshot.getValue(Boolean.class);
                    setButtonColor(sId, value, getView());
                    stopProgressBar();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getContext(), "Error in fetching Schedule Fragment Setting ButtonColor", Toast.LENGTH_SHORT).show();
                }
            });


            y++;
            if (y > '5') {
                y = '1';
                x++;
            }
        }
    }

    long stopProgressBarVariable;
    synchronized private void stopProgressBar()
    {
        if(stopProgressBarVariable==0)
        {
            progressBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            stopProgressBarVariable--;
        }

    }

    private void setButtonText(String sId1, String data, View view) {
        if(getActivity()==null)
            return;
        int id1 = getResources().getIdentifier(sId1, "id", getActivity().getPackageName());
        Button button1 = view.findViewById(id1);
        button1.setText(data);
    }


    private void setButtonColor(String sId1, boolean b, View view) {
        if(getActivity()==null)
            return;
        int id1 = getResources().getIdentifier(sId1, "id", getActivity().getPackageName());
        Button button1 = view.findViewById(id1);
        if (b == true)
            button1.setBackgroundColor(Color.GREEN);
        else
            button1.setBackgroundColor(Color.RED);
    }

    private void setDialogBox(View view) {

        char x = 'b', y = '1';
        for (int i = 1; i <= 40; i++) {
            final String sId = Character.toString(x) + "" + Character.toString(y);

            if(getActivity()==null)
                return;

            int id = getResources().getIdentifier(sId, "id", getActivity().getPackageName());
            if (id == 0) {
                Toast.makeText(getContext(), sId + "NULL", Toast.LENGTH_SHORT).show();
            } else {

                Button button = view.findViewById(id);

                ViewGroup.LayoutParams lp = button.getLayoutParams();
                ((LinearLayout.LayoutParams) lp).setMargins(2, 0, 3, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogBox(v);
                    }
                });
            }

            y++;
            if (y > '5') {
                y = '1';
                x++;
            }
        }
    }



    private void changeSubjectInDialog(final Dialog dialog, final View view) {
            final String email=FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();

        final ProgressBar progressBar1=dialog.getWindow().findViewById(R.id.dialog_schedule_progressbar);

        progressBar1.setVisibility(View.VISIBLE);

            DatabaseReference setterRef=yearRef.child("setter");
            setterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s=dataSnapshot.getValue(String.class);
                    if(s.equals(email))
                    {
                        Toast.makeText(getContext(), "You are a setter ! ", Toast.LENGTH_SHORT).show();

                        Dialog dialog1=new Dialog(getContext());
                        dialog1.setContentView(R.layout.change_subject_dialog);
                        dialog1.setCanceledOnTouchOutside(true);

                        for(int i=1;i<=10;i++)
                        {
                            String sub="subject"+i+"";

                            if(getActivity()==null)
                                return;
                            int id=getResources().getIdentifier(sub,"id",getContext().getPackageName());

                            dialog1.findViewById(id).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String subid=view.getResources().getResourceName(view.getId());

                                    String id1="";

                                    for(int i=0;i<subid.length();i++)
                                    {
                                        if(subid.charAt(i)=='/')
                                        {
                                            for(int j=i+8;j<subid.length();j++)
                                            {
                                                id1+=subid.charAt(j);
                                            }
                                            break;
                                        }
                                    }



                                    Toast.makeText(getContext(), "HO Gya!"+id1, Toast.LENGTH_SHORT).show();


                                    subnumberRef.setValue(id1);

                                }
                            });

                        }

                        dialog1.show();

                    }
                    else
                    {
                        Toast.makeText(getContext(), "You are not a setter ! ", Toast.LENGTH_SHORT).show();

                    }

                    progressBar1.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }


    public void showDialogBox(View view)
    {
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.schedule_student_dialog);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                fetchingButtonInfo();
            }
        });

        final View view1=view;//to send to change Subject In dialog
        final TextView subjectCodeTextView =dialog.getWindow().findViewById(R.id.dialog_subject_code);
        final TextView subjectNameTextView=dialog.getWindow().findViewById(R.id.dialog_subject_name);
        final Button teachernameButton=dialog.getWindow().findViewById(R.id.dialog_teacher);
        final Button changeSubjectButton=dialog.getWindow().findViewById(R.id.schedule_dialog_change_subject_button);
        final Switch s=dialog.getWindow().findViewById(R.id.schedule_dialog_cancel_switch);


        changeSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSubjectInDialog(dialog,view1);
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch s=(Switch)view;
                if(s.isChecked())
                {
                    Toast.makeText(getContext(), "boi1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Boi2", Toast.LENGTH_SHORT).show();
                }

                String email=FirebaseAuth.getInstance().getCurrentUser().getEmail().trim();
                subnumberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s=dataSnapshot.getValue(String.class);
                        DatabaseReference teacherRef=tableRef.child("subjects").child("sub"+1).child("teacherid");
                        teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String temp=dataSnapshot.getValue(String.class);

                                if(temp==null)
                                {
                                    Toast.makeText(getContext(), "Null string in switch", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                }

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
        });
        s.setTextOff("OFF");
        s.setTextOn("ONN");

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

        //Toast.makeText(getContext(), id1, Toast.LENGTH_SHORT).show();

        subnumberRef=tableRef.child(id1).child("subcode");

        final ProgressBar progressBar1=dialog.getWindow().findViewById(R.id.dialog_schedule_progressbar);

        progressBar1.setVisibility(View.VISIBLE);

        Toast.makeText(getContext(), "Setting visibility", Toast.LENGTH_SHORT).show();

        subnumberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s=dataSnapshot.getValue(String.class);

                DatabaseReference subjectRef=tableRef.child("subjects").child("sub"+s);

                DatabaseReference subcodeRef=subjectRef.child("subcode");
                subcodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s=dataSnapshot.getValue(String.class);
                        subjectCodeTextView.setText(s);
                        progressBar1.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference subjectnameRef=subjectRef.child("subjectname");
                subjectnameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s=dataSnapshot.getValue(String.class);
                        subjectNameTextView.setText(s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference teacherRef =subjectRef.child("teachername");
                teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s=dataSnapshot.getValue(String.class);
                        teachernameButton.setText(s);
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

        dialog.show();
    }




    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDialogBox(getView());

        scheduleRef=FirebaseDatabase.getInstance().getReference("schedule");



        spin_year.setSelection(getIndex(spin_year, yearString));
        //fetchingButtonInfo();
        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearString=spin_year.getSelectedItem().toString().trim();
                tableRef=scheduleRef.child(branchString).child(yearString).child("table");
                yearRef=scheduleRef.child(branchString).child(yearString);
                Toast.makeText(getContext(), yearString, Toast.LENGTH_SHORT).show();
                fetchingButtonInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spin_branch.setSelection(getIndex(spin_branch, branchString));

        spin_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchString=spin_branch.getSelectedItem().toString().trim();
                tableRef=scheduleRef.child(branchString).child(yearString).child("table");
                branchRef=scheduleRef.child(branchString);
                fetchingButtonInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin_subjectlist.setSelection(getIndex(spin_subjectlist,"----"));

        spin_subjectlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s=spin_subjectlist.getSelectedItem().toString().trim();
                if(s.equals("SubjectList"))
                {
                    Toast.makeText(getContext(), "HOOOO!", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getActivity(),SubjectListActivity.class);
                    intent.putExtra("branch",branchString);
                    intent.putExtra("year",yearString);

                    uniqueid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    intent.putExtra("uniqueid",uniqueid );

                    startActivityForResult(intent,99);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        spin_subjectlist.setSelection(getIndex(spin_subjectlist,"----"));
        Toast.makeText(getContext(), "wOOOO!", Toast.LENGTH_SHORT).show();
    }
}
