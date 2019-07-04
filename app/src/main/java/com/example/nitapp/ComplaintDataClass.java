package com.example.nitapp;

public class ComplaintDataClass {
    private String complaintType, complaintDescription;
    private String uid;

    public ComplaintDataClass(String complaintType, String complaintDescription, String uid) {
        this.complaintType = complaintType;
        this.complaintDescription = complaintDescription;
        this.uid = uid;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public String getUid() {
        return uid;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
