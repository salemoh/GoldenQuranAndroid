package com.blackstone.goldenquran.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blackstone.goldenquran.models.AhadeethModel;
import com.blackstone.goldenquran.models.Ayah;
import com.blackstone.goldenquran.models.BookmarkModel;
import com.blackstone.goldenquran.models.DataMawdo3ColorModel;
import com.blackstone.goldenquran.models.Mo3jamModel;
import com.blackstone.goldenquran.models.Mos7afModel;
import com.blackstone.goldenquran.models.QuranPageTextModel;
import com.blackstone.goldenquran.models.RecitationModel;
import com.blackstone.goldenquran.models.SearchMawdoo3Model;
import com.blackstone.goldenquran.models.TableOfContents;
import com.blackstone.goldenquran.models.models.WordsMeaningModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {

    private static final String TAG = "DataAdapter";
    private DBHelperFromAssets mDBHelperFromAssets;
    private StorageDBHelper mStorageDBHelper;
    private SQLiteDatabase mDbHelper;
    private SQLiteDatabase mDbHelper1;


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
                mDbHelper1 = mDBHelperFromAssets.getWritableDatabase();
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

    public int getSurahFromPageNumber(int pageNumber) {
        Cursor cursor = mDbHelper.query("page", null, "page_number = ?", new String[]{String.valueOf(pageNumber)}, null, null, null, "1");

        if (cursor.moveToNext()) {

            return cursor.getInt(cursor.getColumnIndex("surah"));

        }
        return 0;
    }


    public String getPageText(ArrayList<Ayah> ayahs) {

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>();

        ArrayList<Integer> suras = new ArrayList<>();
        if (ayahs != null && !ayahs.isEmpty())
            suras.add((int) ayahs.get(0).surah);
        for (int i = 1; i < ayahs.size(); i++) {
            if (!suras.contains((int) ayahs.get(i).surah))
                suras.add((int) ayahs.get(i).surah);
        }

        ArrayList<Integer> ayat = new ArrayList<>();
        for (int i = 0; i < suras.size(); i++) {
            for (int j = 0; j < ayahs.size(); j++) {
                if (ayahs.get(j).surah == suras.get(i)) {
                    ayat.add((int) ayahs.get(j).ayah);
                }
            }
            ayat.add(1000);
        }
        ArrayList<Integer> ayat1 = new ArrayList<>(ayat);

        ArrayList<Integer> ayatBySura = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < suras.size(); i++) {
            ayatBySura.add(suras.get(i));
            if (ayat.contains(1000)) {
                int toIndex = ayat.indexOf(1000);
                List subList = ayat.subList(index, toIndex);
                ayatBySura.addAll(subList);
                ayat.remove(toIndex);
                index = toIndex;
            }
        }

        String query = "";
        index = 0;
        for (int i = 0; i < suras.size(); i++) {
            if (!query.isEmpty())
                query += " or ";
            int numberOfAyat = 1;
            if (ayat1.contains(1000)) {
                int toIndex = ayat1.indexOf(1000);
                List subList = ayat1.subList(index, toIndex);
                numberOfAyat = subList.size();
                ayat1.remove(toIndex);
                index = toIndex;
            }
            query += "(sura =? and ayah in(" + makePlaceholders(numberOfAyat) + "))";
        }

        String[] a = new String[ayatBySura.size()];
        for (int i = 0; i < ayatBySura.size(); i++) {
            a[i] = ayatBySura.get(i).toString();
        }

        Cursor cursor = mDbHelper.query("arabic_text", null, query, a, null, null, null);

        while (cursor.moveToNext()) {
            if (!arrayList.contains(cursor.getString(cursor.getColumnIndex("text")))) {
                arrayList.add(cursor.getString(cursor.getColumnIndex("text")));
                stringBuilder.append(cursor.getString(cursor.getColumnIndex("text"))).append(" \n\n");

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

    public ArrayList<SearchMawdoo3Model> getAllMawdoo3() {
        ArrayList<SearchMawdoo3Model> arrayList = new ArrayList<>();

        Cursor cursor = mDbHelper.query("data", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            arrayList.add(new SearchMawdoo3Model(cursor.getString(cursor.getColumnIndex("Description"))));
        }

        return arrayList;
    }

    public ArrayList<QuranPageTextModel> getAllQuranAyat() {
        ArrayList<QuranPageTextModel> arrayList = new ArrayList<>();

        Cursor cursor = mDbHelper.query("verses", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            arrayList.add(new QuranPageTextModel(cursor.getInt(cursor.getColumnIndex("sura")), cursor.getInt(cursor.getColumnIndex("ayah")), cursor.getString(cursor.getColumnIndex("text"))));
        }

        return arrayList;
    }

    public String getFridayReading() {
        StringBuilder fridayReadingText = new StringBuilder();

        Cursor cursor = mDbHelper.query("arabic_text", null, "sura = 18", null, null, null, null);
        while (cursor.moveToNext()) {
            fridayReadingText.append(cursor.getString(cursor.getColumnIndex("text"))).append("\n\n");
        }

        return fridayReadingText.toString();
    }

    public ArrayList<QuranPageTextModel> getNightReading() {

        ArrayList<QuranPageTextModel> arrayList = new ArrayList<>();

        Cursor cursor = mDbHelper.query("arabic_text", null, "(sura = 2 and ayah in(255, 285, 286)) or (sura in(67, 112, 113, 114))", null, null, null, null);
        while (cursor.moveToNext()) {
            arrayList.add(new QuranPageTextModel(cursor.getInt(cursor.getColumnIndex("sura")), cursor.getInt(cursor.getColumnIndex("ayah")), cursor.getString(cursor.getColumnIndex("text"))));
        }

        return arrayList;
    }

    public void addBookmarck(Ayah ayah) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("soraNo", ayah.surah);
        contentValues.put("page", ayah.pageNumber);
        contentValues.put("verseNo", ayah.ayah);

        mDbHelper1.insert("Bookmark", null, contentValues);

    }

    public void removeBookmark(Ayah ayah) {
        mDbHelper1.delete("Bookmark", "page =? ", new String[]{"" + ayah.pageNumber});

    }

    public ArrayList<BookmarkModel> getAllBookmark() {
        ArrayList<BookmarkModel> arrayList = new ArrayList<>();

        Cursor cursor = mDbHelper.query("Bookmark", null, "soraNo > 0", null, null, null, null);

        while (cursor.moveToNext()) {
            arrayList.add(new BookmarkModel(
                    cursor.getDouble(cursor.getColumnIndex("mushafGuid")),
                    0.0,
                    0.0,
                    cursor.getInt(cursor.getColumnIndex("soraNo")),
                    cursor.getInt(cursor.getColumnIndex("verseNo")),
                    cursor.getInt(cursor.getColumnIndex("page"))
            ));
        }

        return arrayList;
    }

    public String getTafseerForAyah(int surah, int ayah) {
        String s = "";

        Cursor cursor = mDbHelper.query("verses", null, "sura =" + surah + " and ayah =" + ayah, null, null, null, null);

        while (cursor.moveToNext()) {
            s += cursor.getString(cursor.getColumnIndex("text"));
        }

        return s;
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


    public ArrayList<RecitationModel> getReaders() {
        ArrayList<RecitationModel> recitationModels = new ArrayList<>();

        Cursor cursor = mDbHelper.query("recitation", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            recitationModels.add(new RecitationModel(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("reader")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("baseUrl")),
                    cursor.getString(cursor.getColumnIndex("name"))
            ));
        }

        return recitationModels;
    }

    public ArrayList<TableOfContents> getFahrasData() {
        ArrayList<TableOfContents> contents = new ArrayList<>();

        Cursor cursor = mDbHelper.query("tableOfContents", null, "Sora between 1 and 114", null, null, null, "Juz ASC");

        while (cursor.moveToNext()) {
            contents.add(new TableOfContents(
                    cursor.getInt(cursor.getColumnIndex("MushafID")),
                    cursor.getInt(cursor.getColumnIndex("Page")),
                    cursor.getInt(cursor.getColumnIndex("Juz")),
                    cursor.getInt(cursor.getColumnIndex("Sora")),
                    cursor.getInt(cursor.getColumnIndex("VersesCount")),
                    cursor.getInt(cursor.getColumnIndex("Verse")),
                    cursor.getInt(cursor.getColumnIndex("Place")),
                    cursor.getFloat(cursor.getColumnIndex("Hizb"))
            ));
        }

        return contents;
    }

    public int getVersesCountForSora(int soraNumber) {


        Cursor cursor = mDbHelper.query("tableOfContents", null, "Sora between 1 and 114 AND Sora =" + soraNumber, null, null, null, "Juz ASC", "1");

        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("VersesCount"));
        }

        return 0;
    }

    public ArrayList<Mo3jamModel> getAllMo3jam() {
        ArrayList<Mo3jamModel> arrayList = new ArrayList<>();

        Cursor cursor = mDbHelper.query("QuranMo3jm", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            arrayList.add(new Mo3jamModel(cursor.getString(cursor.getColumnIndex("Root")), cursor.getString(cursor.getColumnIndex("Word"))));
        }

        return arrayList;
    }


    public ArrayList<Mos7afModel> getMos7afs() {
        ArrayList<Mos7afModel> mos7afModels = new ArrayList<>();

        Cursor cursor = mDbHelper.query("Mus7af", null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            mos7afModels.add(new Mos7afModel(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("numberOfPages")),
                    cursor.getInt(cursor.getColumnIndex("startOffset")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("baseDownloadURL")),
                    cursor.getString(cursor.getColumnIndex("dbName")),
                    cursor.getString(cursor.getColumnIndex("imagesNameFormat"))
            ));
        }

        return mos7afModels;
    }

    public ArrayList<DataMawdo3ColorModel> getPageColors(ArrayList<Ayah> ayahs) {
        ArrayList<DataMawdo3ColorModel> colors = new ArrayList<>();

        ArrayList<Integer> suras = new ArrayList<>();
        if (ayahs != null && !ayahs.isEmpty())
            suras.add((int) ayahs.get(0).surah);
        for (int i = 1; i < ayahs.size(); i++) {
            if (!suras.contains((int) ayahs.get(i).surah))
                suras.add((int) ayahs.get(i).surah);
        }

        ArrayList<Integer> ayat = new ArrayList<>();
        for (int i = 0; i < suras.size(); i++) {
            for (int j = 0; j < ayahs.size(); j++) {
                if (ayahs.get(j).surah == suras.get(i)) {
                    ayat.add((int) ayahs.get(j).ayah);
                }
            }
            ayat.add(1000);
        }
        ArrayList<Integer> ayat1 = new ArrayList<>(ayat);
        ArrayList<Integer> ayatBySura = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < suras.size(); i++) {
            ayatBySura.add(suras.get(i));
            if (ayat.contains(1000)) {
                int toIndex = ayat.indexOf(1000);
                List subList = ayat.subList(index, toIndex);
                ayatBySura.add((Integer) subList.get(0));
                ayatBySura.add((Integer) subList.get(subList.size() - 1));
                ayatBySura.add((Integer) subList.get(0));
                ayatBySura.add((Integer) subList.get(subList.size() - 1));
                ayat.remove(toIndex);
                index = toIndex;
            }
        }

        String query = "";
        index = 0;
        for (int i = 0; i < suras.size(); i++) {
            if (!query.isEmpty())
                query += " or ";
            int numberOfAyat = 1;
            if (ayat1.contains(1000)) {
                int toIndex = ayat1.indexOf(1000);
                List subList = ayat1.subList(index, toIndex);
                ayat1.remove(toIndex);
                index = toIndex;
            }
            query += "((SoraNo = ? ) and ((FromAyah between ? and ?) or (ToAyah between ? and ?)))";
        }

        String[] a = new String[ayatBySura.size()];
        for (int i = 0; i < ayatBySura.size(); i++) {
            a[i] = ayatBySura.get(i).toString();
        }

        Cursor cursor = mDbHelper.query("data", null, query, a, null, null, null);

        while (cursor.moveToNext()) {
            colors.add(new DataMawdo3ColorModel(
                    cursor.getInt(cursor.getColumnIndex("FromAyah")),
                    cursor.getInt(cursor.getColumnIndex("ToAyah")),
                    cursor.getInt(cursor.getColumnIndex("ColorIndex")),
                    cursor.getInt(cursor.getColumnIndex("SoraNo"))
            ));
        }

        return colors;
    }

    public int getSurahTotalAyaNumber(int currentSurah) {

        Cursor cursor = mDbHelper.query("page", new String[]{"Count(*)"}, "surah = ?", new String[]{String.valueOf(currentSurah)}, null, null, null);

        while (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return 0;
    }
}