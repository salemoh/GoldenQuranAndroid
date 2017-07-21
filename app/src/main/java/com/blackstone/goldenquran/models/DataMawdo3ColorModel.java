package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 4/4/2017.
 */

public class DataMawdo3ColorModel {
    public int fromAyah;
    public int toAyah;
    public int color;

    public DataMawdo3ColorModel(int fromAyah, int toAyah, int color, int soraNo) {
        this.fromAyah = fromAyah;
        this.toAyah = toAyah;
        this.color = color;
        this.soraNo = soraNo;
    }

    public int soraNo;


    public int getFromAyah() {
        return fromAyah;
    }

    public int getToAyah() {
        return toAyah;
    }

    public int getColor() {
        return color;
    }

    public void setFromAyah(int fromAyah) {

        this.fromAyah = fromAyah;
    }

    public void setToAyah(int toAyah) {
        this.toAyah = toAyah;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
