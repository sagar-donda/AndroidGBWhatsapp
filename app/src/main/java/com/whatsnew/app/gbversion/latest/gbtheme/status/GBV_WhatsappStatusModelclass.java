package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.os.Parcel;
import android.os.Parcelable;


public class GBV_WhatsappStatusModelclass implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        @Override
        public GBV_WhatsappStatusModelclass createFromParcel(Parcel parcel) {
            return new GBV_WhatsappStatusModelclass(parcel);
        }

        @Override
        public GBV_WhatsappStatusModelclass[] newArray(int i) {
            return new GBV_WhatsappStatusModelclass[i];
        }
    };
    String name;
    String path;
    String uri;

    @Override
    public int describeContents() {
        return 0;
    }

    public GBV_WhatsappStatusModelclass(String str, String str2, String str3) {
        this.uri = str;
        this.name = str2;
        this.path = str3;
    }

    public GBV_WhatsappStatusModelclass(Parcel parcel) {
        String[] strArr = new String[3];
        parcel.readStringArray(strArr);
        this.uri = strArr[0];
        this.name = strArr[1];
        this.path = strArr[2];
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.uri, this.name, this.path});
    }
}
