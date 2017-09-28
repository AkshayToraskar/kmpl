package com.ak.kmpl.activity;

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
import com.ak.kmpl.realm_model.Vehicle;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import me.anwarshahriar.calligrapher.Calligrapher;

public class AddVehicleActivity extends AppCompatActivity implements AddVehicleData {

    @BindView(R.id.etVehicleName)
    EditText etAddVehicle;//,etLastReading;
    @BindView(R.id.spnVehicleType)
    Spinner spnVehType;
    @BindView(R.id.rlAddVehicle)
    RelativeLayout rlAddVehicle;
    @BindView(R.id.rvVehicleList)
    RecyclerView rvVehicleList;
    @BindView(R.id.btnDone)
    Button btnDone;
    public static LinearLayout llAddData;
    private RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter mAdapter;

    Vehicle vehicle;
    List<Vehicle> vehLst;
    public boolean EDITDATA = false;
    public AddVehicleData addVehicleData = null;
    TextValidation tv;
    private PrefManager prefManager;

    private String[] state = {"1000", "2500", "5000", "8500", "10000", "15000", "20000", "40000", "50000"};
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", true);


        prefManager = new PrefManager(this);

        tv = new TextValidation(this);

        if (!prefManager.isFirstTimeLaunch()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            //vehiclesList = Vehicle.listAll(Vehicle.class);
            //vehLst.addAll(vehiclesList);
            // finish();
        }

        //  AddVehicleActivityPermissionsDispatcher.showgpsWithCheck(this);
        // AddVehicleActivityPermissionsDispatcher.writeDataWithCheck(this);


        getSupportActionBar().setTitle("Add Vehicle");


        llAddData = (LinearLayout) findViewById(R.id.llAddData);


        vehLst = new ArrayList<>();
        vehLst.addAll(realm.where(Vehicle.class).findAll());


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


                vehicle = realm.where(Vehicle.class).equalTo("name", etAddVehicle.getText().toString()).findFirst();

                if (vehicle != null && EDITDATA == false) {
                    Snackbar snackbar = Snackbar.make(rlAddVehicle, "Vehicle Already Present with this name", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    //startActivity(new Intent(AddVehicleActivity.this,ShowRecorsActivity.class));
                    //finishAct();

                }

                saveData();
                finishAct();

                break;
        }

    }

    @Override
    public void getVehicleData(Vehicle veh) {

        etAddVehicle.setText(veh.getName());
        vehicle = veh;
        if (veh.getType() == 1) {
            spnVehType.setSelection(0);
        } else {
            spnVehType.setSelection(1);
        }
        btnDone.setText("Update");
        EDITDATA = true;
    }

    public void finishAct() {


        Intent i = new Intent(AddVehicleActivity.this, ShowRecorsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void saveData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (vehicle == null) {
                    vehicle = realm.createObject(Vehicle.class, new Date().getTime());

                }


                if (EDITDATA == false) {
                    vehicle.setLastReading(0);
                    vehicle.setServiceReminder(0);
                    vehicle.setLastLitre(0);
                    prefManager.setServiceInterval(0);
                    prefManager.setFirstTimeLaunch(false);
                }

                vehicle.setName(etAddVehicle.getText().toString());
                vehicle.setType(spnVehType.getSelectedItemPosition());

                realm.copyToRealmOrUpdate(vehicle);
                mAdapter.notifyDataSetChanged();
            }
        });
    }


}
