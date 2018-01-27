package com.biaoyuan.transfer.domain;

/**
 * Title  :我的传送详情的实体类
 * Create : 2017/6/24
 * Author ：chen
 */

public class MineTransferDetailResult {

    /**
     * packageId : 70
     * packageSize : 20
     * packageWeight : 2
     * publishComfirmTime : 1498196507669
     * publishReqDelivTime : 1498207294983
     * publishReqPickupTime : 1498200094983
     * publishReward : 5.0
     * receiveBasicName : 虎溪街道
     * receiveBasicTelphone : 18883535293
     * receiveLat : 29.6033001
     * receiveLng : 106.302002
     * sendBasicName : 复盛镇
     * sendBasicTelphone : 13883077308
     * sendLat : 29.6401005
     * sendLng : 106.7990036
     */

    private long packageId;
    private int packageSize;
    private int packageWeight;
    private long publishComfirmTime;
    private long publishReqDelivTime;
    private long publishReqPickupTime;
    private double publishReward;
    private String receiveBasicName;
    private String receiveBasicAddress;
    private String sendBasicAddress;
    private String packageCode;
    private long receiveBasicTelphone;
    private double receiveLat;
    private double receiveLng;
    private String sendBasicName;
    private long sendBasicTelphone;
    private double sendLat;
    private double sendLng;

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    /**
     * 取件时间
     */
    private long carryPickupTime;

    /**
     * 验收时间
     */
    private long carryCheckTime;


    public long getCarryPickupTime() {
        return carryPickupTime;
    }

    public void setCarryPickupTime(long carryPickupTime) {
        this.carryPickupTime = carryPickupTime;
    }

    public long getCarryCheckTime() {
        return carryCheckTime;
    }

    public void setCarryCheckTime(long carryCheckTime) {
        this.carryCheckTime = carryCheckTime;
    }

    public String getReceiveBasicAddress() {
        return receiveBasicAddress;
    }

    public void setReceiveBasicAddress(String receiveBasicAddress) {
        this.receiveBasicAddress = receiveBasicAddress;
    }

    public String getSendBasicAddress() {
        return sendBasicAddress;
    }

    public void setSendBasicAddress(String sendBasicAddress) {
        this.sendBasicAddress = sendBasicAddress;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
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

    public long getPublishComfirmTime() {
        return publishComfirmTime;
    }

    public void setPublishComfirmTime(long publishComfirmTime) {
        this.publishComfirmTime = publishComfirmTime;
    }

    public long getPublishReqDelivTime() {
        return publishReqDelivTime;
    }

    public void setPublishReqDelivTime(long publishReqDelivTime) {
        this.publishReqDelivTime = publishReqDelivTime;
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

    public String getReceiveBasicName() {
        return receiveBasicName;
    }

    public void setReceiveBasicName(String receiveBasicName) {
        this.receiveBasicName = receiveBasicName;
    }

    public long getReceiveBasicTelphone() {
        return receiveBasicTelphone;
    }

    public void setReceiveBasicTelphone(long receiveBasicTelphone) {
        this.receiveBasicTelphone = receiveBasicTelphone;
    }

    public double getReceiveLat() {
        return receiveLat;
    }

    public void setReceiveLat(double receiveLat) {
        this.receiveLat = receiveLat;
    }

    public double getReceiveLng() {
        return receiveLng;
    }

    public void setReceiveLng(double receiveLng) {
        this.receiveLng = receiveLng;
    }

    public String getSendBasicName() {
        return sendBasicName;
    }

    public void setSendBasicName(String sendBasicName) {
        this.sendBasicName = sendBasicName;
    }

    public long getSendBasicTelphone() {
        return sendBasicTelphone;
    }

    public void setSendBasicTelphone(long sendBasicTelphone) {
        this.sendBasicTelphone = sendBasicTelphone;
    }

    public double getSendLat() {
        return sendLat;
    }

    public void setSendLat(double sendLat) {
        this.sendLat = sendLat;
    }

    public double getSendLng() {
        return sendLng;
    }

    public void setSendLng(double sendLng) {
        this.sendLng = sendLng;
    }
}
