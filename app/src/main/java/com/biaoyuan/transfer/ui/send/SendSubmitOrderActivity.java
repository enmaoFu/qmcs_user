package com.biaoyuan.transfer.ui.send;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.AddAddressItem;
import com.biaoyuan.transfer.domain.CommitOrderEvent;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 发件-》提交订单页面
 * Create : 2017/5/31
 * Author ：enmaoFu
 */
public class SendSubmitOrderActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_receive_name)
    TextView mTvReceiveName;
    @Bind(R.id.tv_receive_phone)
    TextView mTvReceivePhone;
    @Bind(R.id.collect_user)
    LinearLayout mCollectUser;
    @Bind(R.id.collect_city)
    TextView mCollectCity;
    @Bind(R.id.tv_receive_address)
    TextView mTvReceiveAddress;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_size)
    TextView mTvSize;
    @Bind(R.id.tv_send_name)
    TextView mTvSendName;
    @Bind(R.id.tv_send_phone)
    TextView mTvSendPhone;
    @Bind(R.id.tv_send_address)
    TextView mTvSendAddress;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_price)
    TextView mTvPrice;

    //发送地址
    private AddAddressItem sendAddress;

    //收件地址
    private AddAddressItem receiveAddress;


    //尺寸大小
    private int goodsSize;

    //体积
    private int goodsWeight;

    //类型
    private int goodsType;

    //取件时间
    private long requiredTime;
    private String requiredTimeStr;
    private String goodTypeStr;
    private double mPrice;
    private double mDistance;

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_submit_order;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "提交订单");

        goodsSize = getIntent().getIntExtra("size", 0);
        goodsWeight = getIntent().getIntExtra("weight", 0);
        goodsType = getIntent().getIntExtra("type", 0);

        sendAddress = getIntent().getParcelableExtra("sendAddress");
        receiveAddress = getIntent().getParcelableExtra("receiveAddress");

        requiredTime = getIntent().getLongExtra("longTime", 0L);
        requiredTimeStr = getIntent().getStringExtra("strTime");
        goodTypeStr = getIntent().getStringExtra("typeStr");


        mTvReceiveName.setText(receiveAddress.getName());
        mTvReceivePhone.setText(receiveAddress.getPhone() + "");

        String[] receive_Address = receiveAddress.getAddress().split("\\|");

        mCollectCity.setText(receive_Address[0]);
        mTvReceiveAddress.setText(receive_Address[1] + receive_Address[2]);

        mTvType.setText(goodTypeStr);
        mTvSize.setText("最长边≤" + goodsSize + "cm     " + goodsWeight + "kg");

        mTvSendName.setText(sendAddress.getName());
        mTvSendPhone.setText(sendAddress.getPhone() + "");

        String[] send_Address = sendAddress.getAddress().split("\\|");
        mTvSendAddress.setText(send_Address[0] + send_Address[1] + send_Address[2]);

        mTvTime.setText(requiredTimeStr);

        mPrice = getIntent().getDoubleExtra("price", 0);
        mTvPrice.setText("¥" + MyNumberFormat.m2(mPrice));
        mDistance = getIntent().getDoubleExtra("distance", 0);
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {


    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {

            case 1:
                String orderNo = AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "orderNo");
                long orderId = AppJsonUtil.getLong(AppJsonUtil.getString(result, "data"), "orderId");

                Bundle bundle = new Bundle();

                bundle.putString("orderNo", orderNo);
                bundle.putLong("orderId", orderId);
                bundle.putDouble("price", mPrice);

                //发送通知清除用户填写的数据
                EventBus.getDefault().post(new CommitOrderEvent());

                startActivity(SendPaymentOrderActivity.class, bundle);

                break;
        }
    }


    @OnClick({R.id.textView})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.textView:

                //提交订单
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Send.class).generateOrder(sendAddress.getName(), sendAddress.getPhone(), sendAddress.getAreaId(),
                        sendAddress.getAddress(), sendAddress.getLng(), sendAddress.getLat(), receiveAddress.getName(), receiveAddress.getPhone(),
                        receiveAddress.getAreaId(), receiveAddress.getAddress(), receiveAddress.getLng(), receiveAddress.getLat(),
                        goodsSize, goodsWeight, goodsType, requiredTime, mPrice, mDistance
                ), 1);


                break;
        }
    }
}
