package com.biaoyuan.transfer.ui.mine;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author ：enmaoFu
 * @title :充值成功页面
 * @create : 2017/4/27
 */
public class MineRechargeSuccessActivity extends BaseAty {

    @Bind(R.id.tv_finish)
    TextView mTvFinish;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_code)
    TextView mTvCode;
    @Bind(R.id.tv_type)
    TextView mTvType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_recharge_success;
    }

    @Override
    public void initData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(MineAccountRechargeActivity.class);
            }
        }, 800);

        mTvPrice.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getIntent().getStringExtra("price"))));
        mTvCode.setText(getIntent().getStringExtra("code"));
        mTvType.setText(getIntent().getStringExtra("type"));


    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_finish})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                finish();
                break;
        }
    }
}
