package com.ak.kmpl.app;

import android.util.Log;

import com.ak.kmpl.inteface.AddVehicleData;
import com.ak.kmpl.realm_model.Vehicle;
import com.ak.kmpl.realm_model.VehicleRecords;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dg hdghfd on 25-07-2017.
 * <p>
 * scan the survey and import and export the data into csv format
 */

public class CsvOperation {

    List<Vehicle> vehicleList;


    List<String[]> strData = new ArrayList<>();


    //for generating ans
    public CsvOperation(List<Vehicle> vehicle) {
        this.vehicleList = vehicle;
    }


    //for answers
    public List<String[]> generateString() {


        List<String> st = addHeader();
        String[] head = st.toArray(new String[st.size()]);

        strData.add(head);

        //iterate data vehicle
        for (Vehicle vehicle : vehicleList) {


            //iterate vehicle records
            for (VehicleRecords vehicleRecords : vehicle.getVehicleRecords()) {

                List<String> vehicleData = new ArrayList<>();

                vehicleData.add(String.valueOf(vehicle.getId()));                   //0
                vehicleData.add(vehicle.getName());                                 //1
                vehicleData.add(String.valueOf(vehicle.getServiceReminder()));      //2
                vehicleData.add(String.valueOf(vehicle.getSpinnerPos()));           //3
                vehicleData.add(String.valueOf(vehicle.getType()));                 //4
                vehicleData.add(String.valueOf(vehicle.getLastLitre()));            //5
                vehicleData.add(String.valueOf(vehicle.getLastReading()));          //6

                String date="";
                try {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
                    date=simpleDateFormat.format(vehicleRecords.getDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                vehicleData.add(String.valueOf(vehicleRecords.getId()));            //7
                vehicleData.add(String.valueOf(vehicleRecords.getRecordNo()));      //8
                vehicleData.add(String.valueOf(vehicleRecords.getAmt()));           //9
                vehicleData.add(String.valueOf(vehicleRecords.getAverage()));       //10
                vehicleData.add(date);                                              //11
                vehicleData.add(String.valueOf(vehicleRecords.getDistCover()));     //12
                vehicleData.add(String.valueOf(vehicleRecords.getLitre()));         //13
                vehicleData.add(String.valueOf(vehicleRecords.getReading()));       //14

                String arr[] = vehicleData.toArray(new String[vehicleData.size()]);
                strData.add(arr);

            }


        }

        return strData;
    }


    public List<String> addHeader() {

        List<String> strH = new ArrayList<>();
        strH.add("Id");
        strH.add("Name");
        strH.add("Reminder");
        strH.add("pos");
        strH.add("Type");
        strH.add("Last Litre");
        strH.add("Last Reading");
        strH.add("Rec Id");
        strH.add("Rec No");
        strH.add("Rec Amount");
        strH.add("Rec Average");
        strH.add("Rec Date");
        strH.add("Rec Dist Covered");
        strH.add("Rec Litre");
        strH.add("Rec Reading");

        return strH;
    }


}
