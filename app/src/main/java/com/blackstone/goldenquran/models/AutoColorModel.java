package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 5/30/2017.
 */

public class AutoColorModel {
    public void setAyahNumber(int ayahNumber) {
        this.ayahNumber = ayahNumber;
    }

    public void setSurahNumber(int surahNumber) {
        this.surahNumber = surahNumber;
    }

    public int getAyahNumber() {

        return ayahNumber;
    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public AutoColorModel(int ayahNumber, int surahNumber) {

        this.ayahNumber = ayahNumber;
        this.surahNumber = surahNumber;
    }

    private int ayahNumber, surahNumber;

}
