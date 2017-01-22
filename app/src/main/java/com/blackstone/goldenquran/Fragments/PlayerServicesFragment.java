package com.blackstone.goldenquran.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.PlayerService;

import butterknife.BindView;
import butterknife.ButterKnife;





public class PlayerServicesFragment extends Fragment {

    @BindView(R.id.backward)
    FloatingActionButton backward;
    @BindView(R.id.notipause)
    FloatingActionButton pause;
    @BindView(R.id.stop)
    FloatingActionButton stop;
    @BindView(R.id.forward)
    FloatingActionButton forward;
    @BindView(R.id.play)
    FloatingActionButton play;
    @BindView(R.id.seekbar)
    AppCompatSeekBar seekBar;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    public PlayerServicesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player_services, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        seekBar.setProgress(0);

        Intent intent = new Intent(getActivity(), PlayerService.class);
        getActivity().startService(intent);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("start");
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("stop");
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("pause");
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("forward");
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("backward");
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendMessage("music");
            }
        });
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("Progress"));
        super.onResume();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            seekBar.setMax(intent.getExtras().getInt("duration"));
            seekBar.setProgress(intent.getExtras().getInt("progress"));
        }
    };

    private void sendMessage(String action) {
        Intent intent = new Intent("Service");

        intent.putExtra("message", action);
        intent.putExtra("seek", seekBar.getProgress());
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
