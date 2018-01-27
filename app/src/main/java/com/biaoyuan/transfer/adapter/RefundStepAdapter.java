package com.biaoyuan.transfer.adapter;

import android.content.Context;
import android.view.View;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.MineRefundDetail;

import java.util.List;

/**
 * Title  :赔偿流程
 * Create : 2017/6/21
 * Author ：chen
 */

public class RefundStepAdapter extends CommonAdapter<MineRefundDetail.RefundProcessBean> {
    public RefundStepAdapter(Context context, List<MineRefundDetail.RefundProcessBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MineRefundDetail.RefundProcessBean item, int positon) {

        holder.setTextViewText(R.id.tv_hour, DateTool.timesToStrTime(item.getTime() + "", "HH:mm"))
                .setTextViewText(R.id.tv_date, DateTool.timesToStrTime(item.getTime() + "", "yyyy.MM.dd"))
                .setTextViewText(R.id.tv_msg, item.getTitle())
                .setTextViewText(R.id.tv_phone, item.getContent());


        if (positon == 0) {
            holder.setTextViewTextColor(R.id.tv_hour, mContext.getResources().getColor(R.color.colorAccent))
                    .setTextViewTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.colorAccent))
                    .setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.colorAccent))
                    .setTextViewTextColor(R.id.tv_phone, mContext.getResources().getColor(R.color.font_gray))
                    .setBackgroundDrawable(R.id.tv_circle, mContext.getResources().getDrawable(R.drawable.icon_now))
                    .setViewVisibility(R.id.v_top, View.INVISIBLE)
            ;

            if (mDatas.size() == 1) {
                holder.setViewVisibility(R.id.v_bottom, View.INVISIBLE);

            } else {
                holder.setViewVisibility(R.id.v_bottom, View.VISIBLE);
            }


        } else {

            holder.setTextViewTextColor(R.id.tv_hour, mContext.getResources().getColor(R.color.font_gray))
                    .setTextViewTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.font_gray))
                    .setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.font_black333))
                    .setBackgroundDrawable(R.id.tv_circle, mContext.getResources().getDrawable(R.drawable.shape_circle_gray))
                    .setViewVisibility(R.id.v_top, View.VISIBLE)
                    .setTextViewTextColor(R.id.tv_phone, mContext.getResources().getColor(R.color.font_gray));

            if (positon == mDatas.size() - 1) {
                holder.setViewVisibility(R.id.v_bottom, View.INVISIBLE);

            } else {
                holder.setViewVisibility(R.id.v_bottom, View.VISIBLE);
            }
        }
    }
}
