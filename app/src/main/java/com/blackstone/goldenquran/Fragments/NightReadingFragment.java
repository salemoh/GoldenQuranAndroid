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
import com.blackstone.goldenquran.models.QuranPageTextModel;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NightReadingFragment extends Fragment {

    @BindView(R.id.nightReadingTextOne)
    TextView nightReadingTextOne;
    @BindView(R.id.nightReadingTextTwo)
    TextView nightReadingTextTwo;
    @BindView(R.id.nightReadingTextThree)
    TextView nightReadingTextThree;
    @BindView(R.id.nightReadingTextFour)
    TextView nightReadingTextFour;
    @BindView(R.id.nightReadingTextFive)
    TextView nightReadingTextFive;
    @BindView(R.id.nightReadingTextSix)
    TextView nightReadingTextSix;
    @BindView(R.id.nightReadingTextSeven)
    TextView nightReadingTextSeven;

    Unbinder unBinder;
    static ArrayList<QuranPageTextModel> data;

    public NightReadingFragment() {

    }

    public static void getData(ArrayList<QuranPageTextModel> text) {
        data = text;
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

        View view = inflater.inflate(R.layout.fragment_night_reading, container, false);
        unBinder = ButterKnife.bind(this, view);
        ((DrawerCloser) getActivity()).moveToolbarDown();
        String alFalaq = "";
        String alMulk = "";
        String alEkhlas = "";
        String alNas = "";

        nightReadingTextOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));
        nightReadingTextTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));
        nightReadingTextThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));
        nightReadingTextFour.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));
        nightReadingTextFive.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));
        nightReadingTextSix.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));
        nightReadingTextSeven.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) nightReadingTextOne.getTextSize()));

        if (!data.isEmpty() && data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getSurah() == 2 && data.get(i).getAyah() == 255) {
                    nightReadingTextOne.setText(data.get(i).getQuranText() + "\n\n");
                } else if (data.get(i).getSurah() == 2 && data.get(i).getAyah() == 285) {
                    nightReadingTextTwo.setText(data.get(i).getQuranText() + "\n\n");
                } else if (data.get(i).getSurah() == 2 && data.get(i).getAyah() == 286) {
                    nightReadingTextThree.setText(data.get(i).getQuranText() + "\n\n");
                } else if (data.get(i).getSurah() == 67) {
                    alMulk += data.get(i).getQuranText() + "\n\n";
                } else if (data.get(i).getSurah() == 112) {
                    alEkhlas += data.get(i).getQuranText() + "\n\n";
                } else if (data.get(i).getSurah() == 113) {
                    alFalaq += data.get(i).getQuranText() + "\n\n";
                } else if (data.get(i).getSurah() == 114) {
                    alNas += data.get(i).getQuranText() + "\n\n";
                }
            }

            nightReadingTextFour.setText(alMulk);
            nightReadingTextFive.setText(alEkhlas);
            nightReadingTextSix.setText(alFalaq);
            nightReadingTextSeven.setText(alNas);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(-1);
    }
}
