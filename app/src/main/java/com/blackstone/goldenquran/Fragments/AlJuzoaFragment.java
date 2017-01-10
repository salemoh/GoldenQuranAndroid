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
import com.blackstone.goldenquran.adapters.AlJuzoaAdapter;
import com.blackstone.goldenquran.models.AljuzaModel;

import java.util.ArrayList;

public class AlJuzoaFragment extends Fragment {

    RecyclerView juzoaRecyclerView;

    public AlJuzoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.al_juzoa_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] array = getActivity().getResources().getStringArray(R.array.AlJuzoaArray);
        ArrayList<AljuzaModel> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            arrayList.add(new AljuzaModel(array[i]));
        }
        juzoaRecyclerView = (RecyclerView) getView().findViewById(R.id.juzoaRecyclerView);
        juzoaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        juzoaRecyclerView.setAdapter(new AlJuzoaAdapter(getActivity(), arrayList));

    }
}
