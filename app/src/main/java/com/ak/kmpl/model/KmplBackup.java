package com.ak.kmpl.model;

import java.util.List;

/**
 * Created by dg hdghfd on 22-11-2016.
 */

public class KmplBackup {

    List<Vehicle1> vehicles;
    List<VehicleRecords1> vehicleRecords;

    public List<Vehicle1> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle1> vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleRecords1> getVehicleRecords() {
        return vehicleRecords;
    }

    public void setVehicleRecords(List<VehicleRecords1> vehicleRecords) {
        this.vehicleRecords = vehicleRecords;
    }
}
