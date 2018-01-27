package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/4/25
 * Author ：chen
 */

public class MineSendInfo {

    /**
     * basicName : 巷口镇网点
     * deliveryUpdateTime : 1497866048071
     * orderId : 202
     * orderNo : 5002321001497929823183
     * orderStatus : 9
     * orderTime : 1497929823183
     * payStatus : 2
     * priceAddition : 0.0
     * receiveAddr : 重庆市|重庆|南岸区|铜元局街道|铜元局(地铁站)
     * receiverName : 成都市
     * totalPrice : 20.0
     * transmintName :
     */

    /**
     * 是否三方快件
     */
    private int thirdExpress;

    /**
     * 三方快递公司名称
     */
    private String expressName;

    /**
     * 三方快递编码
     */
    private String expressCode;

    /**
     * 追加金额Id
     */
    private long addtionId;

    public long getAddtionId() {
        return addtionId;
    }

    public void setAddtionId(long addtionId) {
        this.addtionId = addtionId;
    }

    public int getThirdExpress() {
        return thirdExpress;
    }

    public void setThirdExpress(int thirdExpress) {
        this.thirdExpress = thirdExpress;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    private long deliveryUpdateTime;
    private long orderId;
    private String orderNo;
    private int orderStatus;
    private long orderTime;
    private long checkTime;
    private int payStatus;
    private int addtionReason;
    private int addtionQuantity;
    private double priceAddition;
    private String receiveAddr;
    private String receiverName;
    private double totalPrice;
    private String transmintName;

    public int getAddtionQuantity() {
        return addtionQuantity;
    }

    public void setAddtionQuantity(int addtionQuantity) {
        this.addtionQuantity = addtionQuantity;
    }

    /**
     * 取件网点名称
     */
    private String basicSendName;
    /**
     * 目的网点名称
     */
    private String basicReceiveName;


    public String getBasicSendName() {
        return basicSendName;
    }

    public void setBasicSendName(String basicSendName) {
        this.basicSendName = basicSendName;
    }

    public String getBasicReceiveName() {
        return basicReceiveName;
    }

    public void setBasicReceiveName(String basicReceiveName) {
        this.basicReceiveName = basicReceiveName;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public int getAddtionReason() {
        return addtionReason;
    }

    public void setAddtionReason(int addtionReason) {
        this.addtionReason = addtionReason;
    }


    public long getDeliveryUpdateTime() {
        return deliveryUpdateTime;
    }

    public void setDeliveryUpdateTime(long deliveryUpdateTime) {
        this.deliveryUpdateTime = deliveryUpdateTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getPriceAddition() {
        return priceAddition;
    }

    public void setPriceAddition(double priceAddition) {
        this.priceAddition = priceAddition;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransmintName() {
        return transmintName;
    }

    public void setTransmintName(String transmintName) {
        this.transmintName = transmintName;
    }
}
