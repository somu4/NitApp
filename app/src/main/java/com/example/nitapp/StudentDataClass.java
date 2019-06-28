package com.example.nitapp;

public class StudentDataClass {

    private String username,email,contactNumber, rollNumber;
    private int branch;
    private String year ,hostel,roomnumber,gender;
    private String uid;


    public StudentDataClass() {
    }

    public StudentDataClass(String username, String email,
                            String contactNumber, String rollNumber, int branch,
                             String year,
                            String hostel, String roomnumber,
                            String gender) {
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.branch = branch;
        this.rollNumber = rollNumber;
        this.year = year;
        this.hostel = hostel;
        this.roomnumber = roomnumber;
        this.gender = gender;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getBranch() {
        return branch;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getYear() {
        return year;
    }

    public String getHostel() {
        return hostel;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public String getGender() {
        return gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
