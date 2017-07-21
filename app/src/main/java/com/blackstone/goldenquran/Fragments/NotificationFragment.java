package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragment extends Fragment {
    @BindView(R.id.Switch)
    Switch mSwitch;
    @BindView(R.id.SwitchTwo)
    Switch mSwitchTwo;
//    @BindView(R.id.SwitchThree)
//    Switch mSwitchThree;


    public NotificationFragment() {

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

        mSwitch.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "firstSwitch", false));
        mSwitchTwo.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "secondSwitch", false));
//        mSwitchThree.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "ThirdSwitch", false));

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesManager.putBoolean(getActivity(), "firstSwitch", b);
            }
        });

        mSwitchTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesManager.putBoolean(getActivity(), "secondSwitch", b);
            }
        });

//        mSwitchThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                SharedPreferencesManager.putBoolean(getActivity(), "ThirdSwitch", b);
//            }
//        });

    }
}
