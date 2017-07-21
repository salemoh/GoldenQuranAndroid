package com.blackstone.goldenquran.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
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
    @BindView(R.id.settingsNotificationImage)
    ImageView settingsNotificationImage;
    @BindView(R.id.settingsNotificationText)
    TextView settingsNotificationText;
    @BindView(R.id.settingsNotificationRelative)
    RelativeLayout settingsNotificationRelative;
    //    @BindView(R.id.settingsDownloadedSuraImage)
//    ImageView settingsDownloadedSuraImage;
//    @BindView(R.id.settingsDownloadedSuraText)
//    TextView settingsDownloadedSuraText;
//    @BindView(R.id.settingsDownloadedSuraRelative)
//    RelativeLayout settingsDownloadedSuraRelative;
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
    @BindView(R.id.color1)
    FrameLayout color1;
    @BindView(R.id.color2)
    FrameLayout color2;
    @BindView(R.id.color3)
    FrameLayout color3;
    //    @BindView(R.id.color4)
//    FrameLayout color4;
    @BindView(R.id.color5)
    FrameLayout color5;
    //    @BindView(R.id.color6)
//    FrameLayout color6;
    @BindView(R.id.color1Selected)
    ImageView color1Selected;
    @BindView(R.id.color2Selected)
    ImageView color2Selected;
    @BindView(R.id.color3Selected)
    ImageView color3Selected;
    //    @BindView(R.id.color4Selected)
//    ImageView color4Selected;
    @BindView(R.id.color5Selected)
    ImageView color5Selected;
//    @BindView(R.id.color6Selected)
//    ImageView color6Selected;

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

        textSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedPreferencesManager.getInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) textSize.getTextSize()));

        ((DrawerCloser) getActivity()).moveToolbarDown();

        switch (SharedPreferencesManager.getInteger(getActivity(), "color", R.color.lightOrange)) {
            case R.color.lightBlue: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.VISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightGreen: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.VISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightOrange: {
                color1Selected.setVisibility(View.VISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightPink: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.VISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
            case R.color.lightYellow: {
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.VISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
                break;
            }
        }

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightOrange);
                color1Selected.setVisibility(View.VISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
            }
        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightGreen);
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.VISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightBlue);
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.VISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
            }
        });

//        color4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightYellow);
//                color1Selected.setVisibility(View.INVISIBLE);
//                color2Selected.setVisibility(View.INVISIBLE);
//                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.VISIBLE);
//                color5Selected.setVisibility(View.INVISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
//            }
//        });

        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.putInteger(getActivity(), "color", R.color.lightPink);
                color1Selected.setVisibility(View.INVISIBLE);
                color2Selected.setVisibility(View.INVISIBLE);
                color3Selected.setVisibility(View.INVISIBLE);
//                color4Selected.setVisibility(View.INVISIBLE);
                color5Selected.setVisibility(View.VISIBLE);
//                color6Selected.setVisibility(View.INVISIBLE);
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


//        settingsOnePageRadioButton.setChecked(SharedPreferencesManager.getBoolean(getActivity(), "pageStatus", true));
//        settingsOnePageRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    SharedPreferencesManager.putBoolean(getActivity(), "pageStatus", true);
//                } else {
//                    SharedPreferencesManager.putBoolean(getActivity(), "pageStatus", false);
//                }
//            }
//        });
//        settingsTwoPagesRadioButton.setChecked(!SharedPreferencesManager.getBoolean(getActivity(), "pageStatus", false));

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

//        settingsPickTafseerRelative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ExplanationFragment()).addToBackStack(null).commit();
//                ((DrawerCloser) getActivity()).moveToolbarDown();
//            }
//        });

        settingsNotificationRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new NotificationFragment()).addToBackStack(null).commit();
                ((DrawerCloser) getActivity()).moveToolbarDown();
            }
        });

//        settingsDownloadedSuraRelative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DownloadSuraFragment()).addToBackStack(null).commit();
//                ((DrawerCloser) getActivity()).moveToolbarDown();
//            }
//        });

        settingsCallUsRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "abdullaha@blackstoneeit.com")));
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentTextSize = textSize.getTextSize();
                if (currentTextSize <= 95) {
                    textSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentTextSize + 5f);
                    SharedPreferencesManager.putInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) currentTextSize + 5);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentTextSize = textSize.getTextSize();
                if (currentTextSize >= 40) {
                    textSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentTextSize - 5f);
                    SharedPreferencesManager.putInteger(getActivity(), SharedPreferencesManager.TEXT_SIZE, (int) currentTextSize - 5);
                }
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

    @Override
    public void onResume() {
        super.onResume();
        ((DrawerCloser) getActivity()).title(10);
    }
}

