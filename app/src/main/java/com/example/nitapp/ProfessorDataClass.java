package com.example.nitapp;

public class ProfessorDataClass {
    private String username, department ,employeeID, email, contactNumber, gender,uid;

    public  ProfessorDataClass(){

    }

    public ProfessorDataClass(String username, String department, String employeeID, String email, String contactNumber, String gender) {
        this.username = username;
        this.department = department;
        this.employeeID = employeeID;
        this.email = email;
        this.contactNumber = contactNumber;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
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

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
