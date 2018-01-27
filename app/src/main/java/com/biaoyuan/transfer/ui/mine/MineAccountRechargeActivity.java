package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.SystemUtils;
import com.biaoyuan.transfer.wxapi.WXPay;
import com.biaoyuan.transfer.wxapi.WxParams;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author ：enmaoFu
 * @title :账户充值页面
 * @create : 2017/4/27
 */
public class MineAccountRechargeActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.input_money)
    EditText inputMoney;
    @Bind(R.id.text_but)
    TextView textBut;
    @Bind(R.id.rb_weixin)
    RadioButton mRbWeixin;
    @Bind(R.id.rb_zfb)
    RadioButton mRbZfb;


    //支付类型 0：余额支付  1：微信支付  2：支付宝支付
    private int paymentType = 1;


    //判断是否点了了支付按钮
    private boolean isCheck = false;

    private long paymentId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_account_recharge;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "账户充值");

        SpannableString weixin = new SpannableString("微信支付\n推荐安装微信5.0以上版本的用户使用");
        weixin.setSpan(new TextAppearanceSpan(this, R.style.style333), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        weixin.setSpan(new TextAppearanceSpan(this, R.style.styleGray), 4, weixin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRbWeixin.setText(weixin, TextView.BufferType.SPANNABLE);

        SpannableString zfb = new SpannableString("支付宝支付\n推荐有支付宝账号的用户使用");
        zfb.setSpan(new TextAppearanceSpan(this, R.style.style333), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        zfb.setSpan(new TextAppearanceSpan(this, R.style.styleGray), 5, zfb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRbZfb.setText(zfb, TextView.BufferType.SPANNABLE);


        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int len = s.toString().length();
                if (len == 1 && text.equals("0")) {
                    s.clear();
                }
            }
        });
    }

    boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        Logger.v("onResume");
        if (!isFirst && isCheck) {
            //查询交易结果
            if (paymentType == 1) {
                doHttp(RetrofitUtils.createApi(Mine.class).selectWeChatOrderByPaymentId(paymentId), 2);
            }
        }

        isFirst = false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.text_but})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.text_but:

                String moneyStr = inputMoney.getText().toString().trim();

                if (moneyStr.length() == 0) {
                    showErrorToast("请输入充值金额");
                    return;
                }

                if (mRbWeixin.isChecked()) {
                    //判断是否安装了微信
                    if (SystemUtils.isWeixinAvilible(this)) {
                        textBut.setEnabled(false);
                        paymentType = 1;
                        //       showLoadingDialog(null);

                        doHttp(RetrofitUtils.createApi(Mine.class).wechatrecharge(Long.parseLong(UserManger.getUUid()), Integer.parseInt(moneyStr)), 1);


                    } else {
                        showErrorToast("请先安装微信客户端");
                    }


                }


                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                isCheck = true;
                showLoadingDialog(null);
                WxParams wxParams = AppJsonUtil.getObject(result, WxParams.class);
                WXPay wxPay = new WXPay();
                wxPay.pay(wxParams);
                paymentId = wxParams.getPaymentId();

                break;
            case 2:

                isCheck = false;
                String state = AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "trade_state");

                switch (state) {
                    case "SUCCESS":
                        showToast("支付成功");

                        Bundle bundle = new Bundle();
                        bundle.putString("code", AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "out_trade_no"));
                        bundle.putString("price", inputMoney.getText().toString().trim());
                        bundle.putString("type", "微信支付");

                        startActivity(MineRechargeSuccessActivity.class, bundle);
                        textBut.setEnabled(true);

                        break;
                    default:

                        showErrorToast("支付已取消");
                        textBut.setEnabled(true);
                        break;
                }

                break;
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        isCheck = true;
        switch (what) {
            case 2:
                textBut.setEnabled(true);
                break;

        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        isCheck = true;
    }
}
