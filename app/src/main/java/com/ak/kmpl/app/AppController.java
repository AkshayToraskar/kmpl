package com.ak.kmpl.app;

/**
 * Created by WDIPL27 on 8/8/2016.
 */
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static com.ak.kmpl.app.AppConfig.TAG;

//import static com.androidmastermind.gplaces.AppConfig.TAG;

public class AppController {

    private RequestQueue mRequestQueue;
    private static AppController mInstance;

   /* @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }*/

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
           // mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}