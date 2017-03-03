package com.blackstone.goldenquran.models;

public class AhadeethModel {
    public String title, body;

    public AhadeethModel(String title) {
        this.title = title;
    }

    public AhadeethModel(String title, String body) {
        this.title = title;
        this.body = body;
    }
}

