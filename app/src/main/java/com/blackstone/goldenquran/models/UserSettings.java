package com.blackstone.goldenquran.models;

public class UserSettings {
    private static UserSettings ourInstance = new UserSettings();
    private City userCity;

    public static UserSettings getInstance() {
        return ourInstance;
    }

    private UserSettings() {

    }

    public City getUserCity(){
        return userCity;
    }

    public void setUserCity(City city){
        userCity=city;
    }
}
