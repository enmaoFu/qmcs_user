package com.biaoyuan.transfer.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.MineSendInfo;
import com.biaoyuan.transfer.util.MyNumberFormat;

import java.util.List;

/**
 * Title  :
 * Create : 2017/4/25
 * Author ：chen
 */

public class MineSendAdapter extends BaseQuickAdapter<MineSendInfo, BaseViewHolder> {
    public MineSendAdapter(int layoutResId, List<MineSendInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineSendInfo item, int position) {

        helper.setTextViewText(R.id.tv_number, "订单编号：" + item.getOrderNo())
                .setTextViewText(R.id.tv_user_name, item.getReceiverName());

        String address[] = item.getReceiveAddr().split("\\|");

        StringBuffer adr = new StringBuffer();
        for (int i = 0; i < address.length; i++) {
            adr.append(address[i]);
            if (i == 0) {
                adr.append(" ");
            }
            if (i == 2) {
                adr.append("\n");
            }
        }
        helper.setTextViewText(R.id.tv_address, adr.toString())
                .setTextViewText(R.id.tv_time, "下单时间：" + DateTool.timesToStrTime(item.getOrderTime() + "", "yyyy.MM.dd HH:mm:ss"))
        ;

      /*  用户我的发件  先判断是否支付  0未支付 1部分支付 2已支付
        再看订单状态 ｛1，待确认 2：待取件｝｛3，4，5，6，7，15：待签收｝｛8：待评价｝ */

        switch (item.getPayStatus()) {
            case 0:
                //未支付
                helper.setTextViewText(R.id.tv_state, "待支付");

                helper.setViewVisibility(R.id.tv_price, View.VISIBLE).setTextViewText(R.id.tv_price, "¥" + MyNumberFormat.m2(item.getTotalPrice()));
                helper.setViewVisibility(R.id.tv_msg, View.GONE)
                        .setViewVisibility(R.id.tv_right, View.VISIBLE)
                        .setViewVisibility(R.id.tv_left, View.VISIBLE)
                        .setViewVisibility(R.id.v_diver, View.VISIBLE)
                        .setTextViewText(R.id.tv_right, "立即支付")
                        .addOnClickListener(R.id.tv_right)
                        .addOnClickListener(R.id.tv_left)
                ;


                break;
            case 1:
                //部分支付
                helper.setTextViewText(R.id.tv_state, "部分支付");
                helper.setViewVisibility(R.id.tv_price, View.VISIBLE).setTextViewText(R.id.tv_price, "¥" + MyNumberFormat.m2(item.getPriceAddition()));
                helper.setViewVisibility(R.id.tv_msg, View.VISIBLE)
                        .setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.font_red))
                        .setViewVisibility(R.id.v_diver, View.VISIBLE)
                        .setViewVisibility(R.id.tv_right, View.VISIBLE)
                        .addOnClickListener(R.id.tv_right)
                        .setViewVisibility(R.id.tv_left, View.GONE)
                ;
                switch (item.getAddtionReason()) {
                    case 3:
                        //尺寸
                        helper.setTextViewText(R.id.tv_msg, "超出" + item.getAddtionQuantity() + "cm，产生追加费用");

                        break;
                    case 2:
                        //超重
                        helper.setTextViewText(R.id.tv_msg, "超重" + item.getAddtionQuantity() + "kg，产生追加费用");

                        break;
                }


                break;
            case 2:
                //已支付
                helper.setViewVisibility(R.id.tv_price, View.GONE);
                TextView tv_msg = helper.getView(R.id.tv_msg);
                switch (item.getOrderStatus()) {
                    case 1:
                        //待确认
                        helper.setTextViewText(R.id.tv_state, "待确认");
                        helper.setViewVisibility(R.id.tv_right, View.GONE)
                                .setViewVisibility(R.id.tv_left, View.VISIBLE)
                                .addOnClickListener(R.id.tv_left)
                                .setViewVisibility(R.id.v_diver, View.VISIBLE)
                                .setViewVisibility(R.id.tv_msg, View.VISIBLE)
                        ;

                        SpannableString str = new SpannableString("等待" + item.getBasicSendName() + "确认接件");
                        str.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        str.setSpan(new TextAppearanceSpan(mContext, R.style.styleBlue), 2, 2 + item.getBasicSendName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        str.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 2 + item.getBasicSendName().length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_msg.setText(str, TextView.BufferType.SPANNABLE);


                        break;
                    case 2:
                        //待取件
                        helper.setTextViewText(R.id.tv_state, "待取件");

                        helper.setViewVisibility(R.id.tv_right, View.GONE)
                                .setViewVisibility(R.id.tv_left, View.VISIBLE)
                                .addOnClickListener(R.id.tv_left)
                                .setViewVisibility(R.id.v_diver, View.GONE)
                                .setViewVisibility(R.id.tv_msg, View.VISIBLE).setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.bg_yellow))
                        ;
                        SpannableString str2 = new SpannableString("等待" + item.getBasicSendName() + "上门取件");
                        str2.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        str2.setSpan(new TextAppearanceSpan(mContext, R.style.styleBlue), 2, 2 + item.getBasicSendName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        str2.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 2 + item.getBasicSendName().length(), str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_msg.setText(str2, TextView.BufferType.SPANNABLE);


                        break;
                    case 3:
                    case 4:
                    case 15:
                        //取件派送
                        helper.setTextViewText(R.id.tv_state, "待签收");
                        helper.setViewVisibility(R.id.tv_right, View.GONE)
                                .setViewVisibility(R.id.tv_left, View.GONE)
                                .setViewVisibility(R.id.v_diver, View.GONE)
                        ;

                        SpannableString toSend = new SpannableString(item.getBasicSendName() + "正在派送中");
                        toSend.setSpan(new TextAppearanceSpan(mContext, R.style.styleBlue), 0, item.getBasicSendName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        toSend.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), item.getBasicSendName().length(), toSend.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_msg.setText(toSend, TextView.BufferType.SPANNABLE);


                        break;
                    case 5:

                        //传送员传送中

                        helper.setTextViewText(R.id.tv_state, "待签收");
                        helper.setViewVisibility(R.id.tv_right, View.GONE)
                                .setViewVisibility(R.id.tv_left, View.GONE)
                                .setViewVisibility(R.id.v_diver, View.GONE)
                        ;
                        //判断是否是三方快件

                        if (item.getThirdExpress() == 1) {
                            if (TextUtils.isEmpty(item.getExpressName())) {
                                tv_msg.setText("等待物流公司派送");
                            } else {

                                SpannableString name = new SpannableString(item.getExpressName() + "正在派送");
                                name.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 0, item.getExpressName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                name.setSpan(new TextAppearanceSpan(mContext, R.style.styleBlue), item.getExpressName().length(), name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tv_msg.setText(name, TextView.BufferType.SPANNABLE);
                            }


                        } else {

                            SpannableString name = new SpannableString("传送天使" + item.getTransmintName() + "正在传递中");
                            name.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            name.setSpan(new TextAppearanceSpan(mContext, R.style.styleBlue), 4, 4 + item.getTransmintName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            name.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), 4 + item.getTransmintName().length(), name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv_msg.setText(name, TextView.BufferType.SPANNABLE);

                        }


                        break;
                    case 6:
                    case 7:
                    case 12:
                        //送达网点派送


                        helper.setTextViewText(R.id.tv_state, "待签收");
                        helper.setViewVisibility(R.id.tv_right, View.GONE)
                                .setViewVisibility(R.id.tv_left, View.GONE)
                                .setViewVisibility(R.id.v_diver, View.GONE)
                        ;

                        SpannableString toRecive = new SpannableString(item.getBasicReceiveName() + "正在派送中");
                        toRecive.setSpan(new TextAppearanceSpan(mContext, R.style.styleBlue), 0, item.getBasicReceiveName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        toRecive.setSpan(new TextAppearanceSpan(mContext, R.style.styleYellow), item.getBasicReceiveName().length(), toRecive.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_msg.setText(toRecive, TextView.BufferType.SPANNABLE);

                        break;


                    case 8:
                        //待评价
                        helper.setTextViewText(R.id.tv_state, "待评价");
                        helper.setViewVisibility(R.id.tv_right, View.VISIBLE)
                                .setTextViewText(R.id.tv_right, "立即评价")
                                .addOnClickListener(R.id.tv_right)
                                .setViewVisibility(R.id.tv_left, View.GONE)
                                .setViewVisibility(R.id.v_diver, View.VISIBLE)
                                .setViewVisibility(R.id.tv_msg, View.VISIBLE).setTextViewTextColor(R.id.tv_msg, mContext.getResources().getColor(R.color.font_gray))
                                .setTextViewText(R.id.tv_msg, "签收时间：" + DateTool.timesToStrTime(item.getCheckTime() + "", "yyyy.MM.dd HH:mm"))
                        ;

                        break;
                    case 9:
                        //已完成
                        helper.setTextViewText(R.id.tv_state, "已完成");
                        helper.setViewVisibility(R.id.tv_right, View.GONE)
                                .setViewVisibility(R.id.tv_left, View.GONE)
                                .setViewVisibility(R.id.v_diver, View.GONE)
                                .setViewVisibility(R.id.tv_msg, View.VISIBLE).setTextViewTextColor(R.id.tv_msg,mContext.getResources().getColor(R.color.font_gray) )
                                .setTextViewText(R.id.tv_msg, "签收时间：" + DateTool.timesToStrTime(item.getCheckTime() + "", "yyyy.MM.dd HH:mm"))
                        ;

                        break;
                }


                break;
        }


    }
}
