package com.blackstone.goldenquran.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class AlarmBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent toService = new Intent("AlarmService");

        String action = intent.getAction();

        if(action.equalsIgnoreCase("alarm")){
            LocalBroadcastManager.getInstance(context).sendBroadcast(toService);
        }
    }
}
