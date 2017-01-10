package com.blackstone.goldenquran.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;


public class PlayerFragment extends Fragment {
    ImageView imageView;
    Toolbar toolbar;
    TextView min, max;

    CrystalRangeSeekbar crystalRangeSeekbar;

    public PlayerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_settings_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.playerSettings);

        crystalRangeSeekbar = (CrystalRangeSeekbar) getView().findViewById(R.id.rangeSeekbar1);
        min = (TextView) getView().findViewById(R.id.minTextView);
        max = (TextView) getView().findViewById(R.id.maxTextView);

        crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                min.setText(
                        ""+minValue);
                max.setText(maxValue + "");
            }
        });


        imageView = (ImageView) getView().findViewById(R.id.playerLeftArrow);
        if (getResources().getBoolean(R.bool.is_right_to_left))
            imageView.setImageResource(R.drawable.left_arrow);
        else
            imageView.setImageResource(R.drawable.right_arrow);
    }
}
