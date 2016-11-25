package com.ak.kmpl.model;

import java.util.List;

/**
 * Created by dg hdghfd on 22-11-2016.
 */

public class KmplBackup {

    List<Vehicle> vehicles;
    List<VehicleRecords> vehicleRecords;

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleRecords> getVehicleRecords() {
        return vehicleRecords;
    }

    public void setVehicleRecords(List<VehicleRecords> vehicleRecords) {
        this.vehicleRecords = vehicleRecords;
    }
}
