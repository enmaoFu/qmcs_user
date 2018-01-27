package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title  : 常用地址实体类
 * Create : 2017/6/01
 * Author ：enmaoFu
 */
public class SendCommonAddressInfo implements Parcelable {

    /**
     * addressAddress : 天津市天津和平区新兴街道张家界天门山
     * addressAreaCode : 120101000
     * addressAreaId : 120101004
     * addressCreatTime : 1496483762458
     * addressId : 7
     * addressIng : 106.5616121
     * addressIsDefault : 0
     * addressIsDeleted : 0
     * addressLat : 29.5320335
     * addressName : 张家界天门山
     * addressPhone : 15920501256
     * addressType : 2
     * addressUpdateTime : 1496483762458
     * addressUserId : 17
     * addressVersion : 1
     */

    private String addressAddress;
    private int addressAreaCode;
    private int addressAreaId;
    private long addressCreatTime;
    private int addressId;
    private double addressIng;
    private int addressIsDefault;
    private int addressIsDeleted;
    private double addressLat;
    private String addressName;
    private long addressPhone;
    private int addressType;
    private long addressUpdateTime;
    private int addressUserId;
    private int addressVersion;

    public String getAddressAddress() {
        return addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    public int getAddressAreaCode() {
        return addressAreaCode;
    }

    public void setAddressAreaCode(int addressAreaCode) {
        this.addressAreaCode = addressAreaCode;
    }

    public int getAddressAreaId() {
        return addressAreaId;
    }

    public void setAddressAreaId(int addressAreaId) {
        this.addressAreaId = addressAreaId;
    }

    public long getAddressCreatTime() {
        return addressCreatTime;
    }

    public void setAddressCreatTime(long addressCreatTime) {
        this.addressCreatTime = addressCreatTime;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public double getAddressIng() {
        return addressIng;
    }

    public void setAddressIng(double addressIng) {
        this.addressIng = addressIng;
    }

    public int getAddressIsDefault() {
        return addressIsDefault;
    }

    public void setAddressIsDefault(int addressIsDefault) {
        this.addressIsDefault = addressIsDefault;
    }

    public int getAddressIsDeleted() {
        return addressIsDeleted;
    }

    public void setAddressIsDeleted(int addressIsDeleted) {
        this.addressIsDeleted = addressIsDeleted;
    }

    public double getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(double addressLat) {
        this.addressLat = addressLat;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public long getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(long addressPhone) {
        this.addressPhone = addressPhone;
    }

    public int getAddressType() {
        return addressType;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    public long getAddressUpdateTime() {
        return addressUpdateTime;
    }

    public void setAddressUpdateTime(long addressUpdateTime) {
        this.addressUpdateTime = addressUpdateTime;
    }

    public int getAddressUserId() {
        return addressUserId;
    }

    public void setAddressUserId(int addressUserId) {
        this.addressUserId = addressUserId;
    }

    public int getAddressVersion() {
        return addressVersion;
    }

    public void setAddressVersion(int addressVersion) {
        this.addressVersion = addressVersion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressAddress);
        dest.writeInt(this.addressAreaCode);
        dest.writeInt(this.addressAreaId);
        dest.writeLong(this.addressCreatTime);
        dest.writeInt(this.addressId);
        dest.writeDouble(this.addressIng);
        dest.writeInt(this.addressIsDefault);
        dest.writeInt(this.addressIsDeleted);
        dest.writeDouble(this.addressLat);
        dest.writeString(this.addressName);
        dest.writeLong(this.addressPhone);
        dest.writeInt(this.addressType);
        dest.writeLong(this.addressUpdateTime);
        dest.writeInt(this.addressUserId);
        dest.writeInt(this.addressVersion);
    }

    public SendCommonAddressInfo() {
    }

    protected SendCommonAddressInfo(Parcel in) {
        this.addressAddress = in.readString();
        this.addressAreaCode = in.readInt();
        this.addressAreaId = in.readInt();
        this.addressCreatTime = in.readLong();
        this.addressId = in.readInt();
        this.addressIng = in.readDouble();
        this.addressIsDefault = in.readInt();
        this.addressIsDeleted = in.readInt();
        this.addressLat = in.readDouble();
        this.addressName = in.readString();
        this.addressPhone = in.readLong();
        this.addressType = in.readInt();
        this.addressUpdateTime = in.readLong();
        this.addressUserId = in.readInt();
        this.addressVersion = in.readInt();
    }

    public static final Parcelable.Creator<SendCommonAddressInfo> CREATOR = new Parcelable.Creator<SendCommonAddressInfo>() {
        @Override
        public SendCommonAddressInfo createFromParcel(Parcel source) {
            return new SendCommonAddressInfo(source);
        }

        @Override
        public SendCommonAddressInfo[] newArray(int size) {
            return new SendCommonAddressInfo[size];
        }
    };
}
