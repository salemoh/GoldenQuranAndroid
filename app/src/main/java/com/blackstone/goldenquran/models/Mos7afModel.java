package com.blackstone.goldenquran.models;

public class Mos7afModel {
    public Mos7afModel(String mos7afName, String type) {
        this.mos7afName = mos7afName;
        this.type = type;
    }

    public Mos7afModel(int id, int nuberOfPages, int startOffset, String mos7afName, String type, String baseURL, String dbName, String imagesNameFormat) {
        this.id = id;
        this.nuberOfPages = nuberOfPages;
        this.startOffset = startOffset;
        this.mos7afName = mos7afName;
        this.type = type;
        this.baseURL = baseURL;
        this.dbName = dbName;
        this.imagesNameFormat = imagesNameFormat;
    }

    public int id, nuberOfPages, startOffset;
    public String mos7afName, type, baseURL, dbName, imagesNameFormat;

}
