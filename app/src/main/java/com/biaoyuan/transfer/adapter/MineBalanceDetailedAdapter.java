package com.biaoyuan.transfer.adapter;

import android.graphics.Color;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.MineBalanceDetailedInfo;
import com.biaoyuan.transfer.util.MyNumberFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class MineBalanceDetailedAdapter extends BaseQuickAdapter<MineBalanceDetailedInfo,BaseViewHolder> {

    private final int EXPENDITURE = 1;
    private final int RECHARGE = 2;
    private final int REFUND = 3;
    private final int PAYMNET = 4;
    private final int PROFIT = 5;
    private final int DEDUCT = 6;
    private final int WITHDRAWALS = 7;

    public MineBalanceDetailedAdapter(int layoutResId, List<MineBalanceDetailedInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineBalanceDetailedInfo item, int position) {

        helper.setTextViewText(R.id.balanceOperationTime,DateTool.timesToStrTime(item.getBalanceOperationTime() + "", "yyyy-MM-dd HH:mm"));
        helper.setTextViewText(R.id.balanceOperationAfter,"余额¥" + MyNumberFormat.m2(Double.parseDouble(String.valueOf(item.getBalanceOperationAfter()))));
        helper.setTextViewText(R.id.balanceAmount,"¥" + MyNumberFormat.m2(item.getBalanceAmount()));
        //交易类型。1订单支出、2充值、3退款、4赔付5传送收益6扣手续费退款7提现
        switch (item.getBalanceTansactionType()){
            case EXPENDITURE:
                helper.setTextViewText(R.id.balanceTansactionType,"订单支出");
                break;
            case RECHARGE:
                helper.setTextViewText(R.id.balanceTansactionType,"充值");
                break;
            case REFUND:
                helper.setTextViewText(R.id.balanceTansactionType,"退款");
                break;
            case PAYMNET:
                helper.setTextViewText(R.id.balanceTansactionType,"赔付");
                break;
            case PROFIT:
                helper.setTextViewText(R.id.balanceTansactionType,"传送收益");
                break;
            case DEDUCT:
                helper.setTextViewText(R.id.balanceTansactionType,"扣手续费退款");
                break;
            case WITHDRAWALS:
                helper.setTextViewText(R.id.balanceTansactionType,"提现");
                break;
        }

        if (position % 2 == 0) {
            helper.setBackgroundColor(R.id.item, Color.parseColor("#F1F1F3"));
        } else {
            helper.setBackgroundColor(R.id.item, Color.parseColor("#ffffff"));
        }

    }
}
