package com.blackstone.goldenquran.models;

import android.location.Location;

/**
 * Created by SamerGigaByte on 03/08/2016.
 */
public class City {
    private Location mLocation;
    public City(Location location){
        mLocation=location;
    }
    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }



}
