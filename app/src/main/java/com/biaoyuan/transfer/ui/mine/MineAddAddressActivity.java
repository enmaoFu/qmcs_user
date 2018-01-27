package com.biaoyuan.transfer.ui.mine;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;

import butterknife.Bind;

/**
 * Title  :添加新收件人
 * Create : 2017/4/26
 * Author ：chen
 */

public class MineAddAddressActivity extends BaseAty {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_add_address;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar,"添加新收件人");

    }

    @Override
    public void requestData() {

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_feedback_toolbar_menu,menu);
        menu.getItem(0).setTitle("保存");
        return super.onCreateOptionsMenu(menu);
    }
}
