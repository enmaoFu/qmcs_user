package com.biaoyuan.transfer.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :密码设置
 * Create : 2017/4/27
 * Author ：chen
 */

public class MinePwdActivity extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_pwd;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "密码设置");
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_pay_pwd, R.id.tv_login_pwd})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_login_pwd:

                bundle.putInt("pwd", 0);
                bundle.putString("phone", getIntent().getStringExtra("phone"));
                startActivityForResult(MineUpdatePwdFristAty.class, bundle,1);

                break;
            case R.id.tv_pay_pwd:
                bundle.putInt("pwd", 1);
                startActivity(MineUpdatePwdFristAty.class, bundle);
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
}
