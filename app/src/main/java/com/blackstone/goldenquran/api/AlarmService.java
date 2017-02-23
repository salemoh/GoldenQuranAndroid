package com.blackstone.goldenquran.api;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.blackstone.goldenquran.R;


public class AlarmService extends Service {

    MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("AlarmService"));

        NotificationManager alarmNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, new Intent("alarm"), PendingIntent.FLAG_UPDATE_CURRENT);

        mediaPlayer = MediaPlayer.create(this, R.raw.athan);
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Alarm")
                .setSmallIcon(R.drawable.pray_time_clock)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Salah Time"))
                .setContentText("Salah Time")
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        alarmNotificationManager.notify(153163264, alarmNotificationBuilder.build());

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mediaPlayer.stop();
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
