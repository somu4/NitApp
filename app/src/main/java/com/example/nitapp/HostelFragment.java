package com.example.nitapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HostelFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    private Spinner spinnerComplaintType;
    private String complaintType,complaintDescription;
    private EditText editTextDescription;
    private Button buttonSubmitComplaint;
    private TextView textViewHostel;
    private TextView textViewRoomNo;
    private Button buttonshowMyComplaints;
    private ListView listViewMyComplaints;
    private List<ComplaintDataClass> ComplaintsList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRefMyComplaints= database.getReference("mycomplaints").child(mAuth.getUid());;
    private UserLocalStore userLocalStore;
    private StudentDataClass studentDataClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){


        userLocalStore= new UserLocalStore(getContext());
        studentDataClass = userLocalStore.getLoggedInStudent();
        ComplaintsList = new ArrayList<>();

        View view =  inflater.inflate(R.layout.fragment_hostel, container,false);
        spinnerComplaintType = view.findViewById(R.id.spinner_complaint_type);
        editTextDescription= view.findViewById(R.id.edittext_complaint_description);
        buttonSubmitComplaint = view.findViewById(R.id.buttonSubmitComplaint);
        textViewHostel = view.findViewById(R.id.textview_hostel);
        textViewRoomNo = view.findViewById(R.id.textview_room_number);
        buttonshowMyComplaints = view.findViewById(R.id.button_my_complaints);


        textViewHostel.setText("My Hostel : "+studentDataClass.getHostel());
        textViewRoomNo.setText("My Room No. : "+studentDataClass.getRoomnumber());



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.complaint_types,
                    android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComplaintType.setAdapter(adapter);
        spinnerComplaintType.setOnItemSelectedListener( this);

        buttonSubmitComplaint.setOnClickListener(this);
        buttonshowMyComplaints.setOnClickListener(this);




        return view;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myRefMyComplaints.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ComplaintsList.clear();

                for(DataSnapshot mycomplaintsSnapshot :dataSnapshot.getChildren()){
                    MyComplaint myComplaint = mycomplaintsSnapshot.getValue(MyComplaint.class);
                    DatabaseReference databaseReference = database.getReference("complaints").child(myComplaint.getHostel()).
                            child(myComplaint.getComplaintType()).child(myComplaint.getComplaintId());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            ComplaintDataClass complaintDataClass = dataSnapshot2.getValue(ComplaintDataClass.class);
                            ComplaintsList.add(complaintDataClass);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    //ComplaintDataClass complaintDataClass = databaseReference.getClass();
                }
                //progressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void submitComplaint(){
        complaintType = spinnerComplaintType.getSelectedItem().toString().trim();
        complaintDescription = editTextDescription.getText().toString().trim();
        if(checkAllPresent()){
            //Toast.makeText(getActivity(), "Submitting Complaint", Toast.LENGTH_SHORT).show();

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());

            ComplaintDataClass complaintDataClass = new ComplaintDataClass(complaintType,complaintDescription,
                                mAuth.getCurrentUser().getUid(),"pending",currentDateandTime);

            myRef = database.getReference("complaints").child(studentDataClass.getHostel())
                    .child(complaintType);
            String key = myRef.push().getKey();
            complaintDataClass.setComplaintId(key);
            myRef.child(key).setValue(complaintDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //progressBarProfessorSignUp.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Complaint Submission Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myRef = database.getReference("mycomplaints").child(studentDataClass.getUid());
            MyComplaint myComplaint = new MyComplaint(key,studentDataClass.getHostel(),complaintType);
            myRef.push().setValue(myComplaint);
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

    private void showMyComplaints(){


        Dialog myComplaintDialog = new Dialog(getContext());
        myComplaintDialog.setContentView(R.layout.hostel_mycomplaints_dialog);
        listViewMyComplaints = myComplaintDialog.getWindow().findViewById(R.id.listview_mycomplaints);

        MyComplaintsListAdapter myComplaintsListAdapter =new MyComplaintsListAdapter(getActivity(), ComplaintsList);
        listViewMyComplaints.setAdapter(myComplaintsListAdapter);

        myComplaintDialog.setCanceledOnTouchOutside(true);
        myComplaintDialog.show();
    }// end of showMyComplaints


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
            case R.id.button_my_complaints:
                //Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                showMyComplaints();
                break;
        }
    }
}


class MyComplaintsListAdapter extends ArrayAdapter<ComplaintDataClass>{
    private Activity context;
    private List<ComplaintDataClass> myComplaintsList;

    public MyComplaintsListAdapter(Activity context, List<ComplaintDataClass> myComplaintsList){
        super(context,R.layout.list_layout_my_complaints,myComplaintsList);
        this.context=context;
        this.myComplaintsList=myComplaintsList;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_my_complaints,null,true);
        TextView textViewComplaintId= listViewItem.findViewById(R.id.textview_complaint_id);
        TextView textViewComplaintType= listViewItem.findViewById(R.id.textview_complaint_type);
        TextView textViewComplaintDateandTime = listViewItem.findViewById(R.id.textview_complaint_datetime);
        TextView textViewComplaintDescription= listViewItem.findViewById(R.id.textview_complaint_description);
        ComplaintDataClass complaintDataClass = myComplaintsList.get(position);

        textViewComplaintId.setText(complaintDataClass.getComplaintId());
        textViewComplaintType.setText(complaintDataClass.getComplaintType());
        textViewComplaintDateandTime.setText(complaintDataClass.getDateandtime());
        textViewComplaintDescription.setText(complaintDataClass.getComplaintDescription());



        return listViewItem;
    }
}


class MyComplaint{
    private String complaintId;
    private String hostel;
    private String complaintType;

    public MyComplaint(){
    }

    public MyComplaint(String complaintId, String hostel, String complaintType) {
        this.complaintId = complaintId;
        this.hostel = hostel;
        this.complaintType = complaintType;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getHostel() {
        return hostel;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }
}