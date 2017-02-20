/*
 * Copyright (c) Created by Samer Al-Dawaleeb 2016.
 */

package com.blackstone.goldenquran.Fragments;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.City;
import com.blackstone.goldenquran.models.UserSettings;
import com.blackstone.goldenquran.qibla.QiblaDirectionCalculator;
import com.blackstone.goldenquran.qibla.RotatableImageView;
import com.blackstone.goldenquran.utilities.LocationHelper;
import com.blackstone.goldenquran.utilities.Utils;
import com.google.android.gms.common.ConnectionResult;

public class QiblaFragment extends Fragment {
    private Sensor mOrientationSensor;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private float mHeading;
    private float NONE = -1.0f;
    private float mQiblaAngle = NONE;
    private RotatableImageView compassImageView;
    private RotatableImageView compassArrowImageView;

    private LocationHelper mLocationHelper;

    public QiblaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLocationHelper = LocationHelper.newInstance(getActivity(), mLocationListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_qibla, container, false);
        bindViews(view);
        return view;
    }
    private void bindViews(View view) {
        // TODO: 23/07/2016 replace it with butterknife or with data binding model
        compassImageView = (RotatableImageView) view.findViewById(R.id.qibla_compass_imv);
        compassArrowImageView = (RotatableImageView) view.findViewById(R.id.qibla_compass_arrow_imv);
    }

    private LocationHelper.LocationListener mLocationListener = new LocationHelper.LocationListener() {

        @Override
        public void onStartLocationLocate() {
            Log.d("qibla", "onStartLocationLocate");
        }

        @Override
        public void onLocationFound(Location location) {
            Log.d("qibla", "onLocationFound");

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            mQiblaAngle = (float) QiblaDirectionCalculator.
                    getQiblaDirectionFromNorth(latitude, longitude);
            updateQiblaDirections();
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.w("qibla", "onConnectionFailed");

            mLocationHelper.showErrorDialog(connectionResult.getErrorCode(),
                    getActivity());
        }
    };
    private void updateQiblaDirections() {
        if (mQiblaAngle != NONE) {
            final double qiblaRotation = mHeading - mQiblaAngle;
            compassImageView.rotate(-mHeading);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    compassArrowImageView.rotate(-qiblaRotation);
                }
            }, 200);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isCompassAvailable(getActivity())) {
            mSensorManager.registerListener(sensorEventListener, mOrientationSensor,
                    SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(onPhoneUplistenerShowCamera,mAccelerometerSensor,mSensorManager.SENSOR_DELAY_GAME);
            if (mQiblaAngle == NONE) {
                City city = UserSettings.getInstance().getUserCity();
                if (null != city) {
                    mQiblaAngle = (float) QiblaDirectionCalculator.
                            getQiblaDirectionFromNorth(city.getLocation().getLatitude(), city.getLocation().getLongitude());
                    updateQiblaDirections();

                    mLocationHelper.detectLocation(getActivity());
                }
                else {
                    if (LocationHelper.isGpsEnabled(getActivity())) {
                        mLocationHelper.detectLocation(getActivity());
                    }
//                    else {
                //// TODO: enable location service enable dialog
//                    }
                }
            }
//        } else {
//            ErrorDialog.showDialog(getSupportFragmentManager(),
//                    getString(R.string.error_compass_is_not_available), ErrorDialog.DIALOG_REQUEST_CODE_NO_COMPASS);
        }
    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            mHeading = event.values[0];
            updateQiblaDirections();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    SensorEventListener onPhoneUplistenerShowCamera = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onPause() {
        mLocationHelper.onPause();
        mSensorManager.unregisterListener(sensorEventListener);

        super.onPause();
    }
}
