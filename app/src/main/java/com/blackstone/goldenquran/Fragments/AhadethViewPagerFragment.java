package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AhadethViewPagerFragment extends Fragment {


    @BindView(R.id.ahadeethLeftArrow)
    ImageView ahadeethLeftArrow;
    @BindView(R.id.ahadeethTitle)
    TextView ahadeethTitle;
    @BindView(R.id.ahadeethRightArrow)
    ImageView ahadeethRightArrow;
    @BindView(R.id.ahadeethText)
    TextView ahadeethText;

    public AhadethViewPagerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ahadeeth_viewpager_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
