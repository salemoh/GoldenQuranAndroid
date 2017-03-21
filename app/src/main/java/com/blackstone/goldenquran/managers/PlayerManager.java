package com.blackstone.goldenquran.managers;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;

public class PlayerManager {

    static public boolean isImagePresent(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + fileName + ".png";
        File file = new File(pathtoimage);
        return file.exists();
    }

    static public Drawable readImageFromInternalStorage(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/kingFahad/page" + fileName + ".png";
        return Drawable.createFromPath(pathtoimage);
    }

//    static public void readDBFromInternalStorage(Context context) {
//        String pathToDB = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DB" + "/";
//        new DataBaseManager(context, pathToDB).createDatabase();
//    }

    static public boolean isDBPresent(String fileName) {
        String pathToDB = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DB" + "/" + fileName;
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

}
