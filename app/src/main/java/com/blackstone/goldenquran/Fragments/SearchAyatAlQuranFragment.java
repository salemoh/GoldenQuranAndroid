package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.adapters.SearchAyatAlQuranAdapter;
import com.blackstone.goldenquran.models.QueryMessage;
import com.blackstone.goldenquran.models.QuranPageTextModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchAyatAlQuranFragment extends Fragment {


    @BindView(R.id.searchAyatAlQuranRecycler)
    RecyclerView searchAyatAlQuranRecycler;
    static ArrayList<QuranPageTextModel> models;
    Unbinder unbinder;

    public SearchAyatAlQuranFragment() {

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
        ((SearchAyatAlQuranAdapter) searchAyatAlQuranRecycler.getAdapter()).query(queryMessage);
    }

    public static void getData(ArrayList<QuranPageTextModel> modelsAyat) {
        models = modelsAyat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_ayat_al_quran, container, false);
        unbinder = ButterKnife.bind(this, view);

        searchAyatAlQuranRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAyatAlQuranRecycler.setAdapter(new SearchAyatAlQuranAdapter(getActivity(), models));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
