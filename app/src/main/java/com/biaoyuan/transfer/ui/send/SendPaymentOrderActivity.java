package com.biaoyuan.transfer.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.domain.PayOrderResult;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.ui.mine.MineUpdatePwdFristAty;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.Encryption;
import com.biaoyuan.transfer.util.MyNumberFormat;
import com.biaoyuan.transfer.util.SystemUtils;
import com.biaoyuan.transfer.view.keyboardpwd.PassValitationPopwindow;
import com.biaoyuan.transfer.wxapi.WXPay;
import com.biaoyuan.transfer.wxapi.WxParams;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 发件-》提交订单->支付订单页面
 * Create : 2017/5/31
 * Author ：enmaoFu
 */
public class SendPaymentOrderActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.text_but)
    TextView textBut;
    @Bind(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.rb_yue)
    RadioButton mRbYue;
    @Bind(R.id.rb_weixin)
    RadioButton mRbWeixin;
    @Bind(R.id.rb_zfb)
    RadioButton mRbZfb;
    @Bind(R.id.rg_mian)
    RadioGroup mRgMian;

    //密码输入
    private PassValitationPopwindow mPopup;

    private long orderId;
    private String orderNo;
    private double price;

    //支付类型 0：余额支付  1：微信支付  2：支付宝支付  默认为余额支付
    private int paymentType = 0;


    //判断是否点了了支付按钮
    private boolean isCheck = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_payment_order;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "支付订单");


        //关闭上一个界面
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(SendSubmitOrderActivity.class);
            }
        }, 500);


        mPopup = new PassValitationPopwindow(this, textBut, new PassValitationPopwindow.OnInputNumberCodeCallback() {
            @Override
            public void onSuccess(String numberString) {
                /*showToast(numberString);
                startActivity(SendPaymentSuccessActivity.class, null);*/

                showLoadingDialog(null);
                textBut.setEnabled(false);
                try {
                    Logger.v("调用余额支付接口");

                    //判断是否是部分支付
                    if (getIntent().getLongExtra("addtionId", 0) == 0) {
                        doHttp(RetrofitUtils.createApi(Send.class).payOrder(Encryption.encode(numberString), orderId, price, 1), 2);
                    } else {
                        doHttp(RetrofitUtils.createApi(Send.class).payOrder(Encryption.encode(numberString), orderId, price, 2), 2);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        //忘记密码
        mPopup.getForget_pwd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();

                //先判断是否设置了支付密码
                doHttp(RetrofitUtils.createApi(Send.class).getPayPassword(), 3);

            }
        });


        orderId = getIntent().getLongExtra("orderId", 0L);
        orderNo = getIntent().getStringExtra("orderNo");
        price = getIntent().getDoubleExtra("price", 0);


        mTvOrderNumber.setText(orderNo);
        mTvPrice.setText("¥" + MyNumberFormat.m2(price));

        textBut.setText("确认支付" + "¥" + MyNumberFormat.m2(price));

        SpannableString weixin = new SpannableString("微信支付\n推荐安装微信5.0以上版本的用户使用");
        weixin.setSpan(new TextAppearanceSpan(this, R.style.style333), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        weixin.setSpan(new TextAppearanceSpan(this, R.style.styleGray), 4, weixin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mRbWeixin.setText(weixin, TextView.BufferType.SPANNABLE);
        SpannableString zfb = new SpannableString("支付宝支付\n推荐有支付宝账号的用户使用");
        zfb.setSpan(new TextAppearanceSpan(this, R.style.style333), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        zfb.setSpan(new TextAppearanceSpan(this, R.style.styleGray), 5, zfb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mRbZfb.setText(zfb, TextView.BufferType.SPANNABLE);


    }

    boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        Logger.v("onResume");
        if (!isFirst && isCheck) {
            //查询交易结果
            if (paymentType == 1) {
                if (getIntent().getLongExtra("addtionId", 0) != 0) {
                    //部分支付查询
                    doHttp(RetrofitUtils.createApi(Mine.class).selectWeChatOrderByAdditionalId(getIntent().getLongExtra("addtionId", 0)), 5);
                } else {
                    doHttp(RetrofitUtils.createApi(Mine.class).selectWeChatOrderByOrderId(orderId), 5);
                }

            }
        }

        isFirst = false;
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.text_but})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.text_but:
                //防止误点两次
                if (isFastDoubleClick()) {
                    return;
                }

                //判断余额支付
                //先判断是否设置了支付密码
                if (mRbYue.isChecked()) {
                    paymentType = 0;
                    doHttp(RetrofitUtils.createApi(Send.class).getPayPassword(), 1);
                }
                if (mRbWeixin.isChecked()) {
                    //判断是否安装了微信
                    if (SystemUtils.isWeixinAvilible(this)) {
                        paymentType = 1;
                        //        showLoadingDialog(null);
                        textBut.setEnabled(false);
                        //判断是否是部分支付
                        if (getIntent().getLongExtra("addtionId", 0) != 0) {
                            doHttp(RetrofitUtils.createApi(Mine.class).weChatOrderPaymentAdditional(getIntent().getLongExtra("addtionId", 0)), 4);

                        } else {
                            doHttp(RetrofitUtils.createApi(Mine.class).weChatOrderPayment(orderId), 4);
                        }


                    } else {
                        showErrorToast("请先安装微信客户端");
                    }

                }
                if (mRbZfb.isChecked()) {
                    paymentType = 2;
                    Logger.v("选择了支付宝支付");

                }
                break;
        }
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                boolean isSetting = AppJsonUtil.getBoolean(result, "data");

                Logger.v("isSetting=" + isSetting);
                if (isSetting) {
                    mPopup.showDialog();
                } else {
                    Bundle bundle = new Bundle();

                    bundle.putBoolean("isSetting", isSetting);
                    //支付密码类型
                    bundle.putInt("pwd", 1);
                    startActivityForResult(MineUpdatePwdFristAty.class, bundle, 1);

                }


                break;
            case 2:

                showToast("支付成功");

                PayOrderResult orderResult = AppJsonUtil.getMyObject(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "order"), PayOrderResult.class);

                Bundle bundle = new Bundle();
                bundle.putString("orderNo", orderResult.getOrderNo());
                bundle.putLong("orderId", orderResult.getOrderId());
                bundle.putLong("time", orderResult.getPayTime());
                bundle.putInt("type", orderResult.getPayType());
                startActivity(SendPaymentSuccessActivity.class, bundle);


                break;
            case 3:

                boolean isSet = AppJsonUtil.getBoolean(result, "data");
                //忘记密码
                Bundle bun = new Bundle();
                bun.putBoolean("isSetting", isSet);
                //支付密码类型
                bun.putInt("pwd", 1);
                startActivityForResult(MineUpdatePwdFristAty.class, bun, 1);
                break;
            case 4:

                showLoadingDialog(null);
                isCheck = true;

                WxParams wxParams = AppJsonUtil.getObject(result, WxParams.class);
                WXPay wxPay = new WXPay();

                wxPay.pay(wxParams);

                break;
            case 5:

                isCheck = false;
                String state = AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "trade_state");

                switch (state) {
                    case "SUCCESS":
                        showToast("支付成功");

                        Bundle b = new Bundle();
                        b.putString("orderNo", AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "out_trade_no"));
                        b.putLong("orderId", orderId);
                        b.putLong("time", DateTool.strTimeToTimestamp(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "time_end"), "yyyyMMddHHmmss"));
                        b.putInt("type", 10);
                        startActivity(SendPaymentSuccessActivity.class, b);


                        break;
                    default:
                        textBut.setEnabled(true);
                        showErrorToast("支付已取消");

                        break;
                }


                break;


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mPopup.showDialog();
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what) {

            case 1:
            case 2:
            case 4:
                textBut.setEnabled(true);
                break;
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        textBut.setEnabled(true);
        switch (what) {
            case 5:
                isCheck = false;
                break;
            case 1:
            case 2:
            case 4:
                showErrorToast("网络超时，支付失败");
                finish();
                break;
        }
    }

    /**
     * 防止多次点击
     *
     * @return
     */
    long lastClickTime;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
