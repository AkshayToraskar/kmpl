package com.ak.kmpl.realm_model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dg hdghfd on 16-05-2017.
 */

public class Vehicle extends RealmObject {

    @PrimaryKey
    long id;
    String name;
    int type, serviceReminder, spinnerPos;
    float lastReading, lastLitre;
    RealmList<VehicleRecords> vehicleRecords;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getServiceReminder() {
        return serviceReminder;
    }

    public void setServiceReminder(int serviceReminder) {
        this.serviceReminder = serviceReminder;
    }

    public float getLastReading() {
        return lastReading;
    }

    public void setLastReading(float lastReading) {
        this.lastReading = lastReading;
    }

    public float getLastLitre() {
        return lastLitre;
    }

    public void setLastLitre(float lastLitre) {
        this.lastLitre = lastLitre;
    }

    public RealmList<VehicleRecords> getVehicleRecords() {
        return vehicleRecords;
    }

    public void setVehicleRecords(RealmList<VehicleRecords> vehicleRecords) {
        this.vehicleRecords = vehicleRecords;
    }

    public int getSpinnerPos() {
        return spinnerPos;
    }

    public void setSpinnerPos(int spinnerPos) {
        this.spinnerPos = spinnerPos;
    }
}
