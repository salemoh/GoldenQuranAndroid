/*
 * Copyright (c) Created by Samer Al-Dawaleeb 2016.
 */

package com.blackstone.goldenquran.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.TimeZone;

public class LocationHelper {
    private static final String TAG = "LocationHelper";


    public interface LocationListener {
        void onStartLocationLocate();

        void onLocationFound(Location location);

        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public static final int REQUEST_CODE = 6587;

    public static LocationHelper newInstance(Context context,
                                             LocationListener locationListener) {
        return new LocationHelper(context, locationListener);
    }

    private Activity tempActivity;
    private GoogleApiClient mLocationClient;
    private LocationListener mLocationListener;

    private LocationHelper(Context context, LocationListener locationListener) {
        mLocationListener = locationListener;
        mLocationClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();

    }

    public void setLocationListener(LocationListener locationListener) {
        mLocationListener = locationListener;
    }

    public void detectLocation(FragmentActivity fragmentActivity) throws IllegalArgumentException {
        if (checkPlayService(fragmentActivity)) {
            if (mLocationClient.isConnected()) {
                onPause();
            }

            mLocationClient.connect();
            tempActivity = fragmentActivity;
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void onResume(FragmentActivity fragmentActivity) throws IllegalArgumentException {
        detectLocation(fragmentActivity);
    }

    public void onPause() {
        tempActivity = null;
        mLocationClient.disconnect();
    }

    public boolean showErrorDialog(int errorCode, FragmentActivity fragmentActivity) {
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
                fragmentActivity, REQUEST_CODE);
        if (errorDialog == null) {
            return false;
        }

        ErrorDialogFragment errorFragment = new ErrorDialogFragment();
        errorFragment.setDialog(errorDialog);
        errorFragment.show(fragmentActivity.getSupportFragmentManager(), TAG);
        return true;
    }

    private boolean checkPlayService(FragmentActivity fragmentActivity)
            throws IllegalArgumentException {
        int errorCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(fragmentActivity);
        if (ConnectionResult.SUCCESS != errorCode) {
            if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
                Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                        errorCode, fragmentActivity, REQUEST_CODE);

                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(fragmentActivity.getSupportFragmentManager(),
                        TAG);

                return false;
            } else {
                throw new IllegalArgumentException("Play Service not supported in this device");
            }
        } else {
            return true;
        }
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (null == lm) {
            return false;
        }

        boolean gpsEnabled = false;
        try {
            gpsEnabled = lm
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        Log.d(TAG, "isGpsEnabled " + gpsEnabled);
        return gpsEnabled;
    }

    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    if (connectionResult.hasResolution()) {
                        try {
                            connectionResult.startResolutionForResult(tempActivity, REQUEST_CODE);
                            return;
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }

                    mLocationListener.onConnectionFailed(connectionResult);
                }
            };

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {

                @Override
                public void onConnected(Bundle bundle) {
                    if (ActivityCompat.checkSelfPermission(tempActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(tempActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            mLocationClient);

                    if (null != lastLocation) {
                        long locationTime = lastLocation.getTime();
                        TimeZone tz = TimeZone.getDefault();
                        locationTime += tz.getRawOffset() + tz.getDSTSavings();

                        long locationAge = System.currentTimeMillis() - locationTime;
                        if (locationAge < 30 * 60 * 1000) {
                            // less than 30min
                            mLocationListener.onLocationFound(lastLocation);
                            return;
                        }
                    }

                    mLocationListener.onStartLocationLocate();
                    LocationRequest request = LocationRequest.create();
                    request.setPriority(LocationRequest.PRIORITY_LOW_POWER)
                            .setNumUpdates(1);

                    LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient,
                            request, locationListener);

                    //mLocationClient.requestLocationUpdates(request, locationListener);
                }

                @Override
                public void onConnectionSuspended(int i) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, locationListener);

                }


            };

    private com.google.android.gms.location.LocationListener locationListener = new com.google.android.gms.location.LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, locationListener);

            mLocationListener.onLocationFound(location);
        }
    };

    /**
     * @hide
     */
    public static class ErrorDialogFragment extends DialogFragment {

        private Dialog mDialog;

        public ErrorDialogFragment() {
            super();
        }

        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
