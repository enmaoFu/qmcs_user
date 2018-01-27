package com.biaoyuan.transfer.ui.mine.send;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.adapter.SendWaitSingWuniuAdapter;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.MineSendOrderDetail;
import com.biaoyuan.transfer.domain.WaitSignWuniuItem;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.util.AppJsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :待签收  已签收
 * Create : 2017/6/21
 * Author ：chen
 */

public class MineSendWaitSignAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.lv_data)
    ListViewForScrollView mLvData;
    @Bind(R.id.ll_bg)
    LinearLayout mLlBg;
    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.phone)
    TextView mPhone;
    @Bind(R.id.tv_city)
    TextView mTvCity;
    @Bind(R.id.address)
    TextView mAddress;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_go_detail)
    TextView mTvGoDetail;
    @Bind(R.id.tv_start_city)
    TextView mTvStartCity;
    @Bind(R.id.tv_end_city)
    TextView mTvEndCity;
    @Bind(R.id.v_end)
    View mVEnd;
    @Bind(R.id.v_end_bg)
    View mVEndBg;
    @Bind(R.id.img_start)
    ImageView mImgStart;
    @Bind(R.id.tv_end)
    TextView mTvEnd;


    private SendWaitSingWuniuAdapter mAdapter;


    private long orderId;
    private MineSendOrderDetail mOrderDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_send_wait_sign;
    }

    @Override
    public void initData() {

        int order_state = getIntent().getIntExtra("orderStatus", 9);

        switch (order_state) {
            case 9:
                //已完成
                initToolbar(mToolbar, "已完成");
                mVEndBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_circle_yellow));
                mVEnd.setBackgroundColor(Color.parseColor("#F4B95D"));
                mTvEnd.setVisibility(View.VISIBLE);
                mImgStart.setVisibility(View.GONE);
                mTvEndCity.setTextColor(getResources().getColor(R.color.bg_yellow));

                break;
            default:
                initToolbar(mToolbar, "待签收");
                mTvEnd.setVisibility(View.GONE);
                mImgStart.setVisibility(View.VISIBLE);
                break;
        }


        orderId = getIntent().getLongExtra("orderId", 0);
        List<WaitSignWuniuItem> wuniuItems = new ArrayList<>();

        mAdapter = new SendWaitSingWuniuAdapter(this, wuniuItems, R.layout.item_wuniu);

        mLvData.setAdapter(mAdapter);


    }

    @OnClick({R.id.tv_go_detail})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_go_detail:
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", mOrderDetail);
                startActivity(MineOrderDetail.class, bundle);
                break;

        }
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Send.class).orderInfo(orderId), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                mOrderDetail = AppJsonUtil.getObject(result, "order", MineSendOrderDetail.class);

                mName.setText(mOrderDetail.getOrderReceiverName());
                mPhone.setText(mOrderDetail.getOrderReceiverTel() + "");
                String[] address = mOrderDetail.getOrderReceiveAddr().split("\\|");

                mTvCity.setText(address[0] + address[1] + "  " + address[2]);


                String[] addressSend = mOrderDetail.getOrderSendAddr().split("\\|");

                mTvStartCity.setText(addressSend[1] + addressSend[2]);

                mTvEndCity.setText(address[1] + address[2]);

                String s = "";
                for (int i = 3; i < address.length; i++) {
                    s += address[i];
                }
                mAddress.setText(s);

                mTvCode.setText(mOrderDetail.getOrderTrackingCode());
                mAdapter.removeAll();

                //判断是否是第三方
                List<WaitSignWuniuItem> wuniuItems;
                if (mOrderDetail.getOrderIsThirdExpress() == 0) {
                    wuniuItems = AppJsonUtil.getArrayList(result, "delivery", WaitSignWuniuItem.class);
                    if (wuniuItems != null) {
                        Collections.reverse(wuniuItems);
                        mAdapter.addAll(wuniuItems);

                    }
                } else {

                    wuniuItems = AppJsonUtil.getMyArrayList(AppJsonUtil.getString(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "express100"), "data"), WaitSignWuniuItem.class);
                    if (wuniuItems != null) {
                        mAdapter.addAll(wuniuItems);

                    }
                }


                break;


        }
    }
}
