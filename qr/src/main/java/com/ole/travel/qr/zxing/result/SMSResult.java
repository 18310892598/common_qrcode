package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.SMSParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class SMSResult extends Result implements Parcelable {
    private final String[] numbers;
    private final String[] vias;
    private final String subject;
    private final String body;

    public SMSResult(SMSParsedResult smsParsedResult) {
        this.numbers = smsParsedResult.getNumbers();
        this.vias = smsParsedResult.getVias();
        this.subject = smsParsedResult.getSubject();
        this.body = smsParsedResult.getBody();
    }

    public String[] getNumbers() {
        return numbers;
    }

    public String[] getVias() {
        return vias;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.numbers);
        dest.writeStringArray(this.vias);
        dest.writeString(this.subject);
        dest.writeString(this.body);
    }

    protected SMSResult(Parcel in) {
        this.numbers = in.createStringArray();
        this.vias = in.createStringArray();
        this.subject = in.readString();
        this.body = in.readString();
    }

    public static final Creator<SMSResult> CREATOR = new Creator<SMSResult>() {
        @Override
        public SMSResult createFromParcel(Parcel source) {
            return new SMSResult(source);
        }

        @Override
        public SMSResult[] newArray(int size) {
            return new SMSResult[size];
        }
    };
}
