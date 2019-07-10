package com.example.nitapp;

public class ComplaintDataClass {
    private String complaintId,complaintType, complaintDescription;
    private String uid,status,dateandtime;

    public ComplaintDataClass(){

    }

    public ComplaintDataClass(String complaintType, String complaintDescription, String uid,String status,String date) {
        this.complaintType = complaintType;
        this.complaintDescription = complaintDescription;
        this.uid = uid;
        this.status= status;
        this.dateandtime=date;
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

    public String getStatus() {
        return status;
    }

    public String getDateandtime() {
        return dateandtime;
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

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }
}
