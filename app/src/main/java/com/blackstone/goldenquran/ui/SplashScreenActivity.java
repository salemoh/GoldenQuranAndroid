package com.blackstone.goldenquran.ui;

import android.content.Intent;

import com.blackstone.goldenquran.R;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by Abdullah on 6/18/2017.
 */

public class SplashScreenActivity extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(1000);

        configSplash.setTitleSplash("Golden Quran");
        configSplash.setTitleTextSize(30f);
        configSplash.setAnimTitleDuration(2500);
        configSplash.setTitleFont("Raleway-Regular.ttf");
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);

        configSplash.setLogoSplash(R.drawable.splash);
        configSplash.setAnimLogoSplashDuration(2500);
    }



    @Override
    public void animationsFinished() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
