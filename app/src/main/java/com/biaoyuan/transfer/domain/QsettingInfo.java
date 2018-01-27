package com.biaoyuan.transfer.domain;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Title  :计价规则
 * Create : 2017/6/8
 * Author ：chen
 */

public class QsettingInfo implements IPickerViewData {


    /**
     * qsettingCreatTime : 1
     * qsettingId : 9
     * qsettingIsDeleted : 0
     * qsettingParamDesc : 类型1距离最大值
     * qsettingParamFathernode : 6
     * qsettingParamValue : 0
     * qsettingParameter : distanceLimit
     * qsettingUpdateTime : 1
     * qsettingVersion : 0
     */

    private int qsettingId;
    private String qsettingParamDesc;
    private int qsettingParamFathernode;
    private int qsettingParamValue;
    private String qsettingParameter;


    private ArrayList<Integer> sizeList;
    private ArrayList<String> sizeStringList;
    private ArrayList<Integer> weightList;
    private ArrayList<String> weightStringList;

    public ArrayList<String> getSizeStringList() {
        return sizeStringList;
    }

    public void setSizeStringList(ArrayList<String> sizeStringList) {
        this.sizeStringList = sizeStringList;
    }

    public ArrayList<String> getWeightStringList() {
        return weightStringList;
    }

    public void setWeightStringList(ArrayList<String> weightStringList) {
        this.weightStringList = weightStringList;
    }

    public ArrayList<Integer> getSizeList() {
        return sizeList;
    }

    public void setSizeList(ArrayList<Integer> sizeList) {
        this.sizeList = sizeList;
    }

    public ArrayList<Integer> getWeightList() {
        return weightList;
    }

    public void setWeightList(ArrayList<Integer> weightList) {
        this.weightList = weightList;
    }

    private int sizeMax;
    private int sizeMin;
    private int sizeStep;


    private int weightMax;
    private int weightMin;
    private int weightStep;


    public int getSizeMax() {
        return sizeMax;
    }

    public void setSizeMax(int sizeMax) {
        this.sizeMax = sizeMax;
    }

    public int getSizeMin() {
        return sizeMin;
    }

    public void setSizeMin(int sizeMin) {
        this.sizeMin = sizeMin;
    }

    public int getSizeStep() {
        return sizeStep;
    }

    public void setSizeStep(int sizeStep) {
        this.sizeStep = sizeStep;
    }

    public int getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(int weightMax) {
        this.weightMax = weightMax;
    }

    public int getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(int weightMin) {
        this.weightMin = weightMin;
    }

    public int getWeightStep() {
        return weightStep;
    }

    public void setWeightStep(int weightStep) {
        this.weightStep = weightStep;
    }

    public int getQsettingId() {
        return qsettingId;
    }

    public void setQsettingId(int qsettingId) {
        this.qsettingId = qsettingId;
    }

    public String getQsettingParamDesc() {
        return qsettingParamDesc;
    }

    public void setQsettingParamDesc(String qsettingParamDesc) {
        this.qsettingParamDesc = qsettingParamDesc;
    }

    public int getQsettingParamFathernode() {
        return qsettingParamFathernode;
    }

    public void setQsettingParamFathernode(int qsettingParamFathernode) {
        this.qsettingParamFathernode = qsettingParamFathernode;
    }

    public int getQsettingParamValue() {
        return qsettingParamValue;
    }

    public void setQsettingParamValue(int qsettingParamValue) {
        this.qsettingParamValue = qsettingParamValue;
    }

    public String getQsettingParameter() {
        return qsettingParameter;
    }

    public void setQsettingParameter(String qsettingParameter) {
        this.qsettingParameter = qsettingParameter;
    }

    @Override
    public String getPickerViewText() {
        return this.getQsettingParamDesc();
    }
}
