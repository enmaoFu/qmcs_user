package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Title  :街道
 * Create : 2017/5/25
 * Author ：chen
 */

public class Street implements IPickerViewData, Parcelable {

    private int areaCode;
    private String areaName;
    private int areaParentCode;
    private int areaType;


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

    public static Creator<Street> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String getPickerViewText() {
        return this.areaName;
    }

    public Street() {
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
    }

    protected Street(Parcel in) {
        this.areaCode = in.readInt();
        this.areaName = in.readString();
        this.areaParentCode = in.readInt();
        this.areaType = in.readInt();
    }

    public static final Creator<Street> CREATOR = new Creator<Street>() {
        @Override
        public Street createFromParcel(Parcel source) {
            return new Street(source);
        }

        @Override
        public Street[] newArray(int size) {
            return new Street[size];
        }
    };
}
