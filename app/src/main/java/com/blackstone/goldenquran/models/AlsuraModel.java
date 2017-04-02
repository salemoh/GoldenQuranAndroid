package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 12/25/2016.
 */

public class AlsuraModel {
    public String suraName, number, numberOfAya, place, juz;
    public int pageNumber;

    public AlsuraModel(String number, String numberOfAya, String suraName) {
        this.suraName = suraName;
        this.number = number;
        this.numberOfAya = numberOfAya;
    }

    public AlsuraModel(String suraName, String number, String numberOfAya, String place, String juz, int pageNumber) {
        this.suraName = suraName;
        this.number = number;
        this.numberOfAya = numberOfAya;
        this.place = place;
        this.juz = juz;
        this.pageNumber = pageNumber;
    }
}
