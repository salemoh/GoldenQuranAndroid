package com.blackstone.goldenquran.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.blackstone.goldenquran.Application.GoldenApplication;
import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.api.DownloadAllSurahService;
import com.blackstone.goldenquran.api.PlayerService;
import com.blackstone.goldenquran.database.DataBaseManager;
import com.blackstone.goldenquran.managers.PlayerManager;
import com.blackstone.goldenquran.models.ChangeIconEvent;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.ui.PlayerActivityForResult;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;
import com.blackstone.goldenquran.utilities.threads.Task;
import com.blackstone.goldenquran.utilities.threads.ThreadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PlayerSettingsFragment extends Fragment {

    int currentSurah;
    int currentPage;
    int currentAya;
    int surahTotalAyaNumber;
    private Menu menu;
    @BindView(R.id.playerLeftArrow)
    ImageView imageView;
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
    @BindView(R.id.alsuraAlHaleah)
    TextView mAlsuraAlHaleah;
    @BindView(R.id.playerSuraName)
    TextView mPlayerSuraName;
    @BindView(R.id.alSuraAlHaleahRelative)
    RelativeLayout mAlSuraAlHaleahRelative;
    @BindView(R.id.onFinish)
    TextView mOnFinish;
    @BindView(R.id.radio_1)
    RadioButton mRadio1;
    @BindView(R.id.radio_2)
    RadioButton mRadio2;
    @BindView(R.id.onFinishRelative)
    RelativeLayout mOnFinishRelative;
    @BindView(R.id.optionsOfREadingText)
    TextView mOptionsOfREadingText;
    @BindView(R.id.moveTONextPageText)
    TextView mMoveTONextPageText;
    @BindView(R.id.basmalahText)
    TextView mBasmalahText;
    @BindView(R.id.saoutText)
    TextView mSaoutText;
    @BindView(R.id.optionsOfREading)
    RelativeLayout mOptionsOfREading;
    @BindView(R.id.playerShakeName)
    TextView mPlayerShakeName;
    @BindView(R.id.playerShakeImage)
    ImageView mPlayerShakeImage;
    @BindView(R.id.playerShakeNameText)
    TextView mPlayerShakeNameText;
    @BindView(R.id.playerDownloadImage)
    ImageView mPlayerDownloadImage;
    @BindView(R.id.playerDownloadText)
    TextView mPlayerDownloadText;
    private String currentSurahName;
    Unbinder unbinder;

    public PlayerSettingsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

        unbinder = ButterKnife.bind(this, view);

        if (SharedPreferencesManager.getBoolean(getActivity(), "firstTime", false)) {
            mRadio1.setChecked(true);
            SharedPreferencesManager.putInteger(getActivity(), "r", 0);
        } else {
            mRadio2.setChecked(true);
            SharedPreferencesManager.putInteger(getActivity(), "r", 1);
            SharedPreferencesManager.putBoolean(getActivity(), "firstTime", false);
        }


        if (!ThreadManager.isThereThreadPool(GoldenApplication.PLAYER_SETTINGS_FRAGMENT_POLL)) {
            ThreadManager.initializeThreadManagerQueuedPool(GoldenApplication.PLAYER_SETTINGS_FRAGMENT_POLL);
        }

        currentSurah = PlayerManager.getCurrentSurah();
        currentPage = PlayerManager.getCurrentPage();
        currentAya = PlayerManager.getCurrentAya();

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.pleaseWait), true);


        ThreadManager.addTaskToThreadManagerPool(GoldenApplication.PLAYER_SETTINGS_FRAGMENT_POLL, 1, new Task() {
            @Override
            public void onPreRun() {

            }

            @Override
            public void onPreRunFailure(Exception ex) {

            }

            @Override
            public void onRunSuccess() {

            }

            @Override
            public void onRunFailure(Exception ex) {

            }

            @Override
            public void run() {
                String[] suarNames = getResources().getStringArray(R.array.fahrasSuras);
                if (currentSurah != 0) {
                    currentSurahName = suarNames[currentSurah - 1];
                } else if (currentPage != 0) {
                    DataBaseManager dataBaseManager = new DataBaseManager(getActivity(), SharedPreferencesManager.getString(getActivity(), "dbName", "KingFahad1.db"), false);
                    dataBaseManager.open();

                    currentSurah = dataBaseManager.getSurahFromPageNumber(currentPage);
                    currentSurahName = suarNames[currentSurah - 1];
                }

                if (currentSurah != 0) {
                    DataBaseManager dataBaseManager = new DataBaseManager(getActivity(), "Mus7af_1.db", true).createDatabase();
                    dataBaseManager.open();

                    surahTotalAyaNumber = dataBaseManager.getVersesCountForSora(currentSurah);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        initViews();
                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DrawerCloser) getActivity()).moveToolbarDown();

    }

    private void initViews() {

        mPlayerSuraName.setText(currentSurahName);

        if (getResources().getBoolean(R.bool.is_right_to_left)) {
            imageView.setImageResource(R.drawable.left_arrow);
        } else {
            imageView.setImageResource(R.drawable.right_arrow);
        }
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
                if (mRadio1.isChecked()) {
                    SharedPreferencesManager.putInteger(getActivity(), "r", 0);
                } else if (mRadio2.isChecked()) {
                    SharedPreferencesManager.putInteger(getActivity(), "r", 1);
                }
            }
        });

        onFinishRAdioGroup.check(SharedPreferencesManager.getInteger(getActivity(), "radioGroup", -1));

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

        mPlayerSuraName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), PlayerActivityForResult.class), 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                mPlayerSuraName.setText(data.getStringExtra("suraName"));
                surahTotalAyaNumber = Integer.parseInt(data.getStringExtra("suraVersesCount"));
                PlayerManager.setCurrentSurah(Integer.parseInt(data.getStringExtra("suraNumber")));

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.player_menu_items, menu);
        MenuItem menuItem = menu.getItem(0);
        if (PlayerService.mediaPlayer != null && PlayerService.mediaPlayer.isPlaying())
            menuItem.setIcon(R.drawable.noti_pause);
        else
            menuItem.setIcon(R.drawable.play_button);

        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!PlayerService.mediaPlayer.isPlaying()) {
            if (PlayerManager.isSoundPresent(SharedPreferencesManager.getString(getActivity(), "readerName", "Alafasy_128kbps"), String.valueOf(PlayerManager.getCurrentSurah()), String.valueOf(1))) {
                String surah = String.valueOf(PlayerManager.getCurrentSurah());
                for (int i = surah.length(); i < 3; i++) {
                    surah = "0" + surah;
                }
                String ayaa;
                if (SharedPreferencesManager.getBoolean(getActivity(), "basmalahSwitch", true)) {
                    if (PlayerManager.getCurrentSurah() != 1)
                        PlayerManager.setCurrentAya(0);
                    else
                        PlayerManager.setCurrentAya(1);
                } else
                    PlayerManager.setCurrentAya(1);

                ayaa = String.valueOf(PlayerManager.getCurrentAya());
                for (int i = ayaa.length(); i < 3; i++) {
                    ayaa = "0" + ayaa;
                }
                String pathToSound = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + SharedPreferencesManager.getString(getActivity(), "readerName", "Alafasy_128kbps") + "/" + surah + ayaa + ".mp3";

                sendMessage("start", pathToSound);
                item.setIcon(R.drawable.noti_pause);
            } else {
                Intent intent = new Intent(getActivity(), DownloadAllSurahService.class);
                intent.putExtra(DownloadAllSurahService.SURAH_NUMBER, PlayerManager.getCurrentSurah() + "");
                intent.putExtra(DownloadAllSurahService.NUMBER_OF_AYAT, surahTotalAyaNumber);
                DownloadAllSurahService.isDownloadAll = false;
                getActivity().startService(intent);
                item.setIcon(R.drawable.play_button);
            }
        } else

        {
            sendMessage("stop", "");
            item.setIcon(R.drawable.play_button);
        }
        return false;
    }

    @Subscribe
    public void changeTheIcon(ChangeIconEvent iconEvent) {
        MenuItem item = menu.findItem(R.id.menuPlayButton);
        item.setIcon(R.drawable.play_button);
    }

    private void sendMessage(String action, String pathToSound) {
        Intent intent = new Intent("Service");

        intent.putExtra("message", action);
        intent.putExtra("path", pathToSound);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(5);
    }
}
