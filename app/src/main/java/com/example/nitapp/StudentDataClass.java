package com.example.nitapp;

public class StudentDataClass {

    String username;
    String roll;
    String password;

    public StudentDataClass(String username, String roll, String password) {
        this.username = username;
        this.roll = roll;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getRoll() {
        return roll;
    }

    public String getPassword() {
        return password;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
