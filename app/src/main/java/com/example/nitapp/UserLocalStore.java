package com.example.nitapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;



//    String username,email,contactNumber, rollNumber;
//    private int branch;
//    private String year ,hostel,roomnumber,gender;
//    private String uid;



    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeStudentData(StudentDataClass studentDataClass){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name",studentDataClass.getUsername());
        spEditor.putString("email",studentDataClass.getEmail());
        spEditor.putString("contactNumber",studentDataClass.getContactNumber());
        spEditor.putString("rollNumber",studentDataClass.getRollNumber());
        spEditor.putInt("branch",studentDataClass.getBranch());
        spEditor.putString("year",studentDataClass.getYear());
        spEditor.putString("hostel",studentDataClass.getHostel());
        spEditor.putString("roomNumber",studentDataClass.getRoomnumber());
        spEditor.putString("gender",studentDataClass.getGender());
        spEditor.putString("uid",studentDataClass.getUid());
        spEditor.apply();
    }

    public StudentDataClass getLoggedInStudent() {
//        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
//            return null;
//        }

        String username = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String contactNumber = userLocalDatabase.getString("contactNumber", "");
        String rollnumber = userLocalDatabase.getString("rollNumber", "");
        int branch = userLocalDatabase.getInt("branch", -1);
        String  year= userLocalDatabase.getString("year", "");
        String hostel = userLocalDatabase.getString("hostel", "");
        String roomNumber = userLocalDatabase.getString("roomNumber", "");
        String gender = userLocalDatabase.getString("gender", "");
        String uid = userLocalDatabase.getString("uid", "");

        StudentDataClass studentDataClass = new StudentDataClass(username,email,contactNumber,rollnumber,branch,year,hostel,roomNumber,gender);
        studentDataClass.setUid(uid);

        return studentDataClass;
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();

    }

}
