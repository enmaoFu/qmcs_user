package com.biaoyuan.transfer.adapter;

import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.MineTransferInfo;
import com.biaoyuan.transfer.util.MyNumberFormat;

import java.util.List;

/**
 * Title  :
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineTransferAdapter extends BaseQuickAdapter<MineTransferInfo, BaseViewHolder> {

    // 1待取件 2进行中  3完成 4异常
    private int type = 1;

    public MineTransferAdapter(int layoutResId, List<MineTransferInfo> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineTransferInfo item, int position) {


        switch (type) {
            case 1:
                helper.setTextViewText(R.id.tv_content, "包裹  " + item.getPackageWeight() + "kg  最长边≤" + item.getPackageSize() + "cm")


                ;
                helper.setTextViewText(R.id.tv_title, "抢单时间：" + DateTool.timesToStrTime(item.getPublishComfirmTime() + "", "yyyy.MM.dd HH:mm"))
                        .setTextViewText(R.id.tv_state, "待取件").setViewVisibility(R.id.tv_msg, View.GONE).setViewVisibility(R.id.tv_time, View.GONE)
                        .setViewVisibility(R.id.tv_left, View.GONE)
                        .setViewVisibility(R.id.tv_out_time, View.GONE)
                        .setViewVisibility(R.id.tv_right, View.VISIBLE)
                        .setViewVisibility(R.id.tv_price, View.VISIBLE)
                        .setTextViewText(R.id.tv_price, "¥" + MyNumberFormat.m2(item.getPublishReward()))
                        .setTextViewText(R.id.tv_start_address, item.getSendBasicName())
                        .setTextViewText(R.id.tv_end_address, item.getReceiveBasicName())
                        .setViewVisibility(R.id.v_diver, View.VISIBLE);


                break;
            case 2:
                helper.setTextViewText(R.id.tv_content, "包裹  " + item.getPackageWeight() + "kg  最长边≤" + item.getPackageSize() + "cm")


                ;
                helper.setTextViewText(R.id.tv_title, "打包码  " + item.getPackageCode())
                        .setTextViewText(R.id.tv_state, "进行中").setViewVisibility(R.id.tv_msg, View.GONE).setViewVisibility(R.id.tv_time, View.VISIBLE)
                        .setTextViewText(R.id.tv_time, "接单时间：" + DateTool.timesToStrTime(item.getCarryPickupTime() + "", "yyyy.MM.dd HH:mm"))
                        .setViewVisibility(R.id.tv_left, View.GONE)
                        .setViewVisibility(R.id.tv_out_time, View.GONE)
                        .setViewVisibility(R.id.v_diver, View.GONE)
                        .setViewVisibility(R.id.tv_price, View.VISIBLE)
                        .setTextViewText(R.id.tv_price, "¥" + MyNumberFormat.m2(item.getPublishReward()))
                        .setTextViewText(R.id.tv_start_address, item.getSendBasicName())
                        .setTextViewText(R.id.tv_end_address, item.getReceiveBasicName())
                        .setViewVisibility(R.id.tv_right, View.GONE);


                break;
            case 3:
                helper.setTextViewText(R.id.tv_content, "包裹  " + item.getPackageWeight() + "kg  最长边≤" + item.getPackageSize() + "cm")


                ;
                helper.setTextViewText(R.id.tv_title, "打包码  " + item.getPackageCode())
                        .setTextViewText(R.id.tv_state, "已完成").setViewVisibility(R.id.tv_msg, View.VISIBLE).setViewVisibility(R.id.tv_time, View.VISIBLE)
                        .setTextViewText(R.id.tv_time, "接单时间：" + DateTool.timesToStrTime(item.getCarryPickupTime() + "", "yyyy.MM.dd HH:mm"))
                        .setTextViewText(R.id.tv_msg, "送达时间：" + DateTool.timesToStrTime(item.getCarryCheckTime() + "", "yyyy.MM.dd HH:mm"))
                        .setTextViewTextColor(R.id.tv_msg,  mContext.getResources().getColor(R.color.colorAccent))
                        .setViewVisibility(R.id.tv_left, View.VISIBLE)
                        .addOnClickListener(R.id.tv_left)
                        .setViewVisibility(R.id.tv_price, View.VISIBLE)
                        .setTextViewText(R.id.tv_price, "¥" + MyNumberFormat.m2(item.getPublishReward()))
                        .setViewVisibility(R.id.tv_out_time, View.GONE)
                        .setViewVisibility(R.id.v_diver, View.VISIBLE)
                        .setTextViewText(R.id.tv_start_address, item.getSendBasicName())
                        .setTextViewText(R.id.tv_end_address, item.getReceiveBasicName())
                        .setViewVisibility(R.id.tv_right, View.GONE);


                break;
            case 4:


                switch (item.getGoodsType()) {
                    case 1:

                        helper.setTextViewText(R.id.tv_content, "文件  " + item.getGoodsWeight() + "kg  最长边≤" + item.getGoodsSize() + "cm");
                        break;
                    case 2:
                        helper.setTextViewText(R.id.tv_content, "办公／居家  " + item.getGoodsWeight() + "kg  最长边≤" + item.getGoodsSize() + "cm");
                        break;
                    case 3:
                        helper.setTextViewText(R.id.tv_content, "鲜花  " + item.getGoodsWeight() + "kg  最长边≤" + item.getGoodsSize() + "cm");
                        break;
                    case 4:
                        helper.setTextViewText(R.id.tv_content, "包裹  " + item.getGoodsWeight() + "kg  最长边≤" + item.getGoodsSize() + "cm");
                        break;
                    case 5:
                        helper.setTextViewText(R.id.tv_content, "蛋糕  " + item.getGoodsWeight() + "kg  最长边≤" + item.getGoodsSize() + "cm");
                        break;
                }


                helper.setViewVisibility(R.id.tv_msg, View.VISIBLE).setViewVisibility(R.id.tv_time, View.VISIBLE)
                        .setTextViewText(R.id.tv_time, "接单时间：" + DateTool.timesToStrTime(item.getCarryPickupTime() + "", "yyyy.MM.dd HH:mm"))
                        .setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.font_red))
                        .setViewVisibility(R.id.tv_left, View.GONE)
                        .setViewVisibility(R.id.v_diver, View.GONE)
                        .setViewVisibility(R.id.tv_price, View.GONE)
                        .setTextViewText(R.id.tv_start_address, item.getSendName())
                        .setTextViewText(R.id.tv_end_address, item.getReceiveName())
                        .setViewVisibility(R.id.tv_right, View.GONE);

                switch (item.getExcepType()) {
                    case 7:
                        helper.setTextViewText(R.id.tv_title, "快件码  " + item.getPackageCode());
                        //超时
                        helper.setTextViewText(R.id.tv_state, "传送超时").setViewVisibility(R.id.tv_out_time, View.VISIBLE);

                        //如果在传送中
                        if (item.getCarryCheckTime() == 0) {
                            long time = (System.currentTimeMillis() - item.getPublishReqDelivTime()) / 1000;
                            helper.setTextViewText(R.id.tv_msg, "送达时间：未送达");
                            helper.setTextViewText(R.id.tv_out_time, getTime(time));

                        } else {
                            helper.setTextViewText(R.id.tv_msg, "送达时间：" + DateTool.timesToStrTime(item.getCarryCheckTime() + "", "yyyy.MM.dd HH:mm"));
                            helper.setTextViewText(R.id.tv_out_time, getTime((item.getCarryCheckTime() - item.getPublishReqDelivTime()) / 1000));
                        }


                        break;
                    case 5:
                        //丢失
                        helper.setTextViewText(R.id.tv_state, "单件丢失").setTextViewText(R.id.tv_msg, "目的网点未检测到该件")
                                .setViewVisibility(R.id.tv_out_time, View.GONE);
                        helper.setTextViewText(R.id.tv_title, "快件码  " + item.getTrackingCode());

                        break;
                    case 6:
                        //破损
                        helper.setTextViewText(R.id.tv_state, "单件破损").setTextViewText(R.id.tv_msg, "目的网点检测到该件出现严重破损")
                                .setViewVisibility(R.id.tv_out_time, View.GONE)
                        ;
                        helper.setTextViewText(R.id.tv_title, "快件码  " + item.getTrackingCode());

                        break;
                }


                break;


        }

    }

    /**
     * 计算超时
     *
     * @param time 秒
     * @return
     */
    private String getTime(long time) {

        if (time < 60) {

            return "(超时" + time + "秒)";
        }

        if (time < 3600) {

            return "(超时" + time / 60 + "分钟)";
        }


        if (time < 3600 * 24) {

            return "(超时" + time / (60 * 60) + "小时)";
        }


        return "(超时" + time / (60 * 60 * 24) + "天)";
    }


}
