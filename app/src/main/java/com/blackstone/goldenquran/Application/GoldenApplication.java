package com.blackstone.goldenquran.Application;

import android.app.Application;

import com.blackstone.goldenquran.utilities.threads.ThreadManager;

import timber.log.Timber;


public class GoldenApplication extends Application {
    public static final String getPagePoints = "getPagePoints";
    public static final String PLAYER_SETTINGS_FRAGMENT_POLL = "PLAYER_SETTINGS_FRAGMENT_POLL";

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        refWatcher = LeakCanary.install(this);
        ThreadManager.initializeThreadManagerQueuedPool(getPagePoints);
        Timber.plant(new Timber.DebugTree());
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        GoldenApplication application = (GoldenApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;

}
