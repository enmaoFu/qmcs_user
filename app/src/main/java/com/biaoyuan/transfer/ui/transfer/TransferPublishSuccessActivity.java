package com.biaoyuan.transfer.ui.transfer;

import android.os.Handler;
import android.view.View;

import com.and.yzy.frame.util.AppManger;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.ui.mine.MineTripActivity;

import butterknife.OnClick;

/**
 * Title  : 发布成功页面
 * Create : 2017/5/31
 * Author ：enmaoFu
 */
public class TransferPublishSuccessActivity extends BaseAty {


    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_transfer_success;
    }

    @Override
    public void initData() {

        //关闭上一个界面
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(TransferReleaseTripActivity.class);
            }
        },500);



    }

    @OnClick({R.id.rl_home, R.id.tv_go_detail})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.rl_home:
                finish();
                break;
            case R.id.tv_go_detail:
                setHasAnimiation(false);
                startActivity(MineTripActivity.class, null);
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
