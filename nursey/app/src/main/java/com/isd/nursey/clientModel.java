package com.isd.nursey;

public class clientModel {

    private int CID;
    private String fname;
    private String address;
    private String email;
    private int phone_number;
    private String patient_name;
    private String case_details;

    public clientModel(int CID, String fname, String address, String email, int phone_number, String patient_name, String case_details) {
        this.CID = CID;
        this.fname = fname;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
        this.patient_name = patient_name;
        this.case_details = case_details;
    }
    public clientModel() {

    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getCase_details() {
        return case_details;
    }

    public void setCase_details(String case_details) {
        this.case_details = case_details;
    }

    @Override
    public String toString() {
        return "clientModel{" +
                "CID=" + CID +
                ", fname='" + fname + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phone_number +
                ", patient_name='" + patient_name + '\'' +
                ", case_details='" + case_details + '\'' +
                '}';
    }
}
