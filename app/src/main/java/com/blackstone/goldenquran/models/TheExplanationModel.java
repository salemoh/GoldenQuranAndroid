package com.blackstone.goldenquran.models;

public class TheExplanationModel {
    public String tafseerName, tafseerDescribtion;
    private boolean isSelected;

    public TheExplanationModel(String tafseerName, String tafseerDescribtion) {
        this.tafseerName = tafseerName;
        this.tafseerDescribtion = tafseerDescribtion;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
