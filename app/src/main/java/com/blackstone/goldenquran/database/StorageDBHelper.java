package com.blackstone.goldenquran.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StorageDBHelper extends SQLiteOpenHelper {

    public StorageDBHelper(Context context, String name) {
        super(new DatabaseContext(context), name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
