package com.blackstone.goldenquran.managers;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;

public class PlayerManager {

    private static int sCurrentSurah;
    private static int sCurrentPage;
    private static int sCurrentAya;
    private static int sCurrentSurahMaxAyah;

    public static void setsCurrentSurahMaxAyah(int sCurrentSurahMaxAyah) {
        PlayerManager.sCurrentSurahMaxAyah = sCurrentSurahMaxAyah;
    }

    public static int getsCurrentSurahMaxAyah() {
        return sCurrentSurahMaxAyah;
    }

    public static boolean isSoundPresent(String sheikh, String surah, String ayaa) {
        for (int i = surah.length(); i < 3; i++) {
            surah = "0" + surah;
        }

        for (int i = ayaa.length(); i < 3; i++) {
            ayaa = "0" + ayaa;
        }
        String pathToSound = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + sheikh + "/" + surah + ayaa + ".mp3";
        return isExist(pathToSound);
    }

    static public boolean isImagePresent(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + fileName + ".png";
        return isExist(pathtoimage);
    }

    static public Drawable readImageFromInternalStorage(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + fileName + ".png";
        return Drawable.createFromPath(pathtoimage);
    }

    static public boolean isDBPresent(String fileName) {
        String pathToDB = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DB" + "/" + fileName;
        return isExist(pathToDB);
    }

    private static boolean isExist(String pathToDB) {
        File file = new File(pathToDB);
        return file.exists();
    }

    static public int getWidth(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + fileName + ".png";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathtoimage, options);
        return options.outWidth;
    }

    static public int getHeight(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + fileName + ".png";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathtoimage, options);
        return options.outHeight;
    }

    static public int getImagesCount() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/");
        File[] files = dir.listFiles();
        return files.length;
    }


    public static int getCurrentSurah() {
        return sCurrentSurah;
    }

    public static void setCurrentSurah(int currentSurah) {
        sCurrentSurah = currentSurah;
    }

    public static int getCurrentPage() {
        return sCurrentPage;
    }

    public static void setCurrentPage(int currentPage) {
        sCurrentPage = currentPage;
    }

    public static void setCurrentAya(Integer currentAya) {
        sCurrentAya = currentAya;
    }

    public static int getCurrentAya() {
        return sCurrentAya;
    }


}
