package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.URIParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class URIResult extends Result implements Parcelable {
    private final String uri;
    private final String title;

    public URIResult(URIParsedResult uriParsedResult) {
        this.uri = uriParsedResult.getURI();
        this.title = uriParsedResult.getTitle();
    }

    public String getUri() {
        return uri;
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
        dest.writeString(this.uri);
        dest.writeString(this.title);
    }

    protected URIResult(Parcel in) {
        this.uri = in.readString();
        this.title = in.readString();
    }

    public static final Creator<URIResult> CREATOR = new Creator<URIResult>() {
        @Override
        public URIResult createFromParcel(Parcel source) {
            return new URIResult(source);
        }

        @Override
        public URIResult[] newArray(int size) {
            return new URIResult[size];
        }
    };
}
