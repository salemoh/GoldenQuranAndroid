package com.blackstone.goldenquran.models.models;

import android.os.Parcel;
import android.os.Parcelable;

public class WordsMeaningModel implements Parcelable {
   public  String meaning, word;

    public WordsMeaningModel(String meaning, String word) {
        this.meaning = meaning;
        this.word = word;
    }

    protected WordsMeaningModel(Parcel in) {
        meaning = in.readString();
        word = in.readString();
    }

    public static final Creator<WordsMeaningModel> CREATOR = new Creator<WordsMeaningModel>() {
        @Override
        public WordsMeaningModel createFromParcel(Parcel in) {
            return new WordsMeaningModel(in);
        }

        @Override
        public WordsMeaningModel[] newArray(int size) {
            return new WordsMeaningModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(meaning);
        parcel.writeString(word);
    }
}
