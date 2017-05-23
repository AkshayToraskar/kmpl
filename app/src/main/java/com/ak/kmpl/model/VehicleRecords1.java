package com.ak.kmpl.model;



/**
 * Created by WDIPL44 on 7/26/2016.
 */
public class VehicleRecords1   {

    public VehicleRecords1()
    {

    }

    String vName, date;
    int  amt, reading, distCover;
    float average,litre;
    long vId;

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public long getvId() {
        return vId;
    }

    public void setvId(long vId) {
        this.vId = vId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getLitre() {
        return litre;
    }

    public void setLitre(float litre) {
        this.litre = litre;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }

    public int getDistCover() {
        return distCover;
    }

    public void setDistCover(int distCover) {
        this.distCover = distCover;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
