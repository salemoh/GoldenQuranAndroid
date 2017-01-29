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
import com.blackstone.goldenquran.models.TheExplanationModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsPickTafseerFragment extends Fragment {


    @BindView(R.id.altafseerRecycler)
    RecyclerView altafseerRecycler;

    public SettingsPickTafseerFragment() {

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

        altafseerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<TheExplanationModel> arrayList = new ArrayList<>();
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        arrayList.add(new TheExplanationModel(getActivity().getResources().getString(R.string.tafseerName), getActivity().getResources().getString(R.string.tafseerDescription)));
        altafseerRecycler.setAdapter(new AltafseerAdapter(getActivity(), arrayList));

    }
}
