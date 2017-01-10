package com.blackstone.goldenquran.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackstone.goldenquran.R;

public class NavigationDrawerFragment extends Fragment {

    public static NavigationDrawerFragment newInstance() {
        return new NavigationDrawerFragment();
    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_list_layout, container, false);
    }

}
