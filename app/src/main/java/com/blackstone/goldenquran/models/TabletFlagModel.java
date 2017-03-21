package com.blackstone.goldenquran.models;

public class TabletFlagModel {

    public int flagImage;
    public String countryName;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {

        return isSelected;
    }

    boolean isSelected;

    public TabletFlagModel(int flagImage, String countryName) {
        this.flagImage = flagImage;
        this.countryName = countryName;
    }

}
