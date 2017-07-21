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
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.ui.DrawerCloser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlJuzoaFragment extends Fragment {

    @BindView(R.id.juzoaRecyclerView)
    RecyclerView juzoaRecyclerView;

    ArrayList<TableOfContents> data;


    public AlJuzoaFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void query(QueryMessage queryMessage) {
        ((AlJuzoaAdapter) juzoaRecyclerView.getAdapter()).query(queryMessage);
    }

    public void sendTOCData(ArrayList<TableOfContents> tableOfContentses) {
        data = tableOfContentses;
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
        String[] array = getActivity().getResources().getStringArray(R.array.fahrasAjzaa);
        ArrayList<AljuzaModel> arrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            arrayList.add(new AljuzaModel(array[i], i));
        }
        juzoaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        juzoaRecyclerView.setAdapter(new AlJuzoaAdapter(getActivity(), arrayList, data));

    }
}
