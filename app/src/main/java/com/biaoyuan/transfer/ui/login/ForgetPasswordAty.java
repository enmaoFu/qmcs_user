package com.biaoyuan.transfer.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  : 忘记密码页面
 * Create : 2017/5/22
 * Author ：enmaoFu
 */
public class ForgetPasswordAty extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initData() {
        initToolbar(toolbar,"忘记密码");
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_commit})
    @Override
    public void btnClick(View view) {
        switch (view.getId()){
            case R.id.tv_commit:
                startActivity(ResetPasswordActvity.class,null);
                break;
        }
    }
}
