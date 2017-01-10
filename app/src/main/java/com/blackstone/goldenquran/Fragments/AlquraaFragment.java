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
import com.blackstone.goldenquran.models.AlquraaModel;

import java.util.ArrayList;

public class AlquraaFragment extends Fragment {
    RecyclerView recyclerView;

    public AlquraaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alquraa_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.alquraaRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] shakeNames = getResources().getStringArray(R.array.ShakeNames);

        ArrayList<AlquraaModel> arrayList = new ArrayList<>();
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[0]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[1]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[2]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[0]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[1]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[2]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[0]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[1]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[2]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[0]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[1]));
        arrayList.add(new AlquraaModel(R.drawable.abdelbasetjpg, shakeNames[2]));


        recyclerView.setAdapter(new AlquraaAdapter(getActivity(), arrayList));

    }
}
