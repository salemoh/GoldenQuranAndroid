package com.blackstone.goldenquran.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AhadeethModel implements Parcelable {
    public String title, body;

    public AhadeethModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public AhadeethModel(Parcel in) {
        title = in.readString();
        body = in.readString();
    }

    public static final Creator<AhadeethModel> CREATOR = new Creator<AhadeethModel>() {
        @Override
        public AhadeethModel createFromParcel(Parcel in) {
            return new AhadeethModel(in);
        }

        @Override
        public AhadeethModel[] newArray(int size) {
            return new AhadeethModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
    }
}

