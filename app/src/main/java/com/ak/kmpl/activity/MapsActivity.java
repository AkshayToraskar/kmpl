package com.ak.kmpl.activity;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.kmpl.R;
import com.ak.kmpl.adapter.ItemAdapter;
import com.ak.kmpl.app.AppController;
import com.ak.kmpl.model.Item;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
/*import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;*/

import static com.ak.kmpl.app.AppConfig.GEOMETRY;
import static com.ak.kmpl.app.AppConfig.GOOGLE_BROWSER_API_KEY;
import static com.ak.kmpl.app.AppConfig.ICON;
import static com.ak.kmpl.app.AppConfig.LATITUDE;
import static com.ak.kmpl.app.AppConfig.LOCATION;
import static com.ak.kmpl.app.AppConfig.LONGITUDE;
import static com.ak.kmpl.app.AppConfig.NAME;
import static com.ak.kmpl.app.AppConfig.OK;
import static com.ak.kmpl.app.AppConfig.PLACE_ID;
import static com.ak.kmpl.app.AppConfig.PLAY_SERVICES_RESOLUTION_REQUEST;
import static com.ak.kmpl.app.AppConfig.PROXIMITY_RADIUS;
import static com.ak.kmpl.app.AppConfig.RATING;
import static com.ak.kmpl.app.AppConfig.REFERENCE;
import static com.ak.kmpl.app.AppConfig.STATUS;
import static com.ak.kmpl.app.AppConfig.SUPERMARKET_ID;
import static com.ak.kmpl.app.AppConfig.TAG;
import static com.ak.kmpl.app.AppConfig.VICINITY;
import static com.ak.kmpl.app.AppConfig.ZERO_RESULTS;

//import com.arsy.maps_library.MapRipple;
//import com.arsy.maps_library.MapRipple;
//import com.arsy.maps_library.MapRipple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ak.kmpl.R;

//@RuntimePermissions
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, ItemAdapter.ItemListener  {


    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_maps);

        }*/


    /* implements OnMapReadyCallback,
        LocationListener, ItemAdapter.ItemListener {*/

    /*@NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
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

    private static final Location TODO = null;
    private GoogleMap mMap;
    LocationManager locationManager;
    CoordinatorLayout mainCoordinatorLayout;
    String id, place_id, placeName = null, reference, icon, vicinity = null, rating;

    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    LocationManager mLocationManager;
    int PLACE_PICKER_REQUEST = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;
    private ItemAdapter mAdapter;
    private Button btnDialog;//btnView,

    public ImageView imageView;

    private Context mContext;

    public static ArrayList<Item> items = new ArrayList<>();
    public static String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (!isGooglePlayServicesAvailable()) {
            return;
        }
        setContentView(R.layout.activity_maps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


      //  MapsActivityPermissionsDispatcher.showgpsWithCheck(this);

        mainCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainCoordinatorLayout);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationSettings();
        }


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));

        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
    }

    private void showBottomSheetDialog() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter(createItems(), new ItemAdapter.ItemListener() {
            @Override
            public void onItemClick(Item item) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();


                    double lat = item.getLatitude();
                    double lng = item.getLongitude();


                    LatLng latLng = new LatLng(lat, lng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.gaspump32))
                            .title(item.getmTitle())).showInfoWindow();

                }
            }
        }));

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.setListener(null);
    }

    public List<Item> createItems() {


        // items.add(new Item(R.mipmap.ic_launcher, "Item 1"));
        items.add(new Item(R.mipmap.ic_launcher, "Item 2","","",0,0));
        items.add(new Item(R.mipmap.ic_launcher, "Item 3","","",0,0));
        items.add(new Item(R.mipmap.ic_launcher, "Item 4","","",0,0));

        return items;
    }

    @Override
    public void onItemClick(Item item) {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps_activity, menu);
//
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//         SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_search:

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showLocationSettings() {
        Snackbar snackbar = Snackbar
                .make(mainCoordinatorLayout, "Location Error: GPS Disabled!",
                        Snackbar.LENGTH_LONG)
                .setAction("Enable", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }

    private Location getLastKnownLocation() {
        //mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return TODO;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "CircularAir-Light.otf", false);
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        Location location1 = getLastKnownLocation();
//        double latitude = location1.getLatitude();//19.1774;
//        double longitude = location1.getLongitude();//72.8339;

        // LatLng latLng = new LatLng(latitude, longitude);
        // MapRipple mapRipple = new MapRipple(mMap, latLng, this);
        // mapRipple.startRippleMapAnimation();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        showCurrentLocation();

        //changes made for list view
        btnDialog = (Button) findViewById(R.id.btnDialog);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        View bottomSheet = findViewById(R.id.bottom_sheetMap);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMaps);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ItemAdapter(createItems(), this);
        recyclerView.setAdapter(mAdapter);


    }

    private void showCurrentLocation() {
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = getLastKnownLocation(); //locationManager.getLastKnownLocation(bestProvider);
        double l1= location.getLatitude();

        double l2 =  location.getLongitude();
        LatLng currentlocation = new LatLng(l1, l2);


        if (location != null) {
            onLocationChanged(location);

        } else {
            //This is what you need:
            locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
        }

        //changes for marshmallow
        /*else{
            locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
        }

        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);*/
        // onLocationChanged(location);
        //showLocationSettings();


    }
    private void loadNumber(String placeid){

        StringBuilder googlePlacesNumberUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");


        googlePlacesNumberUrl.append("placeid=").append(placeid);

        //googlePlacesDistanceUrl.append("origin=").append(latitude).append(",").append(longitude);

        //googlePlacesDistanceUrl.append("destination=").append(latitude).append(",").append(longitude);
        //changes made
        //googlePlacesUrl.append("&sensor=false");
        //changes made
        // googlePlacesUrl.append("&rankby=distance");

        // googlePlacesUrl.append("&name=pump");
        googlePlacesNumberUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);


        JsonObjectRequest request1 = new JsonObjectRequest(googlePlacesNumberUrl.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {

                        Log.i(TAG, "onResponse: Result= " + result.toString());
                        parseNumberResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(request1);
    }

    private void parseNumberResult(JSONObject result) {

        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place1 = jsonArray.getJSONObject(i);


                    if (!place1.isNull("international_phone_number")) {
                        number = place1.getString("international_phone_number");

                    } else {
                        number = "Not Available";
                    }

                    //id = place.getString(SUPERMARKET_ID);
                   // place_id = place.getString(PLACE_ID);
                   /* if((place.getJSONObject(GEOMETRY)).getDouble(rating)){
                        rating="0";
                    }else{
                    rating = (place.getString(RATING));}

//                    if (!place.isNull(RATING)) {
//                        rating = place.getString(RATING);
//
//                    } else {
//                        rating = "Not Available";
//                    }
//                    // rating = (place.getString(RATING));
//
//                    if (!place.isNull(NAME)) {
//                        placeName = place.getString(NAME);
//
//                    }
//                    if (!place.isNull(VICINITY)) {
//                        vicinity = place.getString(VICINITY);
//                    }
//                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
//                            .getDouble(LATITUDE);
//                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
//                            .getDouble(LONGITUDE);
//                    reference = place.getString(REFERENCE);
//                    icon = place.getString(ICON);

                    if ((place.getString(RATING).isEmpty()))
                    {
                        rating ="No Rating Available";

                    }else{
                    rating = (place.getString(RATING));}*/

                    Log.d("number ", "number " + number);
                    //Log.d("image ", "image URL" + icon);

//
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    LatLng latLng = new LatLng(latitude, longitude);
//
//                    markerOptions.position(latLng);
//                    markerOptions.title(placeName + " : " + vicinity);
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.gaspump32));
//
//                    mMap.addMarker(markerOptions);

//                    imageView = (ImageView) findViewById(R.id.imageView);
//                    Glide.with(this)
//                            .load(icon)
//                            .thumbnail(0.5f)
//                            .crossFade()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(imageView);

//                    Float distance = distFrom(Float.valueOf(String.valueOf(latC)),Float.valueOf(String.valueOf(lonC)),Float.valueOf(String.valueOf(latitude)),Float.valueOf(String.valueOf(longitude)));
//                    distance = Float.valueOf(String.valueOf(distance * 0.001));
//                    String distanceMap = String.format("%.2f", distance);
//                    items.add(new Item(R.drawable.petrolmarker2, placeName ,vicinity, "Rating: " + rating +"   Distance: " + distanceMap+" Kms", latitude, longitude));
//
//
//                    count = count + 1;
                }


              //  Toast.makeText(getBaseContext(), jsonArray.length() + " Petrol Pumps found!",
               //         Toast.LENGTH_LONG).show();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
               // Toast.makeText(getBaseContext(), "No Petrol Pumps found in 5KM radius!!!",
            //            Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }



    private void loadNearByPlaces(double latitude, double longitude) {
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works
        String type = "gas_station";
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        //changes made
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=").append(type);
        //googlePlacesUrl.append("&sensor=false");
        googlePlacesUrl.append("&open_now=true");
        //changes made
        // googlePlacesUrl.append("&rankby=distance");

        // googlePlacesUrl.append("&name=pump");
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);







        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {

                        Log.i(TAG, "onResponse: Result= " + result.toString());
                        parseLocationResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseLocationResult(JSONObject result) {

        //changes made
        double latitude, longitude;
        int count = 1;
        Location loc = getLastKnownLocation();
        double  latC = loc.getLatitude();
        double lonC = loc.getLongitude();




        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                mMap.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);

                    id = place.getString(SUPERMARKET_ID);
                    place_id = place.getString(PLACE_ID);
                    if((place.getJSONObject(GEOMETRY)).getBoolean(rating)){
                        rating="0";
                    }else{
                    rating = (place.getString(RATING));}

                    if (!place.isNull(RATING)) {
                        rating = place.getString(RATING);

                    } else {
                        rating = "Not Available";
                    }
                    // rating = (place.getString(RATING));

                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);

                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE);
                    reference = place.getString(REFERENCE);
                    icon = place.getString(ICON);



                    Log.d("rating ", "rating " + rating);
                    Log.d("image ", "image URL" + icon);


                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(latitude, longitude);

                    markerOptions.position(latLng);
                    markerOptions.title(placeName + " : " + vicinity);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.gaspump32));

                    mMap.addMarker(markerOptions);

//                    imageView = (ImageView) findViewById(R.id.imageView);
//                    Glide.with(this)
//                            .load(icon)
//                            .thumbnail(0.5f)
//                            .crossFade()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(imageView);

                    Float distance = distFrom(Float.valueOf(String.valueOf(latC)), Float.valueOf(String.valueOf(lonC)), Float.valueOf(String.valueOf(latitude)), Float.valueOf(String.valueOf(longitude)));
                    distance = Float.valueOf(String.valueOf(distance * 0.001));
                    String distanceMap = String.format("%.2f", distance);

                    items.add(new Item(android.R.drawable.star_on, placeName ,vicinity, "Distance: " + distanceMap+" Kms", latitude, longitude));


                    count = count + 1;
                }


                Toast.makeText(getBaseContext(), jsonArray.length() + " Petrol Pumps found!",
                        Toast.LENGTH_LONG).show();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No Petrol Pumps found in 5KM radius!!!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }

    public static float distFrom (float lat1, float lng1, float lat2, float lng2 )
    {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Float(dist * meterConversion).floatValue();
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();//19.1774;
        double longitude = location.getLongitude();//72.8339;

        LatLng latLng = new LatLng(latitude, longitude);
        //mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
        //   MapRipple mapRipple = new MapRipple(mMap, latLng, this);
        //  mapRipple.startRippleMapAnimation();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


//        loadNearByPlaces(latitude, longitude);


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
//do not delete
    @Override
    protected void onPostResume() {
        super.onPostResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // MapsActivityPermissionsDispatcher.showgpsWithCheck(this);

        mainCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainCoordinatorLayout);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationSettings();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName(),place.getRating());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                getSupportActionBar().setTitle("" + place.getName());


                //adding marker to search location
                LatLng latLng1 = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(latLng1).title("" + place.getName()));
                //   MapRipple mapRipple = new MapRipple(mMap, latLng, this);
                //  mapRipple.startRippleMapAnimation();
                ///mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }



}
