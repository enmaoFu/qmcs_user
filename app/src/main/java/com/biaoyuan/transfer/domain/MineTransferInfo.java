package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineTransferInfo {

    /**
     * packageCode : null
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


    private String packageCode;
    private long packageId;
    private int packageSize;
    private int packageWeight;
    private long publishComfirmTime;
    private long publishReqDelivTime;
    private long publishReqPickupTime;
    private double publishReward;
    private String receiveBasicName;
    private long receiveBasicTelphone;
    private double receiveLat;
    private double receiveLng;
    private String sendBasicName;
    private String sendName;
    private String receiveName;
    private long sendBasicTelphone;
    private long orderId;
    private double sendLat;
    private double sendLng;
    /**
     * excepReason : 把
     * goodsSize : 20
     * goodsType : 1
     * goodsWeight : 2
     * orderId : 374
     * packageId : 199
     * trackingCode : QMCS555444
     */

    private int goodsSize;
    private int goodsType;
    private int goodsWeight;
    private String trackingCode;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * 取件时间
     */
    private long carryPickupTime;

    /**
     * 验收时间
     */
    private long carryCheckTime;

   // 异常类型4取件超时4发送超时5丢失6破损

    private int excepType;

    public int getExcepType() {
        return excepType;
    }

    public void setExcepType(int excepType) {
        this.excepType = excepType;
    }

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

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
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

    public int getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(int goodsSize) {
        this.goodsSize = goodsSize;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(int goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
