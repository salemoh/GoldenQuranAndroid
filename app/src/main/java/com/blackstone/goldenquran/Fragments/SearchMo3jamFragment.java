package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.SearchMo3jamAdapter;
import com.blackstone.goldenquran.models.Mo3jamModel;
import com.blackstone.goldenquran.models.QueryMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchMo3jamFragment extends Fragment {


    @BindView(R.id.searchMo3jamRecycler)
    RecyclerView searchMo3jamRecycler;
    Unbinder unbinder;
    static ArrayList<Mo3jamModel> mo3jamModels;

    public SearchMo3jamFragment() {
    }

    public static void getData(ArrayList<Mo3jamModel> list) {
        mo3jamModels = list;
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
        ((SearchMo3jamAdapter) searchMo3jamRecycler.getAdapter()).query(queryMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_mo3jam, container, false);
        unbinder = ButterKnife.bind(this, view);

        searchMo3jamRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchMo3jamRecycler.setAdapter(new SearchMo3jamAdapter(getActivity(), mo3jamModels));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
