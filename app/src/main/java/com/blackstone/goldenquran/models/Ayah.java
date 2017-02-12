package com.blackstone.goldenquran.models;

public class Ayah {
    public float x, y, width, height, upperLeftX, upperLeftY, upperRightX, upperRightY, lowerRightX, lowerRightY, lowerLeftX, lowerLeftY, ayah, line, surah, pageNumber, id;

    public Ayah(float x, float y, float width, float height, float upperLeftX, float upperLeftY, float upperRightX, float upperRightY, float lowerRightX, float lowerRightY, float lowerLeftX, float lowerLeftY, float ayah, float line, float surah, float pageNumber, float id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.upperRightX = upperRightX;
        this.upperRightY = upperRightY;
        this.lowerRightX = lowerRightX;
        this.lowerRightY = lowerRightY;
        this.lowerLeftX = lowerLeftX;
        this.lowerLeftY = lowerLeftY;
        this.ayah = ayah;
        this.line = line;
        this.surah = surah;
        this.pageNumber = pageNumber;
        this.id = id;
    }

}
