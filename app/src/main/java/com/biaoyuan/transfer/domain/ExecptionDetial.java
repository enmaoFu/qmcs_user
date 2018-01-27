package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/7/5
 * Author ：chen
 */

public class ExecptionDetial {

    /**
     * carryCheckTime : 1499243915560
     * carryPickupTime : 1499243825970
     * excepReason : 丢失
     * excepType : 5
     * packageCode : QMWD000542
     * publishReqDelivTime : 1499475600000
     * receiveAddress : 重庆市武隆县港口镇芙蓉西路
     * receiveLat : 29.5808844
     * receiveLng : 106.4921093
     * receiveName : 巷口镇网点
     * receiveTelphone : 13996856666
     * sendAddress : 重庆市武隆县港口镇芙蓉西路
     * sendLat : 29.5808844
     * sendLng : 106.4921093
     * sendName : 巷口镇网点
     * sendTelphone : 13996856666
     * trackingCode : QMCS7778888
     */

    private long carryCheckTime;
    private long carryPickupTime;
private long publishReqPickupTime;
    private String excepReason;
    private int excepType;
    private String packageCode;
    private long publishReqDelivTime;
    private String receiveAddress;
    private double receiveLat;
    private double receiveLng;
    private String receiveName;
    private long receiveTelphone;
    private String sendAddress;
    private double sendLat;
    private double sendLng;
    private String sendName;
    private long sendTelphone;
    private String trackingCode;
    private String excepPicUrl;

    private int goodsSize;
    private int goodsType;
    private int goodsWeight;

    private double publishReward;

    public double getPublishReward() {
        return publishReward;
    }

    public void setPublishReward(double publishReward) {
        this.publishReward = publishReward;
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

    public String getExcepPicUrl() {
        return excepPicUrl;
    }

    public void setExcepPicUrl(String excepPicUrl) {
        this.excepPicUrl = excepPicUrl;
    }

    public long getPublishReqPickupTime() {
        return publishReqPickupTime;
    }

    public void setPublishReqPickupTime(long publishReqPickupTime) {
        this.publishReqPickupTime = publishReqPickupTime;
    }

    public long getCarryCheckTime() {
        return carryCheckTime;
    }

    public void setCarryCheckTime(long carryCheckTime) {
        this.carryCheckTime = carryCheckTime;
    }

    public long getCarryPickupTime() {
        return carryPickupTime;
    }

    public void setCarryPickupTime(long carryPickupTime) {
        this.carryPickupTime = carryPickupTime;
    }

    public String getExcepReason() {
        return excepReason;
    }

    public void setExcepReason(String excepReason) {
        this.excepReason = excepReason;
    }

    public int getExcepType() {
        return excepType;
    }

    public void setExcepType(int excepType) {
        this.excepType = excepType;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public long getPublishReqDelivTime() {
        return publishReqDelivTime;
    }

    public void setPublishReqDelivTime(long publishReqDelivTime) {
        this.publishReqDelivTime = publishReqDelivTime;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
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

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public long getReceiveTelphone() {
        return receiveTelphone;
    }

    public void setReceiveTelphone(long receiveTelphone) {
        this.receiveTelphone = receiveTelphone;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
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

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public long getSendTelphone() {
        return sendTelphone;
    }

    public void setSendTelphone(long sendTelphone) {
        this.sendTelphone = sendTelphone;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
