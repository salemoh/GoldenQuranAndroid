package com.blackstone.goldenquran.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.DrawerCloser;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    @BindView(R.id.pickColorText)
    TextView pickColorText;
    @BindView(R.id.color1)
    ImageView color1;
    @BindView(R.id.color2)
    ImageView color2;
    @BindView(R.id.color3)
    ImageView color3;
    @BindView(R.id.color4)
    ImageView color4;
    @BindView(R.id.color5)
    ImageView color5;
    @BindView(R.id.color6)
    ImageView color6;
    @BindView(R.id.minus)
    ImageView minus;
    @BindView(R.id.plus)
    ImageView plus;
    @BindView(R.id.textSize)
    TextView textSize;
    @BindView(R.id.textSizeRelative)
    RelativeLayout textSizeRelative;
    @BindView(R.id.pickShakeImage)
    ImageView pickShakeImage;
    @BindView(R.id.pickShakeText)
    TextView pickShakeText;
    @BindView(R.id.pickShakeRelative)
    RelativeLayout pickShakeRelative;
    @BindView(R.id.settingsDownloadsuraImage)
    ImageView settingsDownloadsuraImage;
    @BindView(R.id.settingsDownloadsuraText)
    TextView settingsDownloadsuraText;
    @BindView(R.id.settingsDownloadsuraRelative)
    RelativeLayout settingsDownloadsuraRelative;
    @BindView(R.id.settingsPickTafseerImage)
    ImageView settingsPickTafseerImage;
    @BindView(R.id.settingsPickTafseerText)
    TextView settingsPickTafseerText;
    @BindView(R.id.settingsPickTafseerRelative)
    RelativeLayout settingsPickTafseerRelative;
    @BindView(R.id.settingsNotificationImage)
    ImageView settingsNotificationImage;
    @BindView(R.id.settingsNotificationText)
    TextView settingsNotificationText;
    @BindView(R.id.settingsNotificationRelative)
    RelativeLayout settingsNotificationRelative;
    @BindView(R.id.settingsDownloadedSuraImage)
    ImageView settingsDownloadedSuraImage;
    @BindView(R.id.settingsDownloadedSuraText)
    TextView settingsDownloadedSuraText;
    @BindView(R.id.settingsDownloadedSuraRelative)
    RelativeLayout settingsDownloadedSuraRelative;
    @BindView(R.id.settingsCallUsImage)
    ImageView settingsCallUsImage;
    @BindView(R.id.settingsCallUsText)
    TextView settingsCallUsText;
    @BindView(R.id.settingsCallUsRelative)
    RelativeLayout settingsCallUsRelative;
    @BindView(R.id.settingsPickLanguageText)
    TextView settingsPickLanguageText;
    @BindView(R.id.settingsArabicRadioButton)
    RadioButton settingsArabicRadioButton;
    @BindView(R.id.settingsEnglishRadioButton)
    RadioButton settingsEnglishRadioButton;
    @BindView(R.id.settingsPickLanguageRelative)
    RelativeLayout settingsPickLanguageRelative;
    @BindView(R.id.pageStateImage)
    ImageView pageStateImage;
    @BindView(R.id.pageStateText)
    TextView pageStateText;
    @BindView(R.id.settingsOnePageRadioButton)
    RadioButton settingsOnePageRadioButton;
    @BindView(R.id.settingsTwoPagesRadioButton)
    RadioButton settingsTwoPagesRadioButton;
    @BindView(R.id.pageStatusRelative)
    RelativeLayout pageStatusRelative;

    public SettingsFragment() {

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
        View view = inflater.inflate(R.layout.settings_layout, container, false);
        ButterKnife.bind(this, view);
        ((DrawerCloser) getActivity()).moveToolbarDown();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DrawerCloser) getActivity()).moveToolbarDown();

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightOrange);
            }
        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightGreen);
            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightBlue);
            }
        });

        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightYellow);
            }
        });

        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightPink);
            }
        });


        settingsArabicRadioButton.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "isArabic", true));
        settingsArabicRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked)
                    SharedPreferencesManager.putBoolean(getActivity(), "isArabic", true);
                else
                    SharedPreferencesManager.putBoolean(getActivity(), "isArabic", false);

                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        });
        settingsEnglishRadioButton.setChecked(!SharedPreferencesManager.getBoolean(getActivity(), "isArabic", false));


        settingsOnePageRadioButton.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "pageStatus", true));
        settingsOnePageRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferencesManager.putBoolean(getActivity(), "pageStatus", true);
                } else {
                    SharedPreferencesManager.putBoolean(getActivity(), "pageStatus", false);
                }
            }
        });
        settingsTwoPagesRadioButton.setChecked(!SharedPreferencesManager.getBoolean(getActivity(), "pageStatus", false));

        settingsDownloadsuraRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DownloadSuraFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

        pickShakeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReadersFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

        settingsPickTafseerRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingsPickTafseerFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

        settingsNotificationRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

        settingsDownloadedSuraRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DownloadSuraFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

        settingsCallUsRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FridayReadingFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentTextSize = textSize.getTextSize();
                textSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentTextSize + 5f);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.getTextSize() - 5f);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (settingsArabicRadioButton.isChecked()) {
            SharedPreferencesManager.putBoolean(getActivity(), "isArabic", true);
        } else if (settingsEnglishRadioButton.isChecked()) {
            SharedPreferencesManager.putBoolean(getActivity(), "isArabic", false);
        }
    }
}

