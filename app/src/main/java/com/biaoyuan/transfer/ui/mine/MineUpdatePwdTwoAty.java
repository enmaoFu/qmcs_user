package com.biaoyuan.transfer.ui.mine;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.http.Send;
import com.biaoyuan.transfer.util.Encryption;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :修改密码第二步
 * Create : 2017/4/27
 * Author ：chen
 */

public class MineUpdatePwdTwoAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_pwd)
    EditText onePwd;
    @Bind(R.id.et_pwd_r)
    EditText twoPwd;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_update_two_pwd;
    }

    @Override
    public void initData() {

        //支付密码
        boolean isSetting = getIntent().getBooleanExtra("isSetting", false);
        if (!isSetting) {
            initToolbar(mToolbar, "设置支付密码");
            onePwd.setHint("请设置您的支付密码");

        } else {
            initToolbar(mToolbar, "重置支付密码");
            onePwd.setHint("请设置您新的支付密码");

        }


    }

    @OnClick({R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:

                String getOnePwd = onePwd.getText().toString().trim();
                String getTwoPwd = twoPwd.getText().toString().trim();

                if (getOnePwd.length() < 6) {
                    showErrorToast(getResources().getString(R.string.pay_pwd_tip));
                    onePwd.requestFocus();
                } else if (getTwoPwd.length() < 6) {
                    showErrorToast(getResources().getString(R.string.r_pay_pwd_tip));
                    twoPwd.requestFocus();
                } else if (!getOnePwd.equals(getTwoPwd)) {
                    showErrorToast(getResources().getString(R.string.pwd_not_same));
                    twoPwd.requestFocus();
                } else {
                    showLoadingDialog(null);
                    try {
                        doHttp(RetrofitUtils.createApi(Send.class).setPayPassword(Encryption.encode(getOnePwd)), 1);
                    } catch (Exception e) {
                        e.printStackTrace();
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
                showToast("设置成功");
                setResult(RESULT_OK);
                finish();
                break;

        }
    }

    @Override
    public void requestData() {

    }


}
