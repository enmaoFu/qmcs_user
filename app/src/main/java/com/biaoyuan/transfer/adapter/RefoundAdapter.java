package com.biaoyuan.transfer.adapter;

import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.RefoundInfo;
import com.biaoyuan.transfer.util.MyNumberFormat;

import java.util.List;

/**
 * Title  :
 * Create : 2017/6/28
 * Author ：chen
 */

public class RefoundAdapter extends BaseQuickAdapter<RefoundInfo, BaseViewHolder> {


    private String state;
    public RefoundAdapter(int layoutResId, List<RefoundInfo> data,String state) {
        super(layoutResId, data);
        this.state=state;

    }

    @Override
    protected void convert(BaseViewHolder helper, RefoundInfo item, int position) {

        helper.setTextViewText(R.id.tv_code, "订单编号：" + item.getOrder().getOrderNo())
                .setTextViewText(R.id.tv_name, item.getOrder().getOrderSenderName())
                .setTextViewText(R.id.tv_take_address, item.getBaseName())
                .setTextViewText(R.id.tv_commit_time, "下单时间：" + DateTool.timesToStrTime(item.getOrder().getOrderTime() + "", "yyyy.MM.dd HH:mm"))
        .setTextViewText(R.id.tv_price, "¥" + MyNumberFormat.m2(item.getOrder().getOrderTotalPrice()))
        ;

        //判断是否在处理中
        switch (item.getOrderExcep().getExcepHandleStatus()) {
            case 1:
            case 2:

               helper .setViewVisibility(R.id.tv_tui_price, View.GONE)
                ;
                break;
            case 3:
                //已处理 显示处理金额

                if (item.getExcepHandle() != null) {
                    helper .setViewVisibility(R.id.tv_tui_price, View.VISIBLE);
                    if (state.equals("refund")){
                        helper.setTextViewText(R.id.tv_tui_price, "(退款:¥" + MyNumberFormat.m2(item.getExcepHandle().getHandleAmount())+")");
                    }else {
                        helper.setTextViewText(R.id.tv_tui_price, "(赔偿:¥" + MyNumberFormat.m2(item.getExcepHandle().getHandleAmount())+")");
                    }


                }else {
                    helper .setViewVisibility(R.id.tv_tui_price, View.GONE);
                }

                break;
        }


        String address[] = item.getOrder().getOrderSendAddr().split("\\|");

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(address[0] + " ");
        stringBuffer.append(" ");
        stringBuffer.append(address[1] + address[2] + "\n");
        for (int i = 3; i < address.length; i++) {
            stringBuffer.append(address[i]);
        }
        helper.setTextViewText(R.id.tv_address, stringBuffer.toString());


        //  异常订单拒收原因：1-违禁物品； 2-重量体积与实际不符； 3-非本平台可接收快件；4-寄送地址无法送达； 5-其他原因（需在异常原因里输入）可能不是拒收的情况为0，默认为0
        //  1取件拒收2用户无偿取消3取件超时4发送超时5丢失6破损7传送员传送超时8派件超时9用户有偿取消10蛋糕鲜花超时

        switch (item.getOrderExcep().getExcepType()) {

            case 1:
                helper.setTextViewText(R.id.tv_state, "取件拒收");


                //拒收原因
                switch (item.getOrderExcep().getExcepRejectionReason()) {
                    case 1:

                        helper.setTextViewText(R.id.tv_msg, "【温馨提示】 该物品为违禁品");
                        break;
                    case 2:
                        helper.setTextViewText(R.id.tv_msg, "【温馨提示】 该物品重量体积与实际不符");
                        break;
                    case 3:
                        helper.setTextViewText(R.id.tv_msg, "【温馨提示】 该物品为非本平台可接收快件");
                        break;
                    case 4:
                        helper.setTextViewText(R.id.tv_msg, "【温馨提示】 该物品寄送地址无法送达");
                        break;
                    default:
                        helper.setTextViewText(R.id.tv_msg, "【温馨提示】 其他原因");
                        break;
                }

                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                    case 2:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "全额退款中");

                        break;
                    case 3:
                        //已处理
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }


                break;
            case 2:
                //无偿取消
                helper.setTextViewText(R.id.tv_state, "无偿取消发件");
                helper.setTextViewText(R.id.tv_msg, "【温馨提示】 用户取消发件");
                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                    case 2:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "全额退款中");

                        break;
                    case 3:
                        //已处理
                        //退款中
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }


                break;

            case 5:
                helper.setTextViewText(R.id.tv_state, "快件丢失");
                helper.setTextViewText(R.id.tv_msg, "【温馨提示】 送达网点未检测到该件");

                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                    case 2:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "与取件网点核实中");

                        break;
                    case 3:
                        //已处理
                        //退款中
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }

                break;
            case 6:
                helper.setTextViewText(R.id.tv_state, "快件破损");
                helper.setTextViewText(R.id.tv_msg, "【温馨提示】 该物品产生严重破损");


                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                    case 2:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "与取件网点核实中");

                        break;
                    case 3:
                        //已处理
                        //退款中
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }
                break;
            case 9:
                //有偿取消
                helper.setTextViewText(R.id.tv_state, "有偿取消发件");
                helper.setTextViewText(R.id.tv_msg, "【温馨提示】 用户取消发件");


                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                    case 2:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "取件网点核实中");

                        break;
                    case 3:
                        //已处理
                        //退款中
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }
                break;
            case 10:
                helper.setTextViewText(R.id.tv_state, "配送超时");
                helper.setTextViewText(R.id.tv_msg, "【温馨提示】 网点配送超时");

                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                    case 2:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "全额退款中");

                        break;
                    case 3:
                        //已处理
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }
                break;
          /*  case 12:
                helper.setTextViewText(R.id.tv_state, "接件人拒收");
                helper.setTextViewText(R.id.tv_msg, "【温馨提示】 接件人拒绝签收快件");

                //退款状态
                switch (item.getOrderExcep().getExcepHandleStatus()) {
                    case 1:
                        //退款中
                        helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.bg_yellow)).setTextViewText(R.id.tv_time, "与目的网点核实中");

                        break;
                    case 2:
                        //已处理
                        //退款中
                        if (item.getExcepHandle() != null)
                            helper.setTextViewTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.font_gray)).setTextViewText(R.id.tv_time, "退款成功：" + DateTool.timesToStrTime(item.getExcepHandle().getHandleTime() + "", "yyyy.MM.dd HH:mm"));

                        break;
                }
                break;*/

        }


    }
}
