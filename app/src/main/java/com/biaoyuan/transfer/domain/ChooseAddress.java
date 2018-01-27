package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title  :
 * Create : 2017/6/8
 * Author ：chen
 */

public class ChooseAddress implements Parcelable {
    private String address="";
    private String street;
    private String addressAll;

    public String getAddressAll() {
        return addressAll;
    }

    public void setAddressAll(String addressAll) {
        this.addressAll = addressAll;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    private double lat;
    private double lng;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public ChooseAddress() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.street);
        dest.writeString(this.addressAll);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }

    protected ChooseAddress(Parcel in) {
        this.address = in.readString();
        this.street = in.readString();
        this.addressAll = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public static final Parcelable.Creator<ChooseAddress> CREATOR = new Parcelable.Creator<ChooseAddress>() {
        @Override
        public ChooseAddress createFromParcel(Parcel source) {
            return new ChooseAddress(source);
        }

        @Override
        public ChooseAddress[] newArray(int size) {
            return new ChooseAddress[size];
        }
    };
}
