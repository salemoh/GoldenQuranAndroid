package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.MainListAdapter;
import com.blackstone.goldenquran.models.MainListFirstModel;
import com.blackstone.goldenquran.models.MainListModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainListFragment extends Fragment {
    RecyclerView recyclerView;
    TextView dateAndTime, hijryDate;


    public MainListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list_layout, container, false);

        dateAndTime = (TextView) view.findViewById(R.id.dateAndTime);
        hijryDate = (TextView) view.findViewById(R.id.hijryDate);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String formattedDate = df.format(c.getTime());
        dateAndTime.setText(formattedDate);

        hijryDate.setText(DateHijri.writeIslamicDate());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.mainList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] mainlist = getResources().getStringArray(R.array.MainList);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MainListFirstModel(R.drawable.star, mainlist[0], mainlist[1], mainlist[2]));
        arrayList.add(new MainListModel(R.drawable.bookmark, mainlist[3]));
        arrayList.add(new MainListModel(R.drawable.menupng, mainlist[4]));
        arrayList.add(new MainListModel(R.drawable.green_circle, mainlist[5]));
        arrayList.add(new MainListModel(R.drawable.book, mainlist[6]));
        arrayList.add(new MainListModel(R.drawable.search, mainlist[7]));
        arrayList.add(new MainListModel(R.drawable.play_button, mainlist[8]));
        arrayList.add(new MainListModel(R.drawable.bell, mainlist[9]));
        arrayList.add(new MainListModel(R.drawable.mosque, mainlist[10]));
        arrayList.add(new MainListModel(R.drawable.book_end, mainlist[11]));
        arrayList.add(new MainListModel(R.drawable.number40, mainlist[12]));
        arrayList.add(new MainListModel(R.drawable.share, mainlist[13]));
        arrayList.add(new MainListModel(R.drawable.settings, mainlist[14]));
        arrayList.add(new MainListModel(R.drawable.statistics, mainlist[15]));

        MainListAdapter mainListAdapter = new MainListAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mainListAdapter);

    }
}

class DateHijri {

    static double gmod(double n, double m) {
        return ((n % m) + m) % m;
    }

    static double[] kuwaiticalendar(boolean adjust) {
        Calendar today = Calendar.getInstance();
        int adj;
        if (adjust) {
            adj = 0;
        } else {
            adj = 1;
        }

        if (adjust) {
            int adjustmili = 1000 * 60 * 60 * 24 * adj;
            long todaymili = today.getTimeInMillis() + adjustmili;
            today.setTimeInMillis(todaymili);
        }
        double day = today.get(Calendar.DAY_OF_MONTH);
        double month = today.get(Calendar.MONTH);
        double year = today.get(Calendar.YEAR);

        double m = month + 1;
        double y = year;
        if (m < 3) {
            y -= 1;
            m += 12;
        }

        double a = Math.floor(y / 100.);
        double b = 2 - a + Math.floor(a / 4.);

        if (y < 1583)
            b = 0;
        if (y == 1582) {
            if (m > 10)
                b = -10;
            if (m == 10) {
                b = 0;
                if (day > 4)
                    b = -10;
            }
        }

        double jd = Math.floor(365.25 * (y + 4716))
                + Math.floor(30.6001 * (m + 1)) + day + b - 1524;

        b = 0;
        if (jd > 2299160) {
            a = Math.floor((jd - 1867216.25) / 36524.25);
            b = 1 + a - Math.floor(a / 4.);
        }
        double bb = jd + b + 1524;
        double cc = Math.floor((bb - 122.1) / 365.25);
        double dd = Math.floor(365.25 * cc);
        double ee = Math.floor((bb - dd) / 30.6001);
        day = (bb - dd) - Math.floor(30.6001 * ee);
        month = ee - 1;
        if (ee > 13) {
            cc += 1;
            month = ee - 13;
        }
        year = cc - 4716;

        double wd = gmod(jd + 1, 7) + 1;

        double iyear = 10631. / 30.;
        double epochastro = 1948084;
        double epochcivil = 1948085;

        double shift1 = 8.01 / 60.;

        double z = jd - epochastro;
        double cyc = Math.floor(z / 10631.);
        z = z - 10631 * cyc;
        double j = Math.floor((z - shift1) / iyear);
        double iy = 30 * cyc + j;
        z = z - Math.floor(j * iyear + shift1);
        double im = Math.floor((z + 28.5001) / 29.5);
        if (im == 13)
            im = 12;
        double id = z - Math.floor(29.5001 * im - 29);

        double[] myRes = new double[8];

        myRes[0] = day; // calculated day (CE)
        myRes[1] = month - 1; // calculated month (CE)
        myRes[2] = year; // calculated year (CE)
        myRes[3] = jd - 1; // julian day number
        myRes[4] = wd - 1; // weekday number
        myRes[5] = id; // islamic date
        myRes[6] = im - 1; // islamic month
        myRes[7] = iy; // islamic year

        return myRes;
    }

    static String writeIslamicDate() {
        String[] wdNames = {"Ahad", "Ithnin", "Thulatha", "Arbaa", "Khams",
                "Jumuah", "Sabt"};
        String[] iMonthNames = {"Muharram", "Safar", "Rabi'ul Awwal",
                "Rabi'ul Akhir", "Jumadal Ula", "Jumadal Akhira", "Rajab",
                "Sha'ban", "Ramadan", "Shawwal", "Dhul Qa'ada", "Dhul Hijja"};
        // This Value is used to give the correct day +- 1 day
        boolean dayTest = true;
        double[] iDate = kuwaiticalendar(dayTest);
        String outputIslamicDate = (int) iDate[5] + "th " + iMonthNames[(int) iDate[6]] + ", " + (int) iDate[7] + "h ";

        return outputIslamicDate;
    }
}