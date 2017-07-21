package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 5/17/2017.
 */

public class RecitationModel {
    int id;
    String reader;
    String type;
    String baseUrl;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {

        return isSelected;
    }

    boolean isSelected;


    public int getId() {
        return id;
    }

    public String getReader() {
        return reader;
    }

    public String getType() {
        return type;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getName() {
        return name;
    }

    public RecitationModel(int id, String reader, String type, String baseUrl, String name) {

        this.id = id;
        this.reader = reader;
        this.type = type;
        this.baseUrl = baseUrl;
        this.name = name;
    }

    String name;
}
