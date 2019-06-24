package com.example.nitapp;

public class StudentDataClass {

    String username,email,contactNumber;
    String uid;

    int branch,gender,roomNumber,rollNumber,year;

    String hostel;

    public StudentDataClass(String username, String email, String contactNumber, int branch, int gender, int roomNumber, int rollNumber, int year, String  hostel) {
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.branch = branch;
        this.gender = gender;
        this.roomNumber = roomNumber;
        this.rollNumber = rollNumber;
        this.year = year;
        this.hostel = hostel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
