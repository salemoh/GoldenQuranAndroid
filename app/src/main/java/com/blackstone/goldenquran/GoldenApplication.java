package com.blackstone.goldenquran;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by SamerGigaByte on 11/12/2016.
 */

public class GoldenApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
