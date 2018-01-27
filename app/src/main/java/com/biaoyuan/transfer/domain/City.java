package com.biaoyuan.transfer.domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Title  :城市
 * Create : 2017/5/25
 * Author ：chen
 */

public class City implements IPickerViewData,Parcelable {
    private int areaCode;
    private String areaName;
    private int areaParentCode;
    private int areaType;
    private List<District> mDistricts;

    public List<District> getDistricts() {
        return mDistricts;
    }

    public void setDistricts(List<District> districts) {
        mDistricts = districts;
    }


    @Override
    public String getPickerViewText() {
        return this.areaName;
    }

    public City() {
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

    public static Creator<City> getCREATOR() {
        return CREATOR;
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
        dest.writeTypedList(this.mDistricts);
    }

    protected City(Parcel in) {
        this.areaCode = in.readInt();
        this.areaName = in.readString();
        this.areaParentCode = in.readInt();
        this.areaType = in.readInt();
        this.mDistricts = in.createTypedArrayList(District.CREATOR);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
