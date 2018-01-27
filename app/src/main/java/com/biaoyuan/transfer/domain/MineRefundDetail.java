package com.biaoyuan.transfer.domain;

import java.util.List;

/**
 * Title  :
 * Create : 2017/7/7
 * Author ：chen
 */

public class MineRefundDetail {

    /**
     * orderSendLng : 107.749542
     * issueAffiliateTelphone : 13996856663
     * acceptAffiliateTelphone : 13996856663
     * issueAffiliateName : 巷口镇网点
     * orderSendLat : 29.324948
     * excepReason : 发布超时
     * issueAffiliateAreaCode : 重庆市重庆武隆县巷口镇
     * excepPicUrl :
     * acceptAffiliateName : 巷口镇网点
     * excepType : 取件拒收
     * refundProcess : [{"time":1499136002857,"title":"发起退款申请","content":"退款原因:取件拒收"},{"time":1499253519699,"title":"平台已处理","content":"平台已成功处理您的订单"},{"time":1499253519699,"title":"退款完成","content":"￥20.00元,已成功退至您的平台账户,请注意查收"}]
     * issueAffiliateAddress : 重庆市武隆县港口镇芙蓉西路
     * orderReceiveAddr : 重庆市|重庆|武隆县|巷口镇|巷口镇巷口镇山坪村巷口镇青坪小学
     * orderNo : 5002321001498894041000
     * orderTotalPrice : 15
     * orderSendAddr : 重庆市|重庆|武隆县|巷口镇|巷口镇世纪广场巷口镇黄金村卫生室
     * orderReceiveLat : 29.353524
     * excepRejectionReason : 其他原因
     * orderSignCode : 802174
     * acceptAffiliateAddress : 重庆市武隆县港口镇芙蓉西路
     * orderReceiveLng : 107.751128
     * acceptAffiliateAreaCode : 重庆市重庆武隆县巷口镇
     */

    private double orderSendLng;
    private double handleAmount;
    private long issueAffiliateTelphone;
    private long acceptAffiliateTelphone;
    private long orderRequiredTime;
    private String issueAffiliateName;
    private double orderSendLat;
    private String excepReason;
    private String issueAffiliateAreaCode;
    private String excepPicUrl;
    private String acceptAffiliateName;
    private String excepType;
    private String issueAffiliateAddress;
    private String orderReceiveAddr;
    private String orderNo;
    private double orderTotalPrice;
    private String orderSendAddr;
    private double orderReceiveLat;
    private String excepRejectionReason;
    private String orderSignCode;
    private String acceptAffiliateAddress;
    private double orderReceiveLng;
    private String acceptAffiliateAreaCode;
    private List<RefundProcessBean> refundProcess;
    private int excepHandleStatus;
    private int orderGoodsType;
    private int orderGoodsSize;
    private int orderGoodsWeight;

    public int getOrderGoodsSize() {
        return orderGoodsSize;
    }

    public void setOrderGoodsSize(int orderGoodsSize) {
        this.orderGoodsSize = orderGoodsSize;
    }

    public int getOrderGoodsWeight() {
        return orderGoodsWeight;
    }

    public void setOrderGoodsWeight(int orderGoodsWeight) {
        this.orderGoodsWeight = orderGoodsWeight;
    }

    public int getOrderGoodsType() {
        return orderGoodsType;
    }

    public void setOrderGoodsType(int orderGoodsType) {
        this.orderGoodsType = orderGoodsType;
    }

    public int getExcepHandleStatus() {
        return excepHandleStatus;
    }

    public void setExcepHandleStatus(int excepHandleStatus) {
        this.excepHandleStatus = excepHandleStatus;
    }

    public long getOrderRequiredTime() {
        return orderRequiredTime;
    }

    public void setOrderRequiredTime(long orderRequiredTime) {
        this.orderRequiredTime = orderRequiredTime;
    }

    public double getHandleAmount() {
        return handleAmount;
    }

    public void setHandleAmount(double handleAmount) {
        this.handleAmount = handleAmount;
    }

    public double getOrderSendLng() {
        return orderSendLng;
    }

    public void setOrderSendLng(double orderSendLng) {
        this.orderSendLng = orderSendLng;
    }

    public long getIssueAffiliateTelphone() {
        return issueAffiliateTelphone;
    }

    public void setIssueAffiliateTelphone(long issueAffiliateTelphone) {
        this.issueAffiliateTelphone = issueAffiliateTelphone;
    }

    public long getAcceptAffiliateTelphone() {
        return acceptAffiliateTelphone;
    }

    public void setAcceptAffiliateTelphone(long acceptAffiliateTelphone) {
        this.acceptAffiliateTelphone = acceptAffiliateTelphone;
    }

    public String getIssueAffiliateName() {
        return issueAffiliateName;
    }

    public void setIssueAffiliateName(String issueAffiliateName) {
        this.issueAffiliateName = issueAffiliateName;
    }

    public double getOrderSendLat() {
        return orderSendLat;
    }

    public void setOrderSendLat(double orderSendLat) {
        this.orderSendLat = orderSendLat;
    }

    public String getExcepReason() {
        return excepReason;
    }

    public void setExcepReason(String excepReason) {
        this.excepReason = excepReason;
    }

    public String getIssueAffiliateAreaCode() {
        return issueAffiliateAreaCode;
    }

    public void setIssueAffiliateAreaCode(String issueAffiliateAreaCode) {
        this.issueAffiliateAreaCode = issueAffiliateAreaCode;
    }

    public String getExcepPicUrl() {
        return excepPicUrl;
    }

    public void setExcepPicUrl(String excepPicUrl) {
        this.excepPicUrl = excepPicUrl;
    }

    public String getAcceptAffiliateName() {
        return acceptAffiliateName;
    }

    public void setAcceptAffiliateName(String acceptAffiliateName) {
        this.acceptAffiliateName = acceptAffiliateName;
    }

    public String getExcepType() {
        return excepType;
    }

    public void setExcepType(String excepType) {
        this.excepType = excepType;
    }

    public String getIssueAffiliateAddress() {
        return issueAffiliateAddress;
    }

    public void setIssueAffiliateAddress(String issueAffiliateAddress) {
        this.issueAffiliateAddress = issueAffiliateAddress;
    }

    public String getOrderReceiveAddr() {
        return orderReceiveAddr;
    }

    public void setOrderReceiveAddr(String orderReceiveAddr) {
        this.orderReceiveAddr = orderReceiveAddr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderSendAddr() {
        return orderSendAddr;
    }

    public void setOrderSendAddr(String orderSendAddr) {
        this.orderSendAddr = orderSendAddr;
    }

    public double getOrderReceiveLat() {
        return orderReceiveLat;
    }

    public void setOrderReceiveLat(double orderReceiveLat) {
        this.orderReceiveLat = orderReceiveLat;
    }

    public String getExcepRejectionReason() {
        return excepRejectionReason;
    }

    public void setExcepRejectionReason(String excepRejectionReason) {
        this.excepRejectionReason = excepRejectionReason;
    }

    public String getOrderSignCode() {
        return orderSignCode;
    }

    public void setOrderSignCode(String orderSignCode) {
        this.orderSignCode = orderSignCode;
    }

    public String getAcceptAffiliateAddress() {
        return acceptAffiliateAddress;
    }

    public void setAcceptAffiliateAddress(String acceptAffiliateAddress) {
        this.acceptAffiliateAddress = acceptAffiliateAddress;
    }

    public double getOrderReceiveLng() {
        return orderReceiveLng;
    }

    public void setOrderReceiveLng(double orderReceiveLng) {
        this.orderReceiveLng = orderReceiveLng;
    }

    public String getAcceptAffiliateAreaCode() {
        return acceptAffiliateAreaCode;
    }

    public void setAcceptAffiliateAreaCode(String acceptAffiliateAreaCode) {
        this.acceptAffiliateAreaCode = acceptAffiliateAreaCode;
    }

    public List<RefundProcessBean> getRefundProcess() {
        return refundProcess;
    }

    public void setRefundProcess(List<RefundProcessBean> refundProcess) {
        this.refundProcess = refundProcess;
    }

    public static class RefundProcessBean {
        /**
         * time : 1499136002857
         * title : 发起退款申请
         * content : 退款原因:取件拒收
         */

        private long time;
        private String title;
        private String content;


        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
