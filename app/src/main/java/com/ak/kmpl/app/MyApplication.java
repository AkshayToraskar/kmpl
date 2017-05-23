package com.ak.kmpl.app;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by dg hdghfd on 28-12-2016.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
    }
}