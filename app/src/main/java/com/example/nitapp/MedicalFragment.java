package com.example.nitapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MedicalFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout calldoctorButton;
    private RelativeLayout callambulanceButton, nearbyHospitalsView;
    private UserLocalStore userLocalStore;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical, container, false);
        userLocalStore= new UserLocalStore(getContext());


        calldoctorButton = view.findViewById(R.id.call_doctor_view);
        callambulanceButton = view.findViewById(R.id.call_ambulance_view);
        nearbyHospitalsView = view.findViewById(R.id.nearby_hospitals_view);

        nearbyHospitalsView.setOnClickListener(this);

        callambulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_DIAL);
//
//                i.setData(Uri.parse("tel:1234567890"));
//                startActivity(i);

                FirebaseAuth.getInstance().signOut();
                userLocalStore.clearUserData();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();


            }
        });

        calldoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);

                i.setData(Uri.parse("tel:0987654321"));
                startActivity(i);


            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nearby_hospitals_view:
//                FirebaseAuth.getInstance().signOut();
//                userLocalStore.clearUserData();
                Intent intent = new Intent(getActivity(), myHostelComplaints.class);
        //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //getActivity().finish();
                break;
            case R.id.call_ambulance_view:
                break;
            case R.id.call_doctor_view:
                break;
        }
    }
}
