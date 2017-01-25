package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.DownloadSuraAdapter;
import com.blackstone.goldenquran.models.DownloadSuraModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadSuraFragment extends Fragment {

    @BindView(R.id.downloadSuraImage)
    ImageView downloadSuraImage;
    @BindView(R.id.shakeNameSuraDownload)
    TextView shakeNameSuraDownload;
    @BindView(R.id.downloadSuraImageAll)
    ImageView downloadSuraImageAll;
    @BindView(R.id.downloadAll)
    TextView downloadAll;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.downloadSuraRecycler)
    RecyclerView recyclerView;

    public DownloadSuraFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_sura_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

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
