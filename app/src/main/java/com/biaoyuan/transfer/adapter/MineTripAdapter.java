package com.biaoyuan.transfer.adapter;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.MineTripInfo;

import java.util.List;

/**
 * Title  :
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineTripAdapter extends BaseQuickAdapter<MineTripInfo, BaseViewHolder> {


    public MineTripAdapter(int layoutResId, List<MineTripInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineTripInfo item, int position) {


        helper.setTextViewText(R.id.tv_address, item.getPathDeparture().getEntCity() + item.getPathDeparture().getEntArea()+item.getPathDeparture().getStreet() + "-" + item.getPathDestination().getEntCity() + item.getPathDestination().getEntArea()+item.getPathDestination().getStreet())
                .setTextViewText(R.id.tv_time, DateTool.timesToStrTime(item.getPathDepartureTime() + "", "yyyy.MM.dd"))
                .setTextViewText(R.id.tv_time_hour, DateTool.timesToStrTime(item.getPathDepartureTime() + "", "HH:mm") + "出发");
    }
}
