package com.blackstone.goldenquran.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class NotificationsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent toService = new Intent("Service");

        String action = intent.getAction();

        if (action.equalsIgnoreCase("playPauseStop")) {

            String message = intent.getStringExtra("message");

            if (message.equalsIgnoreCase("play")) {
                toService.putExtra("message", "notiPlay");
                LocalBroadcastManager.getInstance(context).sendBroadcast(toService);
            } else if (message.equalsIgnoreCase("pause")) {
                toService.putExtra("message", "pause");
                LocalBroadcastManager.getInstance(context).sendBroadcast(toService);
            } else if (message.equalsIgnoreCase("stop")) {
                toService.putExtra("message", "stop");
                LocalBroadcastManager.getInstance(context).sendBroadcast(toService);
            }

        }
    }
}