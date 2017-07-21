package com.blackstone.goldenquran.models;

import java.util.ArrayList;

/**
 * Created by Abdullah on 7/9/2017.
 */

public class TOCData {
    ArrayList<TableOfContents> toc;

    public void setToc(ArrayList<TableOfContents> toc) {
        this.toc = toc;
    }

    public TOCData(ArrayList<TableOfContents> toc) {

        this.toc = toc;
    }

    public ArrayList<TableOfContents> getToc() {

        return toc;
    }
}
