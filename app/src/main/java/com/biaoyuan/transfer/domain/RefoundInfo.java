package com.biaoyuan.transfer.domain;

/**
 * Title  :退款售后
 * Create : 2017/6/28
 * Author ：chen
 */

public class RefoundInfo {


    /**
     * orderSendArea : 重庆市重庆武隆县巷口镇
     * orderExcep : {"excepHandleStatus":1,"excepId":74,"excepReason":"发布超时","excepRejectionReason":5,"excepType":1}
     * excepHandle : {"handleAmount":20,"handleProgress":"处理了一哈就解决了问题","handleTime":1499253519699,"handleType":1}
     * basicName : 巷口镇网点
     * order : {"orderAffiliateReceive":4,"orderId":183,"orderNo":"5002321001498894041000","orderSendAddr":"重庆市|重庆|武隆县|巷口镇|巷口镇世纪广场巷口镇黄金村卫生室","orderSendAreaId":500232100,"orderSenderName":"iOS数据即将离开","orderStatus":12,"orderTime":1498894041000,"orderTotalPrice":15}
     */

    private String orderSendArea;
    private String baseName;
    private OrderExcepBean orderExcep;
    private ExcepHandleBean excepHandle;
    private String basicName;
    private OrderBean order;

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getOrderSendArea() {
        return orderSendArea;
    }

    public void setOrderSendArea(String orderSendArea) {
        this.orderSendArea = orderSendArea;
    }

    public OrderExcepBean getOrderExcep() {
        return orderExcep;
    }

    public void setOrderExcep(OrderExcepBean orderExcep) {
        this.orderExcep = orderExcep;
    }

    public ExcepHandleBean getExcepHandle() {
        return excepHandle;
    }

    public void setExcepHandle(ExcepHandleBean excepHandle) {
        this.excepHandle = excepHandle;
    }

    public String getBasicName() {
        return basicName;
    }

    public void setBasicName(String basicName) {
        this.basicName = basicName;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderExcepBean {
        /**
         * excepHandleStatus : 1
         * excepId : 74
         * excepReason : 发布超时
         * excepRejectionReason : 5
         * excepType : 1
         */

        private int excepHandleStatus;
        private int excepId;
        private String excepReason;
        private int excepRejectionReason;
        private int excepType;

        public int getExcepHandleStatus() {
            return excepHandleStatus;
        }

        public void setExcepHandleStatus(int excepHandleStatus) {
            this.excepHandleStatus = excepHandleStatus;
        }

        public int getExcepId() {
            return excepId;
        }

        public void setExcepId(int excepId) {
            this.excepId = excepId;
        }

        public String getExcepReason() {
            return excepReason;
        }

        public void setExcepReason(String excepReason) {
            this.excepReason = excepReason;
        }

        public int getExcepRejectionReason() {
            return excepRejectionReason;
        }

        public void setExcepRejectionReason(int excepRejectionReason) {
            this.excepRejectionReason = excepRejectionReason;
        }

        public int getExcepType() {
            return excepType;
        }

        public void setExcepType(int excepType) {
            this.excepType = excepType;
        }
    }

    public static class ExcepHandleBean {
        /**
         * handleAmount : 20
         * handleProgress : 处理了一哈就解决了问题
         * handleTime : 1499253519699
         * handleType : 1
         */

        private int handleAmount;
        private String handleProgress;
        private long handleTime;
        private int handleType;

        public int getHandleAmount() {
            return handleAmount;
        }

        public void setHandleAmount(int handleAmount) {
            this.handleAmount = handleAmount;
        }

        public String getHandleProgress() {
            return handleProgress;
        }

        public void setHandleProgress(String handleProgress) {
            this.handleProgress = handleProgress;
        }

        public long getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(long handleTime) {
            this.handleTime = handleTime;
        }

        public int getHandleType() {
            return handleType;
        }

        public void setHandleType(int handleType) {
            this.handleType = handleType;
        }
    }

    public static class OrderBean {
        /**
         * orderAffiliateReceive : 4
         * orderId : 183
         * orderNo : 5002321001498894041000
         * orderSendAddr : 重庆市|重庆|武隆县|巷口镇|巷口镇世纪广场巷口镇黄金村卫生室
         * orderSendAreaId : 500232100
         * orderSenderName : iOS数据即将离开
         * orderStatus : 12
         * orderTime : 1498894041000
         * orderTotalPrice : 15
         */

        private int orderAffiliateReceive;
        private long orderId;
        private String orderNo;
        private String orderSendAddr;
        private int orderSendAreaId;
        private String orderSenderName;
        private int orderStatus;
        private long orderTime;
        private double orderTotalPrice;

        public int getOrderAffiliateReceive() {
            return orderAffiliateReceive;
        }

        public void setOrderAffiliateReceive(int orderAffiliateReceive) {
            this.orderAffiliateReceive = orderAffiliateReceive;
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

        public String getOrderSenderName() {
            return orderSenderName;
        }

        public void setOrderSenderName(String orderSenderName) {
            this.orderSenderName = orderSenderName;
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
    }
}
