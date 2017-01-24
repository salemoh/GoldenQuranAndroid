package com.blackstone.goldenquran.Fragments;


import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blackstone.goldenquran.GetTimeClasses.AthanTime;
import com.blackstone.goldenquran.GetTimeClasses.AthanTimeCalculator;
import com.blackstone.goldenquran.GetTimeClasses.TimeZoneUtil;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.RecyclerAdapter;
import com.blackstone.goldenquran.api.Alarm;
import com.blackstone.goldenquran.models.PrayModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class PrayTimeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double mLatitudeADouble, mLongitudeADouble;
    String fajr, dhuour, aser, maghrib, isha;
    ArrayList<PrayModel> arrayList;
    Intent intent;
    Calendar calfager, calduhor, calaser, calmagreb, calisha;
    @BindView(R.id.salahTimeList)
    RecyclerView recyclerView;
    @BindView(R.id.textOnFaill)
    TextView textView;

    public PrayTimeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_configuration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        arrayList = new ArrayList<>();
        intent = new Intent(getActivity(), Alarm.class);
    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), connectionResult + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            setmLastLocation();
        }
    }

    public void setmLastLocation() {
        if (mLastLocation != null) {
            mLatitudeADouble = mLastLocation.getLatitude();
            mLongitudeADouble = mLastLocation.getLongitude();
            Log.d("tag", "not null");
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("pref", MODE_PRIVATE).edit();
            editor.putString("Latitude", mLatitudeADouble + "");
            editor.putString("Longitude", mLongitudeADouble + "");
            editor.apply();

        } else if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("tag", "null have permission");

                SharedPreferences prefs = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
                mLatitudeADouble = Double.parseDouble(prefs.getString("Latitude", "0"));
                mLongitudeADouble = Double.parseDouble(prefs.getString("Longitude", "0"));

            }
        }

        if (mLongitudeADouble == 0 && mLatitudeADouble == 0) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            Log.d("tag", "+=0");
        } else {
            AthanTime athanTime = new AthanTimeCalculator().getPrayerTimes(Calendar.getInstance(), mLatitudeADouble, mLongitudeADouble, TimeZoneUtil.getTimeZone(new Date()));

            fajr = athanTime.getFajr().getHour() + ":" + athanTime.getFajr().getMinute();
            dhuour = athanTime.getDhuhr().getHour() + ":" + athanTime.getDhuhr().getMinute();
            aser = athanTime.getAsr().getHour() + ":" + athanTime.getAsr().getMinute();
            maghrib = athanTime.getMaghrib().getHour() + ":" + athanTime.getMaghrib().getMinute();
            isha = athanTime.getIsha().getHour() + ":" + athanTime.getIsha().getMinute();

            Log.d("tag", "have the times" + fajr);

            arrayList.add(new PrayModel("fajr", fajr));
            arrayList.add(new PrayModel("dohur", dhuour));
            arrayList.add(new PrayModel("aser", aser));
            arrayList.add(new PrayModel("maghrib", maghrib));
            arrayList.add(new PrayModel("isha", isha));

            ArrayList counter = new ArrayList();
            SharedPreferences count = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
            for (int i = 0; i < 5; i++) {
                counter.add(count.getString(i + "", "0"));
            }

            if (fajr != null) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("salah", MODE_PRIVATE).edit();

                editor.putString("fajr", fajr);
                editor.putString("duhour", dhuour);
                editor.putString("maghrib", maghrib);
                editor.putString("aser", aser);
                editor.putString("isha", isha);
                editor.apply();
                Log.d("tag", "not null fajer");
            }

            SharedPreferences pref = getActivity().getSharedPreferences("salah", Context.MODE_PRIVATE);

            String[] aFajr = pref.getString("fajr", "").split(":");
            calfager = Calendar.getInstance();
            calfager.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aFajr[0].replaceAll(" ", "")));
            calfager.set(Calendar.MINUTE, Integer.parseInt(aFajr[1].replaceAll(" ", "")));

            String[] aDuhour = pref.getString("duhour", "").split(":");
            calduhor = Calendar.getInstance();
            calduhor.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aDuhour[0].replaceAll(" ", "")));
            calduhor.set(Calendar.MINUTE, Integer.parseInt(aDuhour[1].replaceAll(" ", "")));

            String[] aAser = pref.getString("aser", "").split(":");
            calaser = Calendar.getInstance();
            calaser.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aAser[0].replaceAll(" ", "")));
            calaser.set(Calendar.MINUTE, Integer.parseInt(aAser[1].replaceAll(" ", "")));

            String[] aMaghrib = pref.getString("maghrib", "").split(":");
            calmagreb = Calendar.getInstance();
            calmagreb.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aMaghrib[0].replaceAll(" ", "")));
            calmagreb.set(Calendar.MINUTE, Integer.parseInt(aMaghrib[1].replaceAll(" ", "")));

            String[] aIsha = pref.getString("isha", "").split(":");
            calisha = Calendar.getInstance();
            calisha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aIsha[0].replaceAll(" ", "")));
            calisha.set(Calendar.MINUTE, Integer.parseInt(aIsha[1].replaceAll(" ", "")));


            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

            am.setRepeating(AlarmManager.RTC_WAKEUP, calfager.getTimeInMillis(), 0, pendingIntent);
            am.set(AlarmManager.RTC, calduhor.getTimeInMillis(), pendingIntent);
            am.set(AlarmManager.RTC, calaser.getTimeInMillis(), pendingIntent);
            am.set(AlarmManager.RTC, calmagreb.getTimeInMillis(), pendingIntent);
            am.set(AlarmManager.RTC, calisha.getTimeInMillis(), pendingIntent);

            if (arrayList.size() <= 5) {
                Log.d("tag", "if (arrayList.size() <= 5)");
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList, getActivity(), counter);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setmLastLocation();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

}
