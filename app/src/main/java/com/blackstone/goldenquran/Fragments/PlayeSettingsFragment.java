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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.PlayerService;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlayeSettingsFragment extends Fragment {

    @BindView(R.id.alsuraAlHaleah)
    TextView alsuraAlHaleah;
    @BindView(R.id.playerSuraName)
    TextView playerSuraName;
    @BindView(R.id.playerLeftArrow)
    ImageView imageView;
    @BindView(R.id.alSuraAlHaleahRelative)
    RelativeLayout alSuraAlHaleahRelative;
    @BindView(R.id.akhtearAlAyat)
    TextView akhtearAlAyat;
    @BindView(R.id.rangeSeekbar1)
    CrystalRangeSeekbar crystalRangeSeekbar;
    @BindView(R.id.maxTextView)
    TextView max;
    @BindView(R.id.minTextView)
    TextView min;
    @BindView(R.id.akhtearAlAyatRelative)
    RelativeLayout akhtearAlAyatRelative;
    @BindView(R.id.onFinish)
    TextView onFinish;
    @BindView(R.id.onFinishRAdioGroup)
    RadioGroup onFinishRAdioGroup;
    @BindView(R.id.onFinishRelative)
    RelativeLayout onFinishRelative;
    @BindView(R.id.optionsOfREadingText)
    TextView optionsOfREadingText;
    @BindView(R.id.moveToNextPageSwitch)
    Switch moveToNextPageSwitch;
    @BindView(R.id.moveTONextPageText)
    TextView moveTONextPageText;
    @BindView(R.id.basmalahSwitch)
    Switch basmalahSwitch;
    @BindView(R.id.basmalahText)
    TextView basmalahText;
    @BindView(R.id.saoutSwitch)
    Switch saoutSwitch;
    @BindView(R.id.saoutText)
    TextView saoutText;
    @BindView(R.id.optionsOfREading)
    RelativeLayout optionsOfREading;
    @BindView(R.id.playerShakeName)
    TextView playerShakeName;
    @BindView(R.id.playerShakeImage)
    ImageView playerShakeImage;
    @BindView(R.id.playerShakeNameText)
    TextView playerShakeNameText;
    @BindView(R.id.playerShakeRelative)
    RelativeLayout playerShakeRelative;
    @BindView(R.id.playerDownloadImage)
    ImageView playerDownloadImage;
    @BindView(R.id.playerDownloadText)
    TextView playerDownloadText;

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

        crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                min.setText(
                        "" + minValue);
                max.setText(maxValue + "");
            }
        });


        if (getResources().getBoolean(R.bool.is_right_to_left))
            imageView.setImageResource(R.drawable.left_arrow);
        else
            imageView.setImageResource(R.drawable.right_arrow);
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
            Toast.makeText(getActivity(), "start", Toast.LENGTH_SHORT).show();
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
