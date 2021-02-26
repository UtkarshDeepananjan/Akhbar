package com.uds.akhbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Source implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    protected Source(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }

    public Source(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Source() {
    }
}