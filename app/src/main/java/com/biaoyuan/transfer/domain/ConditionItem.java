package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title  :发布条件筛选
 * Create : 2017/6/15
 * Author ：chen
 */

public class ConditionItem implements Parcelable {
    private String date;
    private String hour;
    private String time;


    private long startStreetCode=0;
    private long endStreetCode=0;

    private long startCityCode;
    private long endCityCode;

    private String startAddress;
    private String endAddress;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getStartStreetCode() {
        return startStreetCode;
    }

    public void setStartStreetCode(long startStreetCode) {
        this.startStreetCode = startStreetCode;
    }

    public long getEndStreetCode() {
        return endStreetCode;
    }

    public void setEndStreetCode(long endStreetCode) {
        this.endStreetCode = endStreetCode;
    }

    public long getStartCityCode() {
        return startCityCode;
    }

    public void setStartCityCode(long startCityCode) {
        this.startCityCode = startCityCode;
    }

    public long getEndCityCode() {
        return endCityCode;
    }

    public void setEndCityCode(long endCityCode) {
        this.endCityCode = endCityCode;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.hour);
        dest.writeString(this.time);
        dest.writeLong(this.startStreetCode);
        dest.writeLong(this.endStreetCode);
        dest.writeLong(this.startCityCode);
        dest.writeLong(this.endCityCode);
        dest.writeString(this.startAddress);
        dest.writeString(this.endAddress);
    }

    public ConditionItem() {
    }

    protected ConditionItem(Parcel in) {
        this.date = in.readString();
        this.hour = in.readString();
        this.time = in.readString();
        this.startStreetCode = in.readLong();
        this.endStreetCode = in.readLong();
        this.startCityCode = in.readLong();
        this.endCityCode = in.readLong();
        this.startAddress = in.readString();
        this.endAddress = in.readString();
    }

    public static final Parcelable.Creator<ConditionItem> CREATOR = new Parcelable.Creator<ConditionItem>() {
        @Override
        public ConditionItem createFromParcel(Parcel source) {
            return new ConditionItem(source);
        }

        @Override
        public ConditionItem[] newArray(int size) {
            return new ConditionItem[size];
        }
    };
}
