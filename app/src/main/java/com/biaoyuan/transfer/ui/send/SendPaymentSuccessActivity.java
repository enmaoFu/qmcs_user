package com.biaoyuan.transfer.ui.send;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.and.yzy.frame.util.AppManger;
import com.and.yzy.frame.util.DateTool;
import com.biaoyuan.transfer.R;
import com.biaoyuan.transfer.base.BaseAty;
import com.biaoyuan.transfer.ui.MainAty;
import com.biaoyuan.transfer.ui.mine.send.MineSendAllActivity;
import com.biaoyuan.transfer.ui.mine.send.MineSendNoPayAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Title  : 发件->提交订单->支付订单->支付成功页面
 * Create : 2017/5/31
 * Author ：enmaoFu
 */
public class SendPaymentSuccessActivity extends BaseAty {


    @Bind(R.id.tv_number)
    TextView mTvNumber;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_home)
    TextView mTvHome;

    private long orderId;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_payment_success;
    }

    @Override
    public void initData() {

        type = getIntent().getIntExtra("type", 30);

        switch (type) {
            case 10:
                mTvType.setText("支付方式：微信支付");
                break;
            case 20:
                mTvType.setText("支付方式：支付宝支付");
                break;
            case 30:
                mTvType.setText("支付方式：余额支付");
                break;
        }

        mTvNumber.setText("订单编号：" + getIntent().getStringExtra("orderNo"));

        mTvTime.setText("付款时间：" + DateTool.timesToStrTime(getIntent().getLongExtra("time", 0L) + "", "yyyy.MM.dd HH:mm:ss"));


        orderId = getIntent().getExtras().getLong("orderId");


        //关闭上一个界面
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManger.getInstance().killActivity(SendPaymentOrderActivity.class);
            }
        }, 500);

        if (AppManger.getInstance().isAddActivity(MineSendAllActivity.class)) {
            mTvHome.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick({R.id.rl_home, R.id.tv_go_detail})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()) {
            case R.id.rl_home:
                MainAty.radioButtons.get(0).setChecked(true);
                finish();
                break;
            case R.id.tv_go_detail:
                setHasAnimiation(false);
                if (AppManger.getInstance().isAddActivity(MineSendAllActivity.class)) {
                    AppManger.getInstance().killActivity(MineSendNoPayAty.class);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    startActivity(MineSendAllActivity.class, null);
                    finish();
                }


                break;

        }
    }

    @Override
    public boolean setIsInitRequestData() {
        return false;
    }

    @Override
    public void requestData() {
   //     showLoadingContentDialog();
     //   doHttp(RetrofitUtils.createApi(Send.class).pushMsg(orderId), 1);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 1:


                break;
        }
    }
}
