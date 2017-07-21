package com.blackstone.goldenquran.api;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.blackstone.goldenquran.R;


public class AlarmService extends IntentService {

    MediaPlayer mediaPlayer;

    public AlarmService() {
        super("Alarm Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("AlarmService"));

        mediaPlayer = MediaPlayer.create(this, R.raw.athan);
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();

        NotificationManager alarmNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, new Intent("alarm"), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Alarm")
                .setSmallIcon(R.drawable.pray_time_clock)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Salah Time"))
                .setContentText("Salah Time")
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        alarmNotificationManager.notify(153163264, alarmNotificationBuilder.build());

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                AlarmService.this.stopSelf();
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mediaPlayer.stop();
            AlarmService.this.stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
