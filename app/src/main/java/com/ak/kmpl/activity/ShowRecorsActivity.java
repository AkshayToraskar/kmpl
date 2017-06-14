package com.ak.kmpl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ak.kmpl.R;
import com.ak.kmpl.app.PrefManager;
import com.ak.kmpl.fragment.GraphFragment;
import com.ak.kmpl.fragment.RecordsFragment;
import com.ak.kmpl.inteface.FilterData;

import com.ak.kmpl.realm_model.Vehicle;
import com.ak.kmpl.realm_model.VehicleRecords;
import com.github.fabtransitionactivity.SheetLayout;
import com.mikhaellopez.circularimageview.CircularImageView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.anwarshahriar.calligrapher.Calligrapher;

public class ShowRecorsActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {

    @BindView(R.id.fabAddNewRecord)
    FloatingActionButton fabAddNewRecord;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindViews({R.id.tvMr1, R.id.tvMr2, R.id.tvMr3, R.id.tvMr4, R.id.tvMr5, R.id.tvMr6})
    List<TextView> lstTvMeter;
    @BindView(R.id.spnVehicleName)
    Spinner spnVehName;
    @BindView(R.id.tvKms)
    TextView tvKms;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvAverage)
    TextView tvAverage;
    @BindView(R.id.llAverage)
    LinearLayout llAverage;
    @BindView(R.id.llAmt)
    LinearLayout llAmt;
    @BindView(R.id.bottom_sheet)
    SheetLayout mSheetLayout;
    @BindView(R.id.menu_sheet)
    SheetLayout akSheetLayout;
    @BindView(R.id.civVehIcon)
    CircularImageView civVehIcon;

    private static final int REQUEST_CODE = 1;
    private static final int AK_REQUEST_CODE = 2;
    public static int SELECTEDSPN;
    public static int SELECTEDBTN;
    public static long vid;

    public PrefManager prefManager;
    public List<Vehicle> vehiclesNameList;
    public int service_reminder;

    Realm realm;
    Vehicle vehicle;
    ArrayAdapter<String> spnVehicleNameAdapter;
    Animation animFadein, animBounce;
    public FilterData filterData;

    //public static boolean FIRSTRUN = false;
    //ExplosionField explosionField;
    // Animation
    //static EditText etDate, etLitre, etAmount, etMeterReading;
    //= 5000;//prefManager.getServiceIntervalKMS();
    //static int serviceInterval;// = 5000;//prefManager.getServiceIntervalKMS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records_scroll);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        prefManager = new PrefManager(getApplicationContext());

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animBounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

        //  toolbar = (Toolbar) findViewById(R.id.toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheet);
        //  akSheetLayout = (SheetLayout) findViewById(R.id.menu_sheet);
        //   fabAddNewRecord = (FloatingActionButton) findViewById(R.id.fabAddNewRecord);
        //  viewPager = (ViewPager) findViewById(R.id.viewpager);
        //  ivHome = (ImageView) findViewById(R.id.ivHome);
        // tabLayout = (TabLayout) findViewById(R.id.tabs);
        //  civVehIcon = (CircularImageView) findViewById(R.id.civVehIcon);
        //  spnVehName = (Spinner) findViewById(R.id.spnVehicleName);
        // tvMr1 = (TextView) findViewById(R.id.tvMr1);
        // tvMr2 = (TextView) findViewById(R.id.tvMr2);
        // tvMr3 = (TextView) findViewById(R.id.tvMr3);
        // tvMr4 = (TextView) findViewById(R.id.tvMr4);
        // tvMr5 = (TextView) findViewById(R.id.tvMr5);
        // tvMr6 = (TextView) findViewById(R.id.tvMr6);
        //  tvKms = (TextView) findViewById(R.id.tvKms);
        //  tvAverage = (TextView) findViewById(R.id.tvAverage);
        //  llAverage = (LinearLayout) findViewById(R.id.llAverage);
        //  llAmt = (LinearLayout) findViewById(R.id.llAmt);
        //tvAmount = (TextView) findViewById(R.id.tvAmount);
        //  etMeterReading = (EditText) findViewById(R.id.etMeterReading);
        //prefManager.setServiceInterval(service_reminder);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //explosionField = ExplosionField.attach2Window(this);
        //filterData=(FilterData)this;
        //FIRSTRUN = true;
        //showRecords();

        vehiclesNameList = realm.where(Vehicle.class).findAll();
        final String vehicleName[] = new String[vehiclesNameList.size()];
        for (int i = 0; i < vehicleName.length; i++) {
            vehicleName[i] = vehiclesNameList.get(i).getName();
        }

        spnVehicleNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vehicleName);
        spnVehicleNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnVehName.setAdapter(spnVehicleNameAdapter);

        prefManager = new PrefManager(getApplicationContext());
        spnVehName.setSelection(prefManager.getDefaultVehicle());
        SELECTEDSPN = prefManager.getDefaultVehicle();

        fabAddNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ShowRecorsActivity.this, AddRecordsActivity.class));
                SELECTEDSPN = spnVehName.getSelectedItemPosition();
                SELECTEDBTN = 1;
                mSheetLayout.expandFab();
            }
        });

        mSheetLayout.setFab(fabAddNewRecord);
        mSheetLayout.setFabAnimationEndListener(this);
        akSheetLayout.setFab(ivHome);
        akSheetLayout.setFabAnimationEndListener(this);

        spnVehName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if (FIRSTRUN == false) {
                // notifyDataRecords();
                vid = vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getId();

                /*final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        civVehIcon.clearAnimation();
                    }
                }, 15000);*/

                setData();

                if (filterData != null) {
                    filterData.updateData(String.valueOf(vid));
                }
                civVehIcon.startAnimation(animBounce);
                rollingAnim();

                if (vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getType()==0) {
                    civVehIcon.setImageResource(R.drawable.bike_icon);
                } else {
                   civVehIcon.setImageResource(R.drawable.car_icon);
                }
                RecordsFragment.filterDataRecordFragment.updateData(String.valueOf(vid));

                /*} else {
                    FIRSTRUN = false;
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private final static int FADE_DURATION = 800;

    public void rollingAnim() {

        /*final AnimationSet rollingIn = new AnimationSet(true);
        Animation moving = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        moving.setDuration(1000);
        final Animation rotating = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotating.setDuration(1000);

        rollingIn.addAnimation(rotating);
        rollingIn.addAnimation(moving);*/

        ScaleAnimation rollingIn = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.REVERSE, 1.0f, Animation.REVERSE, 1.0f);
        rollingIn.setDuration(FADE_DURATION);

        tvKms.startAnimation(rollingIn);
        for (int i = 0; i < lstTvMeter.size(); i++) {
            lstTvMeter.get(i).startAnimation(rollingIn);
        }
        /*tvMr2.startAnimation(rollingIn);
        tvMr3.startAnimation(rollingIn);
        tvMr4.startAnimation(rollingIn);
        tvMr5.startAnimation(rollingIn);
        tvMr6.startAnimation(rollingIn);*/

        llAmt.startAnimation(rollingIn);
        llAverage.startAnimation(rollingIn);

    }

    public void setData() {


        Vehicle vehicle = realm.where(Vehicle.class).equalTo("id", vid).findFirst();


        List<VehicleRecords> vehiclesRecords = vehicle.getVehicleRecords();
        /*Select.from(VehicleRecords.class)
                .orderBy("id Desc")
                .where("V_Id = ?", new String[]{vid}).list();*/

        int sum = 0;
        float avgSum = 0, totalAverage;


       /* for (int i = 0; i < vehiclesRecords.size(); i++) {
            sum = sum + vehiclesRecords.get(i).getAmt();
            avgSum = avgSum + vehiclesRecords.get(i).getAverage();
        }*/

        totalAverage = avgSum / vehiclesRecords.size();


        float aaa = (totalAverage == 0.0) ? 0 : totalAverage;

        tvAverage.setText((String.format("%.02f", aaa) + " "));
        tvAmount.setText(sum + " ");


        String totalReading = String.valueOf((int) vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getLastReading());
        String aa = String.format("%1$" + 5 + "s", totalReading);
        String totalReadings[] = aa.split("");

        /*tvMr1.setText("0");
        tvMr2.setText("0");
        tvMr3.setText("0");
        tvMr4.setText("0");
        tvMr5.setText("0");
        tvMr6.setText("0");*/
        for (int i = 0; i < lstTvMeter.size(); i++) {
            lstTvMeter.get(i).setText("0");
        }

        for (int i = 0; i < totalReadings.length; i++) {


            switch (i) {
                case 0:
                    lstTvMeter.get(0).setText("" + totalReadings[i]);
                    break;

                case 1:
                    lstTvMeter.get(1).setText("" + totalReadings[i]);
                    break;

                case 2:
                    lstTvMeter.get(2).setText("" + totalReadings[i]);
                    break;

                case 3:
                    lstTvMeter.get(3).setText("" + totalReadings[i]);
                    break;

                case 4:
                    lstTvMeter.get(4).setText("" + totalReadings[i]);
                    break;

                case 5:
                    lstTvMeter.get(5).setText("" + totalReadings[i]);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_records, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;


            case R.id.action_home:
                SELECTEDBTN = 0;
                //startActivity(new Intent(ShowRecorsActivity.this,MainActivity.class));
                akSheetLayout.expandFab();
                //overridePendingTransition(R.anim.anim_close_act,R.anim.anim_nomove);
                break;


            /*case R.id.action_profile:
                startActivity(new Intent(ShowRecorsActivity.this, ProfileActivity.class));
                break;

            case R.id.action_add_vehicle:
                startActivity(new Intent(ShowRecorsActivity.this, AddVehicleActivity.class));
                break;

            *//*case R.id.action_setting:
                startActivity(new Intent(ShowRecorsActivity.this, SettingsActivity.class));
                break;*//*

            case R.id.action_nearby_petrol_pump:
                startActivity(new Intent(ShowRecorsActivity.this, MapsActivity.class));
                break;

            case R.id.action_fuel_price:
                startActivity(new Intent(ShowRecorsActivity.this,FuelPricesActivity.class));
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        //startActivity(new Intent(MainActivity.this,ShowRecorsActivity.class));
        //overridePendingTransition(R.anim.anim_nomove,R.anim.anim_start_act);

        super.onBackPressed();
    }

    /*public void showRecords() {
        List<VehicleRecords> vehiclesRecords = VehicleRecords.listAll(VehicleRecords.class);
        vehicleRecordses = new ArrayList<>();
        vehicleRecordses.addAll(vehiclesRecords);

        mLayoutManager = new LinearLayoutManager(ShowRecorsActivity.this);
        rvVehicleRecord.setLayoutManager(mLayoutManager);
        mAdapter = new VehicleRecordAdapter(vehicleRecordses);
        rvVehicleRecord.setAdapter(mAdapter);
        rvVehicleRecord.setNestedScrollingEnabled(false);


    }*/

    public void FilterData() {

    }


    @Override
    protected void onResume() {

        vehiclesNameList = realm.where(Vehicle.class).findAll(); //Vehicle.listAll(Vehicle.class);

        // notifyDataRecords();


        if (vehiclesNameList.size() > 0) {

           /* final String vehicleName[] = new String[vehiclesNameList.size()];
            for (int i = 0; i < vehicleName.length; i++) {
                vehicleName[i] = vehiclesNameList.get(i).getName();
            }

            spnVehicleNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vehicleName);
            spnVehicleNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spnVehName.setAdapter(spnVehicleNameAdapter);*/


            vid = vehiclesNameList.get(spnVehName.getSelectedItemPosition()).getId();

            //RecordsFragment.filterData.updateData(vid);
            if (filterData != null) {
                filterData.updateData(String.valueOf(vid));
            }

            civVehIcon.startAnimation(animBounce);
            rollingAnim();

            if (vehiclesNameList.size() >= 1) {
                Log.d("last reading", "last reading" + vehiclesNameList.get(0).getLastReading());
                Log.d("service reminder pref", "service reminder pref" + prefManager.getServiceInterval());

                vehiclesNameList = realm.where(Vehicle.class).findAll(); //Vehicle.listAll(Vehicle.class);
                vehicle = vehiclesNameList.get(spnVehName.getSelectedItemPosition());

                //vehicle.setServiceReminder(prefManager.getServiceInterval());
                Log.d("service reminder data", "service reminder data" + vehicle.getServiceReminder());

                //changes
//                if (((vehicle.getServiceReminder()) !=  prefManager.getServiceInterval())) {
//calculation for service reminder
                if (((vehicle.getServiceReminder()) > 0)) {

                    if (Integer.parseInt(String.valueOf(vehiclesNameList.get(0).getLastReading())) > vehicle.getServiceReminder()) {
                        //   service_reminder=prefManager.getServiceInterval();
                        service_reminder = vehicle.getServiceReminder() + vehicle.getSpinnerPos();
                        //serviceInterval = prefManager.getServiceInterval();

                        // service_reminder = service_reminder + serviceInterval;

                        // prefManager.setServiceInterval(service_reminder);

                        vehicle.setServiceReminder(service_reminder);
                        // vehicle.save();
                        Log.d("SR new data", "SR new data" + vehicle.getServiceReminder());
                        showDialogService();
//                    }
                    }

                }

            }





/*        if (Integer.parseInt(AddRecordsActivity.etMeterReading.getText().toString()) >= prefManager.getServiceInterval()) {
            //   service_reminder=prefManager.getServiceInterval();
            AddRecordsActivity.service_reminder = AddRecordsActivity.serviceInterval + prefManager.getServiceInterval();
            prefManager.setServiceInterval(AddRecordsActivity.service_reminder);

            showDialogService();
        }*/
            //   service_reminder = vehicle.getServiceReminder(); //5000;//prefManager.getServiceIntervalKMS();
            //  serviceInterval =  vehicle.getServiceReminder();//5000;//prefManager.getServiceIntervalKMS();
//        vehicle.setServiceReminder(5000);
//        Log.d("service reminder", "service reminder" + vehicle.getServiceReminder());
//


        }

/*
        if (Integer.parseInt(String.valueOf(vehiclesNameList.get(0).getLastReading())) >= prefManager.getServiceInterval()) {
            //   service_reminder=prefManager.getServiceInterval();
            service_reminder = prefManager.getServiceInterval();
            serviceInterval = prefManager.getServiceInterval();
            //service_reminder = prefManager.getServiceInterval() + prefManager.getServiceInterval();
            service_reminder =service_reminder + serviceInterval;
           // service_reminder = serviceInterval + prefManager.getServiceInterval();
            prefManager.setServiceInterval(service_reminder);

            Log.d("service reminder after pref", "service reminder after pref" + prefManager.getServiceInterval());
            showDialogService();


        }*/

        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecordsFragment(), "Refuelings");
        adapter.addFragment(new GraphFragment(), "Overview");
        //adapter.addFragment(new SummaryFragment(), "Summary");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // noop
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // fire event if the "My Site" page is being scrolled so the fragment can
                // animate its fab to match
                if (position == 0) {
                    fabAddNewRecord.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                } else if (position == 1) {
                    fabAddNewRecord.animate().translationY(fabAddNewRecord.getHeight() + 50).setInterpolator(new AccelerateInterpolator(2)).start();
                }

            }
        });


    }

    @Override
    public void onFabAnimationEnd() {
        if (SELECTEDBTN == 1) {
            Intent intent = new Intent(this, AddRecordsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (SELECTEDBTN == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, AK_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            mSheetLayout.contractFab();
        }
        if (requestCode == AK_REQUEST_CODE) {
            akSheetLayout.contractFab();
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void showDialogService() {
        //prefManager.setServiceReminderDialogLaunch(false);
       /* new PromptDialog(this).setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                .setTitleText("Service Reminder")
                .setContentText("Time to get Vehicle serviced.")
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();*/
    }


}
