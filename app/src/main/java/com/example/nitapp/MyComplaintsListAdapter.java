package com.example.nitapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

class MyComplaintsListAdapter extends ArrayAdapter<ComplaintDataClass> {
    private Activity context;
    private List<ComplaintDataClass> myComplaintsList;

    public MyComplaintsListAdapter(Activity context, List<ComplaintDataClass> myComplaintsList){
        super(context,R.layout.list_layout_my_complaints,myComplaintsList);
        this.context=context;
        this.myComplaintsList=myComplaintsList;
    }




    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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