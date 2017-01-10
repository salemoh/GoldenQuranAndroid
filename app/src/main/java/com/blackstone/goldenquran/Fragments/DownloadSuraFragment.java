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
import com.blackstone.goldenquran.adapters.DownloadSuraAdapter;
import com.blackstone.goldenquran.models.DownloadSuraModel;

import java.util.ArrayList;

public class DownloadSuraFragment extends Fragment {
    RecyclerView recyclerView;

    public DownloadSuraFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.download_sura_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.downloadSuraRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<DownloadSuraModel> arrayList = new ArrayList<>();
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));
        arrayList.add(new DownloadSuraModel(getString(R.string.downloadSura)));

        recyclerView.setAdapter(new DownloadSuraAdapter(getActivity(), arrayList));


    }
}
