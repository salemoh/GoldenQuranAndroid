package com.blackstone.goldenquran.models;

/**
 * Created by Abdullah on 12/29/2016.
 */

public class BookmarkModel {
    double mushafGuid, updatedAt, createdAt;
    int suraNo, verseNo, page;

    public void setMushafGuid(double mushafGuid) {
        this.mushafGuid = mushafGuid;
    }

    public void setUpdatedAt(double updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(double createdAt) {
        this.createdAt = createdAt;
    }

    public void setSuraNo(int suraNo) {
        this.suraNo = suraNo;
    }

    public void setVerseNo(int verseNo) {
        this.verseNo = verseNo;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public double getMushafGuid() {

        return mushafGuid;
    }

    public double getUpdatedAt() {
        return updatedAt;
    }

    public double getCreatedAt() {
        return createdAt;
    }

    public int getSuraNo() {
        return suraNo;
    }

    public int getVerseNo() {
        return verseNo;
    }

    public int getPage() {
        return page;
    }

    public BookmarkModel(double mushafGuid, double updatedAt, double createdAt, int suraNo, int verseNo, int page) {

        this.mushafGuid = mushafGuid;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.suraNo = suraNo;
        this.verseNo = verseNo;
        this.page = page;
    }
}
