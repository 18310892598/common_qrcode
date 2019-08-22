package com.ole.travel.qr.zxing.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.client.result.AddressBookParsedResult;

/**
 * Created by hupei on 2016/8/12.
 */
public class AddressBookResult extends Result implements Parcelable {
    private final String[] names;
    private final String[] nicknames;
    private final String pronunciation;
    private final String[] phoneNumbers;
    private final String[] phoneTypes;
    private final String[] emails;
    private final String[] emailTypes;
    private final String instantMessenger;
    private final String note;
    private final String[] addresses;
    private final String[] addressTypes;
    private final String org;
    private final String birthday;
    private final String title;
    private final String[] urls;
    private final String[] geo;

    public AddressBookResult(AddressBookParsedResult addressBookParsedResult) {
        this.names = addressBookParsedResult.getNames();
        this.nicknames = addressBookParsedResult.getNicknames();
        this.pronunciation = addressBookParsedResult.getPronunciation();
        this.phoneNumbers = addressBookParsedResult.getPhoneNumbers();
        this.phoneTypes = addressBookParsedResult.getPhoneTypes();
        this.emails = addressBookParsedResult.getEmails();
        this.emailTypes = addressBookParsedResult.getEmailTypes();
        this.instantMessenger = addressBookParsedResult.getInstantMessenger();
        this.note = addressBookParsedResult.getNote();
        this.addresses = addressBookParsedResult.getAddresses();
        this.addressTypes = addressBookParsedResult.getAddressTypes();
        this.org = addressBookParsedResult.getOrg();
        this.birthday = addressBookParsedResult.getBirthday();
        this.title = addressBookParsedResult.getTitle();
        this.urls = addressBookParsedResult.getURLs();
        this.geo = addressBookParsedResult.getGeo();
    }

    public String[] getNames() {
        return names;
    }

    public String[] getNicknames() {
        return nicknames;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public String[] getPhoneTypes() {
        return phoneTypes;
    }

    public String[] getEmails() {
        return emails;
    }

    public String[] getEmailTypes() {
        return emailTypes;
    }

    public String getInstantMessenger() {
        return instantMessenger;
    }

    public String getNote() {
        return note;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public String[] getAddressTypes() {
        return addressTypes;
    }

    public String getOrg() {
        return org;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getTitle() {
        return title;
    }

    public String[] getUrls() {
        return urls;
    }

    public String[] getGeo() {
        return geo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.names);
        dest.writeStringArray(this.nicknames);
        dest.writeString(this.pronunciation);
        dest.writeStringArray(this.phoneNumbers);
        dest.writeStringArray(this.phoneTypes);
        dest.writeStringArray(this.emails);
        dest.writeStringArray(this.emailTypes);
        dest.writeString(this.instantMessenger);
        dest.writeString(this.note);
        dest.writeStringArray(this.addresses);
        dest.writeStringArray(this.addressTypes);
        dest.writeString(this.org);
        dest.writeString(this.birthday);
        dest.writeString(this.title);
        dest.writeStringArray(this.urls);
        dest.writeStringArray(this.geo);
    }

    protected AddressBookResult(Parcel in) {
        this.names = in.createStringArray();
        this.nicknames = in.createStringArray();
        this.pronunciation = in.readString();
        this.phoneNumbers = in.createStringArray();
        this.phoneTypes = in.createStringArray();
        this.emails = in.createStringArray();
        this.emailTypes = in.createStringArray();
        this.instantMessenger = in.readString();
        this.note = in.readString();
        this.addresses = in.createStringArray();
        this.addressTypes = in.createStringArray();
        this.org = in.readString();
        this.birthday = in.readString();
        this.title = in.readString();
        this.urls = in.createStringArray();
        this.geo = in.createStringArray();
    }

    public static final Creator<AddressBookResult> CREATOR = new Creator<AddressBookResult>() {
        @Override
        public AddressBookResult createFromParcel(Parcel source) {
            return new AddressBookResult(source);
        }

        @Override
        public AddressBookResult[] newArray(int size) {
            return new AddressBookResult[size];
        }
    };
}
