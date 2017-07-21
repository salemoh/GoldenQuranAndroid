package com.blackstone.goldenquran.api;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.models.models.Download;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;


public class DownloadAllSurahService extends IntentService {

    public static final String NUMBER_OF_AYAT = "NUMBER_OF_AYAT";
    public static final String SURAH_NUMBER = "Surah_Number";
    public static boolean isDownloadAll;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    public DownloadAllSurahService() {
        super("DownloadAllSurahService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_download)
                .setContentText(getString(R.string.downloading))
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notificationManager.notify(0, notification);
        startForeground(0, notification);

        initDownload(intent);
    }

    private void initDownload(Intent intent) {

        if (isDownloadAll) {
            ArrayList<TableOfContents> tableOfContentses = intent.getParcelableArrayListExtra("toc");
            for (int i = 0; i < 114; i++) {
                download(tableOfContentses.get(i).getSurah() + "", tableOfContentses.get(i).getVersesCount());
            }
        } else {
            download(intent.getExtras().getString(SURAH_NUMBER), intent.getExtras().getInt(NUMBER_OF_AYAT));
        }
    }

    public void download(String surahNo, int numberOfAyat) {

        Download download = new Download();
        download.setTotalFileSize((numberOfAyat * 100) / numberOfAyat);
        int s = 0;

        for (int i = 0; i <= numberOfAyat; i++) {
            String ayahFormated = String.valueOf(i);
            for (int j = ayahFormated.length(); j < 3; j++) {
                ayahFormated = "0" + ayahFormated;
            }
            for (int j = surahNo.length(); j < 3; j++) {
                surahNo = "0" + surahNo;
            }
            try {
                downloadFile(SharedPreferencesManager.getString(this, "readerName", "Abdul-Basit (Mujawwad)"), surahNo, ayahFormated);
                int progress = ((s++ * 100) / numberOfAyat);
                download.setProgress(progress);
                download.setCurrentFileSize(progress);
                sendNotification(download);
                if (progress == 100)
                    onDownloadComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadFile(String sheikh, String surahNumber, String ayah) throws IOException {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(SharedPreferencesManager.getString(this, "baseUrl", "http://www.everyayah.com/data/Abdul_Basit_Mujawwad_128kbps/"))
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseBody> request = retrofitInterface.downloadFile(surahNumber + ayah);
        ResponseBody body = request.execute().body();

        int count;
        byte data[] = new byte[1024 * 4];
        if (body != null) {
            long fileSize = body.contentLength();
            InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + sheikh, surahNumber + ayah + ".mp3");
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdir();
            }
            OutputStream output = new FileOutputStream(outputFile);
            long total = 0;
            long startTime = System.currentTimeMillis();
            int timeCount = 1;

            while ((count = bis.read(data)) != -1) {

                total += count;
                totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                double current = Math.round(total / (Math.pow(1024, 2)));

                int progress = (int) ((total * 100) / fileSize);

                long currentTime = System.currentTimeMillis() - startTime;
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            bis.close();
        }
    }

    private void sendNotification(Download download) {

        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(String.format(getString(R.string.downloaded) + " (%d/%d) " + getString(R.string.mb), download.getCurrentFileSize(), download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(getString(R.string.file_downloaded));
        notificationManager.notify(0, notificationBuilder.build());

    }
}