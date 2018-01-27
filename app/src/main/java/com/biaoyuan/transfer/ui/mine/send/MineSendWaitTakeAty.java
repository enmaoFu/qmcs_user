package com.biaoyuan.transfer.ui.mine.send;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
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
 * Title  :待取件 待确认
 * Create : 2017/6/21
 * Author ：chen
 */

public class MineSendWaitTakeAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.lv_data)
    ListViewForScrollView mLvData;
    @Bind(R.id.ll_bg)
    LinearLayout mLlBg;
    @Bind(R.id.phone)
    TextView mPhone;
    @Bind(R.id.tv_city)
    TextView mTvCity;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.tv_go_detail)
    TextView mTvGoDetail;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;

    private SendWaitSingWuniuAdapter mAdapter;

    private int orderStatus;
    private long orderId;
    private MineSendOrderDetail mOrderDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_send_wait_take;
    }

    @Override
    public void initData() {

        orderStatus = getIntent().getIntExtra("orderStatus", 0);
        orderId = getIntent().getLongExtra("orderId", 0);

        switch (orderStatus) {
            case 1:
                initToolbar(mToolbar, "待确认");
                //显示取消按钮
                mTvCommit.setVisibility(View.VISIBLE);
                break;
            case 2:
                initToolbar(mToolbar, "待取件");
                mTvCommit.setVisibility(View.GONE);
                break;
        }


        List<WaitSignWuniuItem> wuniuItems = new ArrayList<>();

        mAdapter = new SendWaitSingWuniuAdapter(this, wuniuItems, R.layout.item_wuniu);

        mLvData.setAdapter(mAdapter);


    }

    @OnClick({R.id.tv_go_detail,R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_go_detail:
                Bundle bundle=new Bundle();
                bundle.putParcelable("data",mOrderDetail);
                startActivity(MineOrderDetail.class, bundle);
                break;
            case R.id.tv_commit:
                //取消订单
                new MaterialDialog(this).setMDMessage("是否确认取消该订单？").setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                    @Override
                    public void dialogBtnOnClick() {
                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(Send.class).orderCancel(orderId), 2);
                    }
                }).show();

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
                String s = "";
                for (int i = 3; i < address.length; i++) {
                    s += address[i];
                }
                mTvAddress.setText(s);

                mAdapter.removeAll();
                List<WaitSignWuniuItem> wuniuItems = AppJsonUtil.getArrayList(result, "delivery", WaitSignWuniuItem.class);
                if (wuniuItems!=null){
                    Collections.reverse(wuniuItems);
                    mAdapter.addAll(wuniuItems);

                }


                break;
            case 2:
                showToast("取消成功");
                finish();
                break;
        }
    }
}
