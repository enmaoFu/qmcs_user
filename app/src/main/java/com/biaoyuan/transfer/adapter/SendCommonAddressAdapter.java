package com.biaoyuan.transfer.adapter;

import com.and.yzy.frame.adapter.recyclerview.BaseQuickAdapter;
import com.and.yzy.frame.adapter.recyclerview.BaseViewHolder;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.domain.SendCommonAddressInfo;

import java.util.List;

/**
 * Title  : 常用地址适配器
 * Create : 2017/6/01
 * Author ：enmaoFu
 */
public class SendCommonAddressAdapter extends BaseQuickAdapter<SendCommonAddressInfo, BaseViewHolder> {


    private SendCommonAddressInfo mAddressInfo = null;

    /**
     * 得到选中的
     *
     * @return
     */
    public SendCommonAddressInfo getSelectAddressInfo() {
        return mAddressInfo;
    }

    public void setAddressInfo(SendCommonAddressInfo addressInfo) {
        mAddressInfo = addressInfo;
    }

    public SendCommonAddressAdapter(int layoutResId, List<SendCommonAddressInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SendCommonAddressInfo item, int position) {

        if (item.getAddressIsDefault() == 1) {

            helper.setBackgroundDrawable(R.id.ll_bg, mContext.getResources().getDrawable(R.drawable.shape_left_top_address))
                    .setRadioBtnChecked(R.id.cb_save, true)
                    .setRadioBtnText(R.id.cb_save, "默认地址")
                    .setTextViewTextColor(R.id.cb_save, mContext.getResources().getColor(R.color.colorAccent))
                    .setBackgroundColor(R.id.divier, mContext.getResources().getColor(R.color.colorAccent))
            ;
            setAddressInfo(item);
        } else {

            helper.setBackgroundDrawable(R.id.ll_bg, mContext.getResources().getDrawable(R.drawable.shape_left_top_no_choose_address))
                    .setRadioBtnChecked(R.id.cb_save, false)
                    .setRadioBtnText(R.id.cb_save, "设为默认地址")
                    .setTextViewTextColor(R.id.cb_save, mContext.getResources().getColor(R.color.font_gray))
                    .setBackgroundColor(R.id.divier, mContext.getResources().getColor(R.color.divier_color))
            ;

        }

        String address[] = item.getAddressAddress().split("\\|");
        helper.setTextViewText(R.id.name, item.getAddressName())
                .setTextViewText(R.id.phone, item.getAddressPhone() + "")
                .setTextViewText(R.id.tv_city, address[0] + address[1] + address[2])

                .addOnClickListener(R.id.tv_edit)
                .addOnClickListener(R.id.tv_dalete)
                .addOnClickListener(R.id.cb_save)
        ;


        if (address.length == 6) {
            helper.setTextViewText(R.id.address, address[3] + address[4] + address[5]);
        } else {
            helper.setTextViewText(R.id.address, address[3] + address[4]);
        }
    }
}
