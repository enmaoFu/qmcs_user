package com.biaoyuan.transfer.ui.mine;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @title  :余额提现结果页面
 * @create : 2017/4/27
 * @author  ：enmaoFu
 */
public class MineTakeMoneyResultActivity extends BaseAty {

    @Bind(R.id.money)
    TextView money;

    private String getInputMoney;

    @Override
    public int getLayoutId() {
        return R.layout.activity_take_money_result;
    }

    @Override
    public void initData() {
        Handler Handler=new Handler();

        Handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(MineTakeMoneyActivity.class);
            }
        },500);

        getInputMoney = getIntent().getExtras().getString("getInputMoney");
        money.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getInputMoney)));
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.messgae})
    @Override
    public void btnClick(View view) {
       switch (view.getId()){
           case R.id.messgae:
               finish();
               break;
       }
    }
}
