package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsPrayTimeFragment extends Fragment {

    @BindView(R.id.Switch)
    android.widget.Switch Switch;
    @BindView(R.id.notificationType)
    TextView notificationType;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.SwitchTwo)
    android.widget.Switch SwitchTwo;
    @BindView(R.id.notificationTypeTwo)
    TextView notificationTypeTwo;
    @BindView(R.id.descriptionTwo)
    TextView descriptionTwo;
    @BindView(R.id.SwitchThree)
    android.widget.Switch SwitchThree;
    @BindView(R.id.notificationTypeThree)
    TextView notificationTypeThree;
    @BindView(R.id.descriptionThree)
    TextView descriptionThree;

    public SettingsPrayTimeFragment() {

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
        View view = inflater.inflate(R.layout.notification, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();
    }
}
