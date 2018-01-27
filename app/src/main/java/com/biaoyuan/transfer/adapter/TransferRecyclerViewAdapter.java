package com.biaoyuan.transfer.adapter;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.TransferRecItem;
import com.biaoyuan.transfer.util.MyNumberFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class TransferRecyclerViewAdapter extends BaseQuickAdapter<TransferRecItem, BaseViewHolder> {


    public TransferRecyclerViewAdapter(int layoutResId, List<TransferRecItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferRecItem item, int position) {


        helper.setTextViewText(R.id.transfer_rec_item_title, "包裹  " + item.getPackageWeight() + "kg  最长边≤" + item.getPackageSize() + "cm")
                .setTextViewText(R.id.transfer_rec_item_price, "¥" + MyNumberFormat.m2(item.getPublishReward()))
                .setTextViewText(R.id.tv_start_address,  item.getOutBasicAreaName()+item.getOutBasicName())
                .setTextViewText(R.id.tv_end_address, item.getEntBasicAreaName()+item.getEntBasicName())
        ;

        String time=DateTool.getTimeType(item.getPublishReqPickupTime());
        if (time==null){
            helper.setTextViewText(R.id.tv_time, DateTool.timesToStrTime(item.getPublishReqPickupTime()+"","yyyy.MM.dd HH:mm")+"之前");
        }else {
            helper.setTextViewText(R.id.tv_time,time+DateTool.timesToStrTime(item.getPublishReqPickupTime()+"","HH:mm")+"之前");
        }




        double distance = item.getOutDistance();
        if (distance < 1000) {
            helper.setTextViewText(R.id.tv_distance, MyNumberFormat.m2(distance) + "m");
        } else {
            helper.setTextViewText(R.id.tv_distance, MyNumberFormat.m2(distance / 1000) + "km");
        }

    }
}
