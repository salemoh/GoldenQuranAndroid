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
import com.blackstone.goldenquran.adapters.AlquraaAdapter;
import com.blackstone.goldenquran.models.ReadersModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadersFragment extends Fragment {
    @BindView(R.id.alquraaRecycler)
    RecyclerView recyclerView;

    public ReadersFragment() {

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
        View view = inflater.inflate(R.layout.alquraa_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DrawerCloser) getActivity()).moveToolbarDown();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] shakeNames = getResources().getStringArray(R.array.ShakeNames);

        ArrayList<ReadersModel> arrayList = new ArrayList<>();
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[0]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[1]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[2]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[0]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[1]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[2]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[0]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[1]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[2]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[2]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[2]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[0]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[1]));
        arrayList.add(new ReadersModel(R.drawable.reader_abdelbaset, shakeNames[2]));


        recyclerView.setAdapter(new AlquraaAdapter(getActivity(), arrayList));

    }
}
