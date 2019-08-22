package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.TelParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class TelResult extends Result implements Parcelable {
    private final String number;
    private final String telURI;
    private final String title;

    public TelResult(TelParsedResult telParsedResult) {
        this.number = telParsedResult.getNumber();
        this.telURI = telParsedResult.getTelURI();
        this.title = telParsedResult.getTitle();
    }

    public String getNumber() {
        return number;
    }

    public String getTelURI() {
        return telURI;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeString(this.telURI);
        dest.writeString(this.title);
    }

    protected TelResult(Parcel in) {
        this.number = in.readString();
        this.telURI = in.readString();
        this.title = in.readString();
    }

    public static final Creator<TelResult> CREATOR = new Creator<TelResult>() {
        @Override
        public TelResult createFromParcel(Parcel source) {
            return new TelResult(source);
        }

        @Override
        public TelResult[] newArray(int size) {
            return new TelResult[size];
        }
    };
}
