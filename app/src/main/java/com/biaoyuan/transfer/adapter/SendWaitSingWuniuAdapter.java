package com.biaoyuan.transfer.adapter;

import android.content.Context;
import android.view.View;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.WaitSignWuniuItem;

import java.util.List;

/**
 * Title  :待签收物流
 * Create : 2017/6/21
 * Author ：chen
 */

public class SendWaitSingWuniuAdapter extends CommonAdapter<WaitSignWuniuItem> {
    public SendWaitSingWuniuAdapter(Context context, List<WaitSignWuniuItem> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, WaitSignWuniuItem item, int positon) {

        holder.setTextViewText(R.id.tv_hour, DateTool.timesToStrTime(item.getTime() + "", "HH:mm"))
                .setTextViewText(R.id.tv_date, DateTool.timesToStrTime(item.getTime() + "", "yyyy.MM.dd"))
                .setTextViewText(R.id.tv_msg, item.getMsg());

        if (item.getTel() == 0) {
            holder.setViewVisibility(R.id.tv_phone, View.GONE);
        } else {
            holder.setViewVisibility(R.id.tv_phone, View.VISIBLE).setTextViewText(R.id.tv_phone, "【" + item.getTel() + "】");


        }

        if (positon == 0) {
            holder.setTextViewTextColor(R.id.tv_hour, mContext.getResources().getColor(R.color.colorAccent))
                    .setTextViewTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.colorAccent))
                    .setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.colorAccent))
                    .setTextViewTextColor(R.id.tv_phone, mContext.getResources().getColor(R.color.font_red))
                    .setBackgroundDrawable(R.id.tv_circle, mContext.getResources().getDrawable(R.drawable.shape_circle_bule))
            ;

        } else {

            holder.setTextViewTextColor(R.id.tv_hour, mContext.getResources().getColor(R.color.font_gray))
                    .setTextViewTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.font_gray))
                    .setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.font_gray))
                    .setBackgroundDrawable(R.id.tv_circle, mContext.getResources().getDrawable(R.drawable.shape_circle_gray))
                    .setTextViewTextColor(R.id.tv_phone, mContext.getResources().getColor(R.color.font_gray));

        }
    }
}
