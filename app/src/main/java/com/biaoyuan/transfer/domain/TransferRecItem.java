package com.biaoyuan.transfer.domain;

/**
 * 传送recyclerView Item实体类
 * @author enmaoFu
 * @date 2017/04/24
 */
public class TransferRecItem {




    private String entBasicAreaName;
    private double entBasicLat;
    private double entBasicLng;
    private String entBasicName;
    private double entDistance;
    private String outBasicAreaName;
    private double outBasicLat;
    private double outBasicLng;
    private String outBasicName;
    private double outDistance;
    private int packageSize;
    private int packageWeight;
    private long publishId;
    private long publishPackageId;
    private long publishReqPickupTime;
    private double publishReward;

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

    public double getEntDistance() {
        return entDistance;
    }

    public void setEntDistance(double entDistance) {
        this.entDistance = entDistance;
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

    public double getOutDistance() {
        return outDistance;
    }

    public void setOutDistance(double outDistance) {
        this.outDistance = outDistance;
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

    public long getPublishId() {
        return publishId;
    }

    public void setPublishId(long publishId) {
        this.publishId = publishId;
    }

    public long getPublishPackageId() {
        return publishPackageId;
    }

    public void setPublishPackageId(long publishPackageId) {
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
