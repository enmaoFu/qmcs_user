package com.biaoyuan.transfer.adapter;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.biaoyuan.transfer.domain.AddressInfo;

import java.util.List;

/**
 * Title  :
 * Create : 2017/4/26
 * Author ：chen
 */

public class AddressAdapter extends BaseQuickAdapter<AddressInfo,BaseViewHolder> {
    public AddressAdapter(int layoutResId, List<AddressInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressInfo item, int position) {

    }
}
