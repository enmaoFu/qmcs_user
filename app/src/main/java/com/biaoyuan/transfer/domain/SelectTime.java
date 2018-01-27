package com.biaoyuan.transfer.domain;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Title  :
 * Create : 2017/6/14
 * Author ：chen
 */

public class SelectTime implements IPickerViewData{
    private String hour;
    private ArrayList<String> min;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public ArrayList<String> getMin() {
        return min;
    }

    public void setMin(ArrayList<String> min) {
        this.min = min;
    }

    @Override
    public String getPickerViewText() {

        return hour;
    }
}
