package com.blackstone.goldenquran.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blackstone.goldenquran.models.AhadeethModel;
import com.blackstone.goldenquran.models.AlsuraModel;
import com.blackstone.goldenquran.models.Ayah;
import com.blackstone.goldenquran.models.DataMawdo3ColorModel;
import com.blackstone.goldenquran.models.Mo3jamModel;
import com.blackstone.goldenquran.models.models.WordsMeaningModel;

import java.io.IOException;
import java.util.ArrayList;

public class DataBaseManager {

    private static final String TAG = "DataAdapter";
    private DBHelperFromAssets mDBHelperFromAssets;
    private StorageDBHelper mStorageDBHelper;
    private SQLiteDatabase mDbHelper;


    public DataBaseManager(Context context, String DBName, boolean isFromAssets) {
        if (!isFromAssets) {
            mStorageDBHelper = new StorageDBHelper(context, DBName);
        } else {
            mDBHelperFromAssets = new DBHelperFromAssets(context, DBName);
        }
    }

    public DataBaseManager createDatabase() throws SQLException {
        try {
            if (mDBHelperFromAssets != null)
                mDBHelperFromAssets.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataBaseManager open() throws SQLException {
        try {
            if (mDBHelperFromAssets != null) {
                mDBHelperFromAssets.openDataBase();
                mDBHelperFromAssets.close();
                mDbHelper = mDBHelperFromAssets.getReadableDatabase();
            } else {
                mDbHelper = mStorageDBHelper.getReadableDatabase();
            }
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public ArrayList<Ayah> getPagePoints(int pageNumber) {

        Cursor cursor = mDbHelper.query("page", null, "page_number = ?", new String[]{String.valueOf(pageNumber)}, null, null, null);

        ArrayList<Ayah> ayah = new ArrayList<>();

        while (cursor.moveToNext()) {
            ayah.add(new Ayah(
                    cursor.getFloat(cursor.getColumnIndex("x")),
                    cursor.getFloat(cursor.getColumnIndex("y")),
                    cursor.getFloat(cursor.getColumnIndex("width")),
                    cursor.getFloat(cursor.getColumnIndex("height")),
                    cursor.getFloat(cursor.getColumnIndex("upper_left_x")),
                    cursor.getFloat(cursor.getColumnIndex("upper_left_y")),
                    cursor.getFloat(cursor.getColumnIndex("upper_right_x")),
                    cursor.getFloat(cursor.getColumnIndex("upper_right_y")),
                    cursor.getFloat(cursor.getColumnIndex("lower_right_x")),
                    cursor.getFloat(cursor.getColumnIndex("lower_right_y")),
                    cursor.getFloat(cursor.getColumnIndex("lower_left_x")),
                    cursor.getFloat(cursor.getColumnIndex("lower_left_y")),
                    cursor.getFloat(cursor.getColumnIndex("ayah")),
                    cursor.getFloat(cursor.getColumnIndex("line")),
                    cursor.getFloat(cursor.getColumnIndex("surah")),
                    cursor.getFloat(cursor.getColumnIndex("page_number")),
                    cursor.getFloat(cursor.getColumnIndex("id"))
            ));
        }
        return ayah;
    }


    public String getPageText(ArrayList<Ayah> ayahs) {

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < ayahs.size(); i++) {
            Cursor cursor = mDbHelper.query("arabic_text", null, "sura =? and ayah =? ", new String[]{String.valueOf(ayahs.get(i).surah), String.valueOf(ayahs.get(i).ayah)}, null, null, null);
            cursor.moveToNext();
            if (!arrayList.contains(cursor.getString(cursor.getColumnIndex("text")))) {
                arrayList.add(cursor.getString(cursor.getColumnIndex("text")));
                stringBuilder.append(cursor.getString(cursor.getColumnIndex("text"))).append("\n");
            }
        }

        return stringBuilder.toString();
    }


    public int getPageNumber(String surahNumber) {

        Cursor cursor = mDbHelper.query("page", null, "surah = ? and id > 10000", new String[]{surahNumber}, null, null, "ayah ASC");
        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndex("page_number"));
    }


    public ArrayList<AhadeethModel> getAhadith() {

        ArrayList<AhadeethModel> ahadith = new ArrayList<>();

        Cursor cursor = mDbHelper.query("HadithTable", null, "HadithGroupID = 0", null, null, null, null);

        while (cursor.moveToNext()) {
            ahadith.add(new AhadeethModel(
                    cursor.getString(cursor.getColumnIndex("HadithSummary")),
                    cursor.getString(cursor.getColumnIndex("HadithFullText"))
            ));
        }
        return ahadith;
    }

    public String getOnFinishQuran() {


        Cursor cursor = mDbHelper.query("HadithTable", null, "HadithGroupID = 1", null, null, null, null);

        String ahadith = "";

        while (cursor.moveToNext()) {
            ahadith = cursor.getString(cursor.getColumnIndex("HadithFullText"));
        }
        return ahadith;
    }

    public String getTr(int surah, int ayah) {

        Cursor cursor = mDbHelper.query("tr", null, "sura =? and aya =?", new String[]{String.valueOf(surah), String.valueOf(ayah)}, null, null, null);

        String ahadith = "";

        while (cursor.moveToNext()) {
            ahadith = cursor.getString(cursor.getColumnIndex("text"));
        }
        return ahadith;
    }

    public String getNozoolReasons(int surah, int ayah) {

        Cursor cursor = mDbHelper.query("NozoolReasons", null, "SoraNo =? and FromAyaNo <=? and ToAyaNo >=?", new String[]{String.valueOf(surah), String.valueOf(ayah), String.valueOf(ayah)}, null, null, null);

        String nozoolReasons = "";

        while (cursor.moveToNext()) {
            nozoolReasons = cursor.getString(cursor.getColumnIndex("Sabab_AlNozool"));
        }
        return nozoolReasons;
    }

    public ArrayList<String> getDataForAyah(int surah, int ayah) {
        ArrayList<String> ayahData = new ArrayList<>();

        Cursor cursor = mDbHelper.query("QuranAdditions", null, "SoraNo =? and AyahNo=? ", new String[]{String.valueOf(surah), String.valueOf(ayah)}, null, null, null);

        while (cursor.moveToNext()) {
            ayahData.add(cursor.getString(cursor.getColumnIndex("E3rab")));
            ayahData.add(cursor.getString(cursor.getColumnIndex("Sarf")));
            ayahData.add(cursor.getString(cursor.getColumnIndex("Blaga")));
        }
        return ayahData;
    }

    public String getMawdoo3OfAyah(int surah, int ayah) {

        Cursor cursor = mDbHelper.query("data", null, "SoraNo =? and FromAyah <=? and ToAyah >=?", new String[]{String.valueOf(surah), String.valueOf(ayah), String.valueOf(ayah)}, null, null, null);

        String data = "";

        while (cursor.moveToNext()) {
            data = cursor.getString(cursor.getColumnIndex("Description"));
        }
        return data;
    }

    public ArrayList<WordsMeaningModel> getWordMeaning(int sorah, int ayah) {
        ArrayList<WordsMeaningModel> words = new ArrayList<>();
        Cursor cursor = mDbHelper.query("WordsMeaning", null, "SoraNo =? and FromAyah <=? and ToAyah >=?", new String[]{"" + sorah, "" + ayah, "" + ayah}, null, null, null);
        while (cursor.moveToNext()) {
            words.add(new WordsMeaningModel(cursor.getString(cursor.getColumnIndex("WordMeaning")), cursor.getString(cursor.getColumnIndex("Word"))));
        }
        return words;
    }

    public ArrayList<Mo3jamModel> getMo3jamWords(int surah, int ayah) {
        ArrayList<Mo3jamModel> words = new ArrayList<>();
        Cursor cursor = mDbHelper.query("QuranMo3jm", null, "SoraNo =? and AyahNo =?", new String[]{String.valueOf(surah), String.valueOf(ayah)}, null, null, null);
        while (cursor.moveToNext()) {
            words.add(new Mo3jamModel(cursor.getString(cursor.getColumnIndex("Root")), cursor.getString(cursor.getColumnIndex("Word"))));
        }
        return words;
    }

    public ArrayList<AlsuraModel> getFahrasData() {
        ArrayList<AlsuraModel> alsuraModels = new ArrayList<>();
        Cursor cursor = mDbHelper.query("fahras", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            alsuraModels.add(new AlsuraModel(
                    cursor.getColumnName(cursor.getColumnIndex("surah")),
                    cursor.getColumnName(cursor.getColumnIndex("surahNo")),
                    cursor.getColumnName(cursor.getColumnIndex("verses")),
                    cursor.getColumnName(cursor.getColumnIndex("place")),
                    cursor.getColumnName(cursor.getColumnIndex("juz")),
                    Integer.parseInt(cursor.getColumnName(cursor.getColumnIndex("page")))
            ));
        }
        return alsuraModels;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public ArrayList<DataMawdo3ColorModel> getPageColors(ArrayList<String> surah, int ayahStart, int ayahEnd) {
        ArrayList<DataMawdo3ColorModel> colors = new ArrayList<>();
        ArrayList<String> values = surah;

        values.add(String.valueOf(ayahStart));
        values.add(String.valueOf(ayahEnd));
        values.add(String.valueOf(ayahStart));
        values.add(String.valueOf(ayahEnd));

        String [] a = new String [values.size()] ;

        Cursor cursor = mDbHelper.query("data", null, "SoraNo in ( " + makePlaceholders(surah.size()) + " ) and ((FromAyah between ? and ?) OR (ToAyah between ? and ?))", values.toArray(a),
        null, null, null);

        while (cursor.moveToNext()) {
            colors.add(new DataMawdo3ColorModel(
                    cursor.getInt(cursor.getColumnIndex("FromAyah")),
                    cursor.getInt(cursor.getColumnIndex("ToAyah")),
                    cursor.getInt(cursor.getColumnIndex("ColorIndex"))
            ));
        }

        return colors;
    }
}