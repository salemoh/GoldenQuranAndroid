package com.blackstone.goldenquran.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blackstone.goldenquran.database.Medina1OpenHelper;

import java.io.IOException;

public class TestAdapter {
    static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private Medina1OpenHelper mDbHelper;


    public TestAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new Medina1OpenHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    void close() {
        mDbHelper.close();
    }

    Cursor Select(float x, float y, int sura, int page_number) {

        mDb = mDbHelper.getWritableDatabase();
        return mDb.query("page", new String[]{"ayah"}, "surah = ? and page_number = ? and "
                + x + " > upper_left_x and "
                + x + " < upper_right_x and "
                + y + " > upper_left_y and "
                + y + " < lower_left_y "
                + "and id > 10000", new String[]{sura + "", page_number + ""}, null, null, null);
    }

    Cursor getAyahLines(int sura, int page_number, float ayah) {
        mDb = mDbHelper.getWritableDatabase();
        return mDb.query("page", null, "surah = ? and page_number = ? and ayah = ? and id > 10000", new String[]{"" + sura, "" + page_number, "" + ayah}, null, null, null);
    }

    public  Cursor getPagePoints(int pageNumber) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.query("page", null, "page_number = ? and id > 10000", new String[]{String.valueOf(pageNumber)}, null, null, null);
    }
}