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
import com.blackstone.goldenquran.adapters.AlsuraAdapter;
import com.blackstone.goldenquran.models.AljuzaModel;
import com.blackstone.goldenquran.models.AlsuraModel;

import java.util.ArrayList;

public class AlSuraFragment extends Fragment {

    RecyclerView alSuraRecyclerView;

    public AlSuraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_al_sura, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        alSuraRecyclerView = (RecyclerView) getView().findViewById(R.id.alSuraRecyclerView);
        alSuraRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String[] ajzaNames = getResources().getStringArray(R.array.ajzaNames);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AljuzaModel(ajzaNames[0]));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));

        arrayList.add(new AljuzaModel(ajzaNames[1]));
        arrayList.add(new AljuzaModel(ajzaNames[2]));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AljuzaModel(ajzaNames[3]));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AljuzaModel(ajzaNames[4]));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AljuzaModel(ajzaNames[5]));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AljuzaModel(ajzaNames[6]));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));
        arrayList.add(new AlsuraModel("1", "7", getString(R.string.suraName)));


        alSuraRecyclerView.setAdapter(new AlsuraAdapter(getActivity(), arrayList));


    }
}
