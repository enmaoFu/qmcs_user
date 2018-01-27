package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.util.MatchStingUtil;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseCodeAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.http.Login;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :修改密码第一步
 * Create : 2017/4/27
 * Author ：chen
 */

public class MineUpdatePwdFristAty extends BaseCodeAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.tv_code)
    TextView mTvCode;

    private int mType;


    private MyCount mMyCount;

    @Override
    public int getLayoutId() {
        return  R.layout.activity_mine_update_first_pwd;
    }

    @Override
    public void initData() {
        mType = getIntent().getIntExtra("pwd", 0);



        mEtPhone.setText(MyNumberFormat.toPwdPhone(Long.parseLong(UserManger.getPhone())));
        mEtPhone.setKeyListener(null);

        switch (mType) {
            case 0:
                //登录密码
                initToolbar(mToolbar, "重置登录密码");
                break;
            case 1:
                //支付密码
                //判断是重置还是第一次设置
                boolean isSetting = getIntent().getBooleanExtra("isSetting", false);

                if (!isSetting) {
                    initToolbar(mToolbar, "设置支付密码");
                } else {
                    initToolbar(mToolbar, "重置支付密码");
                }


                break;
        }


    }

    @OnClick({R.id.tv_commit, R.id.tv_code})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:
                String getInputPhone = UserManger.getPhone();
                String getInputCode = mEtCode.getText().toString().trim();
                if (!MatchStingUtil.isMobile(getInputPhone)) {
                    showErrorToast("请输入正确的手机号");
                    mEtPhone.requestFocus();
                } else if (getInputCode.length() < 4) {
                    showErrorToast("请输入正确的验证码");
                    mEtCode.requestFocus();
                } else {
                    //此处由于验证码暂时不能发送成功，所以直接跳转  成功后取消注释
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Login.class).findPasswordVerification(getInputPhone, getInputCode), 2);
                }


                break;
            case R.id.tv_code:
                //发送验证码
                String phone = UserManger.getPhone();
                if (!MatchStingUtil.isMobile(phone)) {
                    showErrorToast("请输入正确的手机号");
                    mEtPhone.requestFocus();
                    return;
                }
                showLoadingDialog("正在发送");
                doHttp(RetrofitUtils.createApi(Login.class).sendSms(phone, "1"), 1);


                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyCount != null) {
            mMyCount.cancel();
        }
    }

    @Override
    public EditText getEditTextView() {
        return mEtCode;
    }

    private class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (mTvCode != null) {
                mTvCode.setEnabled(false);
                mTvCode.setTextColor(getResources().getColor(R.color.font_gray));
                mTvCode.setText(millisUntilFinished / 1000 + " s" + "后重发");
            }

        }

        @Override
        public void onFinish() {
            if (mTvCode != null) {
                mTvCode.setEnabled(true);
                mTvCode.setTextColor(getResources().getColor(R.color.colorAccent));
                mTvCode.setText("获取验证码");
            }

        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                if (mMyCount == null) {
                    mMyCount = new MyCount(60000, 1000);
                }
                mMyCount.start();
                showToast(AppJsonUtil.getString(result, "msg"));
                break;
            case 2:
                if (getIntent().getIntExtra("pwd", 0) == 0) {
                    startActivityForResult(MineUpdatePwdLoginAty.class, getIntent().getExtras(), 1);
                } else {
                    startActivityForResult(MineUpdatePwdTwoAty.class, getIntent().getExtras(), 1);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
