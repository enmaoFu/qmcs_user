package com.biaoyuan.transfer.domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Title  :镇
 * Create : 2017/5/25
 * Author ：chen
 */

public class District implements IPickerViewData, Parcelable {

    private int areaCode;
    private String areaName="无";
    private int areaParentCode;
    private int areaType;
    private ArrayList<Street> mStreets;

    public ArrayList<Street> getStreets() {
        return mStreets;
    }

    public void setStreets(ArrayList<Street> streets) {
        mStreets = streets;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaParentCode() {
        return areaParentCode;
    }

    public void setAreaParentCode(int areaParentCode) {
        this.areaParentCode = areaParentCode;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public static Creator<District> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String getPickerViewText() {
        return this.areaName;
    }

    public District() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.areaCode);
        dest.writeString(this.areaName);
        dest.writeInt(this.areaParentCode);
        dest.writeInt(this.areaType);
        dest.writeTypedList(this.mStreets);
    }

    protected District(Parcel in) {
        this.areaCode = in.readInt();
        this.areaName = in.readString();
        this.areaParentCode = in.readInt();
        this.areaType = in.readInt();
        this.mStreets = in.createTypedArrayList(Street.CREATOR);
    }

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel source) {
            return new District(source);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };
}
