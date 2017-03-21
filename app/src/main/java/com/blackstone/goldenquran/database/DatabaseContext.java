package com.blackstone.goldenquran.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

class DatabaseContext extends ContextWrapper {

    public DatabaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        String pathToDB = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DB" + "/"+name;

        File result = new File(pathToDB);

        return result;
    }

    /* this version is called for android devices >= api-11. thank to @damccull for fixing this. */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name, mode, factory);
    }

    /* this version is called for android devices < api-11 */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);

        return result;
    }
}