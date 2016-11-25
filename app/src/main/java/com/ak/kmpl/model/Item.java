package com.ak.kmpl.model;

/**
 * Created by WDIPL27 on 8/26/2016.
 */
public class Item {

    private int mDrawableRes;
    private double latitude, longitude;
    private String mTitle;
    private String Rating;

    public String getVicinity() {
        return Vicinity;
    }

    public void setVicinity(String vicinity) {
        Vicinity = vicinity;
    }

    private String Vicinity;

    public String getRating() {
        return Rating;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }


    public Item(int drawable, String title, String vicinity, String rating, double lat, double lng) {
        mDrawableRes = drawable;
        mTitle = title;
        Vicinity = vicinity;
        Rating = rating;
        this.latitude = lat;
        this.longitude = lng;

    }


    public int getmDrawableRes() {
        return mDrawableRes;
    }

    public void setmDrawableRes(int mDrawableRes) {
        this.mDrawableRes = mDrawableRes;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }

}