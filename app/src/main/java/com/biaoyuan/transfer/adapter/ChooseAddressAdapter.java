package com.biaoyuan.transfer.adapter;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.ChooseAddress;

import java.util.List;

/**
 * Title  :
 * Create : 2017/6/8
 * Author ：chen
 */

public class ChooseAddressAdapter extends BaseQuickAdapter<ChooseAddress, BaseViewHolder> {


    public ChooseAddressAdapter(int layoutResId, List<ChooseAddress> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseAddress item, int position) {
        helper.setTextViewText(R.id.tv_name, item.getAddress())
        .setTextViewText(R.id.tv_address,item.getAddressAll())
        ;
    }
}
