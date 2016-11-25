package com.ak.kmpl.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by WDIPL44 on 7/19/2016.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "KMPL";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String DEFAULT_VEHICLE = "DefaultVehicle";
    private static final String SERVICE_INTERVAL = "ServiceInterval";
    private static final String SERVICE_INTERVAL_KMS = "ServiceIntervalKMS";
    private static final String SPINNER_POSITION = "SpinnerPosition";


    public static String M_TIPS_TIME = "tips_time";
    public static String M_TIPS_COUNT = "tips_count";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setDefaultVehicle(int pos) {
        editor.putInt(DEFAULT_VEHICLE, pos);
        editor.commit();
    }

    public int getDefaultVehicle() {
        return pref.getInt(DEFAULT_VEHICLE, 0);
    }

    public void setServiceInterval(int interval) {
        editor.putInt(SERVICE_INTERVAL, interval);
        editor.commit();
    }

    public int getServiceInterval() {
        return pref.getInt(SERVICE_INTERVAL, 0);
    }


    public void setServiceIntervalKMS(int interval) {
        editor.putInt(SERVICE_INTERVAL_KMS, interval);
        editor.commit();
    }

    public int getServiceIntervalKMS() {
        return pref.getInt(SERVICE_INTERVAL_KMS, 0);
    }


    public void setSpinnerPosition(int postion) {
        editor.putInt(SPINNER_POSITION, postion);
        editor.commit();
    }

    public int getSpinnerPosition() {
        return pref.getInt(SPINNER_POSITION, 0);
    }


    //tips and trick

    public void setTipsTimeAndCount(int count)
    {   editor.putString(M_TIPS_TIME, String.valueOf(new Date().getTime()));
        editor.putInt(M_TIPS_COUNT, count);
        editor.commit();
    }

    public long getLastTipsTime() {
        return Long.parseLong(pref.getString(M_TIPS_TIME, "0"));
    }

    public int getTipsCount()
    {
        return pref.getInt(M_TIPS_COUNT, 0);
    }

}