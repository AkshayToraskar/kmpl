package com.ak.kmpl.model;



/**
 * Created by WDIPL44 on 7/26/2016.
 */
public class Vehicle1 {


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



    public Vehicle1() {
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
