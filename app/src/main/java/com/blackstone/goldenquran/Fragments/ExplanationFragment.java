package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.AltafseerAdapter;
import com.blackstone.goldenquran.models.TheExplanationModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExplanationFragment extends Fragment {

    RecyclerView recyclerView;
    @BindView(R.id.flagImage)
    ImageView flagImage;
    @BindView(R.id.anyTafseer)
    TextView anyTafseer;
    @BindView(R.id.downloadTafseer)
    TextView downloadTafseer;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.altafseerRecycler)
    RecyclerView altafseerRecycler;

    public ExplanationFragment() {

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
        View view = inflater.inflate(R.layout.altafseet_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((DrawerCloser) getActivity()).moveToolbarDown();

        ArrayList<TheExplanationModel> arrayList = new ArrayList<>();
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getString(R.string.tafseerName), getString(R.string.tafseerDescription)));


        recyclerView.setAdapter(new AltafseerAdapter(getActivity(), arrayList));
    }
}
