package com.blackstone.goldenquran.models.models;

/**
 * Created by Abdullah on 5/5/2017.
 */

public class ColorEvent {

    public void setColorOn(boolean colorOn) {
        isColorOn = colorOn;
    }

    public boolean isColorOn() {

        return isColorOn;
    }

    boolean isColorOn;

    public ColorEvent(boolean isColorOn) {
        this.isColorOn = isColorOn;
    }
}
