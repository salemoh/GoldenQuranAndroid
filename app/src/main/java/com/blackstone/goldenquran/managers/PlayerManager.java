package com.blackstone.goldenquran.managers;

import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;

public class PlayerManager {

    static public boolean isFilePresent(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + fileName;
        File file = new File(pathtoimage);
        return file.exists();
    }

    static public Drawable readImageFromInternalStorage(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + fileName;
        return Drawable.createFromPath(pathtoimage);
    }

    static public Drawable readDBFromInternalStorage(String fileName) {
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "DB" + "/" + fileName;
        return Drawable.createFromPath(pathtoimage);
    }

}
