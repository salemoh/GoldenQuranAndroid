package com.blackstone.goldenquran.models;

public class PrayTimeSettingsItemTwoModel {

    public int image;
    public String itemText;
    private boolean isSelected;

    public PrayTimeSettingsItemTwoModel(String itemText) {
        this.itemText = itemText;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {

        return isSelected;
    }
}
