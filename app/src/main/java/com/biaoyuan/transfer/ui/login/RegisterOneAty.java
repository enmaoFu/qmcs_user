package com.biaoyuan.transfer.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.and.yzy.frame.util.MatchStingUtil;
import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseCodeAty;
import com.biaoyuan.transfer.http.Login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 用户注册第一步页面
 * Create : 2017/5/23
 * Author ：enmaoFu
 */
public class RegisterOneAty extends BaseCodeAty{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.input_user)
    EditText mInputUser;
    @Bind(R.id.input_phone)
    EditText mInputPhone;
    @Bind(R.id.input_validate)
    EditText mInputValidate;
    @Bind(R.id.get_validate)
    TextView getValidateCode;


    private MyCount mMyCount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_one;
    }


    @Override
    public void initData() {
        initToolbar(mToolbar, "注册新用户");



    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.exit, R.id.get_validate, R.id.tv_login})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.exit:
                String getUser = mInputUser.getText().toString().trim();
                String getPhone = mInputPhone.getText().toString().trim();
                String getCode = mInputValidate.getText().toString().trim();
                if (getUser.length()<3) {
                    showErrorToast("用户名至少为3位");
                    mInputUser.requestFocus();
                    return;
                }
                if (isNumeric(getUser)) {
                    showErrorToast("用户名不能为纯数字");
                    mInputUser.requestFocus();
                    return;
                }
                if (!MatchStingUtil.isMobile(getPhone)) {
                    showErrorToast("请输入正确的手机号");
                    mInputPhone.requestFocus();
                    return;
                }
                if (getCode.length() < 4) {
                    showErrorToast("验证码长度不对");
                    mInputValidate.requestFocus();
                    return;
                }

                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Login.class).mobileVerification(getUser, getPhone, getCode), 2);


                break;
            case R.id.get_validate:


                //发送验证码
                String phone = mInputPhone.getText().toString().trim();
                if (!MatchStingUtil.isMobile(phone)) {
                    showErrorToast("请输入正确的手机号");
                    mInputPhone.requestFocus();
                    return;
                }

                showLoadingDialog("正在发送");
                doHttp(RetrofitUtils.createApi(Login.class).sendSms(phone, "0"), 1);


                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:
                //发送验证码成功
                if (mMyCount == null) {
                    mMyCount = new MyCount(60000, 1000);
                }
                mMyCount.start();
                showToast("发送成功");

                break;
            case 2:
                //验证成功进入下一步
                String getUser = mInputUser.getText().toString().trim();
                String getPhone = mInputPhone.getText().toString().trim();

                Bundle bundle = new Bundle();
                bundle.putString("name", getUser);
                bundle.putString("phone", getPhone);
                startActivity(RegisterTwoAty.class, bundle);

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
        return mInputValidate;
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
            if (getValidateCode != null) {
                getValidateCode.setEnabled(false);
                getValidateCode.setTextColor(getResources().getColor(R.color.font_gray));
                getValidateCode.setText(millisUntilFinished / 1000 + " s" + "后重发");
            }

        }

        @Override
        public void onFinish() {
            if (getValidateCode != null) {
                getValidateCode.setEnabled(true);
                getValidateCode.setTextColor(getResources().getColor(R.color.colorAccent));
                getValidateCode.setText("获取验证码");
            }

        }
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
