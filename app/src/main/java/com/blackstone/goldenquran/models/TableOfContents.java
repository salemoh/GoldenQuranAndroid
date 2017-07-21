package com.blackstone.goldenquran.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abdullah on 4/20/2017.
 */

public class TableOfContents implements Parcelable {

    protected TableOfContents(Parcel in) {
        mus7afId = in.readInt();
        page = in.readInt();
        juz = in.readInt();
        surah = in.readInt();
        versesCount = in.readInt();
        verse = in.readInt();
        place = in.readInt();
        hizb = in.readFloat();
    }

    public static final Creator<TableOfContents> CREATOR = new Creator<TableOfContents>() {
        @Override
        public TableOfContents createFromParcel(Parcel in) {
            return new TableOfContents(in);
        }

        @Override
        public TableOfContents[] newArray(int size) {
            return new TableOfContents[size];
        }
    };

    public void setMus7afId(int mus7afId) {
        this.mus7afId = mus7afId;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setJuz(int juz) {
        this.juz = juz;
    }

    public void setSurah(int surah) {
        this.surah = surah;
    }

    public void setVersesCount(int versesCount) {
        this.versesCount = versesCount;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setHizb(float hizb) {
        this.hizb = hizb;
    }

    public int getMus7afId() {

        return mus7afId;
    }

    public int getPage() {
        return page;
    }

    public int getJuz() {
        return juz;
    }

    public int getSurah() {
        return surah;
    }

    public int getVersesCount() {
        return versesCount;
    }

    public int getVerse() {
        return verse;
    }

    public int getPlace() {
        return place;
    }

    public float getHizb() {
        return hizb;
    }

    int mus7afId, page, juz, surah, versesCount, verse, place;
    float hizb;

    public TableOfContents(int mus7afId, int page, int juz, int surah, int versesCount, int verse, int place, float hizb) {
        this.mus7afId = mus7afId;
        this.page = page;
        this.juz = juz;
        this.surah = surah;
        this.versesCount = versesCount;
        this.verse = verse;
        this.place = place;
        this.hizb = hizb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mus7afId);
        parcel.writeInt(page);
        parcel.writeInt(juz);
        parcel.writeInt(surah);
        parcel.writeInt(versesCount);
        parcel.writeInt(verse);
        parcel.writeInt(place);
        parcel.writeFloat(hizb);
    }
}
