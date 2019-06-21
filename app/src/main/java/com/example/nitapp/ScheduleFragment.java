package com.example.nitapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {

    public String MYPREFERENCE = "mypref";
    public String SubcodeKey = "subcode";
    public String SubnameKey = "subname";
    public String TeachernameKey = "teachername";
    public String RoomnoKey = "roomno";
    boolean access = false;
    class Node {
        String teacher_name, subject_name, subject_code, room_no;

        public Node() {
            this.subject_code = "subject_code";
            this.subject_name = "subject_name";
            this.teacher_name = "Teacher Name";
            this.room_no = "room_no";
        }
    }

    Node[] node;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        generateObjectArrayNode(view);

        String[] string_year = {"2016", "2017", "2018", "2019"};
        String[] string_ugpg = {"UG"};
        String[] string_branch = {"CE", "CSE", "ECE", "EEE", "ME","MME","PIE"};
        Spinner spin_year = view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, string_year);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin_year.setAdapter(adapter1);
        spin_year.setSelection(adapter1.getPosition("2018"));

        Spinner spin_ugpg = view.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, string_ugpg);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin_ugpg.setAdapter(adapter2);
        spin_ugpg.setSelection(adapter2.getPosition("UG"));

        Spinner spin_branch = view.findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, string_branch);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin_branch.setAdapter(adapter3);
        spin_branch.setSelection(adapter3.getPosition("CSE"));

        return view;
    }

    public void generateObjectArrayNode(View view) {
        node = new Node[41];
        for (int i = 0; i < 41; i++)
            node[i] = new Node();
        setButtons(view);
    }

    private void setButtons(View view) {
        char x = 'b', y = '1';
        for (int i = 1; i <= 40; i++) {
            final String sId = Character.toString(x) + "" + Character.toString(y);
            int id = getResources().getIdentifier(sId, "id", getActivity().getPackageName());
            if (id == 0) {
                Toast.makeText(getContext(), sId + "NULL", Toast.LENGTH_SHORT).show();
            } else {
                final int j = i;
                node[i].teacher_name = "B.K.Singh";
                node[i].subject_name = "OPERATING SYSTEMS";
                node[i].room_no = "204";

                Button button = view.findViewById(id);
                button.setText("CS501");
                button.setBackgroundColor(Color.GREEN);
                ViewGroup.LayoutParams lp = button.getLayoutParams();
                ((LinearLayout.LayoutParams) lp).setMargins(2, 0, 3, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    int flag = 0;
                    EditText subject_code_et, subject_name_et, teacher_name_et, room_no_et;
                    Dialog teacher_dialog = new Dialog(getContext());
                    TextView subject_code_dialog, subject_dialog, room_dialog;
                    Dialog student_dialog = new Dialog(getContext());
                    @Override
                    public void onClick(View v) {
                        if (access == true) {
                            teacher_dialog.setContentView(R.layout.schedule_teacher_dialog);

                            subject_code_et = teacher_dialog.getWindow().findViewById(R.id.edit_dialog_sub_code);
                            subject_name_et = teacher_dialog.getWindow().findViewById(R.id.edit_dialog_sub_name);
                            teacher_name_et = teacher_dialog.getWindow().findViewById(R.id.edit_dialog_teacher_name);
                            room_no_et = teacher_dialog.getWindow().findViewById(R.id.edit_dialog_room_no);

                            if (flag == 1) {
                                SharedPreferences sp = getContext().getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
                                String subject_code_sp = sp.getString(SubcodeKey + sId, "");
                                String subject_name_sp = sp.getString(SubnameKey + sId, "");
                                String teacher_name_sp = sp.getString(TeachernameKey + sId, "");
                                String room_no_sp = sp.getString(RoomnoKey + sId, "");
                                subject_code_et.setText(subject_code_sp);
                                subject_name_et.setText(subject_name_sp);
                                teacher_name_et.setText(teacher_name_sp);
                                room_no_et.setText(room_no_sp);
                            }
                            Button save_schedule_btn = teacher_dialog.getWindow().findViewById(R.id.button_save_schedule);
                            save_schedule_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPref = getContext().getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(SubcodeKey + sId, subject_code_et.getText().toString());
                                    editor.putString(SubnameKey + sId, subject_name_et.getText().toString());
                                    editor.putString(TeachernameKey + sId, teacher_name_et.getText().toString());
                                    editor.putString(RoomnoKey + sId, room_no_et.getText().toString());
                                    editor.apply();
                                    flag = 1;
                                    Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
                                    teacher_dialog.dismiss();
                                }
                            });

                            teacher_dialog.setCanceledOnTouchOutside(true);
                            teacher_dialog.show();
                        } else {

                            student_dialog.setContentView(R.layout.schedule_student_dialog);

                            Button teacher_info;
                            teacher_info = student_dialog.getWindow().findViewById(R.id.dialog_teacher);
                            subject_code_dialog = student_dialog.getWindow().findViewById(R.id.dialog_subject_code);
                            subject_dialog = student_dialog.getWindow().findViewById(R.id.dialog_subject);
                            room_dialog = student_dialog.getWindow().findViewById(R.id.dialog_room);
                            subject_code_dialog.setText(node[j].subject_code);
                            subject_dialog.setText(node[j].subject_name);
                            teacher_info.setText(node[j].teacher_name);
                            room_dialog.setText(node[j].room_no);

                            SharedPreferences sp1 = getContext().getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp1.edit();
                            editor.putString(SubcodeKey + sId, node[j].subject_code);
                            editor.putString(SubnameKey + sId, node[j].subject_name);
                            editor.putString(TeachernameKey + sId, node[j].teacher_name);
                            editor.putString(RoomnoKey + sId, node[j].room_no);
                            editor.apply();

                            student_dialog.setCanceledOnTouchOutside(true);
                            student_dialog.show();
                        }
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
