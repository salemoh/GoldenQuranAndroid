package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.PrayTimeSettingsRecyclerAdapter;
import com.blackstone.goldenquran.models.PrayTimeSettingsItemModel;
import com.blackstone.goldenquran.models.PrayTimeSettingsTitleModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrayTimeSettingsFragment extends Fragment {


    @BindView(R.id.settingRecyclerView)
    RecyclerView settingRecyclerView;

    public PrayTimeSettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pray_time_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PrayTimeSettingsTitleModel("طريقه الحساب"));
        arrayList.add(new PrayTimeSettingsItemModel("الهيئه العامه المصريه"));
        arrayList.add(new PrayTimeSettingsItemModel("كراتشي الشافعي"));
        arrayList.add(new PrayTimeSettingsItemModel("كراتشي حنفي"));
        arrayList.add(new PrayTimeSettingsItemModel("شمال امريكا"));
        arrayList.add(new PrayTimeSettingsItemModel("ام القرى رمضان"));
        arrayList.add(new PrayTimeSettingsItemModel("رابطه العالم الاسلامي"));
        arrayList.add(new PrayTimeSettingsItemModel("ام القرى"));
        arrayList.add(new PrayTimeSettingsItemModel("تثبيت صلاه العشاء"));
        arrayList.add(new PrayTimeSettingsItemModel("الهيئهة المصرية الجديدة"));
        arrayList.add(new PrayTimeSettingsItemModel("لجنه موقع رؤي"));
        arrayList.add(new PrayTimeSettingsTitleModel("المذهب"));
        arrayList.add(new PrayTimeSettingsItemModel("شافعي,مالكي,حنبلي"));
        arrayList.add(new PrayTimeSettingsItemModel("حنفي"));
        arrayList.add(new PrayTimeSettingsTitleModel("صوت الاذان"));
        arrayList.add(new PrayTimeSettingsItemModel("صوت1"));
        arrayList.add(new PrayTimeSettingsItemModel("صوت2"));
        arrayList.add(new PrayTimeSettingsItemModel("صوت3"));
        arrayList.add(new PrayTimeSettingsItemModel("صوت4"));
        arrayList.add(new PrayTimeSettingsItemModel("صوت5"));
        arrayList.add(new PrayTimeSettingsItemModel("صوت6"));


        settingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        settingRecyclerView.setAdapter(new PrayTimeSettingsRecyclerAdapter(getActivity(), arrayList));
    }
}
