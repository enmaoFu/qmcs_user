package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.AppUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.ui.WebViewActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  : 关于我们
 * Create : 2017/07/08
 * Author ：enmaoFu
 */

public class MineAboutWeActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.agreement)
    TextView agreement;
    @Bind(R.id.tv_code)
    TextView mTvCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_about_we;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "关于我们");

        mTvCode.setText("版本号v"+ AppUtils.getVersionName(this));

    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.agreement})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.agreement:
                Bundle bundle = new Bundle();
                bundle.putInt("type", WebViewActivity.TYPE_AGREEMENT);
                startActivity(WebViewActivity.class, bundle);
                break;
        }
    }
}
