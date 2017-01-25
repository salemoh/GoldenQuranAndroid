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
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlJuzoaFragment extends Fragment {

    @BindView(R.id.juzoaRecyclerView)
    RecyclerView juzoaRecyclerView;

    public AlJuzoaFragment() {

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

        View view = inflater.inflate(R.layout.al_juzoa_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();
        String[] array = getActivity().getResources().getStringArray(R.array.AlJuzoaArray);
        ArrayList<AljuzaModel> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            arrayList.add(new AljuzaModel(array[i]));
        }
        juzoaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        juzoaRecyclerView.setAdapter(new AlJuzoaAdapter(getActivity(), arrayList));

    }
}
