package com.biaoyuan.transfer.ui.transfer;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.and.yzy.frame.util.AppManger;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.ui.MainAty;
import com.biaoyuan.transfer.ui.mine.transfer.MineTransferActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Title  : 抢单成功页面
 * Create : 2017/5/31
 * Author ：enmaoFu
 */
public class TransferGetSuccessActivity extends BaseAty {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_transfer_success;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "抢单成功");
        //关闭上一个界面
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(TransferOrderDetailsActivity.class);
            }
        },500);


    }

    @OnClick({R.id.tv_go_detail})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.tv_go_detail:
                MainAty.radioButtons.get(0).setChecked(true);
               setHasAnimiation(false);
                startActivity(MineTransferActivity.class,null);
                finish();
                break;

        }
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {

    }
}
