package com.ak.kmpl.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.kmpl.R;
import com.ak.kmpl.model.KmplBackup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ProgressDialog dialog;
    String mUsername, mPhotoUrl;

    @BindView(R.id.civProfile)
    CircularImageView ivProfile;
    @BindView(R.id.tvUserName)
    TextView tvUsername;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        dialog = new ProgressDialog(this);




        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            //startActivity(new Intent(this, SignInActivity.class));
            // finish();
            // return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();

            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();

              //  tvUsername.setText("" + mUsername);

                Glide.with(this)
                        .load(mPhotoUrl)
                        .centerCrop()
                        .placeholder(R.drawable.avatar_08)
                        .crossFade()
                        .into(ivProfile);
            }
        }
    }

    public void onClickBtn(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.civProfile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;

            case R.id.ivNearbyPetrolpump:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;

            case R.id.ivAddVehicle:
                startActivity(new Intent(MainActivity.this, AddVehicleActivity.class));
                break;

            case R.id.ivFuelPrice:
                startActivity(new Intent(MainActivity.this, FuelPricesActivity.class));
                break;

            case R.id.ivTips:
                startActivity(new Intent(MainActivity.this, TipsAndTrickActivity.class));
                break;


            case R.id.ivBackupData:
               // if (mFirebaseUser == null) {
               //     startActivity(new Intent(MainActivity.this, ProfileActivity.class));
             //   } else {
                    startActivity(new Intent(MainActivity.this, BackupActivity.class));

                  //  dialog.setMessage("Please Wait");
                //    dialog.show();

                //    backupCustInfo();
               // }
                break;

            case R.id.ivNotification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;

            case R.id.ivClose:

                finish();
                break;
        }

    }

    private void backupCustInfo() {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");
        mFirebaseInstance.getReference("app_title").setValue("KMPL");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            userId = mFirebaseUser.getUid();
        }

        //  userId = mFirebaseDatabase.push().getKey();


        // User user = new User(name, email);
        //List<Vehicle> vehiclesList = Vehicle.listAll(Vehicle.class);


        KmplBackup kmplBackup = new KmplBackup();
        //kmplBackup.setVehicleRecords(vehiclesRecords);
        //kmplBackup.setVehicles(vehiclesList);

        //DatabaseReference dataRef =  mFirebaseInstance.getReference("Users");

        mFirebaseDatabase.child(userId).setValue(kmplBackup, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                dialog.dismiss();

                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                    Toast.makeText(MainActivity.this, "Some Error occoured..!", Toast.LENGTH_SHORT).show();

                } else {
                    System.out.println("Data saved successfully.");
                    Toast.makeText(MainActivity.this, "Successfully data uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //addUserChangeListener();
    }
}
