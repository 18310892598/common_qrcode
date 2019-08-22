package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.GeoParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class GeoResult extends Result implements Parcelable {
    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final String query;

    public GeoResult(GeoParsedResult geoParsedResult) {
        this.latitude = geoParsedResult.getLatitude();
        this.longitude = geoParsedResult.getLongitude();
        this.altitude = geoParsedResult.getAltitude();
        this.query = geoParsedResult.getQuery();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.altitude);
        dest.writeString(this.query);
    }

    protected GeoResult(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.altitude = in.readDouble();
        this.query = in.readString();
    }

    public static final Creator<GeoResult> CREATOR = new Creator<GeoResult>() {
        @Override
        public GeoResult createFromParcel(Parcel source) {
            return new GeoResult(source);
        }

        @Override
        public GeoResult[] newArray(int size) {
            return new GeoResult[size];
        }
    };
}
