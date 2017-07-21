package com.blackstone.goldenquran.api;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.models.Download;
import com.blackstone.goldenquran.ui.MainActivity;
import com.blackstone.goldenquran.utilities.SharedPreferencesManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

public class DownloadService extends IntentService {

    public static final String SURAH_NUMBER = "Surah_Number";
    public static final String AYAH_NUMBER = "Ayah_Number";
    public static final String MULTI_MODE = "MULTI_MODE";
    public static final String SURAH_AYAH_NUMBERS_KEY = "SURAH_AYAH_NUMBERS_KEY";

    public DownloadService() {
        super("Download Service");
        setIntentRedelivery(true);
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;


    @Override
    protected void onHandleIntent(Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_download)
                .setContentTitle(getString(R.string.download))
                .setContentText(getString(R.string.downloading))
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());

        initDownload(intent);

    }

    private void initDownload(Intent intent) {

        boolean isMultiMode = intent.getExtras().getBoolean(MULTI_MODE, false);
        if (!isMultiMode) {
            String surahNumber = intent.getExtras().getString(SURAH_NUMBER);
            String ayahhNumber = intent.getExtras().getString(AYAH_NUMBER);

            for (int i = surahNumber.length(); i < 3; i++) {
                surahNumber = "0" + surahNumber;
            }

            for (int i = ayahhNumber.length(); i < 3; i++) {
                ayahhNumber = "0" + ayahhNumber;
            }


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SharedPreferencesManager.getString(this, "baseUrl", "http://www.everyayah.com/data/Abdul_Basit_Mujawwad_128kbps/"))
                    .build();

            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

            Call<ResponseBody> request = retrofitInterface.downloadFile(/*"Alafasy_128kbps"*/ surahNumber, ayahhNumber);
            try {

                downloadFile(request.execute().body(), SharedPreferencesManager.getString(this, "readerName", "Abdul-Abdul-Basit (Mujawwad)"), surahNumber, ayahhNumber);


            } catch (IOException e) {

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        } else {
            String[] toDownloadList = intent.getExtras().getStringArray(SURAH_AYAH_NUMBERS_KEY);

            try {
                downloadFile(SharedPreferencesManager.getString(this, "readerName", "Abdul-Basit (Mujawwad)"), toDownloadList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadFile(String sheikh, String[] toDownloadList) throws IOException {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(SharedPreferencesManager.getString(this, "baseUrl", "http://www.everyayah.com/data/Abdul_Basit_Mujawwad_128kbps/"))
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        for (String sorrahAya : toDownloadList) {

            Call<ResponseBody> request = retrofitInterface.downloadFile(/*sheikh,*/ sorrahAya);
            ResponseBody body = request.execute().body();

            int count;
            if (body != null) {
                byte data[] = new byte[1024 * 4];
                long fileSize = body.contentLength();
                InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + sheikh, sorrahAya + ".mp3");
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

                    Download download = new Download();
                    download.setTotalFileSize(totalFileSize);

                    if (currentTime > 1000 * timeCount) {

                        download.setCurrentFileSize((int) current);
                        download.setProgress(progress);
                        sendNotification(download);
                        timeCount++;
                    }

                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                bis.close();
                onDownloadComplete();
            } else {
                Toast.makeText(this, "Servier is down try to download it later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadFile(ResponseBody body, String sheikh, String sorrah, String ayaa) throws IOException {

        int count;
        if (body != null) {
            byte data[] = new byte[1024 * 4];
            long fileSize = body.contentLength();
            InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + sheikh, sorrah + ayaa + ".mp3");
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

                Download download = new Download();
                download.setTotalFileSize(totalFileSize);

                if (currentTime > 1000 * timeCount) {

                    download.setCurrentFileSize((int) current);
                    download.setProgress(progress);
                    sendNotification(download);
                    timeCount++;
                }

                output.write(data, 0, count);
            }
            onDownloadComplete();
            output.flush();
            output.close();
            bis.close();
        } else {
            Toast.makeText(this, "Servier is down try to download it later", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNotification(Download download) {

        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(String.format(getString(R.string.downloaded)+" (%d/%d) "+getString(R.string.mb), download.getCurrentFileSize(), download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendIntent(Download download) {

        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(getString(R.string.file_downloaded));
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }
}