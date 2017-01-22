package com.blackstone.goldenquran.api;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;

import com.blackstone.goldenquran.Fragments.PrayingTimeViewPagerFragment;
import com.blackstone.goldenquran.R;


public class AlarmService extends IntentService {

    MediaPlayer mediaPlayer;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager alarmNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, PrayingTimeViewPagerFragment.class), 0);
        mediaPlayer = MediaPlayer.create(this, R.raw.athan);
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();

        }

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Alarm")
                .setSmallIcon(R.drawable.pray_time_clock)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Salah Time"))
                .setContentText("Salah Time")
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());

}}
