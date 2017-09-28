package com.ak.kmpl.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by WDIPL27 on 8/8/2016.
 */
public final class AppConfig {

    public static final String TAG = "gplaces";

    public static final String RESULTS = "results";
    public static final String STATUS = "status";

    public static final String OK = "OK";
    public static final String ZERO_RESULTS = "ZERO_RESULTS";
    public static final String REQUEST_DENIED = "REQUEST_DENIED";
    public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";

    //    Key for nearby places json from google
    public static final String GEOMETRY = "geometry";
    public static final String LOCATION = "location";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String ICON = "icon";
    public static final String SUPERMARKET_ID = "id";
    public static final String NAME = "name";
    public static final String PLACE_ID = "place_id";
    public static final String REFERENCE = "reference";
    public static final String VICINITY = "vicinity";
    public static final String PLACE_NAME = "place_name";
    public static final String RATING = "rating";

    // remember to change the browser api key
    public static final String GOOGLE_BROWSER_API_KEY =
            "AIzaSyBig1mWsWhuNpCjZO6Qhqjie8MDWrxK2Vw";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int PROXIMITY_RADIUS = 5000; //5kms
    // The minimum distance to change Updates in meters
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50; // 50 meters
    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES =  1000 * 60 * 2; // 2 minute



    public static boolean isNetworkAvailable(final Context context, View view) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        boolean networkStatus=connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

        if(!networkStatus)
        {   Snackbar snackbar = Snackbar
                .make(view, "No Internet Connection!", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                        //snackbar1.show();
                        isNetworkAvailable(context,view);
                    }


                });

            snackbar.show();
        }

        return networkStatus;
    }

}