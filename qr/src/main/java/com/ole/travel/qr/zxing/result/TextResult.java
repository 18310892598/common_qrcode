package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.TextParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class TextResult extends Result implements Parcelable {
    private final String text;
    private final String language;

    public TextResult(TextParsedResult textParsedResult) {
        this.text = textParsedResult.getText();
        this.language = textParsedResult.getLanguage();
    }

    public String getText() {
        return text;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.language);
    }

    protected TextResult(Parcel in) {
        this.text = in.readString();
        this.language = in.readString();
    }

    public static final Creator<TextResult> CREATOR = new Creator<TextResult>() {
        @Override
        public TextResult createFromParcel(Parcel source) {
            return new TextResult(source);
        }

        @Override
        public TextResult[] newArray(int size) {
            return new TextResult[size];
        }
    };
}
