package com.blackstone.goldenquran.Application;

import android.support.multidex.MultiDexApplication;

import timber.log.Timber;


public class GoldenApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
