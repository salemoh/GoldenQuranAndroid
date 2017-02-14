package com.blackstone.goldenquran.models;


public class ReaderModel {
    public int shakeImage;
    public String shakeName;
    private boolean isSelected;


    public ReaderModel(int shakeImage, String shakeName) {
        this.shakeImage = shakeImage;
        this.shakeName = shakeName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
