package com.blackstone.goldenquran.api;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.ui.MainActivity;


public class PlayerService extends Service {
    static MediaPlayer mediaPlayer = null;
    static int x;
    RemoteViews notificationView;
    NotificationCompat.Builder builder;
    NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.athan);
        x = (int) (mediaPlayer.getDuration() * 0.15);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("Service"));

        Intent pause = new Intent("playPauseStop");
        pause.putExtra("message", "pause");
        PendingIntent pauseIntent = PendingIntent.getBroadcast(this, 1, pause, 0);

        Intent stop = new Intent("playPauseStop");
        stop.putExtra("message", "stop");
        PendingIntent stopIntent = PendingIntent.getBroadcast(this, 2, stop, 0);

        Intent play = new Intent("playPauseStop");
        play.putExtra("message", "play");
        PendingIntent playIntent = PendingIntent.getBroadcast(this, 0, play, 0);


        notificationView = new RemoteViews(getPackageName(), R.layout.player_notification_layout);
        notificationView.setProgressBar(R.id.pb_progress, mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition(), false);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.play).
                setAutoCancel(true).
                setTicker("Ticker Text").
                setContent(notificationView);

        notificationView.setOnClickPendingIntent(R.id.notiplay, playIntent);
        notificationView.setOnClickPendingIntent(R.id.notistop, stopIntent);
        notificationView.setOnClickPendingIntent(R.id.notipause, pauseIntent);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        Notification n = builder.build();

        startForeground(1, n);

        notificationManager.notify(1, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler h = new Handler();
        final int delay = 300;
        h.postDelayed(new Runnable() {
            public void run() {

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    sendProgress(mediaPlayer.getCurrentPosition());
                    notificationView.setProgressBar(R.id.pb_progress, mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition(), false);
                    Notification n = builder.build();
                    startForeground(1, n);
                    notificationManager.notify(1, builder.build());
                }
                h.postDelayed(this, delay);

            }
        }, delay);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    public void play() {
        mediaPlayer.start();

    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        sendProgress(0);
        notificationView.setProgressBar(R.id.pb_progress, mediaPlayer.getDuration(),0 , false);
        notificationManager.notify(1, builder.build());
    }

    public void forward() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + x);
    }

    public void backward() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - x);
    }

    public void seek(int i) {
        mediaPlayer.seekTo(i);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra("message");

            switch (message) {
                case "start":
                    play();
                    break;
                case "pause":
                    pause();
                    break;
                case "stop":
                    stop();
                    break;
                case "forward":
                    forward();
                    break;
                case "backward":
                    backward();
                    break;
                case "music":
                    seek(intent.getIntExtra("seek", 0));
                    break;
            }
        }
    };

    public void sendProgress(int progress) {
        Intent intent = new Intent("Progress");
        intent.putExtra("progress", progress);
        intent.putExtra("duration", mediaPlayer.getDuration());
        LocalBroadcastManager.getInstance(PlayerService.this).sendBroadcast(intent);

    }

}







