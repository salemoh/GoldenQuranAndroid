package com.blackstone.goldenquran.api;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.blackstone.goldenquran.managers.PlayerManager;
import com.blackstone.goldenquran.models.AutoColorModel;
import com.blackstone.goldenquran.models.ChangeDataEvent;
import com.blackstone.goldenquran.models.ChangeIconEvent;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


public class PlayerService extends Service {
    public static MediaPlayer mediaPlayer = null;
    Handler handler;
    Runnable r;
    public static int currentSurah;
    public static int currentAyah;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        mediaPlayer = new MediaPlayer();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("Service"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    public void play(String filePath) {
        currentAyah = PlayerManager.getCurrentAya();
        currentSurah = PlayerManager.getCurrentSurah();

        mediaPlayer = MediaPlayer.create(PlayerService.this, Uri.fromFile(new File(filePath)));
        if (mediaPlayer != null)
            mediaPlayer.start();
        EventBus.getDefault().post(new ChangeDataEvent(currentSurah));

        if (!SharedPreferencesManager.getBoolean(PlayerService.this, "voiceSwitch", true)) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    currentAyah++;
                    if (currentAyah > PlayerManager.getsCurrentSurahMaxAyah()) {
                        if (SharedPreferencesManager.getBoolean(PlayerService.this, "basmalahSwitch", true))
                            currentAyah = 0;
                        else
                            currentAyah = 1;

                        if (currentSurah <= 113) {
                            if ((SharedPreferencesManager.getInteger(PlayerService.this, "r", 0) == 0)) {
                                currentSurah++;
                                EventBus.getDefault().post(new ChangeDataEvent(currentSurah));
                            } else if ((SharedPreferencesManager.getInteger(PlayerService.this, "r", 0) == 1)) {
                                mediaPlayer.stop();
                                EventBus.getDefault().post(new ChangeIconEvent());
                                return;
                            }
                        }
                    }
                    if (SharedPreferencesManager.getBoolean(PlayerService.this, "nextSuraStart", true))
                        EventBus.getDefault().post(new AutoColorModel(currentAyah, currentSurah));

                    if (PlayerManager.isSoundPresent(SharedPreferencesManager.getString(PlayerService.this, "readerName", "Alafasy_128kbps"), String.valueOf(currentSurah), String.valueOf(currentAyah))) {
                        String surah = String.valueOf(currentSurah);
                        for (int i = surah.length(); i < 3; i++) {
                            surah = "0" + surah;
                        }
                        String ayaa = String.valueOf(currentAyah);
                        for (int i = ayaa.length(); i < 3; i++) {
                            ayaa = "0" + ayaa;
                        }
                        String pathToSound = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + SharedPreferencesManager.getString(PlayerService.this, "readerName", "Alafasy_128kbps") + "/" + surah + ayaa + ".mp3";

                        PlayerService.mediaPlayer = MediaPlayer.create(PlayerService.this, Uri.fromFile(new File(pathToSound)));
                        PlayerService.mediaPlayer.start();
                        PlayerService.mediaPlayer.setOnCompletionListener(this);
                    } else {
                        Intent mIntent = new Intent(PlayerService.this, DownloadService.class);
                        mIntent.putExtra(DownloadService.SURAH_NUMBER, String.valueOf(currentSurah));
                        mIntent.putExtra(DownloadService.AYAH_NUMBER, String.valueOf(currentAyah));
                        startService(mIntent);
                    }
                }
            });
        } else {
            handler = new Handler();

            r = new Runnable() {
                @Override
                public void run() {
                    currentAyah++;
                    if (currentAyah > PlayerManager.getsCurrentSurahMaxAyah()) {
                        if (SharedPreferencesManager.getBoolean(PlayerService.this, "basmalahSwitch", true))
                            currentAyah = 0;
                        else
                            currentAyah = 1;

                        if (currentSurah <= 113) {
                            if ((SharedPreferencesManager.getInteger(PlayerService.this, "r", 0) == 0)) {
                                currentSurah++;
                                EventBus.getDefault().post(new ChangeDataEvent(currentSurah));
                            } else if ((SharedPreferencesManager.getInteger(PlayerService.this, "r", 0) == 1)) {
                                mediaPlayer.stop();
                                EventBus.getDefault().post(new ChangeIconEvent());
                                return;
                            }
                        }
                    }
                    if (SharedPreferencesManager.getBoolean(PlayerService.this, "nextSuraStart", true))
                        EventBus.getDefault().post(new AutoColorModel(currentAyah, currentSurah));
                    if (PlayerManager.isSoundPresent(SharedPreferencesManager.getString(PlayerService.this, "readerName", "Alafasy_128kbps"), String.valueOf(currentSurah), String.valueOf(currentAyah))) {
                        String surah = String.valueOf(currentSurah);
                        for (int i = surah.length(); i < 3; i++) {
                            surah = "0" + surah;
                        }
                        String ayaa = String.valueOf(currentAyah);
                        for (int i = ayaa.length(); i < 3; i++) {
                            ayaa = "0" + ayaa;
                        }
                        String pathToSound = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + SharedPreferencesManager.getString(PlayerService.this, "readerName", "Alafasy_128kbps") + "/" + surah + ayaa + ".mp3";

                        mediaPlayer = MediaPlayer.create(PlayerService.this, Uri.fromFile(new File(pathToSound)));
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                            handler.postDelayed(this, mediaPlayer.getDuration() - 300);
                        }

                    } else {
                        Intent mIntent = new Intent(PlayerService.this, DownloadService.class);
                        mIntent.putExtra(DownloadService.SURAH_NUMBER, String.valueOf(currentSurah));
                        mIntent.putExtra(DownloadService.AYAH_NUMBER, String.valueOf(currentAyah));
                        startService(mIntent);
                    }
                }

            };
            if (mediaPlayer != null)
                handler.postDelayed(r, mediaPlayer.getDuration() - 300);
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
        }
        if (handler != null)
            handler.removeCallbacks(r);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getStringExtra("message")) {
                case "start":
                    play(intent.getStringExtra("path"));
                    break;
                case "stop":
                    stop();
                    break;
            }
        }
    };
}







