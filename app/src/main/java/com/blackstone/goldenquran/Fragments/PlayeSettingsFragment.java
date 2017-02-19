package com.blackstone.goldenquran.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.PlayerService;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlayeSettingsFragment extends Fragment {

    @BindView(R.id.maxTextView)
    TextView max;
    @BindView(R.id.minTextView)
    TextView min;
    @BindView(R.id.playerLeftArrow)
    ImageView imageView;
    @BindView(R.id.rangeSeekbar1)
    CrystalRangeSeekbar crystalRangeSeekbar;
    @BindView(R.id.onFinishRAdioGroup)
    RadioGroup onFinishRAdioGroup;
    @BindView(R.id.moveToNextPageSwitch)
    Switch moveToNextPageSwitch;
    @BindView(R.id.basmalahSwitch)
    Switch basmalahSwitch;
    @BindView(R.id.saoutSwitch)
    Switch voiceSwitch;
    @BindView(R.id.playerShakeRelative)
    RelativeLayout playerShakeRelative;
    @BindView(R.id.playerDownloadRelative)
    RelativeLayout playerDownloadRelative;

    public PlayeSettingsFragment() {
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
        View view = inflater.inflate(R.layout.player_settings_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();

        crystalRangeSeekbar.setMinStartValue(SharedPreferencesManager.getInteger(getActivity(), "minNumber", 0));
        crystalRangeSeekbar.setMaxStartValue(SharedPreferencesManager.getInteger(getActivity(), "maxNumber", 100));

        crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                SharedPreferencesManager.putInteger(getActivity(), "minNumber", Integer.parseInt(minValue + ""));
                SharedPreferencesManager.putInteger(getActivity(), "maxNumber", Integer.parseInt(maxValue + ""));
                min.setText(minValue + "");
                max.setText(maxValue + "");
            }
        });

        if (getResources().getBoolean(R.bool.is_right_to_left))
            imageView.setImageResource(R.drawable.left_arrow);
        else
            imageView.setImageResource(R.drawable.right_arrow);

        moveToNextPageSwitch.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "nextSuraStart", false));
        basmalahSwitch.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "basmalahSwitch", false));
        voiceSwitch.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "voiceSwitch", false));

        moveToNextPageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesManager.putBoolean(getActivity(), "nextSuraStart", b);
            }
        });

        basmalahSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesManager.putBoolean(getActivity(), "basmalahSwitch", b);
            }
        });

        voiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesManager.putBoolean(getActivity(), "voiceSwitch", b);
            }
        });

        onFinishRAdioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferencesManager.putInteger(getActivity(), "radioGroup", radioGroup.getCheckedRadioButtonId());
            }
        });

        int id = SharedPreferencesManager.getInteger(getActivity(), "radioGroup", -1);
        onFinishRAdioGroup.check(id);

        playerDownloadRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DownloadSuraFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

        playerShakeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReadersFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.player_menu_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuPlayButton) {
            Intent intent = new Intent(getActivity(), PlayerService.class);
            getActivity().startService(intent);
            sendMessage("start");
        }
        return false;
    }

    private void sendMessage(String action) {
        Intent intent = new Intent("Service");
        intent.putExtra("message", action);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
