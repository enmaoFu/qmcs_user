package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title  :
 * Create : 2017/6/3
 * Author ：chen
 */

public class AddAddressItem implements Parcelable {
    private String name;
    private long phone;
    private String address;
    private double lng;
    private double lat;
    private int type;
    private int areaCode;
    private int areaId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public AddAddressItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.phone);
        dest.writeString(this.address);
        dest.writeDouble(this.lng);
        dest.writeDouble(this.lat);
        dest.writeInt(this.type);
        dest.writeInt(this.areaCode);
        dest.writeInt(this.areaId);
    }

    protected AddAddressItem(Parcel in) {
        this.name = in.readString();
        this.phone = in.readLong();
        this.address = in.readString();
        this.lng = in.readDouble();
        this.lat = in.readDouble();
        this.type = in.readInt();
        this.areaCode = in.readInt();
        this.areaId = in.readInt();
    }

    public static final Parcelable.Creator<AddAddressItem> CREATOR = new Parcelable.Creator<AddAddressItem>() {
        @Override
        public AddAddressItem createFromParcel(Parcel source) {
            return new AddAddressItem(source);
        }

        @Override
        public AddAddressItem[] newArray(int size) {
            return new AddAddressItem[size];
        }
    };
}
