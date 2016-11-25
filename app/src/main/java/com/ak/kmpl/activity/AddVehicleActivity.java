package com.ak.kmpl.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ak.kmpl.R;
import com.ak.kmpl.adapter.VehicleListAdapter;
import com.ak.kmpl.app.PrefManager;
import com.ak.kmpl.app.TextValidation;
import com.ak.kmpl.inteface.AddVehicleData;
import com.ak.kmpl.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
/*import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;*/

//@RuntimePermissions
public class AddVehicleActivity extends AppCompatActivity implements AddVehicleData {


  /*  @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void showgps() {


    }


    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForgps() {
        //Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();


    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForgps() {
        //Toast.makeText(this, R.string.permission_camera_neverask, Toast.LENGTH_SHORT).show();
    }*/
/*
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void writeData() {

    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForWrite() {
        //Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();


    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForWrite() {
        //Toast.makeText(this, R.string.permission_camera_neverask, Toast.LENGTH_SHORT).show();
    }*/
    EditText etAddVehicle;//,etLastReading;
    Spinner spnVehType;
    RelativeLayout rlAddVehicle;
    Vehicle vehicle;
    RecyclerView rvVehicleList;
    List<Vehicle> vehiclesList;
    ArrayList<Vehicle> vehLst;
    private RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter mAdapter;
    public boolean EDITDATA = false;
    public AddVehicleData addVehicleData = null;
    Button btnDone;

    public Vehicle vehc;
    TextValidation tv;
    private PrefManager prefManager;
    public static LinearLayout llAddData;

    private String[] state = { "1000", "2500", "5000", "8500", "10000", "15000", "20000", "40000", "50000"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", true);


        prefManager = new PrefManager(this);

        tv = new TextValidation(this);

        if (!prefManager.isFirstTimeLaunch()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            // finish();
        }

      //  AddVehicleActivityPermissionsDispatcher.showgpsWithCheck(this);
       // AddVehicleActivityPermissionsDispatcher.writeDataWithCheck(this);




        getSupportActionBar().setTitle("Add Vehicle");

        etAddVehicle = (EditText) findViewById(R.id.etVehicleName);
        //etLastReading = (EditText) findViewById(R.id.etLastReading);
        spnVehType = (Spinner) findViewById(R.id.spnVehicleType);
        rlAddVehicle = (RelativeLayout) findViewById(R.id.rlAddVehicle);
        rvVehicleList = (RecyclerView) findViewById(R.id.rvVehicleList);
        btnDone = (Button) findViewById(R.id.btnDone);

        llAddData=(LinearLayout)findViewById(R.id.llAddData);


        vehLst = new ArrayList<>();

        vehiclesList = Vehicle.listAll(Vehicle.class);
        vehLst.addAll(vehiclesList);

        addVehicleData = this;


        mLayoutManager = new LinearLayoutManager(AddVehicleActivity.this);
        rvVehicleList.setLayoutManager(mLayoutManager);
        mAdapter = new VehicleListAdapter(vehLst, addVehicleData);
        rvVehicleList.setAdapter(mAdapter);
        rvVehicleList.setNestedScrollingEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finishAct();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickBtn(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnDone:

                if (!tv.validateText(etAddVehicle, "Enter Vehicle Name", false)) {
                    return;
                }

                List<Vehicle> vehiclesRecords = Vehicle.find(Vehicle.class, "Name = ?", etAddVehicle.getText().toString());

                if (vehiclesRecords.size() > 0 && EDITDATA == false) {

                    Snackbar snackbar = Snackbar.make(rlAddVehicle, "Vehicle Already Present with this name", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (EDITDATA == false) {


                    vehicle = new Vehicle();
                    vehicle.setName(etAddVehicle.getText().toString());
                    vehicle.setType(spnVehType.getSelectedItem().toString());
                    vehicle.setLastReading(0);
                    vehicle.setServiceReminder(0);
                    vehicle.setSpinnerItem(0);
                    vehicle.setLastLitre(0);
                    vehicle.save();

                    prefManager.setServiceInterval(0);
                    prefManager.setFirstTimeLaunch(false);
                    //startActivity(new Intent(AddVehicleActivity.this,ShowRecorsActivity.class));
                    finishAct();

                } else if (EDITDATA == true) {

                    vehc.setName(etAddVehicle.getText().toString());
                    vehc.setType(spnVehType.getSelectedItem().toString());
                   // vehicle.setServiceReminder(1000);
                    vehc.save();
                    finishAct();
                }

                break;
        }

    }

    @Override
    public void getVehicleData(Vehicle veh) {

        etAddVehicle.setText(veh.getName());
        vehc = veh;
        if (veh.getType().equalsIgnoreCase("bike")) {
            spnVehType.setSelection(0);
        } else {
            spnVehType.setSelection(1);
        }
        btnDone.setText("Update");
        EDITDATA = true;
    }

    public void finishAct()
    {
        Intent i = new Intent(AddVehicleActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



}
