package com.biaoyuan.transfer.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.http.Mine;
import com.biaoyuan.transfer.util.AppJsonUtil;
import com.biaoyuan.transfer.util.MyNumberFormat;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author ：enmaoFu
 * @title :余额页面
 * @create : 2017/4/27
 */
public class MineBalanceActivity extends BaseAty {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.balance_title)
    TextView balanceTitle;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.frozen_price)
    TextView frozenPrice;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_balance;
    }

    @Override
    public void initData() {
        initToolbar(mToolbar, "余额");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mine_feedback_toolbar_menu, menu);
        menu.getItem(0).setTitle("明细");
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(MineBalanceDetailedActivity.class, null);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean setIsInitRequestData() {
        return true;
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(Mine.class).acount(), 1);
    }

    boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            doHttp(RetrofitUtils.createApi(Mine.class).acount(), 1);
        }

        isFirst = false;
    }

    @OnClick({R.id.recharge, R.id.take})
    @Override
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.recharge:
                startActivity(MineAccountRechargeActivity.class, null);
                break;
            case R.id.take:
                if (!TextUtils.isEmpty(price.getText().toString().trim())) {
                    if (Double.parseDouble(price.getText().toString().trim()) <= 0) {
                        showErrorToast("余额不足，不能提现");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("price", price.getText().toString().trim());
                    startActivity(MineTakeMoneyActivity.class, bundle);
                }


                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:

                String getPrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "accountBalanceBefore")).toString();
                String getFrozenPrice = new StringBuilder().append(AppJsonUtil.getString(AppJsonUtil.getString(result, "data"), "accountMoneyFrozen")).toString();
                price.setText(MyNumberFormat.m2(Double.parseDouble(getPrice)));
                frozenPrice.setText("¥" + MyNumberFormat.m2(Double.parseDouble(getFrozenPrice)));

                break;
        }
    }
}
