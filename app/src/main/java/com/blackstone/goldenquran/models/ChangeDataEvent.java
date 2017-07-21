package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 5/31/2017.
 */

public class ChangeDataEvent {
    private int currentSurah;

    public void setCurrentSurah(int currentSurah) {
        this.currentSurah = currentSurah;
    }

    public int getCurrentSurah() {

        return currentSurah;
    }

    public ChangeDataEvent(int currentSurah) {

        this.currentSurah = currentSurah;
    }
}
