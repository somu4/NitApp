package com.example.nitapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MedicalFragment extends Fragment {
    RelativeLayout calldoctorButton;
    RelativeLayout callambulanceButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view =  inflater.inflate(R.layout.fragment_medical, container,false);

        calldoctorButton= view.findViewById(R.id.call_doctor_view);
        callambulanceButton= view.findViewById(R.id.call_ambulance_view);

        callambulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent( Intent.ACTION_DIAL );

                i.setData( Uri.parse("tel:1234567890"));
                    startActivity(i);


            }
        });

        calldoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent( Intent.ACTION_DIAL );

                i.setData( Uri.parse("tel:0987654321"));
                    startActivity(i);


            }
        });



        return view;
    }
}
