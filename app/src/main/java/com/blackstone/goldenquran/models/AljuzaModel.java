package com.blackstone.goldenquran.models;


public class AljuzaModel {
    String name;
    int number;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {

        return name;
    }

    public int getNumber() {
        return number;
    }

    public AljuzaModel(String name, int number) {

        this.name = name;
        this.number = number;
    }

}