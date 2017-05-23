package com.ak.kmpl.realm_model;


import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dg hdghfd on 16-05-2017.
 */

public class VehicleRecords extends RealmObject {
    @PrimaryKey
    long id;
    Date date;
    float amt, reading, distCover, average, litre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    public float getReading() {
        return reading;
    }

    public void setReading(float reading) {
        this.reading = reading;
    }

    public float getDistCover() {
        return distCover;
    }

    public void setDistCover(float distCover) {
        this.distCover = distCover;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getLitre() {
        return litre;
    }

    public void setLitre(float litre) {
        this.litre = litre;
    }
}
