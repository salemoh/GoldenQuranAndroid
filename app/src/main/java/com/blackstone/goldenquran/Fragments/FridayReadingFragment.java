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
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FridayReadingFragment extends Fragment {


    @BindView(R.id.fridayReadingText)
    TextView fridayReadingText;
    Unbinder unbinder;
    static String data;

    public static void getData(String text) {
        data = text;
    }

    public FridayReadingFragment() {

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
        View view = inflater.inflate(R.layout.fragment_friday_reading, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((DrawerCloser) getActivity()).moveToolbarDown();
        fridayReadingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) fridayReadingText.getTextSize()));
        fridayReadingText.setText(data);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(0);
    }
}
