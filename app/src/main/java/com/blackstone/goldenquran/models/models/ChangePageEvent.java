package com.blackstone.goldenquran.models.models;

/**
 * Created by Abdullah on 5/31/2017.
 */

public class ChangePageEvent {
    private int pageNumber;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {

        return pageNumber;
    }

    public ChangePageEvent(int pageNumber) {

        this.pageNumber = pageNumber;
    }
}
