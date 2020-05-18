package com.isd.nursey;

public class clientTimeModel {
    private int TID;
    private int numberofHour;
    private int CID;
    private String day;
    private String  name;
    private int  time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public clientTimeModel(int TID, int numberofHour, int CID, String day, String name) {
        this.TID = TID;
        this.numberofHour = numberofHour;
        this.CID = CID;
        this.day = day;
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String gettimeInterval(){
        int lasttime=this.time+numberofHour;
        return time+":00-"+lasttime+":00";
}
    public clientTimeModel() {
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getNumberofHour() {
        return numberofHour;
    }

    public void setNumberofHour(int numberofHour) {
        this.numberofHour = numberofHour;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getDay() {
        return day+"day";
    }

    public void setDay(String day) {
        this.day = day;
    }
}
