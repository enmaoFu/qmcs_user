package com.biaoyuan.transfer.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Title  :
 * Create : 2017/6/30
 * Author ：chen
 */

public class MineSendOrderDetail implements Parcelable {

 /**
     * orderAffiliateReceive : 20
     * orderAffiliateSend : 17
     * orderConfirmStaff : null
     * orderCreatTime : 1498803078243
     * orderGoodsDistance : 26.02
     * orderGoodsSize : 20
     * orderGoodsType : 1
     * orderGoodsWeight : 2
     * orderId : 373
     * orderIsDeleted : 0
     * orderIsThirdExpress : 0
     * orderNo : 5001080031498803078243
     * orderPayStatus : 2
     * orderPriceAddition : 0.0
     * orderReceiveAddr : 重庆市|重庆|江北区|复盛镇|复盛考场|1-5
     * orderReceiveAreaId : 500105101
     * orderReceiveLat : 29.643566
     * orderReceiveLng : 106.801039
     * orderReceiverName : 杨溢
     * orderReceiverTel : 18882323558
     * orderRequiredTime : 1498809600000
     * orderSendAddr : 重庆市|重庆|南岸区|南坪街道|浪高凯悦国际商务大厦B座
     * orderSendAreaId : 500108003
     * orderSendLat : 29.529118
     * orderSendLng : 106.566389
     * orderSenderName : 杨溢
     * orderSenderTel : 18512368242
     * orderSignCode : 678282
     * orderStatus : 1
     * orderThirdExpressCode : null
     * orderThirdExpressId : null
     * orderThirdExpressName : null
     * orderTime : 1498803078243
     * orderTotalPrice : 12.0
     * orderTrackingCode :
     * orderTransType : 0
     * orderUpdateTime : 1498803094174
     * orderUserId : 6
     * orderVersion : 0
     */

    private int orderAffiliateReceive;
    private int orderAffiliateSend;
    private String orderConfirmStaff;
    private long orderCreatTime;
    private double orderGoodsDistance;
    private int orderGoodsSize;
    private int orderGoodsType;
    private int orderGoodsWeight;
    private long orderId;
    private int orderIsDeleted;
    private int orderIsThirdExpress;
    private String orderNo;
    private int orderPayStatus;
    private double orderPriceAddition;
    private String orderReceiveAddr;
    private int orderReceiveAreaId;
    private double orderReceiveLat;
    private double orderReceiveLng;
    private String orderReceiverName;
    private long orderReceiverTel;
    private long orderRequiredTime;
    private String orderSendAddr;
    private int orderSendAreaId;
    private double orderSendLat;
    private double orderSendLng;
    private String orderSenderName;
    private long orderSenderTel;
    private String orderSignCode;
    private int orderStatus;

    private long orderTime;
    private double orderTotalPrice;
    private String orderTrackingCode;
    private int orderTransType;
    private long orderUpdateTime;
    private int orderUserId;
    private int orderVersion;

    public int getOrderAffiliateReceive() {
        return orderAffiliateReceive;
    }

    public void setOrderAffiliateReceive(int orderAffiliateReceive) {
        this.orderAffiliateReceive = orderAffiliateReceive;
    }

    public int getOrderAffiliateSend() {
        return orderAffiliateSend;
    }

    public void setOrderAffiliateSend(int orderAffiliateSend) {
        this.orderAffiliateSend = orderAffiliateSend;
    }

    public String getOrderConfirmStaff() {
        return orderConfirmStaff;
    }

    public void setOrderConfirmStaff(String orderConfirmStaff) {
        this.orderConfirmStaff = orderConfirmStaff;
    }

    public long getOrderCreatTime() {
        return orderCreatTime;
    }

    public void setOrderCreatTime(long orderCreatTime) {
        this.orderCreatTime = orderCreatTime;
    }

    public double getOrderGoodsDistance() {
        return orderGoodsDistance;
    }

    public void setOrderGoodsDistance(double orderGoodsDistance) {
        this.orderGoodsDistance = orderGoodsDistance;
    }

    public int getOrderGoodsSize() {
        return orderGoodsSize;
    }

    public void setOrderGoodsSize(int orderGoodsSize) {
        this.orderGoodsSize = orderGoodsSize;
    }

    public int getOrderGoodsType() {
        return orderGoodsType;
    }

    public void setOrderGoodsType(int orderGoodsType) {
        this.orderGoodsType = orderGoodsType;
    }

    public int getOrderGoodsWeight() {
        return orderGoodsWeight;
    }

    public void setOrderGoodsWeight(int orderGoodsWeight) {
        this.orderGoodsWeight = orderGoodsWeight;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getOrderIsDeleted() {
        return orderIsDeleted;
    }

    public void setOrderIsDeleted(int orderIsDeleted) {
        this.orderIsDeleted = orderIsDeleted;
    }

    public int getOrderIsThirdExpress() {
        return orderIsThirdExpress;
    }

    public void setOrderIsThirdExpress(int orderIsThirdExpress) {
        this.orderIsThirdExpress = orderIsThirdExpress;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderPayStatus() {
        return orderPayStatus;
    }

    public void setOrderPayStatus(int orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }

    public double getOrderPriceAddition() {
        return orderPriceAddition;
    }

    public void setOrderPriceAddition(double orderPriceAddition) {
        this.orderPriceAddition = orderPriceAddition;
    }

    public String getOrderReceiveAddr() {
        return orderReceiveAddr;
    }

    public void setOrderReceiveAddr(String orderReceiveAddr) {
        this.orderReceiveAddr = orderReceiveAddr;
    }

    public int getOrderReceiveAreaId() {
        return orderReceiveAreaId;
    }

    public void setOrderReceiveAreaId(int orderReceiveAreaId) {
        this.orderReceiveAreaId = orderReceiveAreaId;
    }

    public double getOrderReceiveLat() {
        return orderReceiveLat;
    }

    public void setOrderReceiveLat(double orderReceiveLat) {
        this.orderReceiveLat = orderReceiveLat;
    }

    public double getOrderReceiveLng() {
        return orderReceiveLng;
    }

    public void setOrderReceiveLng(double orderReceiveLng) {
        this.orderReceiveLng = orderReceiveLng;
    }

    public String getOrderReceiverName() {
        return orderReceiverName;
    }

    public void setOrderReceiverName(String orderReceiverName) {
        this.orderReceiverName = orderReceiverName;
    }

    public long getOrderReceiverTel() {
        return orderReceiverTel;
    }

    public void setOrderReceiverTel(long orderReceiverTel) {
        this.orderReceiverTel = orderReceiverTel;
    }

    public long getOrderRequiredTime() {
        return orderRequiredTime;
    }

    public void setOrderRequiredTime(long orderRequiredTime) {
        this.orderRequiredTime = orderRequiredTime;
    }

    public String getOrderSendAddr() {
        return orderSendAddr;
    }

    public void setOrderSendAddr(String orderSendAddr) {
        this.orderSendAddr = orderSendAddr;
    }

    public int getOrderSendAreaId() {
        return orderSendAreaId;
    }

    public void setOrderSendAreaId(int orderSendAreaId) {
        this.orderSendAreaId = orderSendAreaId;
    }

    public double getOrderSendLat() {
        return orderSendLat;
    }

    public void setOrderSendLat(double orderSendLat) {
        this.orderSendLat = orderSendLat;
    }

    public double getOrderSendLng() {
        return orderSendLng;
    }

    public void setOrderSendLng(double orderSendLng) {
        this.orderSendLng = orderSendLng;
    }

    public String getOrderSenderName() {
        return orderSenderName;
    }

    public void setOrderSenderName(String orderSenderName) {
        this.orderSenderName = orderSenderName;
    }

    public long getOrderSenderTel() {
        return orderSenderTel;
    }

    public void setOrderSenderTel(long orderSenderTel) {
        this.orderSenderTel = orderSenderTel;
    }

    public String getOrderSignCode() {
        return orderSignCode;
    }

    public void setOrderSignCode(String orderSignCode) {
        this.orderSignCode = orderSignCode;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }



    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderTrackingCode() {
        return orderTrackingCode;
    }

    public void setOrderTrackingCode(String orderTrackingCode) {
        this.orderTrackingCode = orderTrackingCode;
    }

    public int getOrderTransType() {
        return orderTransType;
    }

    public void setOrderTransType(int orderTransType) {
        this.orderTransType = orderTransType;
    }

    public long getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public void setOrderUpdateTime(long orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public int getOrderVersion() {
        return orderVersion;
    }

    public void setOrderVersion(int orderVersion) {
        this.orderVersion = orderVersion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.orderAffiliateReceive);
        dest.writeInt(this.orderAffiliateSend);
        dest.writeString(this.orderConfirmStaff);
        dest.writeLong(this.orderCreatTime);
        dest.writeDouble(this.orderGoodsDistance);
        dest.writeInt(this.orderGoodsSize);
        dest.writeInt(this.orderGoodsType);
        dest.writeInt(this.orderGoodsWeight);
        dest.writeLong(this.orderId);
        dest.writeInt(this.orderIsDeleted);
        dest.writeInt(this.orderIsThirdExpress);
        dest.writeString(this.orderNo);
        dest.writeInt(this.orderPayStatus);
        dest.writeDouble(this.orderPriceAddition);
        dest.writeString(this.orderReceiveAddr);
        dest.writeInt(this.orderReceiveAreaId);
        dest.writeDouble(this.orderReceiveLat);
        dest.writeDouble(this.orderReceiveLng);
        dest.writeString(this.orderReceiverName);
        dest.writeLong(this.orderReceiverTel);
        dest.writeLong(this.orderRequiredTime);
        dest.writeString(this.orderSendAddr);
        dest.writeInt(this.orderSendAreaId);
        dest.writeDouble(this.orderSendLat);
        dest.writeDouble(this.orderSendLng);
        dest.writeString(this.orderSenderName);
        dest.writeLong(this.orderSenderTel);
        dest.writeString(this.orderSignCode);
        dest.writeInt(this.orderStatus);
        dest.writeLong(this.orderTime);
        dest.writeDouble(this.orderTotalPrice);
        dest.writeString(this.orderTrackingCode);
        dest.writeInt(this.orderTransType);
        dest.writeLong(this.orderUpdateTime);
        dest.writeInt(this.orderUserId);
        dest.writeInt(this.orderVersion);
    }

    public MineSendOrderDetail() {
    }

    protected MineSendOrderDetail(Parcel in) {
        this.orderAffiliateReceive = in.readInt();
        this.orderAffiliateSend = in.readInt();
        this.orderConfirmStaff = in.readString();
        this.orderCreatTime = in.readLong();
        this.orderGoodsDistance = in.readDouble();
        this.orderGoodsSize = in.readInt();
        this.orderGoodsType = in.readInt();
        this.orderGoodsWeight = in.readInt();
        this.orderId = in.readLong();
        this.orderIsDeleted = in.readInt();
        this.orderIsThirdExpress = in.readInt();
        this.orderNo = in.readString();
        this.orderPayStatus = in.readInt();
        this.orderPriceAddition = in.readDouble();
        this.orderReceiveAddr = in.readString();
        this.orderReceiveAreaId = in.readInt();
        this.orderReceiveLat = in.readDouble();
        this.orderReceiveLng = in.readDouble();
        this.orderReceiverName = in.readString();
        this.orderReceiverTel = in.readLong();
        this.orderRequiredTime = in.readLong();
        this.orderSendAddr = in.readString();
        this.orderSendAreaId = in.readInt();
        this.orderSendLat = in.readDouble();
        this.orderSendLng = in.readDouble();
        this.orderSenderName = in.readString();
        this.orderSenderTel = in.readLong();
        this.orderSignCode = in.readString();
        this.orderStatus = in.readInt();
        this.orderTime = in.readLong();
        this.orderTotalPrice = in.readDouble();
        this.orderTrackingCode = in.readString();
        this.orderTransType = in.readInt();
        this.orderUpdateTime = in.readLong();
        this.orderUserId = in.readInt();
        this.orderVersion = in.readInt();
    }

    public static final Parcelable.Creator<MineSendOrderDetail> CREATOR = new Parcelable.Creator<MineSendOrderDetail>() {
        @Override
        public MineSendOrderDetail createFromParcel(Parcel source) {
            return new MineSendOrderDetail(source);
        }

        @Override
        public MineSendOrderDetail[] newArray(int size) {
            return new MineSendOrderDetail[size];
        }
    };
}
