package com.biaoyuan.transfer.ui.mine.send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.Arith;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.MineSendOrderDetail;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.ui.send.SendPaymentOrderActivity;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :我的发件待支付详情
 * Create : 2017/6/21
 * Author ：chen
 */

public class MineSendNoPayAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.ll_bg)
    LinearLayout mLlBg;
    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.phone)
    TextView mPhone;
    @Bind(R.id.tv_pay_price)
    TextView mTvPayPrice;
    @Bind(R.id.tv_tip)
    TextView mTvTip;
    @Bind(R.id.address)
    TextView mAddress;
    @Bind(R.id.tv_city)
    TextView mTvCity;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_comiit_time)
    TextView mTvComiitTime;
    @Bind(R.id.textView3)
    TextView mTextView3;
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
    @Bind(R.id.textView5)
    TextView mTextView5;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_hase_price)
    TextView mTvHasePrice;
    @Bind(R.id.rl_pay)
    RelativeLayout mRlPay;
    @Bind(R.id.tv_add_price)
    TextView mTvAddPrice;
    @Bind(R.id.rl_add)
    RelativeLayout mRlAdd;
    @Bind(R.id.view)
    View mView;
    @Bind(R.id.tv_need_pay)
    TextView mTvNeedPay;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;
    @Bind(R.id.ll_btn)
    LinearLayout mLlBtn;
    @Bind(R.id.ll_add)
    LinearLayout mLlAdd;
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.tv_right)
    TextView mTvRight;

    private int payStatus;
    private long orderId;
    MineSendOrderDetail mOrderDetail;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_send_no_pay;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "待支付");
        payStatus = getIntent().getIntExtra("payStatus", 0);
        orderId = getIntent().getLongExtra("orderId", 0);

        switch (payStatus) {
            case 0:
                //未支付
                mLlBtn.setVisibility(View.VISIBLE);
                mRlAdd.setVisibility(View.GONE);
                mRlPay.setVisibility(View.GONE);
                mLlAdd.setVisibility(View.GONE);


                break;
            case 1:
                //部分支付
                mLlBtn.setVisibility(View.GONE);
                mRlAdd.setVisibility(View.VISIBLE);
                mRlPay.setVisibility(View.VISIBLE);
                mLlAdd.setVisibility(View.VISIBLE);

                break;
        }
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @OnClick({R.id.tv_commit,R.id.tv_left,R.id.tv_right})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch(view.getId()){
            case R.id.tv_commit:
            case R.id.tv_right:
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", mOrderDetail.getOrderNo());
                bundle.putLong("orderId", mOrderDetail.getOrderId());
                if (payStatus==0){
                    bundle.putDouble("price", mOrderDetail.getOrderTotalPrice());
                }else {
                    bundle.putDouble("price", mOrderDetail.getOrderPriceAddition());
                    bundle.putLong("addtionId",getIntent().getLongExtra("addtionId",0));
                }

                startActivityForResult(SendPaymentOrderActivity.class, bundle,1);

                break;
            case R.id.tv_left:
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==RESULT_OK){
            finish();
        }
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
                mAddress.setText(s);

                mTvCode.setText("订单编号：" + mOrderDetail.getOrderNo());
                mTvComiitTime.setText("下单时间：" + DateTool.timesToStrTime(mOrderDetail.getOrderCreatTime() + "", "yyyy.MM.dd HH:mm"));

                switch (mOrderDetail.getOrderGoodsType()) {
                    case 1:
                        mTvType.setText("文件");
                        break;
                    case 2:
                        mTvType.setText("办公/居家");
                        break;
                    case 3:
                        mTvType.setText("鲜花");
                        break;
                    case 4:
                        mTvType.setText("包裹");
                        break;
                    case 5:
                        mTvType.setText("蛋糕");
                        break;
                }
                mTvSize.setText("最长边≤"+mOrderDetail.getOrderGoodsSize()+"cm  "+mOrderDetail.getOrderGoodsWeight()+"kg");

                mTvSendName.setText(mOrderDetail.getOrderSenderName());
                mTvSendAddress.setText(mOrderDetail.getOrderSendAddr().replace("|",""));
                mTvSendPhone.setText(mOrderDetail.getOrderSenderTel()+"");

                String time=DateTool.getTimeType(mOrderDetail.getOrderRequiredTime());
                if (time==null){
                    mTvTime.setText(DateTool.timesToStrTime(mOrderDetail.getOrderRequiredTime()+"","yyyy.MM.dd HH:mm")+"之前");
                }else {
                    mTvTime.setText(time+DateTool.timesToStrTime(mOrderDetail.getOrderRequiredTime()+"","HH:mm")+"之前");
                }

                mTvPrice.setText("¥"+ MyNumberFormat.m2(mOrderDetail.getOrderTotalPrice()));

                switch(payStatus){
                    case 0:
                        //未支付
                        mTvPayPrice.setText("¥"+ MyNumberFormat.m2(mOrderDetail.getOrderTotalPrice()));


                        break;
                    case 1:
                        //部分支付
                        mTvPayPrice.setText("¥"+ MyNumberFormat.m2(mOrderDetail.getOrderPriceAddition()));
                        mTvAddPrice.setText("¥"+ MyNumberFormat.m2(mOrderDetail.getOrderPriceAddition()));
                        mTvNeedPay.setText("¥"+ MyNumberFormat.m2(mOrderDetail.getOrderPriceAddition()));

                        mTvHasePrice.setText("¥"+ MyNumberFormat.m2(Arith.sub(mOrderDetail.getOrderTotalPrice(),mOrderDetail.getOrderPriceAddition())));

                        break;
                }




                break;
            case 2:
                showToast("取消成功");
                finish();
                break;
        }
    }
}
