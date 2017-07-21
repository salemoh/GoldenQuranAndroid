package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OnFinishOfQuranPrayFragment extends Fragment {

    DataBaseManager data;
    @BindView(R.id.onFinishQuran)
    TextView onFinishQuran;
    static String onFinish;

    public static void getData(String doaa) {
        onFinish = doaa;
    }

    public OnFinishOfQuranPrayFragment() {

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
        View view = inflater.inflate(R.layout.fragment_doaa_khatem_alquran, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DrawerCloser) getActivity()).moveToolbarDown();
        onFinishQuran.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) onFinishQuran.getTextSize()));
        onFinishQuran.setText(onFinish);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(7);
    }
}
