package com.ak.kmpl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ak.kmpl.R;
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


import java.util.List;

public class BackupActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String userId;

    public List<Vehicle> vehiclesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);


        mFirebaseInstance = FirebaseDatabase.getInstance();

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
        });


    }

    public void onClickBtn(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnBackup:
                uploadVehicleInfo();
                break;
        }
    }


    private void uploadVehicleInfo() {
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
    }


}
