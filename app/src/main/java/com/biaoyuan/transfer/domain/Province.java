package com.biaoyuan.transfer.domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Title  :省
 * Create : 2017/5/25
 * Author ：chen
 */

public class Province implements IPickerViewData, Parcelable {
    private int areaCode;
    private String areaName;
    private int areaParentCode;
    private int areaType;


    private List<City> mCityList;

    public List<City> getCityList() {
        return mCityList;
    }

    public void setCityList(List<City> cityList) {
        mCityList = cityList;
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

    public static Creator<Province> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String getPickerViewText() {
        return this.areaName;
    }

    public Province() {
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
        dest.writeTypedList(this.mCityList);
    }

    protected Province(Parcel in) {
        this.areaCode = in.readInt();
        this.areaName = in.readString();
        this.areaParentCode = in.readInt();
        this.areaType = in.readInt();
        this.mCityList = in.createTypedArrayList(City.CREATOR);
    }

    public static final Creator<Province> CREATOR = new Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel source) {
            return new Province(source);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };
}
