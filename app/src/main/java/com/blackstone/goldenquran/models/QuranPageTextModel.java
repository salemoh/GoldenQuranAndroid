package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 5/11/2017.
 */

public class QuranPageTextModel {

    int surah, ayah;
    String quranText;

    public QuranPageTextModel(int surah, int ayah, String quranText) {
        this.surah = surah;
        this.ayah = ayah;
        this.quranText = quranText;
    }

    public int getSurah() {
        return surah;
    }

    public int getAyah() {
        return ayah;
    }

    public String getQuranText() {
        return quranText;
    }
}
