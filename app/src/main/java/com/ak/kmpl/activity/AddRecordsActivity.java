package com.ak.kmpl.activity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.ak.kmpl.R;
import com.ak.kmpl.app.PrefManager;
import com.ak.kmpl.app.TextValidation;
import com.ak.kmpl.model.Vehicle;
import com.ak.kmpl.model.VehicleRecords;
import com.orm.query.Select;

import java.util.Calendar;
import java.util.List;

//import cn.refactor.lib.colordialog.ColorDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class AddRecordsActivity extends AppCompatActivity {

    static EditText etDate, etLitre, etAmount, etMeterReading;
    Spinner spnVehName;
    Button btnDone, btnDelete;
    VehicleRecords vehicleRecords;
    List<Vehicle> vehiclesNameList;
    Vehicle vehicle;
    TextValidation tv;
    String vehicleRecordId;
    String vehicleName[], lastReading;
    PrefManager prefManager;
    static int service_reminder = 5000;
    // static int service_reminder;
    public static int serviceInterval = 5000;


    TextInputLayout tilLitre, tilAmount, tilMeterReading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", true);


        enterFromBottomAnimation();


        etDate = (EditText) findViewById(R.id.etDate);
        etLitre = (EditText) findViewById(R.id.etLitre);
        etAmount = (EditText) findViewById(R.id.etAmount);
        etMeterReading = (EditText) findViewById(R.id.etMeterReading);
        spnVehName = (Spinner) findViewById(R.id.spnVehicleName);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        tilLitre = (TextInputLayout) findViewById(R.id.input_layout_litre);
        tilAmount = (TextInputLayout) findViewById(R.id.input_layout_amount);
        tilMeterReading = (TextInputLayout) findViewById(R.id.input_layout_meter_reading);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Transaction");


        tv = new TextValidation(this);

        vehiclesNameList = Vehicle.listAll(Vehicle.class);
        vehicleName = new String[vehiclesNameList.size()];
        for (int i = 0; i < vehicleName.length; i++) {
            vehicleName[i] = vehiclesNameList.get(i).getName();
        }
        ArrayAdapter<String> spnVehicleNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vehicleName);
        spnVehicleNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnVehName.setAdapter(spnVehicleNameAdapter);

        prefManager = new PrefManager(getApplicationContext());
        spnVehName.setSelection(ShowRecorsActivity.SELECTEDSPN);


        vehicleRecordId = getIntent().getStringExtra("vehicle_record_id");
        if (vehicleRecordId != null) {
            Log.v("aaaa", "adfs " + vehicleRecordId);

            List<VehicleRecords> vehiclesRecords = VehicleRecords.find(VehicleRecords.class, "id = ?", vehicleRecordId);

            etDate.setText(vehiclesRecords.get(0).getDate());
            etLitre.setText(String.valueOf(vehiclesRecords.get(0).getLitre()));
            etAmount.setText(String.valueOf(vehiclesRecords.get(0).getAmt()));
            etMeterReading.setText(String.valueOf(vehiclesRecords.get(0).getReading()));

            btnDone.setText("Update");
            btnDelete.setVisibility(View.VISIBLE);

            /*int spnPOS = 0;
            for (int i = 0; i < vehicleName.length; i++) {
                if (vehicleName.equals(vehiclesRecords.get(0).getvName())) {
                    spnPOS = i;
                }
            }
            Log.v("Indexed Data", " asdsdaf " + spnPOS);*/
            spnVehName.setSelection(spnVehicleNameAdapter.getPosition(vehiclesRecords.get(0).getvName()));


        }


        spnVehName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicle = vehiclesNameList.get(spnVehName.getSelectedItemPosition());
                etMeterReading.setHint("Your Last Reading: " + vehicle.getLastReading());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //setting current date in edit text field
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        etDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);


      /*  etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog(etDate.getText().toString());


            }
        });
*/
    }


    @Override
    protected void onPause() {
        exitToBottomAnimation();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickBtn(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnDone:


                if (!tv.validateText(etDate, "Enter Date", false)) {
                    return;
                }
                if (!tv.validateText(etLitre, "Enter Litre", false)) {
                    return;
                }
                if (!tv.validateText(etAmount, "Enter Amount", false)) {
                    return;
                }
                if (!tv.validateText(etMeterReading, "Enter Meter Reading", false)) {
                    return;
                }
//notification for service
          /*      if (Integer.parseInt(etMeterReading.getText().toString()) >= prefManager.getServiceInterval()) {
                    //   service_reminder=prefManager.getServiceInterval();
                    service_reminder = serviceInterval + prefManager.getServiceInterval();
                    prefManager.setServiceInterval(service_reminder);

                    showServiceNotification();
                }

*/

                /*if (!tv.validateTILText(etLitre, tilLitre, "Enter Litre", false)) {
                    return;
                } else {
                    tilLitre.setErrorEnabled(false);
                }

                if (!tv.validateTILText(etAmount, tilAmount, "Enter Amount", false)) {
                    return;
                } else {
                    tilAmount.setErrorEnabled(false);
                }

                if (!tv.validateTILText(etMeterReading, tilMeterReading, "Your Last Reading is: " + vehicle.getLastReading(), false)) {
                    return;
                } else {
                    tilMeterReading.setErrorEnabled(false);
                }*/

                if (Integer.parseInt(etMeterReading.getText().toString()) <= Integer.parseInt(String.valueOf(vehicle.getLastReading()))) {
                    etMeterReading.setError("Your Last Reading is: " + vehicle.getLastReading());
                    //requestFocus(et);

                    //tilMeterReading.setErrorEnabled(true);
                    //tilMeterReading.setError("Your Last Reading is: " + vehicle.getLastReading());

                    return;
                } /*else {
                    tilMeterReading.setErrorEnabled(false);
                }*/


                if (vehicleRecordId != null) {

                    VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));

                    saveRec(vehicleRec);
                    /*vehicleRec.setvId(vehicle.getId());
                    vehicleRec.setAmt(Integer.parseInt(etAmount.getText().toString()));
                    vehicleRec.setAverage(26);
                    vehicleRec.setDate(etDate.getText().toString());
                    vehicleRec.setDistCover(15);
                    vehicleRec.setLitre(Integer.parseInt(etLitre.getText().toString()));
                    vehicleRec.setvName(spnVehName.getSelectedItem().toString());
                    vehicleRec.setReading(Integer.parseInt(etMeterReading.getText().toString()));
                    vehicleRec.save();

                    vehicle.setLastReading(Integer.parseInt(etMeterReading.getText().toString()));
                    vehicle.save();*/

                    //finish();

                    startShowRecord();


                } else {
                    vehicleRecords = new VehicleRecords();
                    saveRec(vehicleRecords);


                    //finish();
                    startShowRecord();
                }

                break;

            case R.id.btnDelete:

                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));
                                vehicleRec.delete();

                                Log.v("Last Reading", "l: " + lastReading());

                                if (lastReading() >= 0) {

                                    vehicle.setLastReading(lastReading());
                                    vehicle.save();
                                } else {
                                    vehicle.setLastReading(0);
                                    vehicle.save();
                                }
                                startShowRecord();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

               // showDialogService();
               /* new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No !")
                        .setConfirmText("Yes !")
                        .showCancelButton(true)


                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));
                                vehicleRec.delete();

                                Log.v("Last Reading", "l: " + lastReading());

                                if (lastReading() >= 0) {

                                    vehicle.setLastReading(lastReading());
                                    vehicle.save();
                                } else {
                                    vehicle.setLastReading(0);
                                    vehicle.save();
                                }

                                sDialog
                                        .setTitleText("Deleted!")
                                        .setContentText("Your recent record has been deleted!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                startShowRecord();
                                            }
                                        })

                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);


                            }
                        })

               *//* .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })*//*
                        .show();
              *//*  VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));
                vehicleRec.delete();

                vehicle.setLastReading(lastReading());
                vehicle.save();*//*

                //finish();
                // startShowRecord();*/
                break;


            case R.id.etDate:
                showDatePickerDialog(etDate.getText().toString());
                break;
        }

    }

    public void showDialogService() {



        /*new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.trash)
                .setTitle("Delete")
                .setMessage("Are you sure? Won't be able to recover this file!")
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));
                        vehicleRec.delete();

                        Log.v("Last Reading", "l: " + lastReading());

                        if (lastReading() >= 0) {

                            vehicle.setLastReading(lastReading());
                            vehicle.save();
                        } else {
                            vehicle.setLastReading(0);
                            vehicle.save();
                        }
                        startShowRecord();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();*/

        /*ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("DELETE");
        dialog.setColor(R.color.colorPrimary);
        //dialog.setTitleTextColor(android.R.color.black);
        //dialog.setContentTextColor(android.R.color.black);
        dialog.setContentText("Are you sure? Won't be able to recover this file!");
        //dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
        dialog.setPositiveListener("YES", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));
                vehicleRec.delete();

                Log.v("Last Reading", "l: " + lastReading());

                if (lastReading() >= 0) {

                    vehicle.setLastReading(lastReading());
                    vehicle.save();
                } else {
                    vehicle.setLastReading(0);
                    vehicle.save();
                }
                startShowRecord();


            }
        })
                .setNegativeListener("CANCEL", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();*/



        /*//prefManager.setServiceReminderDialogLaunch(false);
        new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                .setTitleText("Delete")
                .setContentText("Are you sure? Won't be able to recover this file!")

            *//*    .setPositiveListener("CANCEL", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                })*//*
                .setOnCancelListener("CANCEL", new PromptDialog.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        VehicleRecords vehicleRec = VehicleRecords.findById(VehicleRecords.class, Integer.parseInt(vehicleRecordId));
                        vehicleRec.delete();

                        Log.v("Last Reading", "l: " + lastReading());

                        if (lastReading() >= 0) {

                            vehicle.setLastReading(lastReading());
                            vehicle.save();
                        } else {
                            vehicle.setLastReading(0);
                            vehicle.save();
                        }
                        startShowRecord();

                    }
                }).show();*/


    }


    public int lastReading() {
        String vid = String.valueOf(vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getId());

        List<VehicleRecords> vehiclesRecords = Select.from(VehicleRecords.class)
                .orderBy("id Desc")
                .where("V_Id = ?", new String[]{vid}).list();

        if (vehiclesRecords.size() == 0) {
            return 0;
        }


        return vehiclesRecords.get(0).getReading();
    }


    public void startShowRecord() {

        //showDialogService();
        exitToBottomAnimation();


        Intent i = new Intent(AddRecordsActivity.this, ShowRecorsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);


    }


    public void saveRec(VehicleRecords vehRec) {
        vehRec.setvId(vehicle.getId());
        vehRec.setAmt(Integer.parseInt(etAmount.getText().toString()));

        vehRec.setDate(etDate.getText().toString());

        if (vehicle.getLastLitre() == 0) {
            vehRec.setAverage(0);
            vehRec.setDistCover(0);
        } else {
            String avg = String.format("%.2f", getDistanceCovered() / vehicle.getLastLitre());
            vehRec.setAverage(Float.parseFloat(avg));
            vehRec.setDistCover(getDistanceCovered());
        }
        vehRec.setLitre(Float.parseFloat(etLitre.getText().toString()));
        vehRec.setvName(spnVehName.getSelectedItem().toString());
        vehRec.setReading(Integer.parseInt(etMeterReading.getText().toString()));
        vehRec.save();

        vehicle.setLastReading(Integer.parseInt(etMeterReading.getText().toString()));
        vehicle.setLastLitre(Float.parseFloat(etLitre.getText().toString()));
        //chang3
        //vehicle.setServiceReminder(5000);
        vehicle.save();
    }

    public int getDistanceCovered() {
        return Integer.parseInt(etMeterReading.getText().toString()) - vehicle.getLastReading();
    }

    //show selected date
    private void showDatePickerDialog(String date) {
        // here date is 5-12-2013
        String[] split = date.split("-");
        int day = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]);
        int year = Integer.valueOf(split[2]);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                etDate.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddRecordsActivity.this,
                dateSetListener, year, month - 1, day);
        datePickerDialog.show();
    }

    protected void enterFromBottomAnimation() {
        overridePendingTransition(R.anim.activity_open_translate_from_bottom, R.anim.activity_no_animation);
    }

    protected void exitToBottomAnimation() {
        overridePendingTransition(R.anim.activity_no_animation, R.anim.activity_close_translate_to_bottom);
    }

    public void showServiceNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, AddRecordsActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("KMPL")
                //.setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.kmpllogo))
                .setSmallIcon(R.drawable.serviceimage)
                .setContentTitle("Service Reminder")
                .setContentText("Get ur car Serviced")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

  /*      PugNotification.with(this)
                .load()
                .title("KMPL")
                .message("Service Reminder")
                //.bigTextStyle(bigtext)
                .smallIcon(R.drawable.serviceimage)
                .largeIcon(R.drawable.kmpllogo)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .simple()
                .build();
*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


}
