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
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :修改手机号第二步
 * Create : 2017/4/27
 * Author ：chen
 */

public class MineUpdatePhoneTwoAty extends BaseCodeAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;


    private MyCount mMyCount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_update_phone;
    }

    @Override
    public void initData() {


        mEtPhone.setHint("请输入您新的手机号码");
        initToolbar(mToolbar, "修改手机号码");

        mTvCommit.setText("确认");

    }

    @OnClick({R.id.tv_commit, R.id.tv_code})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:
                String getInputPhone = mEtPhone.getText().toString().trim();
                String getInputCode = mEtCode.getText().toString().trim();
                if (!MatchStingUtil.isMobile(getInputPhone)) {
                    showErrorToast("请输入正确的手机号");
                    mEtPhone.requestFocus();
                } else if (getInputCode.length() < 4) {
                    showErrorToast("请输入正确的验证码");
                    mEtCode.requestFocus();
                } else {

                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).changePhone(getInputPhone, getInputCode), 2);
                }


                break;
            case R.id.tv_code:
                //发送验证码
                String phone = mEtPhone.getText().toString().trim();
                if (!MatchStingUtil.isMobile(phone)) {
                    showErrorToast("请输入正确的手机号");
                    mEtPhone.requestFocus();
                    return;
                }
                showLoadingDialog("正在发送");
                doHttp(RetrofitUtils.createApi(Mine.class).sendSmsNotSignin(phone), 1);


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
                UserManger.setPhone(mEtPhone.getText().toString().trim());
                UserManger.setAcount(mEtPhone.getText().toString().trim());
                showToast("修改成功");
                Intent intent = getIntent();
                intent.putExtra("phone", mEtPhone.getText().toString().trim());
                setResult(RESULT_OK, intent);
                finish();

                break;
        }
    }


}
