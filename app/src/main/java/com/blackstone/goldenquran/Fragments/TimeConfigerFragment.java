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
import com.blackstone.goldenquran.models.SalahModel;

import java.util.ArrayList;


public class TimeConfigerFragment extends Fragment {

    RecyclerView recyclerView;

    public TimeConfigerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        recyclerView = (RecyclerView) view.findViewById(R.id.configRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SharedPreferences prefs = getActivity().getSharedPreferences("salah", Context.MODE_PRIVATE);

        ArrayList<SalahModel> arrayList = new ArrayList<>();
        arrayList.add(new SalahModel("fajr", prefs.getString("fajr", "00:00")));
        arrayList.add(new SalahModel("duhor", prefs.getString("duhour", "00:00")));
        arrayList.add(new SalahModel("aser", prefs.getString("aser", "00:00")));
        arrayList.add(new SalahModel("maghrib", prefs.getString("maghrib", "00:00")));
        arrayList.add(new SalahModel("isha", prefs.getString("isha", "00:00")));

        ArrayList counter = new ArrayList();
        SharedPreferences count = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
        for (int i = 0; i < 5; i++){
            counter.add(count.getString(i +"", "0"));
        }


        recyclerView.setAdapter(new ConfigureAdapter(getActivity(), arrayList, counter));
    }
}
