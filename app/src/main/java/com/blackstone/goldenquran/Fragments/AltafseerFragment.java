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
import com.blackstone.goldenquran.adapters.AltafseerAdapter;
import com.blackstone.goldenquran.models.ALtafseerModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AltafseerFragment extends Fragment {

    RecyclerView recyclerView;

    public AltafseerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.altafseet_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.altafseerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<ALtafseerModel> arrayList = new ArrayList<>();
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new ALtafseerModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));


        recyclerView.setAdapter(new AltafseerAdapter(getActivity(), arrayList));
    }
}
