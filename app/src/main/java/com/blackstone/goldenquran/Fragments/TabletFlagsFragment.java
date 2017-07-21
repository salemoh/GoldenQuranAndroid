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
import com.blackstone.goldenquran.adapters.TabletFlagAdapter;
import com.blackstone.goldenquran.models.TabletFlagModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabletFlagsFragment extends Fragment {


    @BindView(R.id.flagsRecycler)
    RecyclerView flagsRecycler;

    public TabletFlagsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablet_flags, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ((DrawerCloser) getActivity()).moveToolbarDown();

        ArrayList<TabletFlagModel> arrayList = new ArrayList<>();
//        arrayList.add(new TabletFlagModel(R.drawable.sa, "المملكه العربيه السعوديه"));
//        arrayList.add(new TabletFlagModel(R.drawable.al, "المجر"));
        arrayList.add(new TabletFlagModel(R.drawable.flag_of_germany, "المانيا"));
        arrayList.add(new TabletFlagModel(R.drawable.us, "الولايات المتحدة الامريكية"));
        arrayList.add(new TabletFlagModel(R.drawable.ba, "البوسنه و الهرسك"));
        arrayList.add(new TabletFlagModel(R.drawable.cn, "الصين"));
        arrayList.add(new TabletFlagModel(R.drawable.fr, "فرنسا"));
//        arrayList.add(new TabletFlagModel(R.drawable.in, "اندونيسيا"));
        arrayList.add(new TabletFlagModel(R.drawable.ir, "ايران"));
        arrayList.add(new TabletFlagModel(R.drawable.ku, "كردستام"));
//        arrayList.add(new TabletFlagModel(R.drawable.pk, "باكستان"));
        arrayList.add(new TabletFlagModel(R.drawable.ru, "روسيا"));
        arrayList.add(new TabletFlagModel(R.drawable.tr, "تركيا"));
        arrayList.add(new TabletFlagModel(R.drawable.swedish_flag, "السويد"));
        arrayList.add(new TabletFlagModel(R.drawable.spain_flag, "اسبانيا"));


        flagsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        flagsRecycler.setAdapter(new TabletFlagAdapter(getActivity(), arrayList));
    }
}
