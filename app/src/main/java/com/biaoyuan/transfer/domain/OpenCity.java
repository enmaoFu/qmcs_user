package com.biaoyuan.transfer.domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Title  :城市
 * Create : 2017/5/25
 * Author ：chen
 */

public class OpenCity implements IPickerViewData,Parcelable,IndexableEntity {
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
    public String getFieldIndexBy() {
        if (this.areaName.equals("重庆")||this.areaName.equals("重庆市")){
            return "#chongqing#重庆市";
        }
        return this.areaName;
    }

    @Override
    public void setFieldIndexBy(String indexField) {

        this.areaName=indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {


    }


    @Override
    public String getPickerViewText() {
        return this.areaName;
    }

    public OpenCity() {
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

    public static Creator<OpenCity> getCREATOR() {
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

    protected OpenCity(Parcel in) {
        this.areaCode = in.readInt();
        this.areaName = in.readString();
        this.areaParentCode = in.readInt();
        this.areaType = in.readInt();
        this.mDistricts = in.createTypedArrayList(District.CREATOR);
    }

    public static final Creator<OpenCity> CREATOR = new Creator<OpenCity>() {
        @Override
        public OpenCity createFromParcel(Parcel source) {
            return new OpenCity(source);
        }

        @Override
        public OpenCity[] newArray(int size) {
            return new OpenCity[size];
        }
    };
}
