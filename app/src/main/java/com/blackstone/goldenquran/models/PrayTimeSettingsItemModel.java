package com.blackstone.goldenquran.models;

public class PrayTimeSettingsItemModel {
    public int image;
    public String itemText;
    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public PrayTimeSettingsItemModel(String itemText) {
        this.itemText = itemText;
    }

}
