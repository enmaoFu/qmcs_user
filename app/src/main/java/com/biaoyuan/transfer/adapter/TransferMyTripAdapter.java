package com.biaoyuan.transfer.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.TransferMyTripInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class TransferMyTripAdapter extends CommonAdapter<TransferMyTripInfo> {

    public TransferMyTripAdapter(Context context, List<TransferMyTripInfo> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, TransferMyTripInfo item, int positon) {

        holder.setTextViewText(R.id.tv_address, item.getOutCity()+item.getOutArea() + "-" + item.getEntCity()+item.getEntArea())
                .setTextViewText(R.id.tv_time, DateTool.timesToStrTime(item.getDepartureTime() + "", "yyyy.MM.dd"))
                .setTextViewText(R.id.tv_time_hour, DateTool.timesToStrTime(item.getDepartureTime() + "", "HH:mm") + "出发");

    }
}
