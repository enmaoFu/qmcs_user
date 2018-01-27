package com.biaoyuan.transfer.domain;

/**
 * Created by Administrator on 2017/4/27.
 */

public class MineBalanceDetailedInfo {


    /**
     * balanceAmount : 10
     * balanceDetail :
     * balanceId : 471
     * balanceObjNo : null
     * balanceOperationAfter : 899990
     * balanceOperationTime : 1499240095275
     * balanceOperationType :1
     * balanceTansactionType : 1
     */

    private  double balanceAmount;
    private String balanceDetail;
    private int balanceId;
    private Object balanceObjNo;
    private double balanceOperationAfter;
    private long balanceOperationTime;
    private int balanceOperationType;
    private int balanceTansactionType;

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getBalanceDetail() {
        return balanceDetail;
    }

    public void setBalanceDetail(String balanceDetail) {
        this.balanceDetail = balanceDetail;
    }

    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    public Object getBalanceObjNo() {
        return balanceObjNo;
    }

    public void setBalanceObjNo(Object balanceObjNo) {
        this.balanceObjNo = balanceObjNo;
    }

    public double getBalanceOperationAfter() {
        return balanceOperationAfter;
    }

    public void setBalanceOperationAfter(double balanceOperationAfter) {
        this.balanceOperationAfter = balanceOperationAfter;
    }

    public long getBalanceOperationTime() {
        return balanceOperationTime;
    }

    public void setBalanceOperationTime(long balanceOperationTime) {
        this.balanceOperationTime = balanceOperationTime;
    }

    public int getBalanceOperationType() {
        return balanceOperationType;
    }

    public void setBalanceOperationType(int balanceOperationType) {
        this.balanceOperationType = balanceOperationType;
    }

    public int getBalanceTansactionType() {
        return balanceTansactionType;
    }

    public void setBalanceTansactionType(int balanceTansactionType) {
        this.balanceTansactionType = balanceTansactionType;
    }
}
