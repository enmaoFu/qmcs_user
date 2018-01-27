package com.biaoyuan.transfer.domain;

/**
 * Title  : 消息中心实体类
 * Create : 2017/5/24
 * Author ：enmaoFu
 */
public class IndexMessageNewInfo {


    /**
     * umessageContent : 尊敬的：hdhdhdh您的订单【5002321001499653721497】发件成功！{483}
     * umessageCreatTime : 1499653725180
     * umessageId : 12
     * umessageIsDeleted : 0
     * umessageIsRead : 0
     * umessageReadTime : 0
     * umessageTime : 1499653725180
     * umessageTitle : 发单消息
     * umessageType : 0
     * umessageUpdateTime : 0
     * umessageUserId : 34
     * umessageVersion : 0
     */

    private String umessageContent;
    private long umessageCreatTime;
    private int umessageId;
    private int umessageIsDeleted;
    private int umessageIsRead;
    private long umessageReadTime;
    private long umessageTime;
    private String umessageTitle;
    private int umessageType;
    private long umessageUpdateTime;
    private int umessageUserId;
    private int umessageVersion;
    private int orderId;
    private int payStatus;
    private int orderStatus;
    private int excepType;

    public String getUmessageContent() {
        return umessageContent;
    }

    public void setUmessageContent(String umessageContent) {
        this.umessageContent = umessageContent;
    }

    public long getUmessageCreatTime() {
        return umessageCreatTime;
    }

    public void setUmessageCreatTime(long umessageCreatTime) {
        this.umessageCreatTime = umessageCreatTime;
    }

    public int getUmessageId() {
        return umessageId;
    }

    public void setUmessageId(int umessageId) {
        this.umessageId = umessageId;
    }

    public int getUmessageIsDeleted() {
        return umessageIsDeleted;
    }

    public void setUmessageIsDeleted(int umessageIsDeleted) {
        this.umessageIsDeleted = umessageIsDeleted;
    }

    public int getUmessageIsRead() {
        return umessageIsRead;
    }

    public void setUmessageIsRead(int umessageIsRead) {
        this.umessageIsRead = umessageIsRead;
    }

    public long getUmessageReadTime() {
        return umessageReadTime;
    }

    public void setUmessageReadTime(long umessageReadTime) {
        this.umessageReadTime = umessageReadTime;
    }

    public long getUmessageTime() {
        return umessageTime;
    }

    public void setUmessageTime(long umessageTime) {
        this.umessageTime = umessageTime;
    }

    public String getUmessageTitle() {
        return umessageTitle;
    }

    public void setUmessageTitle(String umessageTitle) {
        this.umessageTitle = umessageTitle;
    }

    public int getUmessageType() {
        return umessageType;
    }

    public void setUmessageType(int umessageType) {
        this.umessageType = umessageType;
    }

    public long getUmessageUpdateTime() {
        return umessageUpdateTime;
    }

    public void setUmessageUpdateTime(long umessageUpdateTime) {
        this.umessageUpdateTime = umessageUpdateTime;
    }

    public int getUmessageUserId() {
        return umessageUserId;
    }

    public void setUmessageUserId(int umessageUserId) {
        this.umessageUserId = umessageUserId;
    }

    public int getUmessageVersion() {
        return umessageVersion;
    }

    public void setUmessageVersion(int umessageVersion) {
        this.umessageVersion = umessageVersion;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getExcepType() {
        return excepType;
    }

    public void setExcepType(int excepType) {
        this.excepType = excepType;
    }
}
