package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title  :
 * Create : 2017/6/20
 * Author ：chen
 */

public class UserInfo implements Parcelable {

    /**
     * userTelphone : 15213478863
     * userLastLogin : 1497324382553
     * userPortraitUrl : fasfafa.jpg
     * accountBalanceBefore : 0
     */


    private long userTelphone;
    private long userCoupon;
    private long userLastLogin;
    private String userPortraitUrl;
    private String rawUserPortraitUrl;
    private String userLoginName;
    private String deliverStatus;
    private String isDeliver;
    private double accountBalanceBefore;

    public long getUserCoupon() {
        return userCoupon;
    }

    public void setUserCoupon(long userCoupon) {
        this.userCoupon = userCoupon;
    }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public String getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(String isDeliver) {
        this.isDeliver = isDeliver;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getRawUserPortraitUrl() {
        return rawUserPortraitUrl;
    }

    public void setRawUserPortraitUrl(String rawUserPortraitUrl) {
        this.rawUserPortraitUrl = rawUserPortraitUrl;
    }

    public long getUserTelphone() {
        return userTelphone;
    }

    public void setUserTelphone(long userTelphone) {
        this.userTelphone = userTelphone;
    }

    public long getUserLastLogin() {
        return userLastLogin;
    }

    public void setUserLastLogin(long userLastLogin) {
        this.userLastLogin = userLastLogin;
    }

    public String getUserPortraitUrl() {
        if (userPortraitUrl==null){
            return "";
        }
        return userPortraitUrl;
    }

    public void setUserPortraitUrl(String userPortraitUrl) {
        this.userPortraitUrl = userPortraitUrl;
    }

    public double getAccountBalanceBefore() {
        return accountBalanceBefore;
    }

    public void setAccountBalanceBefore(double accountBalanceBefore) {
        this.accountBalanceBefore = accountBalanceBefore;
    }

    public UserInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userTelphone);
        dest.writeLong(this.userCoupon);
        dest.writeLong(this.userLastLogin);
        dest.writeString(this.userPortraitUrl);
        dest.writeString(this.rawUserPortraitUrl);
        dest.writeString(this.userLoginName);
        dest.writeString(this.deliverStatus);
        dest.writeString(this.isDeliver);
        dest.writeDouble(this.accountBalanceBefore);
    }

    protected UserInfo(Parcel in) {
        this.userTelphone = in.readLong();
        this.userCoupon = in.readLong();
        this.userLastLogin = in.readLong();
        this.userPortraitUrl = in.readString();
        this.rawUserPortraitUrl = in.readString();
        this.userLoginName = in.readString();
        this.deliverStatus = in.readString();
        this.isDeliver = in.readString();
        this.accountBalanceBefore = in.readDouble();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
