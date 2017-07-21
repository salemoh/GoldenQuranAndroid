package com.blackstone.goldenquran.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageActivity extends AppCompatActivity {
    @BindView(R.id.englishRelative)
    RelativeLayout englishRelative;
    @BindView(R.id.arabicRelative)
    RelativeLayout arabicRelative;
    @BindView(R.id.text)
    TextView mTextView;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.container)
    ViewGroup logoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SharedPreferencesManager.getBoolean(this, "isLanguageSet", false)){
            startActivity(new Intent(this, SplashScreenActivity.class));
            finish();
        }

        setContentView(R.layout.language_activity);
        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts.otf");
        mTextView.setTypeface(font);

        arabicRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.putBoolean(LanguageActivity.this, "isLanguageSet", true);
                SharedPreferencesManager.putBoolean(LanguageActivity.this, "isArabic", true);
                Intent intent = new Intent(LanguageActivity.this, ColorActivity.class);
                startActivity(intent);
                LanguageActivity.this.finish();
            }
        });
        englishRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.putBoolean(LanguageActivity.this, "isLanguageSet", true);
                SharedPreferencesManager.putBoolean(LanguageActivity.this, "isArabic", false);
                Intent intent = new Intent(LanguageActivity.this, ColorActivity.class);
                startActivity(intent);
                LanguageActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTextView == null)
            return;

        mTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTextView != null) {
                    TransitionSet transition = (TransitionSet) new TransitionSet().addTransition(new Fade(Fade.OUT)).addTransition(new ChangeBounds()).addTransition(new Fade(Fade.IN)).setDuration(1000);
                    TransitionManager.beginDelayedTransition(logoContainer, transition);
                    mTextView.setVisibility(View.VISIBLE);
                }
            }
        }, 1000);
    }
}
