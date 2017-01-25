package com.blackstone.goldenquran.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.ConfigureAdapter;
import com.blackstone.goldenquran.models.PrayModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PrayTimeConfigerFragment extends Fragment {

    @BindView(R.id.configRecyclerView)
    RecyclerView recyclerView;

    public PrayTimeConfigerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

        SharedPreferences prefs = getActivity().getSharedPreferences("salah", Context.MODE_PRIVATE);

        ArrayList<PrayModel> arrayList = new ArrayList<>();
        arrayList.add(new PrayModel("fajr", prefs.getString("fajr", "00:00")));
        arrayList.add(new PrayModel("duhor", prefs.getString("duhour", "00:00")));
        arrayList.add(new PrayModel("aser", prefs.getString("aser", "00:00")));
        arrayList.add(new PrayModel("maghrib", prefs.getString("maghrib", "00:00")));
        arrayList.add(new PrayModel("isha", prefs.getString("isha", "00:00")));

        ArrayList counter = new ArrayList();
        SharedPreferences count = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
        for (int i = 0; i < 5; i++) {
            counter.add(count.getString(i + "", "0"));
        }


        recyclerView.setAdapter(new ConfigureAdapter(getActivity(), arrayList, counter));
    }
}
