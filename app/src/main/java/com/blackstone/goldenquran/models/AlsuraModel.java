package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 12/25/2016.
 */

public class AlsuraModel {
    public String suraName, number, numberOfAya;

    public AlsuraModel(String number, String numberOfAya, String suraName) {
        this.suraName = suraName;
        this.number = number;
        this.numberOfAya = numberOfAya;
    }
}
