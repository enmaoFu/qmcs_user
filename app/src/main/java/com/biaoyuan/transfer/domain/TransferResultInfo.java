package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/6/16
 * Author ：chen
 */

public class TransferResultInfo {

    /**
     * entBasicAddress : 重庆市沙坪坝去杨公桥168林泉雅舍1号楼13一3
     * entBasicAreaName : 重庆市,重庆,沙坪坝区,沙坪坝街道
     * entBasicLat : 29.5648003
     * entBasicLng : 106.4449997
     * entBasicName : 沙坪坝网点
     * entBasicTelphone : 13608332127
     * outBasicAddress : 渝北区双龙西路二巷1幢6号
     * outBasicAreaName : 重庆市,重庆,渝北区,回兴街道
     * outBasicLat : 29.5960007
     * outBasicLng : 106.4940033
     * outBasicName : 回兴网点
     * outBasicTelphone : 13709416968
     * packageSize : 5.0
     * packageWeight : 5.0
     * publishId : 1
     * publishPackageId : 1
     * publishReqPickupTime : 2017052809253365
     * publishReward : 15.0
     */

    private String entBasicAddress;
    private String entBasicAreaName;
    private double entBasicLat;
    private double entBasicLng;
    private String entBasicName;
    private long entBasicTelphone;
    private String outBasicAddress;
    private String outBasicAreaName;
    private double outBasicLat;
    private double outBasicLng;
    private String outBasicName;
    private long outBasicTelphone;
    private int packageSize;
    private int packageWeight;
    private int publishId;
    private int publishPackageId;
    private long publishReqPickupTime;
    private long publishReqDelivTime;
    private double publishReward;

    public long getPublishReqDelivTime() {
        return publishReqDelivTime;
    }

    public void setPublishReqDelivTime(long publishReqDelivTime) {
        this.publishReqDelivTime = publishReqDelivTime;
    }

    public String getEntBasicAddress() {
        return entBasicAddress;
    }

    public void setEntBasicAddress(String entBasicAddress) {
        this.entBasicAddress = entBasicAddress;
    }

    public String getEntBasicAreaName() {
        return entBasicAreaName;
    }

    public void setEntBasicAreaName(String entBasicAreaName) {
        this.entBasicAreaName = entBasicAreaName;
    }

    public double getEntBasicLat() {
        return entBasicLat;
    }

    public void setEntBasicLat(double entBasicLat) {
        this.entBasicLat = entBasicLat;
    }

    public double getEntBasicLng() {
        return entBasicLng;
    }

    public void setEntBasicLng(double entBasicLng) {
        this.entBasicLng = entBasicLng;
    }

    public String getEntBasicName() {
        return entBasicName;
    }

    public void setEntBasicName(String entBasicName) {
        this.entBasicName = entBasicName;
    }

    public long getEntBasicTelphone() {
        return entBasicTelphone;
    }

    public void setEntBasicTelphone(long entBasicTelphone) {
        this.entBasicTelphone = entBasicTelphone;
    }

    public String getOutBasicAddress() {
        return outBasicAddress;
    }

    public void setOutBasicAddress(String outBasicAddress) {
        this.outBasicAddress = outBasicAddress;
    }

    public String getOutBasicAreaName() {
        return outBasicAreaName;
    }

    public void setOutBasicAreaName(String outBasicAreaName) {
        this.outBasicAreaName = outBasicAreaName;
    }

    public double getOutBasicLat() {
        return outBasicLat;
    }

    public void setOutBasicLat(double outBasicLat) {
        this.outBasicLat = outBasicLat;
    }

    public double getOutBasicLng() {
        return outBasicLng;
    }

    public void setOutBasicLng(double outBasicLng) {
        this.outBasicLng = outBasicLng;
    }

    public String getOutBasicName() {
        return outBasicName;
    }

    public void setOutBasicName(String outBasicName) {
        this.outBasicName = outBasicName;
    }

    public long getOutBasicTelphone() {
        return outBasicTelphone;
    }

    public void setOutBasicTelphone(long outBasicTelphone) {
        this.outBasicTelphone = outBasicTelphone;
    }

    public int getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }

    public int getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    public int getPublishId() {
        return publishId;
    }

    public void setPublishId(int publishId) {
        this.publishId = publishId;
    }

    public int getPublishPackageId() {
        return publishPackageId;
    }

    public void setPublishPackageId(int publishPackageId) {
        this.publishPackageId = publishPackageId;
    }

    public long getPublishReqPickupTime() {
        return publishReqPickupTime;
    }

    public void setPublishReqPickupTime(long publishReqPickupTime) {
        this.publishReqPickupTime = publishReqPickupTime;
    }

    public double getPublishReward() {
        return publishReward;
    }

    public void setPublishReward(double publishReward) {
        this.publishReward = publishReward;
    }
}
