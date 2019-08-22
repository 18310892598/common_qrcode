package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.ProductParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class ProductResult extends Result implements Parcelable {
    private final String productID;
    private final String normalizedProductID;

    public ProductResult(ProductParsedResult productParsedResult) {
        this.productID = productParsedResult.getProductID();
        this.normalizedProductID = productParsedResult.getNormalizedProductID();
    }

    public String getProductID() {
        return productID;
    }

    public String getNormalizedProductID() {
        return normalizedProductID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productID);
        dest.writeString(this.normalizedProductID);
    }

    protected ProductResult(Parcel in) {
        this.productID = in.readString();
        this.normalizedProductID = in.readString();
    }

    public static final Creator<ProductResult> CREATOR = new Creator<ProductResult>() {
        @Override
        public ProductResult createFromParcel(Parcel source) {
            return new ProductResult(source);
        }

        @Override
        public ProductResult[] newArray(int size) {
            return new ProductResult[size];
        }
    };
}
