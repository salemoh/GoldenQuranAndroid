package com.blackstone.goldenquran.api;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.SplashScreenActivity;


public class AlertIntentSarvice extends IntentService {
    public AlertIntentSarvice() {
        super("AlertIntentSarvice");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager alarmNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashScreenActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        String title = "", message = "";
        if (intent.getExtras() != null) {
            title = intent.getStringExtra("title");
            message = intent.getStringExtra("message");
        }

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.pray_time_clock)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);


        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());

    }
}
