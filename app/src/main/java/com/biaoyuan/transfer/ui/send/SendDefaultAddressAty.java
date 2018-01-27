package com.biaoyuan.transfer.ui.send;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  :默认地址
 * Create : 2017/5/24
 * Author ：chen
 */

public class SendDefaultAddressAty extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_default_address;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "默认地址");
    }

    @OnClick({R.id.ll_data})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void requestData() {

    }
}
