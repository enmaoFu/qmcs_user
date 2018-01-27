package com.biaoyuan.transfer.adapter;

import android.util.Log;
import android.view.View;

import com.and.yzy.frame.adapter.recyclerview.BaseItemDraggableAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.domain.IndexMessageInfo;
import com.biaoyuan.transfer.domain.IndexMessageNewInfo;
import com.biaoyuan.transfer.util.AppJsonUtil;

import java.util.List;

/**
 * Title  : 消息中心适配器
 * Create : 2017/5/24
 * Author ：enmaoFu
 */
public class IndexMessageAdapter extends BaseItemDraggableAdapter<IndexMessageNewInfo,BaseViewHolder> {

    public IndexMessageAdapter(int layoutResId, List<IndexMessageNewInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexMessageNewInfo item, int position) {

        helper.setTextViewText(R.id.title,item.getUmessageTitle());
        helper.setTextViewText(R.id.date,DateTool.timesToStrTime(item.getUmessageCreatTime() + "", "yyyy-MM-dd HH:mm"));
        helper.setTextViewText(R.id.content,item.getUmessageContent());

        switch (item.getUmessageType()){
            case 0:
                helper.setBackgroundDrawable(R.id.change_lin_color,mContext.getResources().getDrawable(R.drawable.shape_msg_left));
                helper.setImageByResource(R.id.change_img_color,R.drawable.terrace_notice);
                break;
            case 1:
                helper.setBackgroundDrawable(R.id.change_lin_color,mContext.getResources().getDrawable(R.drawable.shape_msg_left));
                helper.setImageByResource(R.id.change_img_color,R.drawable.terrace_notice);
                helper.setViewVisibility(R.id.query, View.GONE);
                break;
            case 2:
                helper.setBackgroundDrawable(R.id.change_lin_color,mContext.getResources().getDrawable(R.drawable.shape_msg_left));
                helper.setImageByResource(R.id.change_img_color,R.drawable.terrace_notice);
                helper.setViewVisibility(R.id.query, View.GONE);
                break;
            case 3:
                helper.setBackgroundDrawable(R.id.change_lin_color,mContext.getResources().getDrawable(R.drawable.shape_msg_left1));
                helper.setImageByResource(R.id.change_img_color,R.drawable.refundment_notice);
                break;
            case 4:
                helper.setBackgroundDrawable(R.id.change_lin_color,mContext.getResources().getDrawable(R.drawable.shape_msg_left2));
                helper.setImageByResource(R.id.change_img_color,R.drawable.convey_notice);
                helper.setViewVisibility(R.id.query, View.GONE);

                break;
        }

    }
}
