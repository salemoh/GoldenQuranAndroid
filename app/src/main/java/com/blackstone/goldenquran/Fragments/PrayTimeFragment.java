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
import android.support.annotation.RequiresApi;
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
import com.blackstone.goldenquran.api.AlarmService;
import com.blackstone.goldenquran.models.PrayModel;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
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
        ((DrawerCloser) getActivity()).moveToolbarDown();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        arrayList = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setmLastLocation() {
        if (mLastLocation != null) {
            mLatitudeADouble = mLastLocation.getLatitude();
            mLongitudeADouble = mLastLocation.getLongitude();
            Log.d("tag", "not null");
            SharedPreferencesManager.putString(getActivity(), "Latitude", mLatitudeADouble + "");
            SharedPreferencesManager.putString(getActivity(), "Longitude", mLongitudeADouble + "");
        } else if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("tag", "null have permission");

                mLatitudeADouble = Double.parseDouble(SharedPreferencesManager.getString(getActivity(), "Latitude", "0"));
                mLongitudeADouble = Double.parseDouble(SharedPreferencesManager.getString(getActivity(), "Longitude", "0"));

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

            arrayList.add(new PrayModel("fajr", fajr));
            arrayList.add(new PrayModel("dohur", dhuour));
            arrayList.add(new PrayModel("aser", aser));
            arrayList.add(new PrayModel("maghrib", maghrib));
            arrayList.add(new PrayModel("isha", isha));

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

            ArrayList counter = new ArrayList();
            SharedPreferences count = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
            for (int i = 0; i < 5; i++) {
                counter.add(count.getString(i + "", "0"));
            }

            ArrayList<String> times = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String[] s = arrayList.get(i).time.replaceAll(" ", "").split(":");
                int minutes = Integer.parseInt(counter.get(i) + "") + Integer.parseInt(s[0] + "") * 60 + Integer.parseInt(s[1]);
                int hours = minutes / 60;
                if (hours >= 24) {
                    hours = hours % 24;
                }
                minutes = minutes % 60;

                s[0] = hours + "";
                s[1] = minutes + "";
                if (s[0].length() == 1)
                    s[0] = "0" + s[0];

                times.add((Integer.parseInt(s[1]) >= 10) ? s[0] + ":" + s[1] : s[0] + ":" + "0" + s[1]);
            }

            arrayList.clear();
            arrayList.add(new PrayModel("fajr", times.get(0)));
            arrayList.add(new PrayModel("dohur", times.get(1)));
            arrayList.add(new PrayModel("aser", times.get(2)));
            arrayList.add(new PrayModel("maghrib", times.get(3)));
            arrayList.add(new PrayModel("isha", times.get(4)));

            SharedPreferences.Editor editor = getActivity().getSharedPreferences("salah", MODE_PRIVATE).edit();
            editor.putString("fajr", times.get(0));
            editor.putString("duhour", times.get(1));
            editor.putString("aser", times.get(2));
            editor.putString("maghrib", times.get(3));
            editor.putString("isha", times.get(4));
            editor.apply();

            Log.d("tag", "have the times" + arrayList.get(3).time);

            SharedPreferences pref = getActivity().getSharedPreferences("salah", Context.MODE_PRIVATE);

            String[] aFajr = pref.getString("fajr", "").split(":");
            calfager = Calendar.getInstance();
            calfager.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aFajr[0].replaceAll(" ", "")));
            calfager.set(Calendar.MINUTE, Integer.parseInt(aFajr[1].replaceAll(" ", "")));
            calfager.set(Calendar.SECOND, 0);
            calfager.set(Calendar.MILLISECOND, 0);

            String[] aDuhour = pref.getString("duhour", "").split(":");
            calduhor = Calendar.getInstance();
            calduhor.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aDuhour[0].replaceAll(" ", "")));
            calduhor.set(Calendar.MINUTE, Integer.parseInt(aDuhour[1].replaceAll(" ", "")));
            calduhor.set(Calendar.SECOND, 0);
            calduhor.set(Calendar.MILLISECOND, 0);

            String[] aAser = pref.getString("aser", "").split(":");
            calaser = Calendar.getInstance();
            calaser.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aAser[0].replaceAll(" ", "")));
            calaser.set(Calendar.MINUTE, Integer.parseInt(aAser[1].replaceAll(" ", "")));
            calaser.set(Calendar.SECOND, 0);
            calaser.set(Calendar.MILLISECOND, 0);

            String[] aMaghrib = pref.getString("maghrib", "").split(":");
            calmagreb = Calendar.getInstance();
            calmagreb.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aMaghrib[0].replaceAll(" ", "")));
            calmagreb.set(Calendar.MINUTE, Integer.parseInt(aMaghrib[1].replaceAll(" ", "")));
            calmagreb.set(Calendar.SECOND, 0);
            calmagreb.set(Calendar.MILLISECOND, 0);

            String[] aIsha = pref.getString("isha", "").split(":");
            calisha = Calendar.getInstance();
            calisha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aIsha[0].replaceAll(" ", "")));
            calisha.set(Calendar.MINUTE, Integer.parseInt(aIsha[1].replaceAll(" ", "")));
            calisha.set(Calendar.SECOND, 0);
            calisha.set(Calendar.MILLISECOND, 0);

            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), AlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            am.setExact(AlarmManager.RTC_WAKEUP, calaser.getTimeInMillis(), pendingIntent);

            AlarmManager am2 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent2 = PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), AlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            am2.setExact(AlarmManager.RTC_WAKEUP, calfager.getTimeInMillis(), pendingIntent2);

            AlarmManager am3 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent3 = PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), AlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            am3.setExact(AlarmManager.RTC_WAKEUP, calduhor.getTimeInMillis(), pendingIntent3);

            AlarmManager am4 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent4 = PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), AlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            am4.setExact(AlarmManager.RTC_WAKEUP, calmagreb.getTimeInMillis(), pendingIntent4);

            AlarmManager am5 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent5 = PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), AlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            am5.setExact(AlarmManager.RTC_WAKEUP, calisha.getTimeInMillis(), pendingIntent5);



            if (arrayList.size() <= 5) {
                Log.d("tag", "if (arrayList.size() <= 5)");
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList, getActivity()/*, counter*/);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
