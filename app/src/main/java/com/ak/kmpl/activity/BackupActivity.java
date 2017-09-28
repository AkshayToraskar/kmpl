package com.ak.kmpl.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.kmpl.R;
import com.ak.kmpl.app.CsvOperation;
import com.ak.kmpl.model.KmplBackup;

import com.ak.kmpl.realm_model.Vehicle;
import com.ak.kmpl.realm_model.VehicleRecords;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class BackupActivity extends AppCompatActivity {

    /*private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String userId;

    public List<Vehicle> vehiclesList;*/
    public static int REQUEST_CODE = 14;
    public final static String EXTRA_FILE_PATH = "file_path";
    private File selectedFile;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        realm = Realm.getDefaultInstance();


        /*mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("KMPL");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });*/


    }

    public void onClickBtn(View view) {
        int id = view.getId();

        switch (id) {
            /*case R.id.btnBackup:
                //uploadVehicleInfo();
                break;
            */
            case R.id.btn_import:
                Intent intent = new Intent(this, FilePickerActivityLatest.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.btn_export:
                exportData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {


            if (data.hasExtra(EXTRA_FILE_PATH)) {

                selectedFile = new File
                        (data.getStringExtra(EXTRA_FILE_PATH));


                Log.v("file path", " " + selectedFile.getPath());
                parseCSVData();
            }


        }

    }

    //import patient data
    private void parseCSVData() {


        CSVReader reader;
        try {

            if (getFileExt(selectedFile.getName()).equals("csv")) {


                reader = new CSVReader(new FileReader(selectedFile));
                String[] row;
                List<?> content = reader.readAll();

                int rowCount = 0;

                if (content != null) {

                    for (Object object : content) {
                        if (rowCount > 0) {
                            row = (String[]) object;
                            for (int i = 0; i < row.length; i++) {
                                // display CSV values
                                System.out.println("Cell column index: " + i);
                                System.out.println("Cell Value: " + row[i]);
                                System.out.println("-------------");
                            }

                            //final String strId = row[0] + row[2] + row[3] + row[5];

                            final String[] finalRow = row;
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    long id = Long.parseLong(finalRow[0]);
                                    Vehicle vehicle = realm.where(Vehicle.class).equalTo("id", id).findFirst();

                                    if (vehicle == null) {
                                        vehicle = realm.createObject(Vehicle.class, id);
                                    }

                                    //vehicle.setId(Long.parseLong(finalRow[0]));
                                    vehicle.setName(finalRow[1]);
                                    vehicle.setServiceReminder(Integer.parseInt(finalRow[2]));
                                    vehicle.setSpinnerPos(Integer.parseInt(finalRow[3]));
                                    vehicle.setType(Integer.parseInt(finalRow[4]));
                                    vehicle.setLastLitre(Float.parseFloat(finalRow[5]));
                                    vehicle.setLastReading(Float.parseFloat(finalRow[6]));

                                    RealmList<VehicleRecords> vehicleRecordsesList = new RealmList<VehicleRecords>();
                                    if (vehicle.getVehicleRecords() != null) {

                                        for (VehicleRecords vehRec : vehicle.getVehicleRecords()) {
                                            if (vehRec.getId() != Long.parseLong(finalRow[7])) {
                                                vehicleRecordsesList.add(vehRec);
                                            }
                                        }
                                    }

                                    VehicleRecords vehicleRecords = realm.where(VehicleRecords.class)
                                            .equalTo("id", Long.parseLong(finalRow[7])).findFirst();
                                    if (vehicleRecords == null) {
                                        vehicleRecords = realm.createObject(VehicleRecords.class, Long.parseLong(finalRow[7]));
                                    }
                                    vehicleRecords.setRecordNo(Integer.parseInt(finalRow[8]));
                                    vehicleRecords.setAmt(Float.parseFloat(finalRow[9]));
                                    vehicleRecords.setAverage(Float.parseFloat(finalRow[10]));

                                    Date date = null;
                                    try {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                        date = simpleDateFormat.parse(finalRow[11]);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    vehicleRecords.setDate(date);
                                    vehicleRecords.setDistCover(Float.parseFloat(finalRow[12]));
                                    vehicleRecords.setLitre(Float.parseFloat(finalRow[13]));
                                    vehicleRecords.setReading(Float.parseFloat(finalRow[14]));
                                    vehicleRecordsesList.add(vehicleRecords);


                                    vehicle.setVehicleRecords(vehicleRecordsesList);

                                    realm.copyToRealmOrUpdate(vehicle);
                                }
                            });

                        } else {
                            rowCount = rowCount + 1;
                        }


                    }
                }

              /*  patientList.addAll(realm.where(Patients.class).findAll());
                mAdapter.notifyDataSetChanged();*/

                Toast.makeText(getApplicationContext(), "Data Successfully Imported..!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Select .csv file", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());

            Toast.makeText(getApplicationContext(), "File is not proper format", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            Toast.makeText(getApplicationContext(), "File is not proper format", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Toast.makeText(getApplicationContext(), "File is not proper format", Toast.LENGTH_SHORT).show();
        }


    }

    public static String getFileExt(String fileName) {

        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).trim();
    }


   /* private void uploadVehicleInfo() {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            if (mFirebaseUser == null) {
                // Not signed in, launch the Sign In activity
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return;
            }
            else
            {
                userId=mFirebaseUser.getUid();
            }

          //  userId = mFirebaseDatabase.push().getKey();
        }

        // User user = new User(name, email);
        //vehiclesList = Vehicle.listAll(Vehicle.class);
        //List<VehicleRecords> vehiclesRecords = VehicleRecords.listAll(VehicleRecords.class);

        KmplBackup kmplBackup=new KmplBackup();
        //kmplBackup.setVehicleRecords(vehiclesRecords);
       // kmplBackup.setVehicles(vehiclesList);

        mFirebaseDatabase.child(userId).setValue(kmplBackup);


        //addUserChangeListener();
    }*/

    //export the data into csv file
    public void exportData() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Exporting Data");
        progressDialog.show();

        try {

            File myDirectory = new File(Environment.getExternalStorageDirectory(), "KMPL");
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            List<String[]> data = new ArrayList<String[]>();

            List<Vehicle> vehicles = realm.where(Vehicle.class).findAll();

            CsvOperation csvOperation = new CsvOperation(vehicles);
            List<String[]> strData = csvOperation.generateString();


            String csv = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "KMPL" + File.separator + "Backup_" + currentDateTimeString + ".csv";
            CSVWriter writer = null;
            writer = new CSVWriter(new FileWriter(csv));

            for (int k = 0; k < strData.size(); k++) {
                data.add(strData.get(k));
            }


            writer.writeAll(data);
            writer.close();
            Log.v("Export Data", "SUCCESS");
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Data Successfully Exported", Toast.LENGTH_SHORT).show();
            // Toast.makeText(getApplicationContext(), "Data Exported Successfully into " + survey.getName() + "_" + "Ans_" + currentDateTimeString + ".csv file", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Export Data", "FAIL");
        }

    }

}
