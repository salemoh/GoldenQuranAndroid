package com.blackstone.goldenquran.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Mo3jamModel implements Parcelable {

    public String wordRoot, wordMo3jam;

    public Mo3jamModel(String wordRoot, String wordMo3jam) {
        this.wordRoot = wordRoot;
        this.wordMo3jam = wordMo3jam;
    }

    protected Mo3jamModel(Parcel in) {
        wordRoot = in.readString();
        wordMo3jam = in.readString();
    }

    public static final Creator<Mo3jamModel> CREATOR = new Creator<Mo3jamModel>() {
        @Override
        public Mo3jamModel createFromParcel(Parcel in) {
            return new Mo3jamModel(in);
        }

        @Override
        public Mo3jamModel[] newArray(int size) {
            return new Mo3jamModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wordRoot);
        parcel.writeString(wordMo3jam);
    }
}
