package com.ak.kmpl.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by WDIPL44 on 7/26/2016.
 */
public class Vehicle extends SugarRecord {

    @Unique
    String name;
    String type;
    int lastReading;
    float lastLitre;
    int serviceReminder;

    public int getSpinnerItem() {
        return spinnerItem;
    }

    public void setSpinnerItem(int spinnerItem) {
        this.spinnerItem = spinnerItem;
    }

    int spinnerItem;

    public int getSpinnerPos() {
        return spinnerPos;
    }

    public void setSpinnerPos(int spinnerPos) {
        this.spinnerPos = spinnerPos;
    }

    int spinnerPos;



    public Vehicle() {
    }



    public float getLastLitre() {
        return lastLitre;
    }

    public void setLastLitre(float lastLitre) {
        this.lastLitre = lastLitre;
    }





    public int getLastReading() {
        return lastReading;
    }

    public void setLastReading(int lastReading) {
        this.lastReading = lastReading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getServiceReminder() {
        return serviceReminder;
    }

    public void setServiceReminder(int serviceReminder) {
        this.serviceReminder = serviceReminder;
    }

}
