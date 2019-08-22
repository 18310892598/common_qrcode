package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.ISBNParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class ISBNResult extends Result implements Parcelable {
    private final String isbn;

    public ISBNResult(ISBNParsedResult isbnParsedResult) {
        this.isbn = isbnParsedResult.getISBN();
    }

    public String getISBN() {
        return isbn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.isbn);
    }

    protected ISBNResult(Parcel in) {
        this.isbn = in.readString();
    }

    public static final Creator<ISBNResult> CREATOR = new Creator<ISBNResult>() {
        @Override
        public ISBNResult createFromParcel(Parcel source) {
            return new ISBNResult(source);
        }

        @Override
        public ISBNResult[] newArray(int size) {
            return new ISBNResult[size];
        }
    };
}
