package com.biaoyuan.transfer.adapter;

import android.text.TextUtils;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.PinLunItem;
import com.biaoyuan.transfer.util.MyNumberFormat;

import java.util.List;

import am.widget.drawableratingbar.DrawableRatingBar;

/**
 * Title  :
 * Create : 2017/5/22
 * Author ：chen
 */

public class PinLunAdapter extends BaseQuickAdapter<PinLunItem, BaseViewHolder> {
    public PinLunAdapter(int layoutResId, List<PinLunItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinLunItem item, int position) {

        helper.setImageByUrl(R.id.mine_img, item.getUserPortraitUrl())
                .setTextViewText(R.id.tv_phone, MyNumberFormat.toPwdPhone(item.getUserTelphone()))
                .setTextViewText(R.id.tv_code, item.getOrderNo())
                .setTextViewText(R.id.tv_time, DateTool.timesToStrTime(item.getCommentTime() + "", "yyyy.MM.dd"))
                .setTextViewText(R.id.tv_check_time, DateTool.timesToStrTime(item.getOrderUpdateTime() + "", "yyyy.MM.dd HH:mm"));

        if (TextUtils.isEmpty(item.getCommentContent())) {
            helper.setViewVisibility(R.id.tv_pinlun, View.GONE);
        } else {
            helper.setViewVisibility(R.id.tv_pinlun, View.VISIBLE).setTextViewText(R.id.tv_pinlun, item.getCommentContent());
        }
        DrawableRatingBar ratingBar = helper.getView(R.id.rb_pinfen);
        ratingBar.setRating(item.getCommentScore());
        String address[] = item.getOrderReceiveAddr().split("\\|");

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(address[0] + " ");
        stringBuffer.append(" ");
        stringBuffer.append(address[1] + address[2] + "\n");
        for (int i = 3; i < address.length; i++) {
            stringBuffer.append(address[i]);
        }
        helper.setTextViewText(R.id.tv_address, stringBuffer.toString());


    }
}
