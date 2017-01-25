package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationDrawerFragment extends Fragment {

    @BindView(R.id.hijryDate)
    TextView hijryDate;
    @BindView(R.id.editImageView)
    ImageView editImageView;
    @BindView(R.id.salahTime)
    TextView salahTime;
    @BindView(R.id.dateAndTime)
    TextView dateAndTime;
    @BindView(R.id.mainList)
    RecyclerView mainList;

    public static NavigationDrawerFragment newInstance() {
        return new NavigationDrawerFragment();
    }

    public NavigationDrawerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_list_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();
    }
}
