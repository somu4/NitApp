package com.example.nitapp;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {

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
            String sId = Character.toString(x) + "" + Character.toString(y);
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
                    @Override
                    public void onClick(View v) {
                        Dialog student_dialog = new Dialog(getContext());
                        student_dialog.setContentView(R.layout.schedule_student_dialog);

                        TextView subject_code_dialog, subject_dialog, room_dialog;
                        Button teacher_dialog;
                        teacher_dialog = student_dialog.getWindow().findViewById(R.id.dialog_teacher);
                        subject_code_dialog = student_dialog.getWindow().findViewById(R.id.dialog_subject_code);
                        subject_dialog = student_dialog.getWindow().findViewById(R.id.dialog_subject);
                        room_dialog = student_dialog.getWindow().findViewById(R.id.dialog_room);
                        subject_code_dialog.setText(node[j].subject_code);
                        subject_dialog.setText(node[j].subject_name);
                        teacher_dialog.setText(node[j].teacher_name);
                        room_dialog.setText(node[j].room_no);

                        student_dialog.setCanceledOnTouchOutside(true);
                        student_dialog.show();
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
