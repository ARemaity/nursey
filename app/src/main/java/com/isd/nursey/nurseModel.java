package com.isd.nursey;

import android.util.Log;

import java.time.LocalDate;
import java.time.Period;

public class nurseModel {
    private String fname;
    private int TID;
    private String lname;
    private String dob;
    private   String address;
    private int phone_number;
    private String email;
    private String domain;
    private   String gender;
    private int numberofHour;
    private int NID;
    private String day;
    private int  time;
    private int SID;
private String name;
    public nurseModel() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
public void setName(String fn,String ln){
        this.name=fn+" "+ln;
}
public String getName(){
        return  this.name;
}
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNumberofHour() {
        return numberofHour;
    }

    public String gettimeInterval(){
        int lasttime=this.time+numberofHour;
        return time+":00-"+lasttime+":00";
    }
    public void setNumberofHour(int numberofHour) {
        this.numberofHour = numberofHour;
    }

    public int getNID() {
        return NID;
    }

    public void setNID(int NID) {
        this.NID = NID;
    }

    public String getDay() {
        return day+"day";
    }

    public void setDay(String day) {
        this.day = day;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public  String getAge(){
        Log.d("ssssssssssssssssss age",this.dob);
        String [] partsts= this.dob.split("-");
        int day=Integer.parseInt(partsts[2]);
        int month=Integer.parseInt(partsts[1]);
        int year=Integer.parseInt(partsts[0]);

        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(year,month,day);

        Period p = Period.between(birthday, today);

        int result =p.getYears();

        return  Integer.toString(result);

    }

    public String getEmail() {
        return email;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
