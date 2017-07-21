package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.SearchMawdoo3Adapter;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.SearchMawdoo3Model;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchMawdoo3Fragment extends Fragment {


    @BindView(R.id.searchMawdoo3Recycler)
    RecyclerView searchMawdoo3Recycler;
    Unbinder unbinder;
    static ArrayList<SearchMawdoo3Model> models;

    public SearchMawdoo3Fragment() {
    }

    public static void getData(ArrayList<SearchMawdoo3Model> mawdoo3Models) {
        models = mawdoo3Models;
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
        ((SearchMawdoo3Adapter) searchMawdoo3Recycler.getAdapter()).query(queryMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_mawdoo3, container, false);
        unbinder = ButterKnife.bind(this, view);

        searchMawdoo3Recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchMawdoo3Recycler.setAdapter(new SearchMawdoo3Adapter(getActivity(), models));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
