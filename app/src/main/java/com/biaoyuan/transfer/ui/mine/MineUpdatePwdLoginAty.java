package com.biaoyuan.transfer.ui.mine;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.config.UserManger;
import com.biaoyuan.transfer.http.Mine;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  :修改登录密码第二步
 * Create : 2017/4/27
 * Author ：chen
 */

public class MineUpdatePwdLoginAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_pwd)
    EditText onePwd;
    @Bind(R.id.et_pwd_r)
    EditText twoPwd;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_update_two_login_pwd;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar,"重置登录密码");


    }

    @OnClick({R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_commit:

                String getOnePwd = onePwd.getText().toString().trim();
                String getTwoPwd = twoPwd.getText().toString().trim();
                if (getOnePwd.length() < 8) {
                    showErrorToast(getResources().getString(R.string.pwd_tip));
                    onePwd.requestFocus();
                } else if (getTwoPwd.length() < 8) {
                    showErrorToast(getResources().getString(R.string.r_pwd_tip));
                    twoPwd.requestFocus();
                } else if (!getOnePwd.equals(getTwoPwd)) {
                    showErrorToast(getResources().getString(R.string.pwd_not_same));
                    twoPwd.requestFocus();
                } else {
                    showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(Mine.class).changePwd(getOnePwd), 1);
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
                UserManger.setPwd(onePwd.getText().toString().trim());
                finish();
                break;

        }
    }

    @Override
    public void requestData() {

    }


}
