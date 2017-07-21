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
import com.blackstone.goldenquran.adapters.AlquraaAdapter;
import com.blackstone.goldenquran.models.RecitationModel;
import com.blackstone.goldenquran.ui.DrawerCloser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReadersFragment extends Fragment {
    @BindView(R.id.alquraaRecycler)
    RecyclerView recyclerView;

    static ArrayList<RecitationModel> models;

    public ReadersFragment() {

    }

    public static void getData(ArrayList<RecitationModel> data){
        models = data;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://download.quranicaudio.com/")
//                .build();
//
//        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
//
//        Call<ArrayList<RecitationModel>> request = retrofitInterface.getReaders();
//
//        request.enqueue(new Callback<ArrayList<RecitationModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<RecitationModel>> call, Response<ArrayList<RecitationModel>> response) {
//                models = response.body();
//                processData();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<RecitationModel>> call, Throwable t) {
//
//            }
//        });

    }

    private void processData() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alquraa_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DrawerCloser) getActivity()).moveToolbarDown();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerView.setAdapter(new AlquraaAdapter(getActivity(), models));

    }
}
