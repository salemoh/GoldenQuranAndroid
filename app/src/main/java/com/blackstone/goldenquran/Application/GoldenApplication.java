package com.blackstone.goldenquran.Application;

import android.app.Application;

import timber.log.Timber;


public class GoldenApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
