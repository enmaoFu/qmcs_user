package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/5/22
 * Author ：chen
 */

public class PinLunItem {


    /**
     * orderReceiveAddr : 花园小区
     * orderTrackingCode : QMCS10001
     * commentScore : 4
     * commentContent : 可以的黑666666
     * userTelphone : 15213478863
     * userPortraitUrl : fasfafa.jpg
     * commentTime : 1496476775297
     */

    private String orderReceiveAddr;
    private String orderTrackingCode;
    private int commentScore;
    private String commentContent;
    private long userTelphone;
    private String userPortraitUrl;
    private String orderNo;
    private long commentTime;
    private long orderUpdateTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public void setOrderUpdateTime(long orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public String getOrderReceiveAddr() {
        return orderReceiveAddr;
    }

    public void setOrderReceiveAddr(String orderReceiveAddr) {
        this.orderReceiveAddr = orderReceiveAddr;
    }

    public String getOrderTrackingCode() {
        return orderTrackingCode;
    }

    public void setOrderTrackingCode(String orderTrackingCode) {
        this.orderTrackingCode = orderTrackingCode;
    }

    public int getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(int commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public long getUserTelphone() {
        return userTelphone;
    }

    public void setUserTelphone(long userTelphone) {
        this.userTelphone = userTelphone;
    }

    public String getUserPortraitUrl() {
        return userPortraitUrl;
    }

    public void setUserPortraitUrl(String userPortraitUrl) {
        this.userPortraitUrl = userPortraitUrl;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }
}
